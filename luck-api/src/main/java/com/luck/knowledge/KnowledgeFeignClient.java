package com.luck.knowledge;

import com.luck.pojo.KnowledgeDomain;
import com.luck.pojo.knowledge.AddKnowLedgePayCountDomain;
import com.luck.resp.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 *
 *  如果同时指定name/value和url属性，则以url属性为准，name/value属性指定的值便当做客户端的名称
 * @description:
 * @author: pangcheng
 * @create: 2023-02-21 20:48
 **/
@FeignClient(value = "knowledgeServe")
public interface KnowledgeFeignClient {

    /**
     *  获取付费知识详情
     * @param pkId
     * @return
     */
    @GetMapping("/knowledge/payKnowledge/getApiKnowledgeInfo/{pkId}")
    R<KnowledgeDomain> getApiKnowledgeInfo(@PathVariable("pkId")String pkId);

    @PostMapping("/knowledge/payKnowledge/addKnowLedgePayCount")
    R<KnowledgeDomain> addKnowLedgePayCount(@RequestBody AddKnowLedgePayCountDomain addKnowLedgePayCountDomain);

}
