package com.luck.service;

import com.luck.domain.req.AddKnowLedgeReq;
import com.luck.domain.req.UpdateKnowLedgeReq;
import com.luck.entity.PayKnowledge;
import com.baomidou.mybatisplus.extension.service.IService;
import com.luck.model.KnowledgeDomain;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author pc
 * @since 2023-03-21
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
    KnowledgeDomain getKnowledgeInfo(Long pkId);

    /**
     * 修改付费内容
     * @param updateKnowLedgeReq
     */
    void updateKnowLedge(UpdateKnowLedgeReq updateKnowLedgeReq);
}
