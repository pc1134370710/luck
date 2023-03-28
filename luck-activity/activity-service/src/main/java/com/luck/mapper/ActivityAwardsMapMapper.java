package com.luck.mapper;

import com.luck.entity.ActivityAwardsMap;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author pc
 * @since 2023-03-28
 */
public interface ActivityAwardsMapMapper extends BaseMapper<ActivityAwardsMap> {

    /**
     *  批量添加关联关系
     * @param activityAwardsMapList
     */
    void batchInsert(@Param("list") List<ActivityAwardsMap> activityAwardsMapList);
}
