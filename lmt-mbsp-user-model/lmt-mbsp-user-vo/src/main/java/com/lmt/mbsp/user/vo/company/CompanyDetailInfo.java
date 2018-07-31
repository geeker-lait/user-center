package com.lmt.mbsp.user.vo.company;

import com.lmt.mbsp.user.vo.company.CompanyImgInfo;
import lombok.Data;

import java.util.Date;
import java.util.List;

/*
 * 描述：企业商户详细
 * 作者：Tangsm
 * 创建时间：2018-07-24 19:16:56
 */
@Data
public class CompanyDetailInfo {
    private Long id;                     // 主键ID
    private String name;                // 组织名称
    private Long groupId;              // 顶级公司ID
    private Long pid;                   // 父公司ID
    private String organizationCode;    // 组织机构代码证
    private String typ;                 // 组类型（0内部组 1非内部组）
    private Integer state;              // 状态
    private Integer auditState;        // 审核状态（0待审核 1审核通过2审核拒绝）
    private Date createTime;            // 创建时间

    private String legalPerson;         // 法人
    private Integer type;               // 公司类型（0个人独资 1有限责任公司 2股份制公司 3集团公司 4联营企业 5外商投资企业 6中外合资经营企业 7国有 8私营企业 9全民所有制 10集体所有制...）
    private String validity;            // 经营期限
    private String businessScope;       // 经营范围
    private String capital;             // 注册资金
    private String address;             // 注册地
    private String phone;               // 公司电话
    private String note;                // 审核备注

    private List<CompanyImgInfo> imgs;    // 图片
}
