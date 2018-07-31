package com.lmt.mbsp.user.vo.role;

import lombok.Data;

import javax.persistence.Id;
import java.util.Date;

/**
 * @描述:角色视图.
 * @作者: lijing.
 * @创建时间: 2018-07-03 11:08.
 * @版本: 1.0 .
 *
 * {id: 1, name: "string", icon: "string", state: 0, createTime: null, code: "asdassss"}
 *
 */
@Data
public class RoleInfo {
    // id
    private Long id;
    // 角色名
    private String name;
    //角色对应的图标
    private String icon;
    // 状态(0|1)
    private Integer state;
    // 创建时间
    private Date createTime;

}
