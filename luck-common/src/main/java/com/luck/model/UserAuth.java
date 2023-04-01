package com.luck.model;

import lombok.Data;

import java.util.List;

/**
 * @description: 用户身份信息
 * @author: pangcheng
 * @time: 2023/2/21 16:45
 */
@Data
public class UserAuth {

    /**
     * 用户id
     */
    private String userId;
    /**
     * 用户姓名
     */
    private String userName;
    /**
     * 用户类型
     */
    private Integer userType;
    /**
     * token  信息
     */
    private String token;

}
