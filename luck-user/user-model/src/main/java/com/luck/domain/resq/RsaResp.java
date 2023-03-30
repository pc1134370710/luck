package com.luck.domain.resq;

import lombok.Data;

/**
 * @description: rsa
 * @author: pangcheng
 * @create: 2023-02-26 11:41
 **/
@Data
public class RsaResp {

    /**
     * 公钥
     */
    private String publicKey;
    /**
     * 公钥标识
     */
    private String publicCode;
}
