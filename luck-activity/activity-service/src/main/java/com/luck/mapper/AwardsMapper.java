package com.luck.mapper;

import com.luck.entity.Awards;
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
public interface AwardsMapper extends BaseMapper<Awards> {

    /**
     * 批量添加奖项
     * @param list
     */
    void batchInsert(@Param("list") List<Awards> list);
}
