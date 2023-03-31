package com.luck.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.luck.domain.req.GetOrderListReq;
import com.luck.entity.PayOrder;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author pc
 * @since 2023-02-21
 */
public interface PayOrderMapper extends BaseMapper<PayOrder> {

    /**
     * 分页获取订单
     * @param page
     * @param userId
     * @param getOrderListReq
     * @return
     */
    Page<PayOrder> getOrderList(Page page, @Param("getOrderListReq") GetOrderListReq getOrderListReq, @Param("userId")String userId);
}
