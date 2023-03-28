package com.luck.domain.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @description:
 * @author: pangcheng
 * @create: 2023-03-21 20:16
 **/
@ApiModel(value = "添加订单请求参数")
@Data
public class AddOrderReq {


    @ApiModelProperty(value = "付费知识id")
    private String pkId;

}
