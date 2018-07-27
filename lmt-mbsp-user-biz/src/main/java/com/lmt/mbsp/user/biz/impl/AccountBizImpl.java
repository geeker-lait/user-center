package com.lmt.mbsp.user.biz.impl;

import com.lmt.framework.core.exception.BusinessException;
import com.lmt.mbsp.user.biz.AccountBiz;
import com.lmt.mbsp.user.biz.message.CaptchaService;
import com.lmt.mbsp.user.biz.message.SenderService;
import com.lmt.mbsp.user.biz.message.ValidateService;
import com.lmt.mbsp.user.dto.AccountNameQuery;
import com.lmt.mbsp.user.entity.account.Account;
import com.lmt.mbsp.user.entity.account.AccountName;
import com.lmt.mbsp.user.service.AccountNameService;
import com.lmt.mbsp.user.service.AccountService;
import com.lmt.mbsp.user.vo.*;
import org.apache.ibatis.builder.BuilderException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * 描述: 账号聚合服务.
 * 作者: Tangsm.
 * 创建时间: 2018-06-27 17:18.
 */
@Service("accountBiz")
public class AccountBizImpl implements AccountBiz {
    @Autowired
    private AccountService accountService;
    // private final AccountPasswordService accountPasswordService;
    //@Autowired
    //private ValidateService validateService;
    //@Autowired
    //private CaptchaService captchaService;
    //@Autowired
    //private SenderService senderService;
    @Autowired
    private AccountNameService accountNameService;
//    private ValidateService validateService;
//    private CaptchaService captchaService;
//    private SenderService senderService;


/*    @Autowired
    public AccountBizImpl(AccountService accountService, AccountNameService accountNameService,
                          ValidateService validateService, CaptchaService captchaService, SenderService senderService) {
        this.accountService = accountService;
        this.accountNameService = accountNameService;
        this.validateService = validateService;
        this.captchaService = captchaService;
        this.senderService = senderService;
    }*/

    @Override
    public Boolean checkUserName(String userName) throws Exception {
        AccountName account = selByUserName(userName, 0);
        return account != null;
    }

    @Override
    public Boolean checkMobile(String mobile) throws Exception {
        AccountName account = selByUserName(mobile, 1);
        return account != null;
    }

    @Override
    public String sendSms(String mobile) throws Exception {
        String messageFormat = "你的验证码是：%s";
//        String code = validateService.createCode(mobile); // 创建的验证码已经放入session
//        return senderService.sendSns(mobile, String.format(messageFormat, code));
        return null;
    }

    @Override
    public Long register(RegistInfo info) throws Exception {
        if (!info.getPassword().equals(info.getPwdAgain())) {
            throw new BusinessException(RegistErrorInfo.DIFF_PASSWORD.name());
        }
        // TODO: 判断是什么类型的注册方式，手机/邮箱/其他

        if (this.checkMobile(info.getAccount())) {
            throw new BusinessException(RegistErrorInfo.EXIST_MOBILE.name());
        }
        if (this.checkUserName(info.getUsername())) {
            throw new BuilderException(RegistErrorInfo.EXIST_USERNAME.name());
        }
//        // 图片验证码与session进行验证
//        if (!captchaService.verifyImage(info.getImageVerifyCode())){
//            throw new BuilderException(RegistErrorInfo.WRONG_VERIFYCODE_IMAGE.name());
//        }
//        // 手机短信验证码验证
//        if (!validateService.verifyCode(info.getAccount(), info.getMobileVerifyCode())) {
//            throw new BusinessException(RegistErrorInfo.WRONG_VERIFYCODE_MOBILE.name());
//        }

        // 保存账号
        Long accId = accountService.insert(assembleAccount(info.getUsername(), info.getPassword()));

        // 保存主账号名称
        accountNameService.insert(assembleAccName(accId, info.getUsername(), 0));

        // TODO : type值传入需要判断是手机还是邮箱，保存子账号名称
        accountNameService.insert(assembleAccName(accId, info.getMobile(), 1));

        // TODO 连接短信通道发送手机信息提示注册成功

        return accId;
    }

    @Override
    public void forgetPwd(ForgotPasswordInfo info) throws Exception {
        // TODO
        // 图片验证码与session进行验证
        // 手机短信验证码验证
    }

    @Override
    public void resetPwd(ResetPasswordInfo info) throws Exception {
        // 根据用户名查询账号信息
        AccountName accountName = selByUserName4Ex(info.getAccount());

        Account account = selAccountById4Ex(accountName.getAccountId());

        // 重置密码
        resetPwd(account, info.getNewPwd(), info.getNewPwdAgain(), false, "");
    }

    @Override
    public void resetOperatorPwd(EditPasswordInfo info) throws Exception {
        // 查询账号信息
        Account account = selAccountById4Ex(info.getAccountId());

        // 重置密码
        resetPwd(account, info.getNewPwd(), info.getNewPwdAgain(), false, "");
    }

    @Override
    public void editPwd(EditPasswordInfo info) throws Exception {
        // 查询账号信息
        Account account = selAccountById4Ex(info.getAccountId());

        // 重置密码
        resetPwd(account, info.getNewPwd(), info.getNewPwdAgain(), true, info.getOldPwd());
    }

    @Override
    public String selectPwd(Long accountId) throws Exception {
        // 查询账号信息
        Account account = selAccountById(accountId);
        if (account != null) {
            return account.getPassword();
        } else {
            return "";
        }
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
        acc.setPassword(password); // TODO: 需要加密处理
        acc.setCreateTime(new Date());
        acc.setManagerType(0);
        acc.setMaster(0);
        acc.setState(0);
        acc.setLockTime(0);
        acc.setIsLock(false);
        acc.setRegisterType(0);
        return acc;
    }

    /**
     * 组装账号密码对象信息并返回
     *
     * @param accountId 账号ID
     * @param username  用户名
     * @param type      类型（0主账号，1手机，2邮箱）
     * @return AccountName
     */
    private AccountName assembleAccName(Long accountId, String username, Integer type) {
        AccountName accPwd = new AccountName();
        accPwd.setAccountId(accountId);
        accPwd.setAccountName(username);
        accPwd.setTyp(type);

        return accPwd;
    }

    /**
     * 验证并重置密码
     *
     * @param acc                账号信息
     * @param newPwd             新密码
     * @param newAgainPwd        确认新密码
     * @param isValidationOldPwd 是否要验证旧密码，重置不需要
     * @param oldPwd             旧密码，如果不需验证旧密码可传空字符串
     */
    private void resetPwd(Account acc, String newPwd, String newAgainPwd, Boolean isValidationOldPwd, String oldPwd) throws BusinessException {
        if (acc != null) {
            if (!newPwd.equals(newAgainPwd)) {
                throw new BusinessException("对不起，两次密码不一致，请重新输入！");
            }
            if (isValidationOldPwd) {
                if (!acc.getPassword().equals(oldPwd)) {
                    throw new BusinessException("输入的旧密码有误，请重新输入！");
                }
            }

            acc.setPassword(newPwd);

            // 更新密码
            accountService.updateByPk(acc.getId(), acc);
        }
    }

    /**
     * 根据用户姓名查询账号信息，如果不存在抛出异常
     *
     * @param userName 用户名
     * @return AccountName
     */
    private AccountName selByUserName4Ex(String userName) {
        AccountName account = selByUserName(userName);
        if (account == null) {
            throw new BusinessException("对不起，该账号不存在！");
        }

        return account;
    }

    /**
     * 根据账号名称查询账号名称信息
     *
     * @param userName 账号名称
     * @return AccountName
     */
    private AccountName selByUserName(String userName) {
        return accountNameService.selectByAccountName(userName);
    }

    /**
     * 根据账号名称+账号类型查询账号名称信息
     *
     * @param userName 用户名
     * @param type     类型（0主账号，1手机，2邮箱）
     * @return AccountName
     */
    private AccountName selByUserName(String userName, Integer type) {
        AccountNameQuery query = new AccountNameQuery();
        query.setAccountName(userName);
        query.setTyp(type);

        return accountNameService.selectSingle(query);
    }

    /**
     * 根据登录账号查询账号信息并检测是否存在抛出异常
     *
     * @param id 主键ID
     * @return Account
     */
    private Account selAccountById4Ex(Long id) {
        Account a = selAccountById(id);
        if (a == null) {
            throw new BusinessException("对不起，该账号不存在！");
        }

        return a;
    }

    /**
     * 根据登录账号查询账号信息
     *
     * @param id 主键ID
     * @return Account
     */
    private Account selAccountById(Long id) {
        return accountService.selectByPk(id);
    }
}