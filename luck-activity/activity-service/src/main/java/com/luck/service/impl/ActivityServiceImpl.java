package com.luck.service.impl;

import com.alibaba.nacos.shaded.org.checkerframework.checker.units.qual.A;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.luck.domain.req.AddActivityAwardsReq;
import com.luck.domain.req.AddActivityReq;
import com.luck.entity.Activity;
import com.luck.entity.ActivityAwardsMap;
import com.luck.entity.Awards;
import com.luck.mapper.ActivityAwardsMapMapper;
import com.luck.mapper.ActivityMapper;
import com.luck.mapper.AwardsMapper;
import com.luck.service.IActivityService;
import com.luck.utils.Snowflake;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author pc
 * @since 2023-03-28
 */
@Service
public class ActivityServiceImpl extends ServiceImpl<ActivityMapper, Activity> implements IActivityService {

    @Autowired
    private ActivityMapper activityMapper;

    @Autowired
    private AwardsMapper awardsMapper;

    @Autowired
    private ActivityAwardsMapMapper activityAwardsMapMapper;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void addActivity(AddActivityReq addActivityReq) {
        Activity activity = new Activity();
        activity.setId(Snowflake.nextId());
        activity.setActivityName(addActivityReq.getActivityName());
        activity.setActivityMemo(addActivityReq.getActivityMemo());
        activity.setEndTime(addActivityReq.getEndTime());
        activity.setPayUserCount(0);
        activityMapper.insert(activity);
        // 添加奖项
        List<Awards> awardsList  = new ArrayList<>();
        List<ActivityAwardsMap> activityAwardsMapList = new ArrayList<>();
        for(AddActivityAwardsReq addActivityAwardsReq : addActivityReq.getAwardsList()){
            Awards awards = new Awards();
            awards.setId(Snowflake.nextId());
            // 设置额定数量
            awards.setCnt(addActivityAwardsReq.getCnt());
            // 设置库存剩余量
            awards.setStock(addActivityAwardsReq.getCnt());
            awards.setAwardsName(addActivityAwardsReq.getAwardsName());
            awards.setPkId(addActivityAwardsReq.getPkId());
            awardsList.add(awards);

            // 设置奖项跟 活动的关联关系
            ActivityAwardsMap activityAwardsMap = new ActivityAwardsMap();
            // 设置奖项
            activityAwardsMap.setAdId(awards.getId());
            // 设置活动id
            activityAwardsMap.setAyId(activity.getId());
            activityAwardsMap.setId(Snowflake.nextId());
            activityAwardsMapList.add(activityAwardsMap);
        }
        // 批量添加奖项
        awardsMapper.batchInsert(awardsList);
        // 将奖项 、抽奖活动建立关联关系
        activityAwardsMapMapper.batchInsert(activityAwardsMapList);

    }
}
