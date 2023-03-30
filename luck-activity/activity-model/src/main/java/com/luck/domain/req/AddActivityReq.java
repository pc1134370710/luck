package com.luck.domain.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

/**
 * @description:
 * @author: pangcheng
 * @time: 2023/2/2 16:57
 */
@Data
@ApiModel(value = "添加奖项")
public class AddActivityReq {

    @ApiModelProperty(value = "抽奖活动名称")
    private String activityName;
    @ApiModelProperty(value = "抽奖活动描述")
    private String activityMemo;
    @ApiModelProperty(value = "活动截止时间")
    private LocalDateTime endTime;
    @ApiModelProperty(value = "奖项列表")
    private List<AddActivityAwardsReq> awardsList;

}
