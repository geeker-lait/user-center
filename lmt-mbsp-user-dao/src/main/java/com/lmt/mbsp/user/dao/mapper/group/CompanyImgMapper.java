package com.lmt.mbsp.user.dao.mapper.group;

import com.lmt.framework.support.dao.mybatis.BaseMapper;
import com.lmt.mbsp.user.entity.group.CompanyImg;

import java.util.List;

public interface CompanyImgMapper extends BaseMapper<CompanyImg, Long> {
    /**
     * 根据公司ID查询公司下所有图片信息
     * @param companyId 公司ID
     * @return List<CompanyImg>
     */
    List<CompanyImg> selectByCompanyId(Long companyId);

    /**
     * 根据公司ID删除公司下所有图片信息
     * @param companyId 公司ID
     */
    void deleteByCompanyId(Long companyId);
}