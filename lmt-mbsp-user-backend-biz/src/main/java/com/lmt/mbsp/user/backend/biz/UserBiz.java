package com.lmt.mbsp.user.backend.biz;

import com.lmt.framework.support.entity.PagerResult;
import com.lmt.mbsp.user.dto.GroupUserQuery;
import com.lmt.mbsp.user.dto.UserQuery;
import com.lmt.mbsp.user.vo.operator.OperatorListInfo;
import com.lmt.mbsp.user.vo.user.EditUserInfo;
import com.lmt.mbsp.user.vo.user.UserDetailInfo;
import com.lmt.mbsp.user.vo.user.UserInfo;

/**
 * 描述: 用户聚合层.
 * 作者: Tangsm.
 * 创建时间: 2018-06-27 9:31.
 */
public interface UserBiz {
    /**
     * 获取某个组下用户列表（含账号/角色信息）
     * @param info  查询用户参数
     * @return PagerResult<User>
     */
    PagerResult<OperatorListInfo> groupUserList(GroupUserQuery info) throws Exception;

    /**
     * 根据条件查询用户列表
     * @param info  查询用户参数
     * @return PagerResult<User>
     */
    PagerResult<UserInfo> userList(UserQuery info) throws Exception;

    /**
     * 根据参数获取用户相关信息
     * @param userId    用户ID
     * @return UserDetailInfo
     */
    UserDetailInfo userDetail(Long userId) throws Exception;

    /**
     * 根据用户主键ID组装编辑用户所需展示的数据
     * @param userId 用户主键ID
     * @return UserInfo
     */
    EditUserInfo toEditUser(Long userId)throws Exception;

    /**
     * 更新用户信息
     * @param info 更新参数
     */
    void editUser(EditUserInfo info) throws Exception;

    /**
     * 将用户加入某个组
     * @param userId 用户ID
     * @param groupId   组ID
     */
    void add2Group(Long userId, Long groupId) throws Exception;

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
}
