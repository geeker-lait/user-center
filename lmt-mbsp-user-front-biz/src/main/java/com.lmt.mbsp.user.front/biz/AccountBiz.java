package com.lmt.mbsp.user.front.biz;

import com.lmt.mbsp.user.vo.account.*;

import java.util.List;

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
     * 找回密码发送手机验证码
     * @param mobile 手机号
     * @return Boolean
     */
    Boolean findPwd(String mobile) throws Exception;

    /**
     * 前台重置密码
     * @param info 查询参数
     */
    void resetPwd(ResetPasswordInfo info) throws Exception;

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

    /**
     * 根据主键ID禁用账号信息
     * @param id 主键ID
     */
    void disabled(Long id) throws Exception;

    /**
     * 根据主键ID激活账号信息
     * @param id 主键ID
     */
    void unDisabled(Long id) throws Exception;

    /**
     * 根据账号名称表主键ID组装进入编辑用户账号页面所需数据
     * @param id 主键ID
     * @return ToEditAccountInfo
     */
    ToEditAccountInfo toEditAccount(Long id) throws Exception;

    /**
     * 修改账号信息
     * @param info 修改参数
     */
    void editAccount(EditAccountInfo info) throws Exception;

    /**
     * 绑定账号信息
     * @param info 修改参数
     */
    void bindAcc(BindAccountInfo info) throws Exception;

    /**
     * 获取操作员授权信息
     * @param userId    用户ID
     * @param accountId 账号ID
     * @return UserDetailInfo
     */
    ToAccountAuthorizeInfo toAuthorize(Long userId, Long accountId) throws Exception;

    /**
     * 用户授权void
     * @param info    授权参数
     */
    void authorize(SaveAccountAuthorizeInfo info) throws Exception;
}
