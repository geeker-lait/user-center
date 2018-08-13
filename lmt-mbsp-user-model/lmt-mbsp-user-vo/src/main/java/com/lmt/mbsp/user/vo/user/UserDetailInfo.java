package com.lmt.mbsp.user.vo.user;

import com.lmt.mbsp.user.vo.account.AccountListInfo;
import com.lmt.mbsp.user.vo.company.CompanyInfo;
import com.lmt.mbsp.user.vo.role.RoleInfo;
import lombok.Data;

import java.util.List;
import java.util.Map;

/*
 * 描述：用户详细页需展示的参数
 * 作者：Tangsm
 * 创建时间：2018-07-18 11:46:14
 */
@Data
public class UserDetailInfo {
    private UserInfo user;              // 用户信息
    private AccountListInfo account; // 账号信息
    private List<CompanyInfo> companyInfos;   // 所属公司
}
