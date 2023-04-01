package com.luck.exception;

import com.luck.constant.CommonEnum;
import com.luck.resp.R;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @description:
 * @author: pangcheng
 * @time: 2023/3/31 14:23
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
    private static Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);


    @ExceptionHandler(value = GlobalException.class)
    public R resolveException(GlobalException ex) throws Exception {
        log.error("发生异常 = ",ex);
        return ex.getR();
    }

    @ExceptionHandler(value = Exception.class)
    public R resolveException(Exception ex) throws Exception {
        log.error("发生异常 = ",ex);
        return R.ERROR(CommonEnum.FAIL);
    }


}
