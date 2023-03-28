package com.luck.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.luck.domain.req.AddOrderReq;
import com.luck.domain.req.GetOrderListReq;
import com.luck.domain.resp.PayOrderResp;
import com.luck.entity.PayOrder;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author pc
 * @since 2023-03-21
 */
public interface IPayOrderService extends IService<PayOrder> {

    /**
     * 添加订单接口
     * @param addOrderReq
     * @return
     */
    PayOrderResp addOrder(AddOrderReq addOrderReq);

    /**
     * 获取 支付二维码接口
     * @param orderId
     * @return
     */
    PayOrderResp getPayCode(String orderId);

    /**
     * 获取订单列表
     * @param getOrderListReq
     * @return
     */
    Page<PayOrder> getOrderList(GetOrderListReq getOrderListReq);
}
