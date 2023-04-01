package com.luck.domain.resp;

import com.luck.entity.PayKnowledge;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @description:
 * @author: pangcheng
 * @time: 2023/2/21 17:42
 */
@ApiModel(value = "付费知识详情")
@Data
public class GetKnowLedgeDetailResp {


    @ApiModelProperty(value = "数据库主键")
    private String id;

    @ApiModelProperty(value = "付费知识名称")
    private String name;

    @ApiModelProperty(value = "付费知识标题")
    private String title;

    @ApiModelProperty(value = "查看人数")
    private Integer lookCount;

    @ApiModelProperty(value = "付费人数")
    private Integer payCount;

    @ApiModelProperty(value = "0不可查看，1可查看")
    private Integer lookPower;
    @ApiModelProperty(value ="收费付费情况0免费，1收费")
    private Integer payLookStatus;


    @ApiModelProperty(value = "价格")
    private BigDecimal payPrice;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "修改时间")
    private LocalDateTime updateTime;

}
