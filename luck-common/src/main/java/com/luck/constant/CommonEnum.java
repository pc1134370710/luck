package com.luck.constant;

/**
 * @description:
 * @author: pangcheng
 * @create: 2023-03-20 22:35
 **/
public enum CommonEnum {

    OK(200,"操作成功"),
    LOGIN_FAIL(10001,"登录失败，密码错误"),

    GET_KNOWLEDGE_INFO_FAIL(10002,"获取付费知识失败"),
    ORDER_IS_EXP(10003,"订单已过期");


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
