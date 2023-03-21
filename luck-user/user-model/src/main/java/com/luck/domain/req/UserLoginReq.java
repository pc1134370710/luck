package com.luck.domain.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @description:
 * @author: pangcheng
 * @create: 2023-03-20 22:15
 **/
@Data
@ApiModel(value = "登录请求参数")
public class UserLoginReq {


    @ApiModelProperty(value = "账号")
    @NotNull(message = "账号不允许为空")
    private String userName;
    @ApiModelProperty(value = "密码")
    @NotNull(message = "密码不允许为空")
    private String password;
    @ApiModelProperty(value = "验证码")
    private String code;
    @ApiModelProperty(value = "rsa 加密key")
    private String key;

}
