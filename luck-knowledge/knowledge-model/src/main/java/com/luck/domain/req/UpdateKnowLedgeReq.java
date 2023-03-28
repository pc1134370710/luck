package com.luck.domain.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @description:
 * @author: pangcheng
 * @time: 2023/3/21 17:31
 */
@Data
@ApiModel(value = "修改付费知识请求参数")
public class UpdateKnowLedgeReq {

    @ApiModelProperty(value = "数据库id")
    private String pkId;


    @ApiModelProperty(value = "付费知识名称")
    private String name;

    @ApiModelProperty(value = "付费知识标题")
    private String title;

    @ApiModelProperty(value = "内容")
    private String content;

    @ApiModelProperty(value = "价格")
    private BigDecimal payPrice;
}
