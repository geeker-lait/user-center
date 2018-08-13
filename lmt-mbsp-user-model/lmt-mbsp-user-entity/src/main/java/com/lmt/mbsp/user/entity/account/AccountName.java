package com.lmt.mbsp.user.entity.account;

import com.lmt.framework.support.entity.Entity;
import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @Auther: lait.zhang@gmail.com
 * @Tel:15801818092
 * @Date: 5/31/2018 17:51
 * @Description:
 */
@Data
@Table(name = "t_account_name")
public class AccountName implements Entity {
    @Id
    private Long id;
    // 账号ID
    private Long accountId;
    // 账号名
    private String accountName;

    // 类型(0 主账号、1 手机账号 、2 邮箱账号、 3 其他[微信、微博]...)
    private Integer type;

    // 密码
    //private String password;
    // 来源、通道
    //private Integer channel;
}
