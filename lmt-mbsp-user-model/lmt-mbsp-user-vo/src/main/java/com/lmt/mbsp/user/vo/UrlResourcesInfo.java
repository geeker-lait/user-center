package com.lmt.mbsp.user.vo;

import lombok.Data;

import java.util.Date;

/**
 * @Auther: lait.zhang@gmail.com
 * @Tel:15801818092
 * @Date: 7/19/2018 15:45
 * @Description:
 */
@Data
public class UrlResourcesInfo {
    private Long id; //主键自增
    private String name; //资源名称
    private String uri; //资源定位
    private String icon; //资源icon图标
    private Integer state; //状态(0|1)
    private String code;// 编码
    private String parentCode;// 父编码
    private String description; // 描述
    private Integer sort;      // 序号
    private Date createTime;    // 创建时间
}
