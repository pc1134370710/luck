package com.luck.pojo.knowledge;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @description:
 * @author: pangcheng
 * @time: 2023/2/21 17:31
 */
@Data
@ApiModel(value = "修改付费知识请求参数")
public class AddKnowLedgePayCountDomain {

    @ApiModelProperty(value = "数据库id")
    private String pkId;

    @ApiModelProperty(value = "付费人数")
    private Integer payCount;

}
