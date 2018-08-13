package com.lmt.mbsp.user.vo.regist;

import io.swagger.annotations.ApiModel;

/**
 * @Auther: lex (lex@lmt21.com)
 * @Date: 2018/7/25 上午10:47
 * @Description:
 */
@ApiModel("注册错误返回码")
public enum RegistErrorInfo {
    EXIST_MOBILE,
    EXIST_USERNAME,
    DIFF_PASSWORD,
    /**
     * 用户名密码为空
     */
    USERNAME_AND_PASSWORD,
    MOBILE_AND_PASSWORD,
    WRONG_VERIFYCODE_MOBILE,
    WRONG_VERIFYCODE_IMAGE,

}
