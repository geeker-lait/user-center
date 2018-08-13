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
@ApiModel("手机注册信息")
public class RegistMobileInfo extends RegistInfo{
    @ApiModelProperty("手机号")
    private String mobile;
    @ApiModelProperty("手机验证码")
    private String verifyCode;
}
