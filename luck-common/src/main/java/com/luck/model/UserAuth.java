package com.luck.model;

import lombok.Data;

/**
 * @description: 用户身份信息
 * @author: pangcheng
 * @time: 2023/3/21 16:45
 */
@Data
public class UserAuth {

    private Long userId;
    private String userName;
    private Integer userType;


}
