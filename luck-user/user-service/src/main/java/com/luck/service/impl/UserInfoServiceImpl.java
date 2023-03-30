package com.luck.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.luck.constant.CommonEnum;
import com.luck.constant.Constant;
import com.luck.domain.req.GetUserListReq;
import com.luck.domain.req.RegisterUserReq;
import com.luck.domain.req.UserLoginReq;
import com.luck.entity.UserInfo;
import com.luck.exception.GlobalException;
import com.luck.mapper.UserInfoMapper;
import com.luck.model.UserAuth;
import com.luck.pojo.UserInfoDomain;
import com.luck.resp.R;
import com.luck.domain.resq.UserLoginResp;
import com.luck.service.IUserInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.luck.utils.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author pc
 * @since 2023-02-20
 */
@Service
@Slf4j
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo> implements IUserInfoService {

    @Autowired
    private UserInfoMapper userInfoMapper;
    @Autowired
    private PasswordRSAUtil passwordRSAUtil;

    @Autowired
    private RedisUtils redisUtils;

    @Override
    public UserLoginResp login(UserLoginReq userLoginReq) {
        QueryWrapper<UserInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_name",userLoginReq.getUserName());
        UserInfo userInfo = userInfoMapper.selectOne(queryWrapper);
        // rsa 解密
        String pwd = passwordRSAUtil.decrypt(userLoginReq.getKey(),userLoginReq.getPassword());

        String loginPwd = PasswordEncoder.encode(pwd,userInfo.getSalt());
        if(!loginPwd.equals(userInfo.getPassword())){
            // 登录失败
            log.warn("cmd =  login | msg = 登录失败，密码错误 | userLoginReq={}",userLoginReq );
            throw new GlobalException(R.ERROR(CommonEnum.LOGIN_FAIL.getCode(),CommonEnum.LOGIN_FAIL.getMsg()));
        }
        // 生成 token 存入redis 缓存中
        Map<String,Object> map = new HashMap<>();
        map.put("userName",userInfo.getUserName());
        map.put("userType",userInfo.getUserType());
        String accessToken = JwtTokenUtil.getAccessToken(userInfo.getId().toString(), map);
        UserLoginResp userLoginResp = new UserLoginResp();
        userLoginResp.setToken(accessToken);
        userLoginResp.setUserId(userInfo.getId());
        userLoginResp.setUserName(userInfo.getUserName());
        userLoginResp.setUserType(userInfo.getUserType());

        UserAuth userAuth = new UserAuth();
        userAuth.setUserId(userInfo.getId());
        userAuth.setUserName(userInfo.getUserName());
        userAuth.setUserType(userInfo.getUserType());
        redisUtils.set(accessToken,userAuth, Constant.REDIS_USER_AUTO_TIME_EXP);

        return userLoginResp;
    }

    @Override
    public void registerUser(RegisterUserReq registerUserReq) {
        // 雪花算法获取id
        UserInfo userInfo = new UserInfo();
        userInfo.setId(Snowflake.nextId());
        userInfo.setUserName(registerUserReq.getUserName());
        // rsa 解密
        String pwd = passwordRSAUtil.decrypt(registerUserReq.getKey(),registerUserReq.getPassword());
        // 获取md5 加密盐值
        String salt = PasswordEncoder.getSalt();
        pwd = PasswordEncoder.encode(pwd,salt);
        userInfo.setPassword(pwd);
        userInfo.setUserType(Constant.COMMON_USER);
        userInfo.setStatus(1);
        userInfo.setSalt(salt);
        userInfoMapper.insert(userInfo);
    }

    @Override
    public Page<UserInfo> getUserList(GetUserListReq getUserListReq) {
        return userInfoMapper.getUserList(getUserListReq.getPage(),getUserListReq);
    }

    @Override
    public UserInfoDomain getUserInfo(String userId) {
        UserInfo userInfo = userInfoMapper.selectById(userId);
        UserInfoDomain userInfoDomain = new UserInfoDomain();
        userInfoDomain.setUserId(userId);
        userInfoDomain.setUserName(userInfo.getUserName());
        userInfoDomain.setUserType(userInfo.getUserType());
        return userInfoDomain;
    }
}
