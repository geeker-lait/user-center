package com.lmt.mbsp.user.controller.login;

import com.lmt.mbsp.user.entity.group.Group;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/*
 * @描述：登录信息
 * @作者：Tangsm
 * @创建时间：2018-07-11 11:00:25
 */
@Data
public class LoginContext {
    private Long userId;        // 用户ID
    private Long accountId;     // 账号ID
    private Long parentGroupId;    // 顶级父公司ID
    private String username;    // 用户名称
    private String account;     // 账号
    private String ipAddress;   // IP地址
    private Integer manager;    // 管理员类型（0普通管理员 1超级管理员）
    private Integer master;     // 是否被授权管理员
    private Group group;        // 当前组信息（当前部门信息）
    private List<Group> groups; // 父级组集合信息（父级部门信息）

    public static LoginContext getLoginContext(){
        LoginContext context = new LoginContext();
        context.setParentGroupId(1L);
        context.setUserId(1L);
        context.setUsername("tangsm");
        context.setAccount("13888888888");
        context.setAccountId(1L);
        context.setIpAddress("192.168.1.40");
        context.setManager(0);
        context.setMaster(0);

        Group group = new Group();
        group.setId(1L);
        group.setGroupId(1L);
        group.setCode("01");
        group.setCodePath("00");
        group.setName("路贸通集团");
        group.setState(0);

        List<Group> groups = new ArrayList<Group>();
        groups.add(group);

        context.setGroup(group);
        context.setGroups(groups);

        return context;
    }
}
