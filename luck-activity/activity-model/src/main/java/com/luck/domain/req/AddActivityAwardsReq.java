package com.luck.domain.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalTime;

/**
 * @description:
 * @author: pangcheng
 * @time: 2023/22023/2 16:57
 */
@Data
@ApiModel(value = "添加活动奖项")
public class AddActivityAwardsReq {

    @ApiModelProperty(value = "奖项名称（几等奖 ）")
    private String awardsName;
    @ApiModelProperty(value = "付费知识id")
    private String  pkId;
    @ApiModelProperty(value = "'奖项数量'")
    private Integer  cnt;


}
