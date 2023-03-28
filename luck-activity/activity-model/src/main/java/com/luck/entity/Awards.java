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
@TableName("luck_awards")
@ApiModel(value="Awards对象", description="")
public class Awards implements Serializable {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "主键")
    private String id;

    @ApiModelProperty(value = "付费知识id")
    private String pkId;

    @ApiModelProperty(value = "奖项库存剩余量")
    private Integer stock;

    @ApiModelProperty(value = "奖项数量")
    private Integer cnt;

    @ApiModelProperty(value = "奖项名称（几等奖 ）")
    private String awardsName;


}
