package com.luck.pay;

import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayApiException;
import com.alipay.api.domain.AlipayTradePrecreateModel;
import com.alipay.api.domain.AlipayTradeQueryModel;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradePrecreateRequest;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.response.AlipayTradePrecreateResponse;
import com.alipay.api.response.AlipayTradeQueryResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Objects;


/**
 *
 *  https://opendocs.alipay.com/open/02np94
 *
 */
@Slf4j
@Component
public class AliPay {

    @Autowired
    private MyAliPayConfig myAliPayConfig;




    /**
     *  获取支付二维码
     * https://opendocs.alipay.com/open/02ekfg?scene=19#%E8%AF%B7%E6%B1%82%E7%A4%BA%E4%BE%8B
     *
     * @param outTradeNo  订单id
     * @param subject     商品名称
     * @param totalAmount 金额
     * @param body        其它参数
     * @return 二维码地址
     * @throws AlipayApiException
     */
    public String getCode(String orderId, String subject, String totalAmount, String body) throws AlipayApiException {
        log.info("支付请求参数：orderId={}，subject={}，totalAmount={}，body={}", orderId, subject, totalAmount, body);
        //统一收单线下交易预创建
        AlipayTradePrecreateRequest payRequest = new AlipayTradePrecreateRequest();
        AlipayTradePrecreateModel model = new AlipayTradePrecreateModel();
        // 订单id
        model.setOutTradeNo(orderId);
        //   // (必填) 订单标题，粗略描述用户的支付目的。如“xxx品牌xxx门店当面付扫码消费”
        model.setSubject(subject);
        // 金额
        model.setTotalAmount(totalAmount);
        // 描述
        model.setBody(body);
        payRequest.setBizModel(model);
        // 异步回调url
        payRequest.setNotifyUrl(myAliPayConfig.getPayCallbackUrl());
        AlipayTradePrecreateResponse response = myAliPayConfig.getAlipayClient().execute(payRequest);
        log.info("支付请求结果：{}", JSON.toJSONString(response));
        return response.getQrCode();
    }

    /**
     * 查询订单支付状态
     *
     * @param orderId 订单编号
     * @return
     * @throws AlipayApiException
     */
    public String tradeQuery(String orderId) throws AlipayApiException {
        log.info("订单编号：{}", orderId);
        AlipayTradeQueryRequest queryRequest = new AlipayTradeQueryRequest();
        AlipayTradeQueryModel queryModel = new AlipayTradeQueryModel();
        queryModel.setOutTradeNo(orderId);
        queryRequest.setBizModel(queryModel);
        AlipayTradeQueryResponse queryResponse = myAliPayConfig.getAlipayClient().execute(queryRequest);
        log.info("订单信息：{}", JSON.toJSONString(queryResponse));
        return queryResponse.getTradeStatus();
    }

    /**
     * https://opendocs.alipay.com/open/194/103296
     * https://blog.csdn.net/weixin_44004020/article/details/111472797
     *
     */
    public void payCallback() {

    }
}