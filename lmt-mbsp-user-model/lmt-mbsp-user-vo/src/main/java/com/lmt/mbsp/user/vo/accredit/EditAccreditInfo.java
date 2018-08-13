package com.lmt.mbsp.user.vo.accredit;

import lombok.Data;

import java.util.List;

/**
 * @Auther: lait.zhang@gmail.com
 * @Tel:15801818092
 * @Date: 7/24/2018 20:23
 * @Description:
 */
@Data
public class EditAccreditInfo {
    private Long id;
    private Integer state;
    private Long roleId;
    // 资源ID
    private List<Long> resourceIds;
    //  权限集合
    private List<String> permissions;
}
