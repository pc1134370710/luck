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
    GET_USER_POWER_FAIL(10003,"获取用户查看权限失败"),
    NOT_LOOK_POWER(10004,"没有查看权限"),

    GET_KNOWLEDGE_INFO_FAIL(20001,"获取付费知识失败"),
    ORDER_IS_EXP(20002,"订单已过期"),
    ORDER_SEND_MSG_FAIL(20003,"订单发送mq消息失败"),
    LUCK_DRAW_REPEAT(20004,"您已经抽过奖啦"),
    LUCK_DRAW_ERROR(20005,"抽奖服务异常，请稍后重试"),
    LUCK_DRAW_NOT_RESULT(20007,"暂无抽奖结果"),
    LUCK_DRAW_NOT_DRAW(20008,"很遗憾未中奖"),


    ;


    public  int code;
    public String msg;
    public int getCode(){
        return code;
    }
    public String getMsg(){
        return msg;
    }

    CommonEnum(int code,String msg) {
        this.code=code;
        this.msg=msg;
    }
}
