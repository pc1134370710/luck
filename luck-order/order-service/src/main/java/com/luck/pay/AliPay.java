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
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.luck.constant.CommonEnum;
import com.luck.constant.MqConstant;
import com.luck.entity.PayOrder;
import com.luck.exception.GlobalException;
import com.luck.knowledge.KnowledgeFeignClient;
import com.luck.mapper.PayOrderMapper;
import com.luck.resp.R;
import com.luck.user.UserFeignClient;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.SendStatus;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.apache.rocketmq.spring.support.RocketMQHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import java.util.Map;
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


    @Autowired
    private RocketMQTemplate rocketMQTemplate;
    /**
     *  获取支付二维码
     * https://opendocs.alipay.com/open/02ekfg?scene=19#%E8%AF%B7%E6%B1%82%E7%A4%BA%E4%BE%8B
     *
     * @param orderId  订单id
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
        /*
        订单相对超时时间。 从预下单请求时间开始计算。
该笔订单允许的最晚付款时间，逾期将关闭交易。取值范围：1m～15d。m-分钟，h-小时，d-天，1c-当天（1c-当天的情况下，无论交易何时创建，都在0点关闭）。 该参数数值不接受小数点， 如 1.5h，可转换为 90m。
当面付场景默认值为3h；

注：
1. 二维码最长有效期是2小时，不管该参数传递的值是多少，超过2小时后二维码都将失效不能再进行扫码支付。
2. time_expire和timeout_express两者只需传入一个或者都不传，如果两者都传，优先使用time_expire。

         */
//        model.setTimeoutExpress("30m");
        payRequest.setBizModel(model);
        // 异步回调url
        payRequest.setNotifyUrl(myAliPayConfig.getPayCallbackUrl());
        AlipayTradePrecreateResponse response = myAliPayConfig.getAlipayClient().execute(payRequest);
        log.info("支付请求结果：{}", JSON.toJSONString(response));
        // 验签
//        boolean rsa2 = AlipaySignature.rsaCheckV1(response.getParams(), myAliPayConfig.getPrivateKey(), "utf-8", "RSA2");
//        if (!rsa2){
//            // 验签失败
//            throw new GlobalException(R.ERROR(CommonEnum.FAIL));
//        }

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
    public String payCallback(Map<String, String> params) {
        // 获取订单号
        String orderId = params.get("out_trade_no");
        Message<String> message = MessageBuilder.withPayload(orderId)
                .setHeader(RocketMQHeaders.KEYS, orderId)
                .build();
        // 回调成功后， 将消息投递至MQ中 ，确保权限能100% 加入到用户权限中
        SendResult sendResult = rocketMQTemplate.syncSend(MqConstant.ORDER_PAY_SUCCESS, message);
        if(sendResult.getSendStatus().name().equals(SendStatus.SEND_OK)){
            return "success";
        }
        return "fail";
    }
}