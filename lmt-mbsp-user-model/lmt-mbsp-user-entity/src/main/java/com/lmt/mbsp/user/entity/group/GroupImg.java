package com.lmt.mbsp.user.entity.group;

import com.lmt.framework.support.entity.Entity;
import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;

/*
 * @描述：企业图片
 * @作者：Tangsm
 * @创建时间：2018-07-09 17:17:07
 */
@Data
@javax.persistence.Entity
@Table(name = "t_group_img")
public class GroupImg implements Entity {
    @Id
    private Long id;
    // 组织ID
    private Long groupId;
    // 证件类型(0三证合一 1营业执照 2经营许可证 3税务登记证 4管理员授权书 5法人身份证）
    private Integer type;
    // 证件图片地址
    private String imgUrl;
}
