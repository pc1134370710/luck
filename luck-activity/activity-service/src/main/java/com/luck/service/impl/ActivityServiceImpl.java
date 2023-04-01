package com.luck.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.luck.constant.LuckDrawConstant;
import com.luck.domain.req.AddActivityAwardsReq;
import com.luck.domain.req.AddActivityReq;
import com.luck.domain.req.GetActivityListReq;
import com.luck.domain.resp.GetActivityListResp;
import com.luck.entity.Activity;
import com.luck.entity.Awards;
import com.luck.mapper.ActivityMapper;
import com.luck.mapper.AwardsMapper;
import com.luck.model.UserAuth;
import com.luck.service.IActivityService;
import com.luck.utils.RedisUtils;
import com.luck.utils.Snowflake;
import com.luck.utils.UserInfoThreadLocal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
    @Autowired
    private RedisUtils redisUtils;

    @Override
    public void initActivityStock() {

        // 找出所有的活动
        List<Activity> activities = activityMapper.selectList(null);
        for(Activity activity: activities){
            QueryWrapper queryWrapper = new QueryWrapper();
            queryWrapper.eq("activity_id",activity.getId());
            List<Awards> list = awardsMapper.selectList(queryWrapper);
            for(Awards awards: list){
                String key = LuckDrawConstant.getLuckDrawStock(activity.getId(),awards.getId());
                // 只预热 真实商品
                if(awards.getPkId()!=null){
                    redisUtils.set(key,awards.getStock());
                }
            }

        }


    }

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

    @Override
    public Page<GetActivityListResp> getActivityList(GetActivityListReq getActivityListReq) {
        UserAuth userAuth = UserInfoThreadLocal.get();
        // todo 顺便练习SQL 的能力
        Page<GetActivityListResp> page = activityMapper.getActivityList(getActivityListReq.getPage(),getActivityListReq,userAuth.getUserId());

        List<String> activityIds = page.getRecords().stream().map(Activity::getId).collect(Collectors.toList());
        List<Awards> list =   awardsMapper.batchSelectAwards(activityIds);
        Map<String,List<Awards>> map= new HashMap<>();
        for(Awards awards : list){
            List<Awards> orDefault = map.getOrDefault(awards.getActivityId(), new ArrayList<>());
            orDefault.add(awards);
            map.put(awards.getActivityId(),orDefault);
        }
        // 设置奖品回去
        for(GetActivityListResp getActivityListResp : page.getRecords()){
            List<Awards> awards = map.get(getActivityListResp.getId());
            getActivityListResp.setAwardsList(awards);
        }

        return page;
    }
}
