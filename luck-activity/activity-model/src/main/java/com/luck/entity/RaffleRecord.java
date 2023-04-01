package com.luck.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author pc
 * @since 2023-02-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("luck_raffle_record")
@ApiModel(value="RaffleRecord对象", description="")
public class RaffleRecord implements Serializable {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "用户id")
    private String id;

    private String userId;

    @ApiModelProperty(value = "抽奖活动id")
    private String ayId;

    @ApiModelProperty(value = "0正在抽奖，1抽奖完成")
    private Integer status;
    @ApiModelProperty(value = "是否中奖，0否，1是")
    private Integer isDraw;

    @ApiModelProperty(value = "奖品id")
    private String adId;

    @ApiModelProperty(value = "奖品名称")
    private String awardsName;
    @ApiModelProperty(value = "抽奖活动名称")
    private String activityName;

    @ApiModelProperty(value = "抽奖时间")
    private LocalDateTime createTime;


}
