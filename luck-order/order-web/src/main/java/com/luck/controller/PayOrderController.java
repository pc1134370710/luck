package com.luck.controller;


import cn.hutool.extra.qrcode.QrCodeUtil;
import cn.hutool.http.server.HttpServerResponse;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.luck.domain.req.AddOrderReq;
import com.luck.domain.req.GetOrderListReq;
import com.luck.domain.resp.PayOrderResp;
import com.luck.entity.PayOrder;
import com.luck.pay.AliPay;
import com.luck.resp.R;
import com.luck.service.IPayOrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

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

    @Autowired
    private AliPay aliPay;

    @PostMapping("/addOrder")
    @ApiOperation(value = "添加订单接口")
    public R<PayOrderResp> addOrder(@RequestBody AddOrderReq addOrderReq){
        return R.OK( payOrderService.addOrder(addOrderReq));
    }

    @GetMapping("/getPayCode/{orderId}")
    @ApiOperation(value = "获取支付二维码")
    public void getPayCode(@PathVariable("orderId") String orderId, HttpServletResponse response) throws IOException {
        PayOrderResp payCode = payOrderService.getPayCode(orderId);
        QrCodeUtil.generate(payCode.getQrCodeUrl(), 67+12*(30-1), 67+12*(30-1),"png",response.getOutputStream());
        return;
    }

    @PostMapping("/getOrderList")
    @ApiOperation(value = "获取订单列表")
    public R<Page<PayOrder>> getOrderList(@RequestBody GetOrderListReq getOrderListReq){
        Page<PayOrder> page =  payOrderService.getOrderList(getOrderListReq);
        return R.OK();

    }


    @GetMapping("/payCallUrl")
    @ApiOperation(value = "支付宝回调")
    public R<String> payCallUrl(HttpServletRequest request){
        System.out.println("支付宝回调");

        Map<String, String[]> requestParams = request.getParameterMap();
        // 获取支付宝POST过来反馈信息
        Map<String, String> params = new HashMap<>();
        // 循环遍历支付宝请求过来的参数存入到params中
        for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext(); ) {
            String name = (String) iter.next();
            String[] values = requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            // 乱码解决，这段代码在出现乱码时使用。
            //valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
            params.put(name, valueStr);
        }

        aliPay.payCallback( params);
        return R.OK("success");
    }


}

