package com.lmt.mbsp.user.biz;

import com.lmt.mbsp.user.vo.company.*;
import com.lmt.mbsp.user.vo.operator.IndexInfo;
import com.lmt.mbsp.user.vo.role.RoleInfo;

import java.util.List;

/*
 * 描述：组-企业商户
 * 作者：Tangsm
 * 创建时间：2018-07-24 14:03:07
 */
public interface CompanyBiz {
    /**
     * 新增企业商户
     * @param info 新增参数
     * @return Long
     */
    Long add(AddCompanyInfo info) throws Exception;

    /**
     * 组装进入编辑企业商户页面所需参数
     * @param id 主键ID
     * @return ToEditDeptInfo
     */
    ToEditCompanyInfo toEdit(Long id) throws Exception;

    /**
     * 编辑企业商户信息
     * @param info 编辑参数
     * @return
     * @throws Exception
     */
    void edit(EditCompanyInfo info) throws Exception;

    /**
     * 根据公司ID组装授权页面所需数据
     * @param id 公司ID
     * @return ToCompanyAuthorizeInfo
     * @throws Exception
     */
    ToCompanyAuthorizeInfo toAuthorize(Long id) throws Exception;

    /**
     * 保存企业商户授权信息
     * @param info  授权信息对象
     * @throws Exception
     */
    void authorize(SaveCompanyAuthorizeInfo info) throws Exception;

    /**
     * 根据公司ID查询公司信息
     * @param id
     * @return
     * @throws Exception
     */
    CompanyDetailInfo detail(Long id) throws Exception;

    /**
     * 查询企业商户角色信息
     * @param groupId 公司Id
     * @return List<RoleInfo>
     * @throws Exception
     */
    List<RoleInfo> searchRoles(Long groupId) throws Exception;

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

    /**
     * 审核企业商户
     * @param id    公司主键ID
     * @param type  类型(0审核通过 1审核拒绝)
     * @throws Exception
     */
    void audit(Long id, Integer type) throws Exception;

    /**
     * 用户中心首页
     * @param accountId 账号ID
     * @param userId    用户ID
     * @param groupId   公司ID
     * @return IndexInfo
     * @throws Exception
     */
    IndexInfo csIndex(Long accountId, Long userId, Long groupId) throws Exception;
}
