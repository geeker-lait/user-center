package com.lmt.mbsp.user.service;

import com.lmt.framework.core.exception.ServiceException;
import com.lmt.framework.support.service.CrudService;
import com.lmt.mbsp.user.entity.group.CompanyImg;

import java.util.List;

public interface CompanyImgService extends CrudService<CompanyImg,Long> {
    /**
     * 根据公司ID查询公司下所有图片信息
     * @param companyId 公司ID
     * @return List<CompanyImg>
     */
    List<CompanyImg> selectByCompanyId(Long companyId) throws ServiceException;

    /**
     * 根据公司ID删除公司下所有图片信息
     * @param companyId 公司ID
     */
    void deleteByCompanyId(Long companyId) throws ServiceException;
}
