package com.lmt.mbsp.user.vo;

import lombok.Data;

/*
 * 描述：新增企业商户所需参数
 * 作者：Tangsm
 * 创建时间：2018-07-24 15:54:14
 */
@Data
public class AddCompanyInfo {
    private String name;        // 公司名
    private String phone;       // 电话
    private String legalPerson;    // 法人
    private Integer type;           // 公司类型（0个人独资 1有限责任公司 2股份制公司 3集团公司 4联营企业 5外商投资企业 6中外合资经营企业 7国有 8私营企业 9全民所有制 10集体所有制...）
    private String validity;        // 经营期限
    private String businessScope;   // 经营范围
    private String address;         // 注册地
    private String capital;         // 注册资金
    private String organizationCode;    // 组织机构代码

    // certificate 证书
    // 授权书
}
