package com.luck.resp;


import com.luck.constant.CommonEnum;
import lombok.Data;

import java.io.Serializable;


/**
 * 响应信息主体
 * @author pc
 */
@Data
public class R<T> implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 响应编码
     */
    private int code;
    /**
     * 提示信息
     */
    private String msg;
    /**
     * 返回数据
     */
    private T data;

    public R() {

    }

    public static R OK(){
        R r = new R();
        r.setCode(CommonEnum.OK.code);
        r.setMsg(CommonEnum.OK.msg);
        return r;
    }
    public static <T> R OK(T data){
        R r = new R();
        r.setCode(CommonEnum.OK.code);
        r.setMsg(CommonEnum.OK.getMsg());
        r.setData(data);
        return r;
    }
    public static R ERROR(int code,String msg){
        R r = new R();
        r.setCode(code);
        r.setMsg(msg);
        return r;
    }

    public static R ERROR(CommonEnum commonEnum){
        R r = new R();
        r.setCode(commonEnum.code);
        r.setMsg(commonEnum.msg);
        return r;
    }
    public static <T>  R ERROR(CommonEnum commonEnum,T data){
        R r = new R();
        r.setCode(commonEnum.code);
        r.setMsg(commonEnum.msg);
        r.setData(data);
        return r;
    }
}

