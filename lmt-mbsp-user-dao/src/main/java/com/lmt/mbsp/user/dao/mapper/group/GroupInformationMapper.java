package com.lmt.mbsp.user.dao.mapper.group;

import com.lmt.framework.support.dao.mybatis.BaseMapper;
import com.lmt.mbsp.user.entity.group.GroupInformation;

public interface GroupInformationMapper extends BaseMapper<GroupInformation,Long> {
    /**
     * 根据公司ID查询公司资料信息
     * @param groupId 公司主键ID
     * @return GroupInformation
     */
    GroupInformation selectByGroupId(Long groupId);
}
