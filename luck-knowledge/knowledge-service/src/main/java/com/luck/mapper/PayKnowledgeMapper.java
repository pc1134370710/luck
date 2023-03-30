package com.luck.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.luck.domain.req.GetKnowLedgeListReq;
import com.luck.domain.resp.GetKnowLedgeDetailResp;
import com.luck.entity.PayKnowledge;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author pc
 * @since 2023-02-21
 */
public interface PayKnowledgeMapper extends BaseMapper<PayKnowledge> {

    /**
     * 分页获取付费知识
     * @param getKnowLedgeListReq
     * @param page
     * @return
     */
    Page<GetKnowLedgeDetailResp> getKnowLedgeList(Page pag ,@Param("getKnowLedgeListReq") GetKnowLedgeListReq getKnowLedgeListReq);
}
