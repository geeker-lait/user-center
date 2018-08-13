package com.lmt.mbsp.user.backend.biz;

import com.lmt.mbsp.user.vo.account.*;


/**
 * 描述: 账号聚合层.
 * 作者: Tangsm.
 * 创建时间: 2018-06-27 17:18.
 */
public interface AccountBiz {
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
     * @param info 绑定参数
     */
    void bindAcc(BindAccountInfo info) throws Exception;

    /**
     * 设为超管
     * @param id 主键ID
     */
    void toSuperAccount(Long id) throws Exception;

    /**
     * 设为超管
     * @param id 主键ID
     */
    void cancelSuperAccount(Long id) throws Exception;

    /**
     * 获取账号授权信息
     * @param userId    用户ID
     * @param id        账号主键ID
     * @return UserDetailInfo
     */
    ToAccountAuthorizeInfo toAuthorize(Long userId, Long id) throws Exception;

    /**
     * 用户授权void
     * @param info    授权参数
     */
    void authorize(SaveAccountAuthorizeInfo info) throws Exception;

    /**
     * 设置企业商户管理员
     * @param accountId    账号ID
     */
    void addManager(Long accountId) throws Exception;

    /**
     * 取消企业商户管理员
     * @param accountId    账号ID
     */
    void cancelManager(Long accountId) throws Exception;
}
