package com.lmt.mbsp.user.service.impl;

import com.lmt.framework.core.exception.ServiceException;
import com.lmt.framework.support.dao.mybatis.BaseMapper;
import com.lmt.framework.support.service.common.BaseServiceImpl;
import com.lmt.mbsp.user.dao.mapper.group.CompanyImgMapper;
import com.lmt.mbsp.user.entity.group.CompanyImg;
import com.lmt.mbsp.user.service.CompanyImgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/*
 * 描述：企业图片
 * 作者：Tangsm
 * 创建时间：2018-07-24 17:21:49
 */
@Service
public class CompanyImgServiceImpl extends BaseServiceImpl<CompanyImg, Long> implements CompanyImgService {
    @Autowired
    private CompanyImgMapper companyImgMapper;

    @Override
    public BaseMapper getBaseMapper() {
        return companyImgMapper;
    }

    @Override
    public CompanyImg createEntity() {
        return new CompanyImg();
    }

    @Override
    public List<CompanyImg> selectByCompanyId(Long companyId)  throws ServiceException{
        return companyImgMapper.selectByCompanyId(companyId);
    }

    @Override
    public void deleteByCompanyId(Long companyId) throws ServiceException {
        companyImgMapper.deleteByCompanyId(companyId);
    }
}
