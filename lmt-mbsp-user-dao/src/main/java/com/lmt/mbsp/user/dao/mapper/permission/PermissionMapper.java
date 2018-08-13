package com.lmt.mbsp.user.dao.mapper.permission;

import com.lmt.framework.support.dao.mybatis.BaseMapper;
import com.lmt.mbsp.user.entity.permission.Permission;

import java.util.List;

public interface PermissionMapper extends BaseMapper<Permission, Long> {

    List<Permission> selectByVal(List<Integer> vals);

}