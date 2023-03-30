package com.luck.pojo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @description:
 * @author: pangcheng
 * @time: 2023/2/28 18:00
 */
@Data
@ApiModel(value = "扣减库存的消息")
public class LuckDrawStockMessage {

    @ApiModelProperty(value = "抽奖活动id")
    private String activityId;
    @ApiModelProperty(value = "抽奖记录id")
    private String recordId;
    @ApiModelProperty(value = "奖品id")
    private String awardsId;

    @ApiModelProperty(value = "用户id")
    private String userId;
    @ApiModelProperty(value = "中奖的商品id")
    private String pkId;




}
