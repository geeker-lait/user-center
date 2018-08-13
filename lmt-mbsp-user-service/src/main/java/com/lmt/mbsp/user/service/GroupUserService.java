package com.lmt.mbsp.user.service;

import com.lmt.framework.core.exception.ServiceException;
import com.lmt.framework.support.service.CrudService;
import com.lmt.mbsp.user.entity.group.GroupUser;

import java.util.List;
import java.util.Map;

/**
 * 描述: 群组用户.
 * 作者: Tangsm.
 * 创建时间: 2018-07-03 10:07.
 */
public interface GroupUserService extends CrudService<GroupUser,Long> {
    /**
     * 根据用户ID删除该用户所有组信息
     * @param userId 用户ID
     */
    void deleteByUserId(Long userId) throws ServiceException;

    /**
     * 根据用户ID查询组用户关联表信息
     * @param userId
     * @return
     */
    List<GroupUser> selectByUserId(Long userId) throws ServiceException;
}
