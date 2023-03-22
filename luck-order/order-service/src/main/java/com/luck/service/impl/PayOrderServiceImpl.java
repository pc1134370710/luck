package com.luck.service.impl;

import com.alipay.api.AlipayApiException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.luck.KnowledgeFeignClient;
import com.luck.constant.CommonEnum;
import com.luck.constant.OrderStatusConstant;
import com.luck.domain.req.AddOrderReq;
import com.luck.domain.resp.PayOrderResp;
import com.luck.entity.PayOrder;
import com.luck.exception.GlobalException;
import com.luck.mapper.PayOrderMapper;
import com.luck.model.KnowledgeDomain;
import com.luck.pay.AliPay;
import com.luck.resp.R;
import com.luck.service.IPayOrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.luck.utils.Snowflake;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.apache.rocketmq.spring.support.RocketMQHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author pc
 * @since 2023-03-21
 */
@Service
public class PayOrderServiceImpl extends ServiceImpl<PayOrderMapper, PayOrder> implements IPayOrderService {

    @Autowired
    private PayOrderMapper payOrderMapper;
    @Autowired
    private AliPay aliPay;

    @Autowired
    private KnowledgeFeignClient knowledgeFeignClient;
    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    private static String order_mq_topic="order";

    @Transactional(rollbackFor = Exception.class)
    @Override
    public PayOrderResp addOrder(AddOrderReq addOrderReq) {
        PayOrder order = new PayOrder();
        Long id = Snowflake.nextId();
        // 生成订单id
        String orderId = id+System.currentTimeMillis()+"";
        order.setId(id);
        order.setOrderId(orderId);
        order.setPkId(addOrderReq.getPkId());
        order.setCreateTime(LocalDateTime.now());
        // 获取付费知识价格
        R<KnowledgeDomain> knowledgeInfo = knowledgeFeignClient.getKnowledgeInfo(addOrderReq.getPkId());
        if( knowledgeInfo.getCode() != CommonEnum.OK.getCode()){
            throw new GlobalException(R.ERROR(CommonEnum.GET_KNOWLEDGE_INFO_FAIL));
        }
        KnowledgeDomain data = knowledgeInfo.getData();
        order.setOrderPrice(data.getPayPrice());
        order.setOrderName(data.getName());
        order.setStatus(OrderStatusConstant.ORDER_STATUS_NOT_PAY);
        // 保存到数据库中
        payOrderMapper.insert(order);
        PayOrderResp payOrderResp = new PayOrderResp();
        payOrderResp.setOrderId(orderId);

        Message<String> message = MessageBuilder.withPayload(orderId)
                .setHeader(RocketMQHeaders.KEYS,2).
                        build();
        // 发送到 延迟队列
        // 参数，1, 主题：类型tag||类型tag  2,消息, 3，发送超时时间单位ms，4 延时等级
        // private String messageDelayLevel = "1s 5s 10s 30s 1m 2m 3m 4m 5m 6m 7m 8m 9m 10m 20m 30m 1h 2h";
        SendResult sendResult = rocketMQTemplate.syncSend(order_mq_topic,message,2000,16);
        System.out.println(sendResult);
        return payOrderResp;
    }

    @Override
    public PayOrderResp getPayCode(String orderId) {
        PayOrderResp payOrderResp = new PayOrderResp();
        payOrderResp.setOrderId(orderId);
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("order_id",orderId);
        PayOrder order = payOrderMapper.selectOne(queryWrapper);

        if(order.getStatus() == OrderStatusConstant.ORDER_STATUS_IS_EXT){
            throw new GlobalException(R.ERROR(CommonEnum.ORDER_IS_EXP));
        }

        long until = LocalDateTime.now().until(order.getCreateTime(), ChronoUnit.MINUTES);
        if(until>30){
            // 订单已过期
        }


        try {
            String code = aliPay.getCode(orderId,order.getOrderName(),order.getOrderPrice().toPlainString(),"");
            payOrderResp.setQrCodeUrl(code);
        } catch (AlipayApiException e) {
           log.error(" 生成支付二维码失败，orderId="+orderId,e);
        }
        return payOrderResp;
    }
}
