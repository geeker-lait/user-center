package com.lmt.mbsp.user.dao.mapper.group;

import com.lmt.mbsp.user.entity.group.GroupUser;
import com.lmt.framework.support.dao.mybatis.BaseMapper;

import java.util.List;

public interface GroupUserMapper extends BaseMapper<GroupUser,Long> {
    /**
     * 根据用户ID删除该用户所有组信息
     * @param userId 用户ID
     */
    void deleteByUserId(Long userId);

    /**
     * 根据用户ID查询组用户关联表信息
     * @param userId
     * @return
     */
    List<GroupUser> selectByUserId(Long userId);
}