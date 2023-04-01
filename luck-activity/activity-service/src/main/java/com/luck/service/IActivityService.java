package com.luck.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.luck.domain.req.AddActivityReq;
import com.luck.domain.req.GetActivityListReq;
import com.luck.domain.resp.GetActivityListResp;
import com.luck.entity.Activity;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author pc
 * @since 2023-02-28
 */
public interface IActivityService extends IService<Activity> {

    /**
     * 缓存预热
     */
    void initActivityStock();


    /**
     * 添加抽个奖活动
     * @param addActivityReq
     */
    void addActivity(AddActivityReq addActivityReq);

    /**
     *  获取抽奖活动列表
     * @param getActivityListReq
     * @return
     */
    Page<GetActivityListResp> getActivityList(GetActivityListReq getActivityListReq);
}
