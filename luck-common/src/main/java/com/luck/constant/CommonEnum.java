package com.luck.constant;

/**
 * @description:
 * @author: pangcheng
 * @create: 2023-02-20 22:35
 **/
public enum CommonEnum {

    OK(200,"操作成功"),
    LOGIN_FAIL(10001,"登录失败，密码错误"),
    FAIL(10002,"系统异常"),

    GET_KNOWLEDGE_INFO_FAIL(20001,"获取付费知识失败"),
    ORDER_IS_EXP(20002,"订单已过期"),
    ORDER_SEND_MSG_FAIL(20003,"订单发送mq消息失败"),
    LUCK_DRAW_REPEAT(20004,"您已经抽过奖啦"),
    LUCK_DRAW_ERROR(20005,"抽奖服务异常，请稍后重试"),
    LUCK_DRAW_NOT_RESULT(20007,"暂无抽奖结果"),


    ;


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
