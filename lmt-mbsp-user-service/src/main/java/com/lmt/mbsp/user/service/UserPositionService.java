package com.lmt.mbsp.user.service;

import com.lmt.framework.core.exception.ServiceException;
import com.lmt.framework.support.service.CrudService;
import com.lmt.framework.support.service.Service;
import com.lmt.mbsp.user.entity.user.UserPosition;

public interface UserPositionService extends CrudService<UserPosition,Long> {
    /**
     * 根据用户ID删除该用户下所有职位信息
     * @param userId 用户ID
     */
    void deleteByUserId(Long userId) throws ServiceException;

    /**
     * 根据用户ID查询
     * @param userId 用ID
     * @return UserPosition
     */
    UserPosition selectByUserId(Long userId) throws ServiceException;
}
