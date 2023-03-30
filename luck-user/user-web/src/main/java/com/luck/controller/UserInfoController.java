package com.luck.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.luck.domain.req.GetUserListReq;
import com.luck.domain.req.RegisterUserReq;
import com.luck.domain.req.UpdateUserReq;
import com.luck.entity.UserInfo;
import com.luck.pojo.UserInfoDomain;
import com.luck.resp.R;
import com.luck.service.IUserInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author pc
 * @since 2023-02-20
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

    @PostMapping()
    @ApiOperation(value = "修改用户")
    public R<Object> updateUser(@RequestBody UpdateUserReq updateUserReq){
        UserInfo user = new UserInfo();
        BeanUtils.copyProperties(updateUserReq,user);
        userInfoService.updateById(user);
        return R.OK();
    }

    @PostMapping("/getUserList")
    @ApiOperation(value = "获取用户列表")
    public R<Page<UserInfo>> getUserList(@RequestBody @Valid GetUserListReq getUserListReq){
        return R.OK(userInfoService.getUserList(getUserListReq));
    }

    @GetMapping("/getUserInfo/{id}")
    @ApiOperation(value = "根据用户id 获取用户信息")
    public R<UserInfoDomain> getUserInfo(@PathVariable("id") String userId){
        return R.OK(userInfoService.getUserInfo(userId));
    }





}

