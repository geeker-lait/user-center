package com.lmt.mbsp.user.service;

import com.lmt.framework.support.service.CrudService;
import com.lmt.mbsp.user.entity.user.User;


/**
 * Auther: lait.zhang@gmail.com
 * Tel:15801818092
 * Date: 6/1/2018 10:03
 * Description:用户服务接口
 */
public interface UserService extends CrudService<User,Long> {
    @Override
    default User createEntity() {
        return new User();
    }
}
