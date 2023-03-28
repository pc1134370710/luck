package com.luck.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.time.LocalDateTime;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

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
@TableName("luck_activity")
@ApiModel(value="Activity对象", description="")
public class Activity implements Serializable {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "主键")
    private String id;

    @ApiModelProperty(value = "抽奖活动名称")
    private String activityName;

    @ApiModelProperty(value = "活动描述")
    private String activityMemo;

    private LocalDateTime createTime;


    @ApiModelProperty(value = "活动参与人数")
    private Integer payUserCount;

    @ApiModelProperty(value = "活动截止时间")
    private LocalDateTime endTime;


}
