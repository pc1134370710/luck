package com.luck.manager;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import com.luck.constant.CommonEnum;
import com.luck.entity.Activity;
import com.luck.entity.Awards;
import com.luck.exception.GlobalException;
import com.luck.resp.R;
import org.apache.commons.codec.CharEncoding;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.nio.charset.Charset;
import java.util.List;

/**
 * @description:
 * @author: pangcheng
 * @create: 2023-03-28 20:46
 **/

@Service
public class RedisLuckDrawService extends BasicLuckDrawService {

    private static String stockDeductionLua;
    private static String stockRollbackLua;

    static {
        stockDeductionLua = read("lua/stock_deduction.lua");
        stockRollbackLua = read("lua/stock_rollback.lua");
    }

    /**
     * 读取lua 脚本
     * @param fileName
     * @return
     */
    public static String read(String fileName) {
        String val = "";
        try {
            val = IoUtil.read(new FileInputStream(FileUtil.file(fileName)), Charset.forName("utf-8"));
        } catch (Exception e) {
            //错误处理
            throw new GlobalException(R.ERROR(CommonEnum.FAIL));
        }
        return val;
    }

    @Override
    protected void afterHandle(Activity activity, Awards awards) {
        // 开始扣减库存
        // 并且插入不可见记录

    }

    @Override
    protected void verifyAwardStock(List<Awards> awardsList) {

    }

    @Override
    protected void verifyLuckDrawRule(Activity activity) {

    }
}
