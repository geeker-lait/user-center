package com.lmt.mbsp.user.front.biz;

import com.lmt.mbsp.user.front.biz.vo.LoginContext;
import com.lmt.mbsp.user.vo.login.LoginInfo;

/**
 * @Auther: lex (lex@lmt21.com)
 * @Date: 2018/8/2 下午4:03
 * @Description: 
 */
public interface LoginBiz {
    /**
     * 登录验证
     *
     * @param loginInfo 登录对象
     * @return 是否登录成功
     * @throws Exception
     */
    Boolean login(LoginInfo loginInfo) throws Exception;

    LoginContext getLoginContext() throws Exception;

    void Logout() throws Exception;
}
