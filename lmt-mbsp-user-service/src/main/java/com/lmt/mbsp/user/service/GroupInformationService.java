package com.lmt.mbsp.user.service;

import com.lmt.framework.core.exception.ServiceException;
import com.lmt.framework.support.service.CrudService;
import com.lmt.mbsp.user.entity.group.GroupInformation;


public interface GroupInformationService extends CrudService<GroupInformation,Long> {
    /**
     * 根据公司ID查询公司资料信息
     * @param groupId 公司主键ID
     * @return GroupInformation
     */
    GroupInformation selectByGroupId(Long groupId) throws ServiceException;
}
