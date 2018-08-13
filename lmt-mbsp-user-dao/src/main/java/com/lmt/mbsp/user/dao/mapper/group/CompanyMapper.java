package com.lmt.mbsp.user.dao.mapper.group;

import com.lmt.framework.support.dao.mybatis.BaseMapper;
import com.lmt.mbsp.user.entity.group.Company;

public interface CompanyMapper extends BaseMapper<Company,Long> {
    /**
     * 根据公司ID查询公司资料信息
     * @param groupId 公司主键ID
     * @return Company
     */
    Company selectByGroupId(Long groupId);

    /**
     * 根据公司名称查询公司是否存在
     * @param name 公司名称
     * @return Company
     */
    Company selectByName(String name);
}
