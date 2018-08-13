package com.lmt.mbsp.user.front.biz.impl;

import com.lmt.framework.core.exception.BusinessException;
import com.lmt.framework.utils.RandomUtil;
import com.lmt.framework.utils.StringUtils;
import com.lmt.framework.utils.encrypt.MD5Utils;
import com.lmt.framework.utils.random.RandomUtils;
import com.lmt.mbsp.user.adapter.service.CaptchaClient;
import com.lmt.mbsp.user.adapter.service.SenderClient;
import com.lmt.mbsp.user.adapter.service.ValidateClient;
import com.lmt.mbsp.user.entity.user.User;
import com.lmt.mbsp.user.entity.user.UserAccount;
import com.lmt.mbsp.user.front.biz.AccountBiz;
import com.lmt.mbsp.user.front.biz.RegistBiz;
import com.lmt.mbsp.user.entity.account.Account;
import com.lmt.mbsp.user.entity.account.AccountName;
import com.lmt.mbsp.user.service.AccountNameService;
import com.lmt.mbsp.user.service.AccountService;
import com.lmt.mbsp.user.service.UserAccountService;
import com.lmt.mbsp.user.service.UserService;
import com.lmt.mbsp.user.vo.regist.*;
import org.apache.ibatis.builder.BuilderException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Random;

/**
 * @Auther: lex
 * @Date: 2018/7/31 下午1:41
 * @Description:
 */
@Service
public class RegistBizImpl implements RegistBiz {

    private final CaptchaClient captchaClient;
    private final SenderClient senderClient;
    private final ValidateClient validateClient;

    private final AccountService accountService;
    private final AccountNameService accountNameService;
    private final UserService userService;
    private final UserAccountService userAccountService;

    private final AccountBiz accountBiz;

    @Autowired
    public RegistBizImpl(CaptchaClient captchaClient, SenderClient senderClient, ValidateClient validateClient, AccountService accountService, AccountNameService accountNameService, UserService userService, UserAccountService userAccountService, AccountBiz accountBiz) {
        this.captchaClient = captchaClient;
        this.senderClient = senderClient;
        this.validateClient = validateClient;
        this.accountService = accountService;
        this.accountNameService = accountNameService;
        this.userService = userService;
        this.userAccountService = userAccountService;
        this.accountBiz = accountBiz;
    }

    @Override
    public Long regist(RegistInfo info) throws Exception {
        Long result = -1L;
        switch (info.getRegistWay()) {
            case USERNAME:
                result = registByUserName((RegistUserNameInfo) info);
                break;
            case MOBILE:
                result = registByMobile((RegistMobileInfo) info);
                break;
            case EMAIL:
                result = registByEmail((RegistEmailInfo) info);
                break;
            case OTHER:
                // TODO 第三方注册（扩展点）
                break;
        }
        return result;
    }

    @Override
    public Boolean sendSms(String mobile) throws Exception {
        String code = validateClient.createCode(mobile);
        String message = String.format("您的注册验证码：%s，五分钟内有效", code);
        ResponseEntity response = senderClient.sendSms(mobile, message);
        System.out.println("sms = [" + message + "]");
        return response.getStatusCode().is2xxSuccessful();
    }

    @Override
    public Boolean verifySms(String mobile, String code) throws Exception {
        return validateClient.verifyCode(mobile, code);
    }

    /**
     * 主要方式
     *
     * @param info
     * @return 注册的新AccountId
     * @throws Exception
     */
    private Long registByMobile(RegistMobileInfo info) throws Exception {
        if (accountBiz.checkMobile(info.getMobile())) {
            throw new BusinessException(RegistErrorInfo.EXIST_MOBILE.name());
        }
        // 手机短信验证码验证
        if (!validateClient.verifyCode(info.getMobile(), info.getVerifyCode())) {
            throw new BusinessException(RegistErrorInfo.WRONG_VERIFYCODE_MOBILE.name());
        }
        // 手机注册或别的情况，自动设置一个密码
        String randomPwd = RandomUtil.randomChar(6);
        return createNewAccount(info.getMobile(), randomPwd, RegistWay.MOBILE);
    }

    private Long registByUserName(RegistUserNameInfo info) throws Exception {
        if (StringUtils.isNullOrEmpty(info.getUsername()) || StringUtils.isNullOrEmpty(info.getPassword())) {
            throw new BusinessException(RegistErrorInfo.USERNAME_AND_PASSWORD.name());
        }
        // 检查两次密码是否一致
        if (!info.getPassword().equals(info.getPwdAgain())) {
            throw new BusinessException(RegistErrorInfo.DIFF_PASSWORD.name());
        }
        // 检查账号是否存在
        if (accountBiz.checkUserName(info.getUsername())) {
            throw new BuilderException(RegistErrorInfo.EXIST_USERNAME.name());
        }
        // 图片验证码与session进行验证
        if (!captchaClient.verifyImage(info.getImageVerifyCode())) {
            throw new BuilderException(RegistErrorInfo.WRONG_VERIFYCODE_IMAGE.name());
        }
        return createNewAccount(info.getUsername(), info.getPassword(), RegistWay.USERNAME);
    }

    private Long registByEmail(RegistEmailInfo info) throws Exception {
        return null;
        // TODO EMAIL注册
//        return createNewAccount(info);
    }

    private Long createNewAccount(String accountName, String password, RegistWay registWay) throws Exception {
        // 保存账号
        Long accId = accountService.insert(assembleAccount(getAccountName(), password));
        // 保存主账号名称
        accountNameService.insert(assembleAccName(accId, accountName, registWay.ordinal()));
        // 注册是默认添加一个空用户
        Date createDate = new Date();
        User user = new User();
        user.setCreateTime(createDate);
        Long userId = userService.insert(user);
        UserAccount userAccount = new UserAccount();
        userAccount.setAccountId(accId);
        userAccount.setUserId(userId);
        userAccount.setCreateTime(createDate);
        userAccountService.insert(userAccount);

        return accId;
    }

    /**
     * 根据注册信息组装保存账号需要的对象并返回
     *
     * @param accountName 账号名称
     * @param password    密码
     * @return Account
     */
    private Account assembleAccount(String accountName, String password) {
        Account acc = new Account();
        acc.setAccountName(accountName);
        acc.setPassword(MD5Utils.generatePassword(password));
        acc.setCreateTime(new Date());
        acc.setManagerType(0);
        acc.setMaster(0);
        acc.setState(0);
        acc.setLockTimes(0);
        acc.setIsLock(false);
        acc.setRegisterType(0);
        return acc;
    }

    /**
     * 组装账号密码对象信息并返回
     *
     * @param accountId   账号ID
     * @param accountName 用户名
     * @param type        类型（0主账号，1手机，2邮箱）
     * @return AccountName
     */
    private AccountName assembleAccName(Long accountId, String accountName, Integer type) {
        AccountName name = new AccountName();
        name.setAccountId(accountId);
        name.setAccountName(accountName);
        name.setType(type);
        return name;
    }

    /**
     * id唯一值生成工具类<br>
     * 格式：AN_xxxx，总共16位随机码
     *
     * @return AN_+8位随机码
     */
    private String getAccountName() {
        // 前缀
        String prefix = "AN_";
        // 随机码
        String main = RandomUtils.getRandomId(new Random().nextInt(1000));
        String end = RandomUtils.getRandomAlphamericStrNew(4);

        return prefix + main + end;
    }
}
