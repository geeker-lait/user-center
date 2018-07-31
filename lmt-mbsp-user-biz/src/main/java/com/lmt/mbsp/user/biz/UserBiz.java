package com.lmt.mbsp.user.biz;

import com.lmt.framework.support.entity.PagerResult;
import com.lmt.mbsp.user.dto.UserQuery;
import com.lmt.mbsp.user.vo.admin.AddAdminInfo;
import com.lmt.mbsp.user.vo.admin.EditAdminInfo;
import com.lmt.mbsp.user.vo.admin.ToEditAdminInfo;
import com.lmt.mbsp.user.vo.operator.AddOperatorInfo;
import com.lmt.mbsp.user.vo.operator.OperatorListInfo;
import com.lmt.mbsp.user.vo.operator.ToEditOperatorInfo;
import com.lmt.mbsp.user.vo.person.EditUserInfo;
import com.lmt.mbsp.user.vo.person.UserDetailInfo;
import com.lmt.mbsp.user.vo.person.UserInfo;

import java.util.List;

/**
 * 描述: 用户聚合层.
 * 作者: Tangsm.
 * 创建时间: 2018-06-27 9:31.
 */
public interface UserBiz {
    /**
     * 组装进入完善/编辑个人资料页面所需展示数据
     * @param userId    用户ID
     * @throws Exception
     */
    ToEditAdminInfo toSupplement(Long userId) throws Exception;

    /**
     * 完善/编辑个人资料
     * @param info 用户信息
     * @throws Exception
     */
    void supplement(EditUserInfo info) throws Exception;

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
     * @param type  0查询个人用户 1查询系统用户
     * @return UserDetailInfo
     */
    UserDetailInfo userDetail(Long userId, Integer type) throws Exception;

    /**
     * 新增系统用户
     * @param info 系统用户参数
     * @return Long
     */
    Long addSysUser(AddAdminInfo info) throws Exception;

    /**
     * 将用户加入某个组
     * @param groupId   组ID
     * @param userId    用户ID
     */
    void add2Group(Long groupId, Long userId) throws Exception;

    /**
     * 查询编辑系统用户所需信息
     * @param userId    用户ID
     * @return UserDetailInfo
     */
    ToEditAdminInfo toEditSysUser(Long userId) throws Exception;

    /**
     * 编辑系统用户
     * @param info 系统用户参数
     */
    void editSysUser(EditAdminInfo info) throws Exception;

    /**
     * 根据条件查询用户列表
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
     * 根据公司ID查询公司下所有用户账号列表（不含角色信息,企业商户下账号信息）
     * @param groupId 公司ID
     * @return List<OperatorListInfo>
     */
    List<OperatorListInfo> userAccountList(Long groupId) throws Exception;

    /**
     * 组装进入编辑操作员信息页面所需参数
     * @param userId 用户主键ID
     * @return ToEditOperatorInfo
     * @throws Exception
     */
    ToEditOperatorInfo toEditOperator(Long userId, Long accountId)throws Exception;
}
