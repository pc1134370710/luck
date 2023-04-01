package com.luck.controller;


import com.luck.entity.RaffleRecord;
import com.luck.resp.R;
import com.luck.service.IRaffleRecordService;
import com.luck.service.impl.RaffleRecordServiceImpl;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author pc
 * @since 2023-02-28
 */
@RestController
@RequestMapping("/raffleRecord")
@Api(value = "抽奖记录接口")
public class RaffleRecordController {

    @Autowired
    private IRaffleRecordService raffleRecordService;

    @GetMapping("/getRaffleRecord")
    public R<List<RaffleRecord>> getRaffleRecord(){
        List<RaffleRecord> list = raffleRecordService.getRaffleRecord();
       return R.OK(list);
    }


}

