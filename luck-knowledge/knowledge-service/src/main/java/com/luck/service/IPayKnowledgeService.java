package com.luck.service;

import com.luck.domin.req.AddKnowLedgeReq;
import com.luck.entity.PayKnowledge;
import com.baomidou.mybatisplus.extension.service.IService;

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
}
