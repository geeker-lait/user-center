package com.lmt.mbsp.user.dao.mapper.group;

import com.lmt.framework.support.dao.mybatis.BaseMapper;
import com.lmt.mbsp.user.entity.group.GroupImg;

import java.util.List;

public interface GroupImgMapper extends BaseMapper<GroupImg, Long> {
    /**
     * 根据公司ID查询公司下所有图片信息
     * @param groupId 公司ID
     * @return List<GroupImg>
     */
    List<GroupImg> selectByGroupId(Long groupId);

    /**
     * 根据公司ID删除公司下所有图片信息
     * @param groupId 公司ID
     */
    void deleteByGroupId(Long groupId);
}