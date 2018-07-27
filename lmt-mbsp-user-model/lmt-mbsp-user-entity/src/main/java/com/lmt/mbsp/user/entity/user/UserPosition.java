package com.lmt.mbsp.user.entity.user;

import com.lmt.framework.support.entity.Entity;
import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/*
 * @描述：用户职位信息
 * @作者：Tangsm
 * @创建时间：2018-07-09 15:31:10
 */
@Data
@javax.persistence.Entity
@Table(name = "t_user_position")
public class UserPosition implements Entity {
    @Id
    private Long id;
    //用户ID
    private Long userId;
    //职位类别ID
    private Long categoryId;
    //职位名称(冗余)
    private String position;
}
