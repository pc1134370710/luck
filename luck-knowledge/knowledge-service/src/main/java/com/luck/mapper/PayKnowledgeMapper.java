package com.luck.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.luck.domain.req.GetKnowLedgeListReq;
import com.luck.domain.resp.GetKnowLedgeDetailResp;
import com.luck.entity.PayKnowledge;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author pc
 * @since 2023-03-21
 */
public interface PayKnowledgeMapper extends BaseMapper<PayKnowledge> {

    /**
     * 分页获取付费知识
     * @param getKnowLedgeListReq
     * @param page
     * @return
     */
    Page<GetKnowLedgeDetailResp> getKnowLedgeList(GetKnowLedgeListReq getKnowLedgeListReq, Page page);
}
