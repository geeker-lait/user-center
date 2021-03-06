package com.lmt.mbsp.user.service.impl;

import com.lmt.framework.core.exception.ServiceException;
import com.lmt.framework.support.dao.mybatis.BaseMapper;
import com.lmt.framework.support.service.common.BaseServiceImpl;
import com.lmt.mbsp.user.entity.group.Group;
import com.lmt.mbsp.user.dao.mapper.group.GroupMapper;
import com.lmt.mbsp.user.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 描述: 群组服务实现.
 * 作者: Tangsm.
 * 创建时间: 2018-06-26 9:59.
 */
@Service
public class GroupServiceImpl extends BaseServiceImpl<Group,Long> implements GroupService {
    @Autowired
    private GroupMapper groupMapper;

    @Override
    public BaseMapper getBaseMapper() {
        return groupMapper;
    }

    @Override
    public Group createEntity() {
        return new Group();
    }

    @Override
    public List<Group> selectSonTreeById(Long id) throws ServiceException{
        return groupMapper.selectSonTreeById(id);
    }

    @Override
    public Group selectParentTreeById(Long id) throws ServiceException{
        return groupMapper.selectParentTreeById(id);
    }

    @Override
    public Group selectByGroupIdAndName(Long groupId, String name) throws ServiceException{
        Map<String, Object> param = new HashMap<>();
        param.put("groupId", groupId);
        param.put("name", name);

        return groupMapper.selectByGroupIdAndName(param);
    }

    @Override
    public Group selectByGroupName(String name) throws ServiceException{
        return groupMapper.selectByGroupName(name);
    }

    @Override
    public String selectMaxCodeByPid(Long pid) throws ServiceException {
        return groupMapper.selectMaxCodeByPid(pid);
    }
}
