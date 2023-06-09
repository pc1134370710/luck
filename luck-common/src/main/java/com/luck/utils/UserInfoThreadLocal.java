package com.luck.utils;

import com.luck.model.UserAuth;

/**
 * @description:
 * @author: pangcheng
 * @time: 2023/2/2 10:44
 */
public class UserInfoThreadLocal {

    private static ThreadLocal<UserAuth> userAuthThreadLocal  = new ThreadLocal<>();
//    private static InheritableThreadLocal<UserAuth> userAuthThreadLocal  = new InheritableThreadLocal<>();
    public static void set(UserAuth userAuth){
        userAuthThreadLocal.set(userAuth);
    }
    public static UserAuth get(){
      return   userAuthThreadLocal.get();
    }
    public static void remove(){
      userAuthThreadLocal.remove();
    }
}
