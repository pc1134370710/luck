package com.luck.entity;

import com.baomidou.mybatisplus.annotation.TableName;
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
@TableName("luck_activity_awards_map")
@ApiModel(value="ActivityAwardsMap对象", description="")
public class ActivityAwardsMap implements Serializable {

    private static final long serialVersionUID=1L;

    private String id;

    @ApiModelProperty(value = "活动id")
    private String ayId;

    @ApiModelProperty(value = "奖项id")
    private String adId;


}
