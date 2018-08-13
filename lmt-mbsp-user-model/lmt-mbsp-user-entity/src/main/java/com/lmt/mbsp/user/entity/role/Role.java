package com.lmt.mbsp.user.entity.role;

import com.lmt.framework.support.entity.Entity;
import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * Auther: lait.zhang@gmail.com
 * Tel:15801818092
 * Date: 5/31/2018 17:48
 * Description: 角色
 */
@Data
@Table(name = "t_role")
public class Role  implements Entity {
    @Id
    private Long id;
    private String name;    // 角色名称
    private String icon;    //角色对应的图标
    private Integer state;  // 状态(0|1)
    private String descr; // 描述
    private Date createTime;    // 创建时间
}
