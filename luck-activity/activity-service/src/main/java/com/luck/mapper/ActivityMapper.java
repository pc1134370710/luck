package com.luck.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.luck.domain.req.GetActivityListReq;
import com.luck.domain.resp.GetActivityListResp;
import com.luck.entity.Activity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.luck.entity.Awards;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author pc
 * @since 2023-02-28
 */
public interface ActivityMapper extends BaseMapper<Activity> {

    /**
     *  获取抽奖列表
     * @param page
     * @param getActivityListReq
     * @param userId 当前登录用户的id
     * @return
     */
    Page<GetActivityListResp> getActivityList(Page page, @Param("getActivityListReq") GetActivityListReq getActivityListReq,@Param("userId") String userId);
}
