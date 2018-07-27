package com.lmt.mbsp.user.biz;


import com.lmt.framework.core.exception.BusinessException;
import com.lmt.mbsp.user.entity.group.Group;
import com.lmt.mbsp.user.vo.*;

import java.util.List;

/**
 * 描述: 群组业务聚合层.
 * 作者: Tangsm.
 * 创建时间: 2018-06-29 9:26.
 */
public interface GroupDeptBiz {
    /****************************************************************************************** 后台-部门 ******************************************************************/
    /**
     * 获取指定组及其子组信息（返回树）
     * @param groupId 组ID
     * @return List<GroupInfo>
     * @throws Exception
     */
    List<GroupInfo> selById(Long groupId) throws Exception;

    /**
     * 根据组ID+组名称查询组信息（名称为模糊搜索）
     * @param grade 组类型（0内部组 1非内部组）
     * @param name  组名称
     * @return boolean
     * @throws Exception
     */
    List<GroupInfo> selByGradeAndName(Integer grade, String name) throws Exception;

    /**
     * 根据公司ID+部门名称查询该部门是否已经存在
     * @param groupId 公司ID
     * @param name 部门名称
     * @return Group
     */
    boolean selByGroupIdAndName(Long groupId, String name) throws Exception;

    /**
     * 根据公司名称查询该公司是否已经存在
     * @param name 公司名称
     * @return Group
     */
    boolean selByGroupName(String name) throws Exception;

    /**
     * 进入新增部门页面所需数据
     * @param parentCode 父部门Code（可为空，为空代表新增一级部门）
     * @return ToEditDeptInfo
     * @throws Exception
     */
    ToAddDeptInfo toAddDept(String parentCode) throws Exception;

    /**
     * 新增部门信息
     * @param info 新增参数
     * @return Long
     * @throws Exception
     */
    Long addDept(AddDeptInfo info) throws Exception;

    /**
     * 组装进入编辑部门页面数据
     * @param id    部门ID
     * @return ToEditDeptInfo
     * @throws Exception
     */
    ToEditDeptInfo toEditDept(Long id) throws Exception;

    /**
     * 修改部门信息
     * @param info 修改参数
     * @return Long
     * @throws Exception
     */
    void editDept(EditDeptInfo info) throws Exception;

    /**
     * 部门详细页需展示数据
     * @param id   部门主键ID
     * @return DeptDetailInfo
     * @throws Exception
     */
    DeptDetailInfo detailDept(Long id) throws Exception;

    /**
     * 禁用组
     * @param id 主键ID
     * @throws Exception
     */
    void disable(Long id) throws Exception;

    /**
     * 激活组
     * @param id 主键ID
     * @throws Exception
     */
    void unDisable(Long id) throws Exception;

    /****************************************************************************************** 后台-内部公司 ******************************************************************/

    /**
     * 进入新增公司页面所需数据
     * @param parentId 父公司ID（可为空，为空代表新增一级公司）
     * @return GroupInfo
     * @throws Exception
     */
    List<GroupInfo> toAddCompany(Long parentId) throws Exception;

    /**
     * 新增公司
     * @param groupId 父公司ID（可为空，如果为空代表创建一级公司）
     * @param name  公司名称
     * @return
     */
    Long addCompany(Long groupId, String name) throws Exception;

    /**
     * 编辑公司名称
     * @param groupId 公司ID
     * @param name  新名称
     * @throws Exception
     */
    void editCompany(Long groupId, String name) throws Exception;

    /**
     * 公司详细页需要展示的数据
     * @param id 主键ID
     * @return InnerCompanyDetailInfo
     * @throws Exception 异常
     */
    InnerCompanyDetailInfo detailCompany(Long id) throws Exception;
}
