package com.lmt.mbsp.user.biz;

import com.lmt.framework.support.entity.PagerResult;
import com.lmt.mbsp.user.dto.UserQuery;
import com.lmt.mbsp.user.entity.user.User;
import com.lmt.mbsp.user.vo.*;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * 描述: 用户聚合层.
 * 作者: Tangsm.
 * 创建时间: 2018-06-27 9:31.
 */
public interface UserBiz {
    /**
     * 完善个人资料
     * @param info 用户信息
     */
    void supplementInfo(UserInfo info) throws Exception;

    /**
     * 编辑个人资料
     * @param info 编辑用户信息
     */
    void editInfo(UserInfo info) throws Exception;

    /**
     * 新增操作员
     * @param info 新增操作员参数信息
     * @return Long
     */
    Long addOperator(AddOperatorInfo info) throws Exception;

    /**
     * 根据条件查询系统用户列表
     * @param info  查询用户参数
     * @return PagerResult<User>
     */
    PagerResult<UserInfo> sysUserList(UserQuery info) throws Exception;

    /**
     * 根据参数获取用户相关信息
     * @param userId    用户ID
     * @return UserDetailInfo
     */
    UserDetailInfo userDetail(Long userId) throws Exception;

    /**
     * 新增系统用户
     * @param info 系统用户参数
     * @return Long
     */
    Long addSysUser(AddSysUserInfo info) throws Exception;

    /**
     * 将用户加入某个组
     * @param groupId   组ID
     * @param userId    用户ID
     */
    void add2Group(Long groupId, Long userId) throws Exception;

    /**
     * 获取用户授权信息
     * @param userId    用户ID
     */
    UserDetailInfo getUserAuthorize(Long userId) throws Exception;

    /**
     * 用户授权void
     * @param info    授权参数
     */
     void userAuthorize(SaveUserAuthorizeInfo info) throws Exception;

    /**
     * 查询编辑系统用户所需信息
     * @param userId    用户ID
     * @return UserDetailInfo
     */
    ToEditSysUserInfo toEditUser(Long userId) throws Exception;

    /**
     * 编辑系统用户
     * @param info 系统用户参数
     */
    void editSysUser(EditSysUserInfo info) throws Exception;

    /**
     * 根据条件查询系统用户列表
     * @param info  查询用户参数
     * @return PagerResult<User>
     */
    PagerResult<UserInfo> personUserList(UserQuery info) throws Exception;

    /**
     * 禁用用户
     * @param id 用户主键ID
     */
    void disableUser(Long id) throws Exception;

    /**
     * 启用用户
     * @param id 用户主键ID
     */
    void unDisableUser(Long id) throws Exception;

    /**
     * 根据公司ID获取该组下所有的用户账号列表（含角色信息）
     * @param groupId 公司ID
     * @return List<OperatorListInfo>
     */
    List<OperatorListInfo> operatorList(Long groupId) throws Exception;

    /**
     * 根据公司ID查询公司下所有用户账号列表（不含角色信息）
     * @param groupId 公司ID
     * @return List<OperatorListInfo>
     */
    List<OperatorListInfo> userAccountList(Long groupId) throws Exception;

    /**
     * 设置企业商户管理员
     * @param groupId   公司ID
     * @param accountId    账号ID
     * @throws Exception
     */
    void addManager(Long groupId, Long accountId) throws Exception;

    /**
     * 获取操作员授权信息
     * @param userId    用户ID
     * @param accountId 账号ID
     * @return UserDetailInfo
     */
    UserDetailInfo toSysUserAuthorize(Long userId, Long accountId) throws Exception;
}
