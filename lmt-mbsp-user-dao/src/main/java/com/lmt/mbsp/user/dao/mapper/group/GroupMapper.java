package com.lmt.mbsp.user.dao.mapper.group;

import com.lmt.framework.support.dao.mybatis.BaseMapper;
import com.lmt.mbsp.user.entity.group.Group;

import java.util.List;
import java.util.Map;

public interface GroupMapper extends BaseMapper<Group,Long> {
    /**
     * 根据公司ID查询该公司下所有部门信息
     * @param id 主键ID
     * @return List<Group>
     */
    List<Group> selectSonTreeById(Long id);

    /**
     * 根据当前级别ID查询父级信息
     * @param id 主键ID查询参数
     * @return List<Group>
     */
    Group selectParentTreeById(Long id);

    /**
     * 根据公司ID+部门名称查询该部门是否已经存在
     * @param param 查询参数
     * @return Group
     */
    Group selectByGroupIdAndName(Map<String, Object> param);

    /**
     * 根据公司名称查询该公司是否已经存在
     * @param name 公司名称
     * @return Group
     */
    Group selectByGroupName(String name);

    /**
     * 根据父级部门ID查询当前最大编码
     * @param pid 父级ID
     * @return String
     */
    String selectMaxCodeByPid(Long pid);
}