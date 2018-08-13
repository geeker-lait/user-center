package com.lmt.mbsp.user.entity.user;

import lombok.Data;
import lombok.ToString;

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
@Entity
@Data
@ToString
@Table(name = "t_user_account")
public class UserAccount  implements com.lmt.framework.support.entity.Entity {
    @Id
    private Long id;
    // 用户id
    private Long userId;
    // 账号id
    private Long accountId;
    // 创建时间
    private Date createTime;
}
