package com.lmt.mbsp.user.entity.group;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @Auther: lait.zhang@gmail.com
 * @Tel:15801818092
 * @Date: 5/31/2018 17:52
 * @Description:
 */
@Data
@Entity
@Table(name="t_group_user")
public class GroupUser  implements com.lmt.framework.support.entity.Entity {
    @Id
    Long id; // 主键自增
    private Long groupId;   // 组ID
    private Long userId;    // 用户ID
    private Integer type;   // 组类型（0公司 1部门）
    private Date createTime;    // 创建时间
}
