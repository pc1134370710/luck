package com.luck.domain.resp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @description:
 * @author: pangcheng
 * @time: 2023/3/28 17:58
 */
@Data
@ApiModel(value = "抽奖 结果")
public class LuckDrawResp {
    @ApiModelProperty(value = "0未中奖，1中奖")
    private Integer luckDrawStatus;

    @ApiModelProperty(value = "奖项名称")
    private String awardsName;

    @ApiModelProperty(value = "抽奖活动名称")
    private String activityName;
    @ApiModelProperty(value = "活动id")
    private String activityId;


}
