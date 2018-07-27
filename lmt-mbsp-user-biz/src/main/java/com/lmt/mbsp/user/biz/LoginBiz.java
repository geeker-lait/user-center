package com.lmt.mbsp.user.biz;

import com.lmt.mbsp.user.vo.LoginInfo;

/**
 * @Auther: lait.zhang@gmail.com
 * @Tel:15801818092
 * @Date: 7/17/2018 11:46
 * @Description:
 */
public interface LoginBiz {


    Object login(LoginInfo loginInfo) throws Exception;
}
