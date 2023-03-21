package com.luck.controller;


import com.luck.domain.req.AddOrderReq;
import com.luck.domain.resp.PayOrderResp;
import com.luck.resp.R;
import com.luck.service.IPayOrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author pc
 * @since 2023-03-21
 */
@Api(value = "订单相关接口")
@RestController
@RequestMapping("/payOrder")
public class PayOrderController {

    @Autowired
    private IPayOrderService payOrderService;


    @PostMapping("/addOrder")
    @ApiOperation(value = "添加订单接口")
    public R<PayOrderResp> addOrder(@RequestBody AddOrderReq addOrderReq){
        return R.OK( payOrderService.addOrder(addOrderReq));
    }

    @GetMapping("/getPayCode/{orderId}")
    @ApiOperation(value = "获取支付二维码")
    public R<PayOrderResp> getPayCode(@PathVariable("orderId") String orderId){
        return R.OK( payOrderService.getPayCode(orderId));

    }



}

