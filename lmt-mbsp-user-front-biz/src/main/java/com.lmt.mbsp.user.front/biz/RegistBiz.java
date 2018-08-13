package com.lmt.mbsp.user.front.biz;

import com.lmt.mbsp.user.vo.regist.RegistInfo;

/**
 * @Auther: lex (lex@lmt21.com)
 * @Date: 2018/7/25 上午10:45
 * @Description:
 */
public interface RegistBiz {
    /**
     * 前台用户注册
     *
     * @param info
     * @return
     * @throws Exception
     */
    Long regist(RegistInfo info) throws Exception;

    /**
     * 发送注册验证码
     *
     * @param mobile 手机号码
     * @return
     */
    Boolean sendSms(String mobile) throws Exception;

    /**
     * 验证手机验证码
     *
     * @param mobile
     * @param code
     * @return
     * @throws Exception
     */
    Boolean verifySms(String mobile, String code) throws Exception;
}
