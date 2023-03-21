package com.luck.domain.resp;

import com.luck.entity.PayKnowledge;
import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * @description:
 * @author: pangcheng
 * @time: 2023/3/21 17:42
 */
@ApiModel(value = "付费知识详情")
@Data
public class GetKnowLedgeDetailResp extends PayKnowledge {
}
