package com.luck.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.luck.constant.CommonEnum;
import com.luck.domain.req.AddKnowLedgeReq;
import com.luck.domain.req.GetKnowLedgeListReq;
import com.luck.domain.req.UpdateKnowLedgeReq;
import com.luck.domain.resp.GetKnowLedgeDetailResp;
import com.luck.entity.PayKnowledge;
import com.luck.exception.GlobalException;
import com.luck.mapper.PayKnowledgeMapper;
import com.luck.model.UserAuth;
import com.luck.pojo.KnowledgeDomain;
import com.luck.pojo.UserLookPowerDomain;
import com.luck.pojo.knowledge.AddKnowLedgePayCountDomain;
import com.luck.resp.R;
import com.luck.service.IPayKnowledgeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.luck.user.UserFeignClient;
import com.luck.utils.Snowflake;
import com.luck.utils.UserInfoThreadLocal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author pc
 * @since 2023-02-21
 */
@Service
public class PayKnowledgeServiceImpl extends ServiceImpl<PayKnowledgeMapper, PayKnowledge> implements IPayKnowledgeService {

    private static Logger log = LoggerFactory.getLogger(PayKnowledgeServiceImpl.class);

    @Autowired
    private PayKnowledgeMapper payKnowledgeMapper;

    @Autowired
    private UserFeignClient userFeignClient;


    @Override
    public void addKnowLedge(AddKnowLedgeReq addKnowLedgeReq) {

        PayKnowledge payKnowledge = new PayKnowledge();
        String id = Snowflake.nextId();
        payKnowledge.setId(id);
        payKnowledge.setName(addKnowLedgeReq.getName());
        payKnowledge.setContent(addKnowLedgeReq.getContent());
        payKnowledge.setTitle(addKnowLedgeReq.getTitle());
        payKnowledge.setPayPrice(addKnowLedgeReq.getPayPrice());
        payKnowledgeMapper.insert(payKnowledge);
    }

    @Override
    public KnowledgeDomain getApiKnowledgeInfo(String pkId) {
        PayKnowledge payKnowledge = payKnowledgeMapper.selectById(pkId);
        KnowledgeDomain knowledgeDomain  = new KnowledgeDomain();
        BeanUtils.copyProperties(payKnowledge,knowledgeDomain);
        return knowledgeDomain;
    }

    @Override
    public KnowledgeDomain getKnowledgeInfo(String pkId) {

        UserAuth userAuth = UserInfoThreadLocal.get();

        // 获取用户可见列表
        R<UserLookPowerDomain> lookPower = userFeignClient.getLookPower(userAuth.getUserId());
        if(CommonEnum.OK.code != lookPower.getCode()){
            // 获取失败
            throw new GlobalException(R.ERROR(CommonEnum.GET_USER_POWER_FAIL));
        }
        UserLookPowerDomain data = lookPower.getData();
        if(data.getPkIds().indexOf(pkId)<0){
            log.warn("当前用户{}，没有{} 的查看权限,",userAuth,pkId);
            throw new GlobalException(R.ERROR(CommonEnum.NOT_LOOK_POWER));
        }

        PayKnowledge payKnowledge = payKnowledgeMapper.selectById(pkId);
        KnowledgeDomain knowledgeDomain  = new KnowledgeDomain();
        BeanUtils.copyProperties(payKnowledge,knowledgeDomain);
        return knowledgeDomain;
    }

    @Override
    public void updateKnowLedge(UpdateKnowLedgeReq updateKnowLedgeReq) {
        PayKnowledge payKnowledge = new PayKnowledge();
        BeanUtils.copyProperties(updateKnowLedgeReq,payKnowledge);
        payKnowledge.setId(updateKnowLedgeReq.getPkId());
        payKnowledgeMapper.updateById(payKnowledge);
    }


    @Override
    public void addKnowLedgePayCount(AddKnowLedgePayCountDomain addKnowLedgePayCountDomain) {
        payKnowledgeMapper.addKnowLedgePayCount(addKnowLedgePayCountDomain);


    }

    @Override
    public Page<GetKnowLedgeDetailResp> getKnowLedgeList(GetKnowLedgeListReq getKnowLedgeListReq) {
        UserAuth userAuth = UserInfoThreadLocal.get();

        // 获取用户可见列表
        R<UserLookPowerDomain> lookPower = userFeignClient.getLookPower(userAuth.getUserId());
        if(CommonEnum.OK.code != lookPower.getCode()){
            // 获取失败
            throw new GlobalException(R.ERROR(CommonEnum.GET_USER_POWER_FAIL));
        }
        UserLookPowerDomain data = lookPower.getData();
        Map<String,String> map = new HashMap<>();
        for(String pkId : data.getPkIds()){
            map.put(pkId,userAuth.getUserId());
        }

        Page<GetKnowLedgeDetailResp> knowLedgeList = payKnowledgeMapper.getKnowLedgeList(getKnowLedgeListReq.getPage(), getKnowLedgeListReq);

        List<GetKnowLedgeDetailResp> records = knowLedgeList.getRecords();
        for(GetKnowLedgeDetailResp getKnowLedgeDetailResp : records){
            // 在可查看范围内
            getKnowLedgeDetailResp.setLookPower(0);
            // 付款了 或者是免费的
            if(map.get(getKnowLedgeDetailResp.getId())!=null || getKnowLedgeDetailResp.getPayLookStatus()==0){
                getKnowLedgeDetailResp.setLookPower(1);
            }
        }
        return  knowLedgeList;
    }
}
