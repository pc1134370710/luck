package com.luck.user;

import com.luck.pojo.UserLookPowerDomain;
import com.luck.resp.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @description:
 * @author: pangcheng
 * @time: 2023/2/21 17:46
 */
@FeignClient(value = "userServe")
public interface UserFeignClient {

    /**
     * 获取用户信息
     * @return
     */
    @GetMapping("/user/user/getUserInfo/{userId}")
    R getUserInfo(@PathVariable("userId")String userId);

    /**
     * 获取用户访问权限
     * @param userId
     * @return
     */
    @GetMapping("/user/user/getLookPower/{userId}")
    R<UserLookPowerDomain> getLookPower(@PathVariable("userId")String userId);
}
