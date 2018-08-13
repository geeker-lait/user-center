package com.lmt.mbsp.user.vo.enumutil;

/*
 * 描述：状态枚举
 * 作者：Tangsm
 * 创建时间：2018-08-01 17:29:59
 */
public enum  StatusEnum {
    // 禁用、激活、密码锁定禁用
    DISABLED("1"),
    UNDISABLED("0"),
    PASSWORD_LOCK("2"),
    // 超管管理员、普通系统管理员
    SUPER_ADMIN("1"),
    SYSTEM_ADMIN("0"),
    // 企业管理员、企业普通用户
    MASTER("1"),
    CANCEL_MASTER("0"),
    // 用户名、手机、邮箱
    USERNAME_TYPE("0"),
    MOBILE_TYPE("1"),
    EMAIL_TYPE("2"),
    // 公司类型、部门类型
    COMPANY_TYPE("0"),
    DEPT_TYPE("1"),
    // 待审核、审核通过、审核拒绝
    WAIT_AUDIT("0"),
    AUDIT_REJECT("1"),
    AUDIT_PASS("2"),
    ;


    private final String status;

    public Integer getStatus(){
        return Integer.valueOf(this.status);
    }

    StatusEnum(String status) {
        this.status = status;
    }

    public static void main(String[] args) {
        System.out.println(StatusEnum.DISABLED.getStatus());
    }
}
