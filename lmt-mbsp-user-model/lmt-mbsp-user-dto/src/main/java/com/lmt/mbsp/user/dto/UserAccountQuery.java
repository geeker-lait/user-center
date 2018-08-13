package com.lmt.mbsp.user.dto;

import com.lmt.framework.support.entity.Entity;
import lombok.Data;

import java.util.List;

/*
 * @描述：用户账号关联表查询参数
 * @作者：Tangsm
 * @创建时间：2018-07-17 15:54:21
 */
@Data
public class UserAccountQuery extends PageQueryEntity{
    private Long id;        // 主键ID
    private Long userId;    // 用户ID
    private Long accountId; // 账号ID
    private List<Long> userIds; // 用ID集合
}
