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
 * @since 2023-03-28
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

    @ApiModelProperty(value = "0未中奖，1已中奖")
    private Integer status;

    @ApiModelProperty(value = "奖品id")
    private String adId;

    @ApiModelProperty(value = "抽奖时间")
    private LocalDateTime createTime;


}
