package com.lmt.mbsp.user.biz;

import com.lmt.mbsp.user.vo.account.RegistInfo;

/**
 * @Auther: lex (lex@lmt21.com)
 * @Date: 2018/7/25 上午10:45
 * @Description: 
 */
public interface RegistBiz {

    /**
     * 手机号注册用户
     *
     * @param registInfo 注册用户数据
     * @return 是否注册成功
     * @throws Exception
     */
    Long registByPhone(RegistInfo registInfo) throws Exception;

    /**
     * 邮件注册用户
     * @param registInfo 注册用户数据
     * @return 是否注册成功
     * @throws Exception
     */
    Long registByEmail(RegistInfo registInfo) throws Exception;
}
