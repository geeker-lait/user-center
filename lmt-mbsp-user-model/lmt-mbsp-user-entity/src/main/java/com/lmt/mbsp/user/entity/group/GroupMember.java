package com.lmt.mbsp.user.entity.group;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @Auther: lait.zhang@gmail.com
 * @Tel:15801818092
 * @Date: 5/31/2018 17:51
 * @Description:
 */
@Data
@Entity
@Table(name="t_group_member")
public class GroupMember implements com.lmt.framework.support.entity.Entity {
    @Id
    private Long id;
    //会员ID
    private Long memberId;
    //群组ID;
    private Long groupId;
}
