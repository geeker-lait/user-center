package com.lmt.mbsp.user.front.biz;

import com.lmt.mbsp.user.vo.operator.AddOperatorInfo;
import com.lmt.mbsp.user.vo.operator.OperatorListInfo;
import com.lmt.mbsp.user.vo.user.EditUserInfo;
import com.lmt.mbsp.user.vo.user.UserDetailInfo;
import com.lmt.mbsp.user.vo.user.UserInfo;

import java.util.List;

/**
 * 描述: 用户聚合层.
 * 作者: Tangsm.
 * 创建时间: 2018-06-27 9:31.
 */
public interface UserBiz {
    /**
     * 根据参数获取用户相关信息
     * @param userId    用户ID
     * @return UserDetailInfo
     */
    UserDetailInfo userDetail(Long userId) throws Exception;

    /**
     * 组装进入完善/编辑个人资料页面所需展示数据
     * @param userId    用户ID
     */
    UserInfo toEdit(Long userId) throws Exception;

    /**
     * 完善/编辑个人资料
     * @param info 用户信息
     */
    void edit(EditUserInfo info) throws Exception;

    /**
     * 新增操作员
     * @param info 新增操作员参数信息
     * @return Long
     */
    Long addOperator(AddOperatorInfo info) throws Exception;

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
}
