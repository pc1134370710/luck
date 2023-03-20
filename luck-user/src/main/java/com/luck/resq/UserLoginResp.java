package com.luck.resq;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @description:
 * @author: pangcheng
 * @create: 2023-03-20 22:20
 **/
@Data
@ApiModel(value = "登录成功返回参数")
public class UserLoginResp {


    @ApiModelProperty(value = "唯一标识")
    private Integer userId;

    @ApiModelProperty(value = "账号")
    private String userName;
    @ApiModelProperty(value = "token")
    private String token;

}
