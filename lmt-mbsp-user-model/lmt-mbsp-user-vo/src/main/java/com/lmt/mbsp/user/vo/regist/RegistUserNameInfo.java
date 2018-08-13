package com.lmt.mbsp.user.vo.regist;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Auther: lex (lex@lmt21.com)
 * @Date: 2018/7/25 上午10:47
 * @Description:
 */
@Data
public class RegistUserNameInfo extends RegistInfo {
    @ApiModelProperty("用户名")
    private String username;
    @ApiModelProperty("图片验证码")
    private String imageVerifyCode;
    @ApiModelProperty("密码 ")
    private String password;
    @ApiModelProperty("确认密码")
    private String pwdAgain;
}
