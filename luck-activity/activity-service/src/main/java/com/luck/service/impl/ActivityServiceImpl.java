package com.luck.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.luck.domain.req.AddActivityAwardsReq;
import com.luck.domain.req.AddActivityReq;
import com.luck.entity.Activity;
import com.luck.entity.Awards;
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
 * @since 2023-02-28
 */
@Service
public class ActivityServiceImpl extends ServiceImpl<ActivityMapper, Activity> implements IActivityService {

    @Autowired
    private ActivityMapper activityMapper;

    @Autowired
    private AwardsMapper awardsMapper;


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
        for(AddActivityAwardsReq addActivityAwardsReq : addActivityReq.getAwardsList()){
            Awards awards = new Awards();
            awards.setId(Snowflake.nextId());
            // 设置额定数量
            awards.setCnt(addActivityAwardsReq.getCnt());
            // 设置库存剩余量
            awards.setStock(addActivityAwardsReq.getCnt());
            awards.setAwardsName(addActivityAwardsReq.getAwardsName());
            awards.setPkId(addActivityAwardsReq.getPkId());
            awards.setActivityId(activity.getId());
            awardsList.add(awards);
        }
        // 批量添加奖项
        awardsMapper.batchInsert(awardsList);


    }
}
