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
 * @since 2023-03-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("luck_pay_power")
@ApiModel(value="PayPower对象", description="")
public class PayPower implements Serializable {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "数据库主键")
    private Long id;

    @ApiModelProperty(value = "付费知识id")
    private Integer pkId;

    @ApiModelProperty(value = "用户主键id")
    private Integer userId;


}