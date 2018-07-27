package com.lmt.mbsp.user.dao.mapper.user;

import com.lmt.framework.support.dao.mybatis.BaseMapper;
import com.lmt.mbsp.user.entity.user.UserPosition;

public interface UserPositionMapper extends BaseMapper<UserPosition,Long> {
    /**
     * 根据用户ID删除该用户下所有职位信息
     * @param userId 用户ID
     */
    void deleteByUserId(Long userId);

    /**
     * 根据用户ID查询
     * @param userId 用ID
     * @return UserPosition
     */
    UserPosition selectByUserId(Long userId);
}
