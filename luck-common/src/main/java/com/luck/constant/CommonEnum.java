package com.luck.constant;

/**
 * @description:
 * @author: pangcheng
 * @create: 2023-03-20 22:35
 **/
public enum CommonEnum {

    LOGIN_FAIL(10001,"登录失败，密码错误");


    private int code;
    private String msg;
    public int getCode(){
        return code;
    }
    public String getMsg(){
        return msg;
    }

    CommonEnum(int code,String msg) {
    }
}
