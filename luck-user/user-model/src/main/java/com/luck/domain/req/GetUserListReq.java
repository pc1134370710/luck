package com.luck.domain.req;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @description:
 * @author: pangcheng
 * @time: 2023/3/28 15:16
 */
@Data
@ApiModel(value = "分页获取用户信息请求参数")
public class GetUserListReq {

    @ApiModelProperty(value = "搜索内容")
    private String key;
    private Page page;
}
