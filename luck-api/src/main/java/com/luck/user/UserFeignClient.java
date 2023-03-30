package com.luck.user;

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
    @GetMapping("/user/{userId}")
    R getUserInfo(@PathVariable("userId")String userId);

}
