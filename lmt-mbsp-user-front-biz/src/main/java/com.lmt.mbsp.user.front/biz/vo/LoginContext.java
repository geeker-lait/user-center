package com.lmt.mbsp.user.front.biz.vo;

import lombok.Data;

import java.io.Serializable;

/*
 * @描述：登录信息
 * @作者：Tangsm
 * @创建时间：2018-07-11 11:00:25
 */
@Data
public class LoginContext implements Serializable {
    private Long userId;        // 用户ID
    private Long accountId;     // 账号ID
    private String realName;    // 真实姓名
    private String username;    // 用户名称
    private String ipAddress;   // IP地址
    private Integer master;     // 是否被授权管理员
    private Long companyId;     // 公司ID
    private Long groupId;       // 组ID

    public static LoginContext getLoginContext(){
        LoginContext context = new LoginContext();
        context.setUserId(1L);
        context.setUsername("tangsm");
        context.setAccountId(1L);
        context.setIpAddress("192.168.1.40");
        context.setMaster(0);
        context.setCompanyId(1L);
        context.setGroupId(1L);

        return context;
    }
}
