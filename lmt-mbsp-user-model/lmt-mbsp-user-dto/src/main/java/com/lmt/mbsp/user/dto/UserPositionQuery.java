package com.lmt.mbsp.user.dto;

import com.lmt.framework.support.entity.Entity;
import lombok.Data;

/*
 * @描述：用户地址查询参数
 * @作者：Tangsm
 * @创建时间：2018-07-17 15:57:55
 */
@Data
public class UserPositionQuery extends PageQueryEntity{
    private Long id;        // 主键ID
    private Long userId;    // 用户ID
    private Long categoryId;    // 职位类别ID
}
