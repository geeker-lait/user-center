package com.lmt.mbsp.user.vo.regist;

import com.lmt.mbsp.user.vo.regist.RegistWay;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Auther: lex (lex@lmt21.com)
 * @Date: 2018/7/25 上午10:47
 * @Description:
 */
@Data
public class RegistInfo {
    @ApiModelProperty(value = "注册途径（0主账号，1手机，2邮箱，3第三方）", hidden = true)
    private RegistWay registWay;
    @ApiModelProperty(value = "第三方注册渠道:待定", hidden = true)
    private Integer channel;
}
