package com.lmt.mbsp.user.backend.biz.vo;

import lombok.Data;

/*
 * @描述：登录信息
 * @作者：Tangsm
 * @创建时间：2018-07-11 11:00:25
 */
@Data
public class LoginContext {
    private Long userId;        // 用户ID
    private Long accountId;     // 账号ID
    private String username;    // 用户名称
    private String ipAddress;   // IP地址
    private Integer managerType;    // 管理员类型（0普通管理员 1超级管理员）
    private Long companyId;     // 公司ID
    private Long groupId;       // 组ID

    public static LoginContext getLoginContext(){
        LoginContext context = new LoginContext();
        context.setUserId(1L);
        context.setUsername("tangsm");
        context.setAccountId(1L);
        context.setIpAddress("192.168.1.40");
        context.setManagerType(0);
        context.setCompanyId(1L);
        context.setGroupId(1L);

        return context;
    }
}
