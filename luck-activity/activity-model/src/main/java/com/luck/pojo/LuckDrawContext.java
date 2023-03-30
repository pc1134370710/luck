package com.luck.pojo;

import com.luck.entity.Activity;
import com.luck.entity.Awards;
import com.luck.model.UserAuth;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @description:
 * @author: pangcheng
 * @create: 2023-02-28 20:28
 **/
@Data
@ApiModel("抽奖数据实体类")
public class LuckDrawContext {

    @ApiModelProperty(value = "当前抽奖用户")
    private UserAuth userAuth;

    @ApiModelProperty(value = "抽奖活动")
    private Activity activity;

    @ApiModelProperty(value = "奖项")
    private List<Awards> awardsList ;

}
