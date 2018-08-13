package com.lmt.mbsp.user.vo.regist;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;

/**
 * @Auther: lex
 * @Date: 2018/7/31 下午1:46
 * @Description:
 */
@ApiModel("注册方式")
public enum RegistWay {
    /**
     * 账号注册
     */
    @ApiModelProperty("账号密码注册") USERNAME,
    /**
     * 手机注册
     */
    @ApiModelProperty("手机注册")
    MOBILE,
    /**
     * 邮箱注册
     */
    @ApiModelProperty("邮箱注册")
    EMAIL,
    /**
     * 其他（第三方）注册
     */
    @ApiModelProperty("其他注册方式")
    OTHER
}
