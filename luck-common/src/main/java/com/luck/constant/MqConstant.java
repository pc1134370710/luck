package com.luck.constant;

/**
 * @description:
 * @author: pangcheng
 * @time: 2023/2/2 11:16
 */
public class MqConstant {

    /**
     *  订单状态 队列topic
     */
    public static final String ORDER_STATUS="order_status";

    /**
     * 订单支付成功 队列
     */
    public static final String ORDER_PAY_SUCCESS="order_pay_success";

    /**
     *  添加商品权限到用户权限中的消息队列
     */
    public static final String USER_POWER="add_user_power";

    /**
     * 抽奖队列
     */
    public static final String LUCK_DRAW="luck_draw";

    /**
     * 异步扣减库存队列
     */
    public static final String LUCK_DRAW_STOCK="luck_draw_stock";





}
