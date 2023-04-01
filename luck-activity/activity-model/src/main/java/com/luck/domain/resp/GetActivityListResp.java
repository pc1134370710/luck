package com.luck.domain.resp;

import com.luck.entity.Activity;
import com.luck.entity.Awards;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @description:
 * @author: pangcheng
 * @create: 2023-04-01 15:24
 **/
@Data
@ApiModel(value = "获取活动列表响应参数")
public class GetActivityListResp extends Activity {

    @ApiModelProperty(value = "是否已经参与抽奖了，0否1是")
    private Integer isPay;

    @ApiModelProperty(value = "奖项")
    private List<Awards> awardsList;

}
