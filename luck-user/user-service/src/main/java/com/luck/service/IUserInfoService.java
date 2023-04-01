package com.luck.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.luck.domain.req.GetUserListReq;
import com.luck.domain.req.RegisterUserReq;
import com.luck.domain.req.UserLoginReq;
import com.luck.entity.UserInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.luck.domain.resq.UserLoginResp;
import com.luck.pojo.UserInfoDomain;
import com.luck.pojo.UserLookPowerDomain;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author pc
 * @since 2023-02-20
 */
public interface IUserInfoService extends IService<UserInfo> {

    /**
     * 登录逻辑处理
     * @param userLoginReq
     * @return
     */
    UserLoginResp login(UserLoginReq userLoginReq);

    /**
     * 注册用户
     * @param registerUserReq
     */
    void registerUser(RegisterUserReq registerUserReq);

    /**
     * 获取用户信息
     * @param userId
     * @return
     */
    UserInfoDomain getUserInfo(String userId);


    Page<UserInfo> getUserList(GetUserListReq getUserListReq);

    /**
     * 获取用户查看权限
     * @param userId
     * @return
     */
    UserLookPowerDomain getLookPower(String userId);
}
