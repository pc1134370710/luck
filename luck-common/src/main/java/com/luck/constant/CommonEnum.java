package com.luck.constant;

/**
 * @description:
 * @author: pangcheng
 * @create: 2023-03-20 22:35
 **/
public enum CommonEnum {

    OK(200,"操作成功"),
    LOGIN_FAIL(10001,"登录失败，密码错误"),
    FAIL(10002,"支付宝验签失败"),

    GET_KNOWLEDGE_INFO_FAIL(20001,"获取付费知识失败"),
    ORDER_IS_EXP(20002,"订单已过期"),
    ORDER_SEND_MSG_FAIL(20003,"订单发送mq消息失败");


    public int code;
    public String msg;
    public int getCode(){
        return code;
    }
    public String getMsg(){
        return msg;
    }

    CommonEnum(int code,String msg) {
    }
}
