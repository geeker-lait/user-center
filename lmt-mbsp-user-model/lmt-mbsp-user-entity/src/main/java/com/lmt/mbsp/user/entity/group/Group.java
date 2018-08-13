package com.lmt.mbsp.user.entity.group;

import lombok.Data;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;
import java.util.List;

/**
 * Auther: Tangsm
 */

@Data
@Entity
@Table(name = "t_group")
public class Group implements com.lmt.framework.support.entity.Entity {
    @Id
    private Long id;                    // id主键
    private Integer type;                // 组类型（0内部组 1非内部组）
}
