package com.lmt.mbsp.user.vo.account;

import com.lmt.mbsp.user.entity.account.AccountName;
import lombok.Data;

import java.util.List;

/*
 * 描述：进入编码账号页面需展示的参数
 * 作者：Tangsm
 * 创建时间：2018-07-27 13:37:45
 */
@Data
public class ToEditAccountInfo {
    private Long accountId;    // 账号主键ID
    private String accountName; // 主账号名称
    private String sonName; // 子账号名称
    private Long sonId;     // 账号名称表主键ID
    private Integer type;   // 账号名称表类型
}
