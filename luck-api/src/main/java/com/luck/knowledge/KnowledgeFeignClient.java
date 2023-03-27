package com.luck.knowledge;

import com.luck.pojo.KnowledgeDomain;
import com.luck.resp.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 *
 *  如果同时指定name/value和url属性，则以url属性为准，name/value属性指定的值便当做客户端的名称
 * @description:
 * @author: pangcheng
 * @create: 2023-03-21 20:48
 **/
@FeignClient(value = "knowledgeServe")
public interface KnowledgeFeignClient {

    @GetMapping("/payKnowledge/getKnowledgeInfo/{pkId}")
    R<KnowledgeDomain> getKnowledgeInfo(@PathVariable("pkId")Long pkId);

}