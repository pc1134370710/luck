package com.luck;

import com.luck.model.KnowledgeDomain;
import com.luck.resp.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @description:
 * @author: pangcheng
 * @create: 2023-03-21 20:48
 **/
@FeignClient(value = "knowledgeServe")
public interface KnowledgeFeignClient {

    @GetMapping("/getKnowledgeInfo/{pkId}")
    R<KnowledgeDomain> getKnowledgeInfo(@PathVariable("pkId")Long pkId);

}
