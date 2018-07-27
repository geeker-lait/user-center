package com.lmt.mbsp.user.entity.role;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @Auther: lait.zhang@gmail.com
 * @Tel:15801818092
 * @Date: 5/31/2018 17:48
 * @Description: 角色
 */
@Entity
@Data
@Table(name = "t_role")
public class Role  implements com.lmt.framework.support.entity.Entity {
    @Id
    private Long id;
    private String name;
    //角色对应的图标
    private String icon;
    // 状态(0|1)
    private Integer state;
    // 创建时间
    private Date createTime;
}
