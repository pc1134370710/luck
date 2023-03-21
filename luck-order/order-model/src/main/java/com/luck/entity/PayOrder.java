package com.luck.entity;

import java.math.BigDecimal;
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
 * @since 2023-03-21
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("luck_pay_order")
@ApiModel(value="PayOrder对象", description="")
public class PayOrder implements Serializable {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "数据库主键")
    private Long id;

    @ApiModelProperty(value = "订单id")
    private String orderId;
    @ApiModelProperty(value = "订单名称")
    private String orderName;

    @ApiModelProperty(value = "用户主键id")
    private Integer userId;

    @ApiModelProperty(value = "付费知识id")
    private Long pkId;

    @ApiModelProperty(value = "支付金额")
    private BigDecimal payMoney;
    @ApiModelProperty(value = "订单价格")
    private BigDecimal orderPrice;

    @ApiModelProperty(value = "支付状态0：待支付，1：已支付 2：已过期")
    private Integer status;

    @ApiModelProperty(value = "逻辑删除")
    private Integer isDel;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "修改时间")
    private LocalDateTime updateTime;


}
