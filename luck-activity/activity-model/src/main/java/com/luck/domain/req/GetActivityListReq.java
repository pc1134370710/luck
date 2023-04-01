package com.luck.domain.req;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.luck.entity.Activity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @description:
 * @author: pangcheng
 * @create: 2023-04-01 15:24
 **/
@Data
@ApiModel(value = "获取活动列表请求参数")
public class GetActivityListReq  {

    private Integer key;

    private Page page;

}
