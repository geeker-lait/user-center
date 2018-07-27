package com.lmt.mbsp.user.entity.account;

import com.lmt.framework.support.entity.Entity;
import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @Auther: lait.zhang@gmail.com
 * @Tel:1580181
 * @Date: 5/31/2018 17:50
 * @Description:
 */
@Data
@Table(name = "t_account_role")
public class AccountRole implements Entity {
    @Id
    private Long id;
    private Long roleId;
    private Long accountId;

}
