package com.luck.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @description:
 * @author: pangcheng
 * @create: 2023-04-01 12:46
 **/
@Data
@ApiModel(value = "用户查看权限")
public class UserLookPowerDomain {

    @ApiModelProperty(value = "可访问权限付费知识id集合")
    private List<String> pkIds;

    @ApiModelProperty(value = "用户id")
    private String userId;

}
