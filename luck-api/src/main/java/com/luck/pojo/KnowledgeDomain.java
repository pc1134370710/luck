package com.luck.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @description:
 * @author: pangcheng
 * @create: 2023-03-21 20:57
 **/
@Data
@ApiModel(value = "付费知识")
public class KnowledgeDomain {


    @ApiModelProperty(value = "数据库主键")
    private Long id;

    @ApiModelProperty(value = "付费知识名称")
    private String name;

    @ApiModelProperty(value = "付费知识标题")
    private String title;

    @ApiModelProperty(value = "内容")
    private String content;

    @ApiModelProperty(value = "查看人数")
    private Integer lookCount;

    @ApiModelProperty(value = "付费人数")
    private Integer payCount;

    @ApiModelProperty(value = "价格")
    private BigDecimal payPrice;
    @ApiModelProperty(value = "当前知识状态，0不可见，1可见（对已付费用户不影响）")
    private Integer status;

}
