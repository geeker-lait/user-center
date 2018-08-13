package com.lmt.mbsp.user.entity.group;

import com.lmt.framework.support.entity.Entity;
import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;

/*
 * @描述：组角色
 * @作者：Tangsm
 * @创建时间：2018-07-09 17:31:57
 */
@Data
@javax.persistence.Entity
@Table(name = "t_group_img")
public class GroupRole implements Entity {
    @Id
    private Long id;
    // 组ID
    private Long groupId;
    // 角色ID
    private Long roleId;
}
