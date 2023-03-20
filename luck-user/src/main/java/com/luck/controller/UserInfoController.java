package com.luck.controller;


import com.luck.domin.req.RegisterUserReq;
import com.luck.resp.R;
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
 * <p>
 *  前端控制器
 * </p>
 *
 * @author pc
 * @since 2023-03-20
 */
@RestController
@RequestMapping("/user")
@Api(value = "用户相关操作")
public class UserInfoController {


    @Autowired
    private IUserInfoService userInfoService;


    @PostMapping("/register")
    @ApiOperation(value = "注册用户接口")
    public R<Object> registerUser(@RequestBody @Valid RegisterUserReq registerUserReq){
        userInfoService.registerUser(registerUserReq);
        return R.OK();
    }




}

