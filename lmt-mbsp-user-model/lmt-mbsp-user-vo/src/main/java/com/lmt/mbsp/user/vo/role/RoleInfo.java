package com.lmt.mbsp.user.vo.role;

import lombok.Data;

import javax.persistence.Id;
import java.util.Date;

/**
 * 描述:角色.
 * 作者: Tangsm.
 * 创建时间: 2018-07-03 11:08.
 */
@Data
public class RoleInfo {
    // id
    private Long id;
    private String name;// 角色名
    private String icon;//角色对应的图标
    private String descr; // 描述
    private Integer state;// 状态(0|1)
    private Date createTime;// 创建时间
}
