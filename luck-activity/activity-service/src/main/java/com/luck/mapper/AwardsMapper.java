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
 * @since 2023-02-28
 */
public interface AwardsMapper extends BaseMapper<Awards> {

    /**
     * 批量添加奖项
     * @param list
     */
    void batchInsert(@Param("list") List<Awards> list);

    /**
     * 修改库存
     * @param awardsId
     * @param count 数量
     * @return
     */
    int updateStock(String awardsId,int count);
}
