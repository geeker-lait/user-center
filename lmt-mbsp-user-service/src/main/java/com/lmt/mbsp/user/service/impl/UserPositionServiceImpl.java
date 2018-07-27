package com.lmt.mbsp.user.service.impl;

import com.lmt.framework.core.exception.ServiceException;
import com.lmt.framework.support.dao.mybatis.BaseMapper;
import com.lmt.framework.support.service.common.BaseServiceImpl;
import com.lmt.mbsp.user.dao.mapper.user.UserPositionMapper;
import com.lmt.mbsp.user.entity.user.UserPosition;
import com.lmt.mbsp.user.service.UserPositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/*
 * @描述：
 * @作者：Tangsm
 * @创建时间：2018-07-11 16:02:05
 */
@Service("userPositionService")
public class UserPositionServiceImpl extends BaseServiceImpl<UserPosition,Long> implements UserPositionService {
    @Autowired
    private UserPositionMapper userPositionMapper;

    @Override
    public BaseMapper getBaseMapper() {
        return userPositionMapper;
    }

    @Override
    public UserPosition createEntity() {
        return new UserPosition();
    }

    @Override
    public void deleteByUserId(Long userId) throws ServiceException {
        userPositionMapper.deleteByUserId(userId);
    }

    @Override
    public UserPosition selectByUserId(Long userId) throws ServiceException{
        return userPositionMapper.selectByUserId(userId);
    }
}
