package com.luck.service.impl;

import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.luck.constant.CommonEnum;
import com.luck.domin.UserLoginReq;
import com.luck.entity.UserInfo;
import com.luck.exception.GlobalException;
import com.luck.mapper.UserInfoMapper;
import com.luck.resp.R;
import com.luck.resq.UserLoginResp;
import com.luck.service.IUserInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.luck.utils.JwtTokenUtil;
import com.luck.utils.PasswordEncoder;
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
 * @since 2023-03-20
 */
@Service
@Slf4j
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo> implements IUserInfoService {

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Override
    public UserLoginResp login(UserLoginReq userLoginReq) {
        QueryWrapper<UserInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_name",userLoginReq.getUserName());
        UserInfo userInfo = userInfoMapper.selectOne(queryWrapper);

        String loginPwd = PasswordEncoder.encode(userInfo.getPassword(),userInfo.getSalt());
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
        return userLoginResp;
    }
}
