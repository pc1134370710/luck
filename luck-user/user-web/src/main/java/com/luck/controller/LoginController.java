package com.luck.controller;

import com.luck.annotation.NotNeedLogin;
import com.luck.domain.req.UserLoginReq;
import com.luck.domain.resq.RsaResp;
import com.luck.resp.R;
import com.luck.domain.resq.UserLoginResp;
import com.luck.service.IUserInfoService;
import com.luck.utils.PasswordRSAUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

/**
 * @description:
 * @author: pangcheng
 * @create: 2023-02-20 22:11
 **/
@RestController
@RequestMapping("/login")
@Api(value = "登录相关")
public class LoginController {

    @Autowired
    private IUserInfoService userInfoService;

    @Autowired
    private PasswordRSAUtil passwordRSAUtil;

    @ApiOperation(value = "web端登录接口")
    @PostMapping("/webLogin")
    @NotNeedLogin
    public R<UserLoginResp> login(@Valid @RequestBody UserLoginReq userLoginReq){
        UserLoginResp userLoginResp =  userInfoService.login(userLoginReq);
        return R.OK(userLoginResp);
    }


    @GetMapping("/getPublicKey")
    @NotNeedLogin
    public R getPublicKey(){
        String key = UUID.randomUUID().toString();
        String publicKey = passwordRSAUtil.getPublicKey(key);
        RsaResp rsaResp =new RsaResp();
        rsaResp.setPublicKey(publicKey);
        rsaResp.setPublicCode(key);
        return R.OK(rsaResp);
    }

}
