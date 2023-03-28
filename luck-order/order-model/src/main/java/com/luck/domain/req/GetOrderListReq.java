package com.luck.domain.req;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * @description:
 * @author: pangcheng
 * @time: 2023/3/28 15:27
 */
@Data
@ApiModel(value = "分页获取订单请求参数")
public class GetOrderListReq  {

    private String key;
    private Page page;
}
