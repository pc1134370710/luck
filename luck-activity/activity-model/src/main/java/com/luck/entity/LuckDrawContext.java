package com.luck.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @description:
 * @author: pangcheng
 * @create: 2023-03-28 20:28
 **/
@Data
@ApiModel("抽奖数据实体类")
public class LuckDrawContext {


    @ApiModelProperty(value = "抽奖记录")
    private RaffleRecord raffleRecord;


    @ApiModelProperty(value = "抽奖活动")
    private Activity activity;


    @ApiModelProperty(value = "奖项")
    private List<Awards> awardsList ;

}
