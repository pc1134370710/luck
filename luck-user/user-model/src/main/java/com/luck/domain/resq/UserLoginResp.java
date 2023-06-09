package com.luck.domain.resq;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @description:
 * @author: pangcheng
 * @create: 2023-02-20 22:20
 **/
@Data
@ApiModel(value = "登录成功返回参数")
public class UserLoginResp {


    @ApiModelProperty(value = "唯一标识")
    private String userId;

    @ApiModelProperty(value = "账号")
    private String userName;
    @ApiModelProperty(value = "token")
    private String token;
    /**
     * 用户类型
     */
    private Integer userType;

}
