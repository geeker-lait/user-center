package com.lmt.mbsp.user.service.impl;

import com.lmt.framework.core.exception.ServiceException;
import com.lmt.framework.support.dao.mybatis.BaseMapper;
import com.lmt.framework.support.service.CrudService;
import com.lmt.framework.support.service.common.BaseServiceImpl;
import com.lmt.mbsp.user.dao.mapper.account.AccountMapper;
import com.lmt.mbsp.user.dao.mapper.group.GroupInformationMapper;
import com.lmt.mbsp.user.entity.account.Account;
import com.lmt.mbsp.user.entity.group.GroupInformation;
import com.lmt.mbsp.user.service.GroupInformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/*
 * 描述：企业资料
 * 作者：Tangsm
 * 创建时间：2018-07-24 16:20:21
 */
@Service
public class GroupInformationServiceImpl extends BaseServiceImpl<GroupInformation, Long> implements GroupInformationService {
    @Autowired
    private GroupInformationMapper groupInformationMapper;

    @Override
    public BaseMapper getBaseMapper() {
        return groupInformationMapper;
    }

    @Override
    public GroupInformation createEntity() {
        return new GroupInformation();
    }

    @Override
    public GroupInformation selectByGroupId(Long groupId) throws ServiceException {return groupInformationMapper.selectByGroupId(groupId);}
}
