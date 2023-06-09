package com.luck.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.luck.entity.RaffleRecord;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author pc
 * @since 2023-02-28
 */
public interface IRaffleRecordService extends IService<RaffleRecord> {

    List<RaffleRecord> getRaffleRecord();
}
