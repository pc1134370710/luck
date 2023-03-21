package com.luck.service.impl;

import com.luck.domain.req.AddKnowLedgeReq;
import com.luck.domain.req.UpdateKnowLedgeReq;
import com.luck.entity.PayKnowledge;
import com.luck.mapper.PayKnowledgeMapper;
import com.luck.model.KnowledgeDomain;
import com.luck.service.IPayKnowledgeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.luck.utils.Snowflake;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author pc
 * @since 2023-03-21
 */
@Service
public class PayKnowledgeServiceImpl extends ServiceImpl<PayKnowledgeMapper, PayKnowledge> implements IPayKnowledgeService {

    @Autowired
    private PayKnowledgeMapper payKnowledgeMapper;

    @Override
    public void addKnowLedge(AddKnowLedgeReq addKnowLedgeReq) {

        PayKnowledge payKnowledge = new PayKnowledge();
        long id = Snowflake.nextId();
        payKnowledge.setId(id);
        payKnowledge.setName(addKnowLedgeReq.getName());
        payKnowledge.setContent(addKnowLedgeReq.getContent());
        payKnowledge.setTitle(addKnowLedgeReq.getTitle());
        payKnowledge.setPayPrice(addKnowLedgeReq.getPayPrice());
        payKnowledgeMapper.insert(payKnowledge);
    }

    @Override
    public KnowledgeDomain getKnowledgeInfo(Long pkId) {
        PayKnowledge payKnowledge = payKnowledgeMapper.selectById(pkId);
        KnowledgeDomain knowledgeDomain  = new KnowledgeDomain();
        BeanUtils.copyProperties(payKnowledge,knowledgeDomain);
        return knowledgeDomain;
    }

    @Override
    public void updateKnowLedge(UpdateKnowLedgeReq updateKnowLedgeReq) {

    }
}