package com.luck.controller;


import com.luck.domin.req.AddKnowLedgeReq;
import com.luck.resp.R;
import com.luck.service.IPayKnowledgeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

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



}

