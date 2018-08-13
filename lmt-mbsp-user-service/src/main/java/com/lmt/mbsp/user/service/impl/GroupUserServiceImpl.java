package com.lmt.mbsp.user.service.impl;

import com.lmt.framework.core.exception.ServiceException;
import com.lmt.mbsp.user.dao.mapper.group.GroupUserMapper;
import com.lmt.mbsp.user.dto.GroupUserQuery;
import com.lmt.mbsp.user.entity.group.GroupUser;
import com.lmt.framework.support.dao.mybatis.BaseMapper;
import com.lmt.framework.support.service.common.BaseServiceImpl;
import com.lmt.mbsp.user.service.GroupUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class GroupUserServiceImpl extends BaseServiceImpl<GroupUser, Long> implements GroupUserService {

    @Autowired
    private GroupUserMapper groupUserMapper;

    @Override
    public BaseMapper<GroupUser, Long> getBaseMapper() {
        return groupUserMapper;
    }

    @Override
    public GroupUser createEntity() {
        return new GroupUser();
    }

    @Override
    public void deleteByUserId(Long userId) throws ServiceException {
        groupUserMapper.deleteByUserId(userId);
    }

    @Override
    public List<GroupUser> selectByUserId(Long userId) throws ServiceException{
        return groupUserMapper.selectByUserId(userId);
    }
}
