package com.luck.exception;

import com.luck.resp.R;

/**
 * @description: 异常
 * @author: pangcheng
 * @create: 2023-03-20 22:31
 **/
public class GlobalException extends RuntimeException {

    private R r;

    public GlobalException(int code,String msg){
        r =new R();
        r.setCode(code);
        r.setMsg(msg);
    }

    public GlobalException(R r){
       this.r= r;
    }
}