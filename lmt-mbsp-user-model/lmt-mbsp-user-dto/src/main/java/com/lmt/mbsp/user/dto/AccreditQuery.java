package com.lmt.mbsp.user.dto;

import lombok.Data;

/**
 * @Auther: lait.zhang@gmail.com
 * @Tel:15801818092
 * @Date: 7/24/2018 20:23
 * @Description:
 */
@Data
public class AccreditQuery extends PageQueryEntity {
    private Long accountId;
    private Long roleId;
    private Long resourceId;
    private Long permissionId;
    private String permissions;
    private Integer state;


}