package com.luck.domain.req;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @description:
 * @author: pangcheng
 * @create: 2023-02-27 20:00
 **/
@Data
@ApiModel(value = "获取知识列表请求参数")
public class GetKnowLedgeListReq {

    @ApiModelProperty(value = "分页参数")
    private Page page;

    @ApiModelProperty(value = "标题搜索")
    private String key;

}
