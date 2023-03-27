package com.luck.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.luck.domain.req.AddKnowLedgeReq;
import com.luck.domain.req.GetKnowLedgeListReq;
import com.luck.domain.req.UpdateKnowLedgeReq;
import com.luck.domain.resp.GetKnowLedgeDetailResp;
import com.luck.pojo.KnowledgeDomain;
import com.luck.resp.R;
import com.luck.service.IPayKnowledgeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author pc
 * @since 2023-03-21
 */
@RestController
@RequestMapping("/payKnowledge")
@Api(value = "付费知识相关接口")
public class PayKnowledgeController {

    @Autowired
    private IPayKnowledgeService payKnowledgeService;


    @PostMapping("/addKnowLedge")
    @ApiOperation(value = "添加付费知识接口")
    public R addKnowLedge(@RequestBody @Validated AddKnowLedgeReq addKnowLedgeReq){
        payKnowledgeService.addKnowLedge(addKnowLedgeReq);
        return R.OK();
    }

    @PostMapping("/updateKnowLedge")
    @ApiOperation(value = "修改付费知识接口")
    public R updateKnowLedge(@RequestBody @Validated UpdateKnowLedgeReq updateKnowLedgeReq){
        payKnowledgeService.updateKnowLedge(updateKnowLedgeReq);
        return R.OK();
    }

    @PostMapping("/getKnowLedgeList")
    @ApiOperation(value = "分页获取付费知识列表")
    public R<GetKnowLedgeDetailResp> getKnowLedgeList(@RequestBody GetKnowLedgeListReq getKnowLedgeListReq){
        Page<GetKnowLedgeDetailResp> page = payKnowledgeService.getKnowLedgeList(getKnowLedgeListReq);
        return R.OK();
    }


    @ApiOperation(value = "获取服务内容详情")
    @GetMapping("/getKnowledgeInfo/{pkId}")
    public R<KnowledgeDomain> getKnowledgeInfo(@PathVariable("pkId")Long pkId){
        KnowledgeDomain knowledgeDomain =  payKnowledgeService.getKnowledgeInfo(pkId);
        return R.OK(knowledgeDomain);
    }


}

