package com.lmt.mbsp.user.entity.group;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/*
 * @描述：公司信息
 * @作者：Tangsm
 * @创建时间：2018-07-09 15:23:50
 */
@Data
@javax.persistence.Entity
@Table(name = "t_group_info")
public class GroupInformation implements com.lmt.framework.support.entity.Entity{
    @Id
    private Long id;
    //组织ID
    private Long groupId;
    //法人
    private String legalPerson;
    //公司类型（0个人独资 1有限责任公司 2股份制公司 3集团公司 4联营企业 5外商投资企业 6中外合资经营企业 7国有 8私营企业 9全民所有制 10集体所有制...）
    private Integer typ;
    //经营期限
    private String validity;
    //经营范围
    private String businessScope;
    //注册资金
    private String capital;
    //注册地
    private String address;
    //公司电话
    private String phone;
    //状态
    private Integer state;
    //创建时间
    private Date createTime;
    private String note;    // 审核备注
}
