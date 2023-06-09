package com.luck.domain.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @description:
 * @author: pangcheng
 * @time: 2023/2/21 17:31
 */
@Data
@ApiModel(value = "请求付费知识请求参数")
public class GetKnowLedgeReq {

    @ApiModelProperty(value = "付费知识名称")
    private String name;

    @ApiModelProperty(value = "付费知识标题")
    private String title;


}
