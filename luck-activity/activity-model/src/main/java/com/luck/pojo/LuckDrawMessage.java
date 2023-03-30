package com.luck.pojo;

import com.luck.model.UserAuth;
import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * @description:
 * @author: pangcheng
 * @time: 2023/2/28 16:26
 */
@Data
@ApiModel(value = "抽奖mq消息对象")
public class LuckDrawMessage {

    /**
     * 当前登录用户信息
     */
    private UserAuth userAuth;
    /**
     * 活动id
     */
    private String activityId;
}
