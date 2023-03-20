package com.luck.service;

import com.luck.domin.req.RegisterUserReq;
import com.luck.domin.req.UserLoginReq;
import com.luck.entity.UserInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.luck.domin.resq.UserLoginResp;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author pc
 * @since 2023-03-20
 */
public interface IUserInfoService extends IService<UserInfo> {

    /**
     * 登录逻辑处理
     * @param userLoginReq
     * @return
     */
    UserLoginResp login(UserLoginReq userLoginReq);

    void registerUser(RegisterUserReq registerUserReq);
}
