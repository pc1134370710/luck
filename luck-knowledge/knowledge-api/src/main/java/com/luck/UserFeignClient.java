package com.luck;

import com.luck.resp.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @description:
 * @author: pangcheng
 * @time: 2023/3/21 17:46
 */
@FeignClient(value = "")
public interface UserFeignClient {

    /**
     * 获取用户信息
     * @return
     */
    @GetMapping("/")
    R getUserInfo();

}
