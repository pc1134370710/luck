package com.luck.controller;

import com.luck.domin.req.UserLoginReq;
import com.luck.resp.R;
import com.luck.domin.resq.UserLoginResp;
import com.luck.service.IUserInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @description:
 * @author: pangcheng
 * @create: 2023-03-20 22:11
 **/
@RestController
@RequestMapping("/login")
@Api(value = "登录相关")
public class LoginController {

    @Autowired
    private IUserInfoService userInfoService;



    @ApiOperation(value = "web端登录接口")
    @PostMapping("/webLogin")
    public R<UserLoginResp> login(@Valid @RequestBody UserLoginReq userLoginReq){
        UserLoginResp userLoginResp =  userInfoService.login(userLoginReq);
        return R.OK(userLoginResp);
    }




}
