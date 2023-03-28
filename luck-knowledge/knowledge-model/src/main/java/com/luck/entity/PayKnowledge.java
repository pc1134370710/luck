package com.luck.entity;

import com.baomidou.mybatisplus.annotation.TableName;

import java.math.BigDecimal;
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
 * @since 2023-03-21
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("luck_pay_knowledge")
@ApiModel(value="PayKnowledge对象", description="")
public class PayKnowledge implements Serializable {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "数据库主键")
    private String id;

    @ApiModelProperty(value = "付费知识名称")
    private String name;

    @ApiModelProperty(value = "付费知识标题")
    private String title;

    @ApiModelProperty(value = "内容")
    private String content;

    @ApiModelProperty(value = "查看人数")
    private Integer lookCount;

    @ApiModelProperty(value = "付费人数")
    private Integer payCount;

    @ApiModelProperty(value = "价格")
    private BigDecimal payPrice;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "修改时间")
    private LocalDateTime updateTime;


}
