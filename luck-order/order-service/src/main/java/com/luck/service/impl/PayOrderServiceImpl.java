package com.luck.service.impl;

import com.alipay.api.AlipayApiException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.luck.constant.CommonEnum;
import com.luck.constant.MqConstant;
import com.luck.constant.OrderStatusConstant;
import com.luck.domain.req.AddOrderReq;
import com.luck.domain.req.GetOrderListReq;
import com.luck.domain.resp.PayOrderResp;
import com.luck.entity.PayOrder;
import com.luck.exception.GlobalException;
import com.luck.knowledge.KnowledgeFeignClient;
import com.luck.mapper.PayOrderMapper;
import com.luck.model.UserAuth;
import com.luck.pojo.KnowledgeDomain;
import com.luck.pay.AliPay;
import com.luck.resp.R;
import com.luck.service.IPayOrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.luck.utils.RedisUtils;
import com.luck.utils.UserInfoThreadLocal;
import com.luck.utils.Snowflake;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.apache.rocketmq.spring.support.RocketMQHeaders;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author pc
 * @since 2023-02-21
 */
@Service
public class PayOrderServiceImpl extends ServiceImpl<PayOrderMapper, PayOrder> implements IPayOrderService {
    private Logger log = LoggerFactory.getLogger("PayOrderServiceImpl");
    @Autowired
    private PayOrderMapper payOrderMapper;
    @Autowired
    private AliPay aliPay;

    @Autowired
    private KnowledgeFeignClient knowledgeFeignClient;
    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    @Autowired
    private RedisUtils redisUtils;



    @Transactional(rollbackFor = Exception.class)
    @Override
    public PayOrderResp addOrder(AddOrderReq addOrderReq) {

        // 获取付费知识价格
        R<KnowledgeDomain> knowledgeInfo = knowledgeFeignClient.getKnowledgeInfo(addOrderReq.getPkId());
        if( knowledgeInfo.getCode() != CommonEnum.OK.getCode()){
            throw new GlobalException(R.ERROR(CommonEnum.GET_KNOWLEDGE_INFO_FAIL));
        }
        KnowledgeDomain data = knowledgeInfo.getData();

        PayOrder order = new PayOrder();
        String id = Snowflake.nextId();
        // 生成订单id
        String orderId = id+System.currentTimeMillis()+"";
        order.setId(id);
        order.setOrderId(orderId);
        // 将当前用户添加到 订单信息中
        UserAuth userAuth = UserInfoThreadLocal.get();
        order.setUserId(userAuth.getUserId());

        order.setPkId(addOrderReq.getPkId());
        order.setCreateTime(LocalDateTime.now());
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
        SendResult sendResult = rocketMQTemplate.syncSend( MqConstant.ORDER_STATUS,message,2000,16);
        if(!sendResult.getSendStatus().name().equals("SEND_OK")){
            log.warn("cmd = addOrder | msg = 发送订单mq消息失败 sendResult={}",sendResult);
            throw new GlobalException(R.ERROR(CommonEnum.ORDER_SEND_MSG_FAIL));
        }
        log.info("cmd = addOrder | msg =发送消息到队列中 | sendResult={}",sendResult);
        return payOrderResp;
    }

    @Override
    public PayOrderResp getPayCode(String orderId) {
        log.info("cmd = getPayCode | msg = 获取支付二维码 | orderId={} ",orderId);
        PayOrderResp payOrderResp = new PayOrderResp();
        payOrderResp.setOrderId(orderId);
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("order_id",orderId);
        PayOrder order = payOrderMapper.selectOne(queryWrapper);

        // 订单已过期
        if(order.getStatus() == OrderStatusConstant.ORDER_STATUS_IS_EXT){
            throw new GlobalException(R.ERROR(CommonEnum.ORDER_IS_EXP));
        }
        String key = OrderStatusConstant.getPayCodeCacheKay(orderId);

        // 缓存时间跟 支付宝设置二维码连接过期的时间 一致，半个小时，支付成功后
        // 支付成功后会删除改二维码
        String code = (String) redisUtils.get(key);
        if(code != null){
            log.info("从缓存中取出上次的支付二维码，orderId={} | code={}",orderId,code);
            payOrderResp.setQrCodeUrl(code);
            return payOrderResp;
        }

        try {
            // 开发者需要自己使用工具根据内容生成二维码图片。
             code = aliPay.getCode(orderId,order.getOrderName(),order.getOrderPrice().toPlainString(),"");
             payOrderResp.setQrCodeUrl(code);
            redisUtils.set(key,code,30L, TimeUnit.SECONDS);
        } catch (AlipayApiException e) {
           log.error(" 生成支付二维码失败，orderId="+orderId,e);
        }
        return payOrderResp;
    }

    @Override
    public Page<PayOrder> getOrderList(GetOrderListReq getOrderListReq) {
        UserAuth userAuth = UserInfoThreadLocal.get();
        Page<PayOrder> page =   payOrderMapper.getOrderList(getOrderListReq.getPage(),getOrderListReq, userAuth.getUserId());
        return page;
    }
}
