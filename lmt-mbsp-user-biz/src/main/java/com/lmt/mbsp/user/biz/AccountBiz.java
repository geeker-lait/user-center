package com.lmt.mbsp.user.biz;

import com.lmt.mbsp.user.vo.*;

/**
 * 描述: 账号聚合层.
 * 作者: Tangsm.
 * 创建时间: 2018-06-27 17:18.
 */
public interface AccountBiz {
    /**
     * 校验用户名是否已经存在
     * @param userName 用户名
     * @return Boolean True: 用户名存在，False: 用户不存在
     */
    Boolean checkUserName(String userName) throws Exception;

    /**
     * 校验手机号是否已经存在
     * @param mobile 手机号
     * @return Boolean True: 手机号存在，False: 手机号不存在
     */
    Boolean checkMobile(String mobile) throws Exception;

    /**
     * 发送手机验证码
     * @param mobile 手机号
     * @return String
     */
    String sendSms(String mobile) throws Exception;

    /**
     * 用户注册
     * @param info 注册参数
     */
    Long register(RegistInfo info) throws Exception;

    /**
     * 忘记密码手机号验证
     * @param info 查询参数
     */
    void forgetPwd(ForgotPasswordInfo info) throws Exception;

    /**
     * 前台重置密码
     * @param info 查询参数
     */
    void resetPwd(ResetPasswordInfo info) throws Exception;

    /**
     * 重置操作员密码
     * @param info 查询参数
     */
    void resetOperatorPwd(EditPasswordInfo info) throws Exception;

    /**
     * 修改密码
     * @param info 查询参数
     */
    void editPwd(EditPasswordInfo info) throws Exception;

    /**
     * 查询密码
     * @param accountId 查询参数
     * @return String
     */
    String selectPwd(Long accountId) throws Exception;
}
