package com.lmt.mbsp.user.vo.login;

import com.lmt.framework.support.entity.Entity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Auther: lait.zhang@gmail.com
 * @Tel:15801818092
 * @Date: 7/17/2018 11:57
 * @Description:
 */
@Data
@ApiModel(value = "登录信息")
public class LoginInfo implements Entity {

    @ApiModelProperty("账号（手机号）")
    private String accountName;

    @ApiModelProperty("密码")
    private String password;

    @ApiModelProperty("")
    private String cataptchCode;

    private Boolean remember;
}
