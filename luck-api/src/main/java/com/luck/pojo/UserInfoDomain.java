package com.luck.pojo;

import lombok.Data;

/**
 * @description:
 * @author: pangcheng
 * @create: 2023-03-21 20:10
 **/
@Data
public class UserInfoDomain {

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

}
