package com.lmt.mbsp.user.service;

import com.lmt.framework.core.exception.ServiceException;
import com.lmt.framework.support.service.CrudService;
import com.lmt.mbsp.user.entity.group.Group;

import java.util.List;
import java.util.Map;

/**
 * Auther: lait.zhang@gmail.com
 * Tel:15801818092
 * Date: 6/1/2018 10:03
 * Description:群组接口
 */
public interface GroupService extends CrudService<Group,Long> {
    /**
     * 根据公司ID查询该公司下所有部门信息
     * @param id 主键ID
     * @return List<Group>
     */
    List<Group> selectSonTreeById(Long id) throws ServiceException;

    /**
     * 根据当前级别ID查询父级信息
     * @param id 主键ID查询参数
     * @return List<Group>
     */
    Group selectParentTreeById(Long id) throws ServiceException;

    /**
     * 根据公司ID+部门名称查询该部门是否已经存在
     * @param groupId 公司ID
     * @param name 部门名称
     * @return Group
     */
    Group selectByGroupIdAndName(Long groupId, String name) throws ServiceException;

    /**
     * 根据公司名称查询该公司是否已经存在
     * @param name 公司名称
     * @return Group
     */
    Group selectByGroupName(String name) throws ServiceException;

    /**
     * 根据父级部门ID查询当前最大编码
     * @param pid 父级ID
     * @return String
     */
    String selectMaxCodeByPid(Long pid) throws ServiceException;
}
