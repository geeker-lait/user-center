package com.lmt.mbsp.user.service.impl;

import com.lmt.framework.core.exception.ServiceException;
import com.lmt.framework.support.dao.mybatis.BaseMapper;
import com.lmt.framework.support.service.common.BaseServiceImpl;
import com.lmt.mbsp.user.dao.mapper.group.CompanyMapper;
import com.lmt.mbsp.user.entity.group.Company;
import com.lmt.mbsp.user.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/*
 * 描述：企业资料
 * 作者：Tangsm
 * 创建时间：2018-07-24 16:20:21
 */
@Service
public class CompanyServiceImpl extends BaseServiceImpl<Company, Long> implements CompanyService {
    @Autowired
    private CompanyMapper companyMapper;

    @Override
    public BaseMapper getBaseMapper() {
        return companyMapper;
    }

    @Override
    public Company createEntity() {
        return new Company();
    }

    @Override
    public Company selectByGroupId(Long groupId) throws ServiceException {return companyMapper.selectByGroupId(groupId);}

    @Override
    public Company selectByName(String name){return companyMapper.selectByName(name);}
}
