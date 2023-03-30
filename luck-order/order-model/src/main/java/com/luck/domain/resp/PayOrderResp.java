package com.luck.domain.resp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @description:
 * @author: pangcheng
 * @create: 2023-02-21 21:55
 **/
@Data
@ApiModel(value = "生成订单返回")
public class PayOrderResp {

    @ApiModelProperty(value = "订单编号")
    private String orderId;

    @ApiModelProperty(value = "付款二维码地址")
    private String qrCodeUrl;

}
