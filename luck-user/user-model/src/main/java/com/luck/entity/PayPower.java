package com.luck.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author pc
 * @since 2023-02-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("luck_pay_power")
@ApiModel(value="PayPower对象", description="")
public class PayPower implements Serializable {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "数据库主键")
    private String id;

    @ApiModelProperty(value = "付费知识id")
    private String pkId;

    @ApiModelProperty(value = "用户主键id")
    private String userId;


}
