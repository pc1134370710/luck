package com.luck.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.luck.entity.RaffleRecord;
import com.luck.mapper.RaffleRecordMapper;
import com.luck.model.UserAuth;
import com.luck.service.IRaffleRecordService;
import com.luck.utils.UserInfoThreadLocal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
public class RaffleRecordServiceImpl extends ServiceImpl<RaffleRecordMapper, RaffleRecord> implements IRaffleRecordService {


    @Autowired
    private RaffleRecordMapper raffleRecordMapper;

    @Override
    public List<RaffleRecord> getRaffleRecord() {
        UserAuth userAuth = UserInfoThreadLocal.get();
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("user_id",userAuth.getUserId());
        List<RaffleRecord> list = raffleRecordMapper.selectList(queryWrapper);
        return list;
    }
}
