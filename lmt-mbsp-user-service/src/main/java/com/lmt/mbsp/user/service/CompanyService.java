package com.lmt.mbsp.user.service;

import com.lmt.framework.core.exception.ServiceException;
import com.lmt.framework.support.service.CrudService;
import com.lmt.mbsp.user.entity.group.Company;


public interface CompanyService extends CrudService<Company,Long> {
    /**
     * 根据公司ID查询公司资料信息
     * @param groupId 公司主键ID
     * @return Company
     */
    Company selectByGroupId(Long groupId) throws ServiceException;

    /**
     * 根据公司名称查询公司是否存在
     * @param name 公司名称
     * @return Company
     */
    Company selectByName(String name);
}
