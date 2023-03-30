package com.luck.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.luck.domain.req.AddKnowLedgeReq;
import com.luck.domain.req.GetKnowLedgeListReq;
import com.luck.domain.req.UpdateKnowLedgeReq;
import com.luck.domain.resp.GetKnowLedgeDetailResp;
import com.luck.entity.PayKnowledge;
import com.baomidou.mybatisplus.extension.service.IService;
import com.luck.pojo.KnowledgeDomain;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author pc
 * @since 2023-02-21
 */
public interface IPayKnowledgeService extends IService<PayKnowledge> {

    /**
     * 添加付费知识
     * @param addKnowLedgeReq
     */
    void addKnowLedge(AddKnowLedgeReq addKnowLedgeReq);

    /**
     * 获取付费详情
     * @param pkId
     * @return
     */
    KnowledgeDomain getKnowledgeInfo(String pkId);

    /**
     * 修改付费内容
     * @param updateKnowLedgeReq
     */
    void updateKnowLedge(UpdateKnowLedgeReq updateKnowLedgeReq);

    /**
     * 分页获取付费知识列表
     * @param getKnowLedgeListReq
     * @return
     */
    Page<GetKnowLedgeDetailResp> getKnowLedgeList(GetKnowLedgeListReq getKnowLedgeListReq);
}
