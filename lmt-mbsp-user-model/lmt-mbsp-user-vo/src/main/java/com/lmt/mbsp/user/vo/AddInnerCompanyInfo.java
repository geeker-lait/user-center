package com.lmt.mbsp.user.vo;

import lombok.Data;

/*
 * 描述：创建内部公司所需参数
 * 作者：Tangsm
 * 创建时间：2018-07-24 13:48:38
 */
@Data
public class AddInnerCompanyInfo {
    private Long parentId;  // 父级公司ID
    private String name;    // 当前公司名称
}
