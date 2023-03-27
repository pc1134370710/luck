package com.luck.pay;


import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.AlipayConfig;
import com.alipay.api.DefaultAlipayClient;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;


/**
 * https://opendocs.alipay.com/open/02np94
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "ali.pay.config")
public class MyAliPayConfig {
    /**
     * 支付宝网关（固定）。
     */
    private String serverUrl;
    /**
     * 此处请填写你当面付的APPID
     */
    private String appId;
    /**
     * 应用私钥
     */
    private String privateKey;
    /**
     * 支付宝公钥。
     */
    private String alipayPublicKey;
    /**
     * 回调url
     */
    private String payCallbackUrl;

    private volatile AlipayClient alipayClient;

    /**
     * 按照官网生成
     * https://opendocs.alipay.com/open/02np94
     * @return
     */
    public synchronized AlipayClient getAlipayClient()  {
        AlipayConfig alipayConfig = new AlipayConfig();
        //设置网关地址
        alipayConfig.setServerUrl(serverUrl);
            //设置应用ID
        alipayConfig.setAppId(appId);
        //设置应用私钥
        alipayConfig.setPrivateKey(privateKey);
        //设置请求格式，固定值json
        alipayConfig.setFormat("JSON");
        //设置字符集
//        alipayConfig.setCharset(alipayPublicKey);
        //设置签名类型
        alipayConfig.setSignType("RSA2");
        //设置支付宝公钥
        alipayConfig.setAlipayPublicKey(alipayPublicKey);
        alipayConfig.setCharset("utf-8");
        //实例化客户端
        AlipayClient alipayClient = null;
        try {
            alipayClient = new DefaultAlipayClient(alipayConfig);
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        return alipayClient;
    }


}
