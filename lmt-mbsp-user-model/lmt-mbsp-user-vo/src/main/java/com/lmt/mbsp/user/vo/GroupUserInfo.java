package com.lmt.mbsp.user.vo;

import lombok.Data;

/**
 * 描述: 组用户查询参数.
 * 作者: Tangsm.
 * 创建时间: 2018-07-05 16:09.
 */
@Data
public class GroupUserInfo {
    // 用户ID
    private Long userId;
    // 组ID
    private Long groupId;
}
