package com.lmt.mbsp.user.vo;

import lombok.Data;

import java.util.Date;
import java.util.List;

/*
 * 描述：用户中心首页需展示的数据
 * 作者：Tangsm
 * 创建时间：2018-07-25 11:54:34
 */
@Data
public class IndexInfo {
    private Long id;                     // 主键ID
    private String name;                // 组织名称
    private String organizationCode;    // 组织机构代码证
    private Integer state;              // 状态
    private Integer auditState;        // 审核状态（0待审核 1审核通过2审核拒绝）
    private Date createTime;            // 创建时间

    private String legalPerson;         // 法人
    private Integer typ;               // 公司类型（0个人独资 1有限责任公司 2股份制公司 3集团公司 4联营企业 5外商投资企业 6中外合资经营企业 7国有 8私营企业 9全民所有制 10集体所有制...）
    private String validity;            // 经营期限
    private String businessScope;       // 经营范围
    private String capital;             // 注册资金
    private String address;             // 注册地
    private String phone;               // 公司电话

    private List<GroupImgInfo> imgs;    // 图片
    private UserInfo userInfo;          // 用户信息
    private List<RoleInfo> userRole;     // 用户角色信息
}
