package com.lmt.mbsp.user.service;

import com.lmt.framework.core.exception.ServiceException;
import com.lmt.framework.support.service.CrudService;
import com.lmt.mbsp.user.entity.group.GroupImg;

import java.util.List;

public interface GroupImgService extends CrudService<GroupImg,Long> {
    /**
     * 根据公司ID查询公司下所有图片信息
     * @param groupId 公司ID
     * @return List<GroupImg>
     */
    List<GroupImg> selectByGroupId(Long groupId) throws ServiceException;

    /**
     * 根据公司ID删除公司下所有图片信息
     * @param groupId 公司ID
     */
    void deleteByGroupId(Long groupId) throws ServiceException;
}
