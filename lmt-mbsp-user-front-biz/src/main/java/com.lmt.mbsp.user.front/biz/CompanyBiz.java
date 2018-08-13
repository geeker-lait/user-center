package com.lmt.mbsp.user.front.biz;

import com.lmt.mbsp.user.vo.company.*;
import com.lmt.mbsp.user.vo.operator.IndexInfo;

/*
 * 描述：组-企业商户
 * 作者：Tangsm
 * 创建时间：2018-07-24 14:03:07
 */
public interface CompanyBiz {
    /**
     * 根据公司名查询该公司是否存在
     * @param name 公司名称
     * @return Boolean true存在，false不存在
     */
    Boolean checkCompanyName(String name) throws Exception;

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
     */
    void edit(EditCompanyInfo info) throws Exception;

    /**
     * 用户中心首页
     * @param accountId 账号ID
     * @param userId    用户ID
     * @param companyId   公司ID
     * @return IndexInfo
     */
    IndexInfo index(Long accountId, Long userId, Long companyId) throws Exception;
}
