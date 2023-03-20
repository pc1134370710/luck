package com.hyt.utils;


import lombok.Data;
import org.springframework.stereotype.Component;

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


}

