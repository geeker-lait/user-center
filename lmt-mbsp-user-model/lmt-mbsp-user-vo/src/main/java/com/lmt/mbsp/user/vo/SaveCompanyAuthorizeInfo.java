package com.lmt.mbsp.user.vo;

import lombok.Data;

/*
 * 描述：保存企业商户授权参数
 * 作者：Tangsm
 * 创建时间：2018-07-24 19:08:06
 */
@Data
public class SaveCompanyAuthorizeInfo {
    private Long id;    // 公司主键ID
    private String roleIds; // 已选角色ID集合
}
