package com.lmt.mbsp.user.dto;

import com.lmt.framework.support.entity.QueryEntity;
import lombok.Data;

/**
 * @描述: 分页查询对象.
 * @作者: lijing.
 * @创建时间: 2018-06-28 9:04.
 * @版本: 1.0 .
 */
@Data
public class PageQueryEntity implements QueryEntity {
    private Integer pageNum;
    private Integer pageSize;
    private Integer total;



}
