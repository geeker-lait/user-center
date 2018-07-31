package com.lmt.mbsp.user.vo.company;

import lombok.Data;

/*
 * 描述：组图片信息
 * 作者：Tangsm
 * 创建时间：2018-07-24 17:05:51
 */
@Data
public class CompanyImgInfo {
    private Long id;    // 主键ID
    private String imgUrl; // 图片地址
    private Integer type;   // 证件类型(0三证合一 1营业执照 2经营许可证 3税务登记证 4管理员授权书 5法人身份证）
}