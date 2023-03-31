package com.luck.exception;

import com.alibaba.fastjson.JSON;
import com.luck.resp.R;
import lombok.Data;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @description: 异常
 * @author: pangcheng
 * @create: 2023-02-20 22:31
 **/
@Data
public class GlobalException extends RuntimeException {

    private R r;

    public GlobalException(int code,String msg){
        r =new R();
        r.setCode(code);
        r.setMsg(msg);
    }

    public GlobalException(R r){
        super(JSON.toJSONString(r));
       this.r= r;
    }

}
