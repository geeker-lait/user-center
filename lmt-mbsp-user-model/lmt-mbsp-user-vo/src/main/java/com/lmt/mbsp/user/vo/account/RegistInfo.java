package com.lmt.mbsp.user.vo.account;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Auther: lex (lex@lmt21.com)
 * @Date: 2018/7/25 上午10:47
 * @Description:
 */
@Data
public class RegistInfo {
    @ApiModelProperty("手机号或邮箱地址")
    private String account;
    @ApiModelProperty("第三方注册渠道:待定")
    private Integer channel;
    @ApiModelProperty("手机号")
    private String mobile;
    @ApiModelProperty("电子邮箱")
    private String email;
    @ApiModelProperty("用户名")
    private String username;
    @ApiModelProperty("密码 ")
    private String password;
    @ApiModelProperty("确认密码")
    private String pwdAgain;
    @ApiModelProperty("手机验证码")
    private String mobileVerifyCode;
    @ApiModelProperty("图片验证码")
    private String imageVerifyCode;
}
