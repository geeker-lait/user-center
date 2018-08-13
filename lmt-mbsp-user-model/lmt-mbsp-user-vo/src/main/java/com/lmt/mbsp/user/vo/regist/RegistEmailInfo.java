package com.lmt.mbsp.user.vo.regist;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Auther: lex
 * @Date: 2018/8/6 下午1:43
 * @Description:
 */
@Data
@ApiModel("邮件注册信息")
public class RegistEmailInfo extends RegistInfo{
    @ApiModelProperty("电子邮箱")
    private String email;
    @ApiModelProperty("图片验证码")
    private String imageVerifyCode;
    @ApiModelProperty("密码 ")
    private String password;
    @ApiModelProperty("确认密码")
    private String pwdAgain;
}
