package com.luck.domin.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @description:
 * @author: pangcheng
 * @create: 2023-03-20 23:04
 **/
@ApiModel(value = "注册用户请求参数")
@Data
public class RegisterUserReq {


    @ApiModelProperty(value = "账号")
    @NotNull(message = "账号不能为空")
    private String userName;
    @ApiModelProperty(value = "密码")
    @NotNull(message = "密码不能为空")
    private String password;
    @ApiModelProperty(value = "加密(rsa)key")
    private String key;

}
