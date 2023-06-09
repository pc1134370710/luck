package com.luck.constant;

/**
 * @description:
 * @author: pangcheng
 * @create: 2023-02-21 21:09
 **/
public class OrderStatusConstant {

    /**
     * 订单未支付
     */
    public static  final int ORDER_STATUS_NOT_PAY=0;
    /**
     * 订单已支付
     */
    public static  final int ORDER_STATUS_IS_PAY=1;
    /**
     * 订单已过期
     */
    public static  final int ORDER_STATUS_IS_EXT=2;

    /**
     * 缓存支付二维码 url
     */

    public static final  String PAY_CODE_CACHE_KAY="payCodeKey";


    /**
     * 获取支付订单缓存key
     * @param orderId
     * @return
     */
    public static String getPayCodeCacheKay(String orderId){
        return PAY_CODE_CACHE_KAY+":"+orderId;
    }

}
