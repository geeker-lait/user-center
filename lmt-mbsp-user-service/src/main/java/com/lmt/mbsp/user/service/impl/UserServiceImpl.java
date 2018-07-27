package com.lmt.mbsp.user.service.impl;

import com.lmt.framework.support.dao.mybatis.BaseMapper;
import com.lmt.framework.support.service.common.BaseServiceImpl;
import com.lmt.mbsp.user.entity.user.User;
import com.lmt.mbsp.user.dao.mapper.user.UserMapper;
import com.lmt.mbsp.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 描述: 用户服务实现.
 * 作者: Tangsm.
 * 创建时间: 2018-06-26 9:59.
 */
@Service
public class UserServiceImpl extends BaseServiceImpl<User,Long> implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public BaseMapper getBaseMapper() {
        return userMapper;
    }

    @Override
    public User createEntity() {
        return new User();
    }
}
