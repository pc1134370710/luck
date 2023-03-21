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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

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
        return payOrderResp;
    }

    @Override
    public PayOrderResp getPayCode(String orderId) {
        PayOrderResp payOrderResp = new PayOrderResp();
        payOrderResp.setOrderId(orderId);
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("order_id",orderId);
        PayOrder order = payOrderMapper.selectOne(queryWrapper);
        try {
            String code = aliPay.getCode(orderId,order.getOrderName(),order.getOrderPrice().toPlainString(),"");
            payOrderResp.setQrCodeUrl(code);
        } catch (AlipayApiException e) {
           log.error(" 生成支付二维码失败，orderId="+orderId,e);
        }
        return payOrderResp;
    }
}
