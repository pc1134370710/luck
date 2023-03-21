package com.luck.model;

import lombok.Data;

/**
 * @description:
 * @author: pangcheng
 * @create: 2023-03-21 20:10
 **/
@Data
public class UserInfoDomain {

    private Long userId;
    private String userName;
    private Integer userType;

}
