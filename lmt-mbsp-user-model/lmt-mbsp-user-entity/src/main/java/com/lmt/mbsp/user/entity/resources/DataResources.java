package com.lmt.mbsp.user.entity.resources;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @Auther: lait.zhang@gmail.com
 * @Tel:15801818092
 * @Date: 6/1/2018 14:07
 * @Description:
 */
@Entity
@Data
@Table(name="t_data_resources")
public class DataResources  implements com.lmt.framework.support.entity.Entity {
    //主键自增
    @Id
    private Long id;
    private String name; //资源名称
    private String uri; //资源定位
    private String icon; //资源icon图标
    private Integer state; //状态(0|1)
    private String code;// 编码
    private String parent_code;// 父编码
    private String description; // 描述
    private Long createTime;    // 创建时间
}
