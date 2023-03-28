package com.luck.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.luck.domain.req.AddActivityReq;
import com.luck.entity.Activity;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author pc
 * @since 2023-03-28
 */
public interface IActivityService extends IService<Activity> {

    /**
     * 添加抽个奖活动
     * @param addActivityReq
     */
    void addActivity(AddActivityReq addActivityReq);
}
