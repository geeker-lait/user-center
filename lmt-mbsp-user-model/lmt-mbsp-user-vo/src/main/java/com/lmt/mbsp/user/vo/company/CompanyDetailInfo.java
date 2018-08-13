package com.lmt.mbsp.user.vo.company;

import com.lmt.mbsp.user.vo.company.CompanyImgInfo;
import com.lmt.mbsp.user.vo.operator.OperatorListInfo;
import com.lmt.mbsp.user.vo.role.RoleInfo;
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
    private CompanyInfo companyInfo;    // 公司信息
    private List<CompanyImgInfo> imgs;    // 图片
    private List<RoleInfo> roleInfos;   // 公司已赋角色
}
