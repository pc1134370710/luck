package com.luck.constant;

/**
 * @description:
 * @author: pangcheng
 * @time: 2023/2/28 16:17
 */
public class LuckDrawConstant {

    /**
     * 抽奖结果缓存key
     */
    public static final String CACHE_KEY_LUCK_DRAW_RESULT ="LuckDrawResult";
    public static final String CACHE_KEY_LUCK_DRAW_RESULT_PREFIX ="userResult";

    public static final String CACHE_KEY_LUCK_DRAW_REPEAT_PREFIX ="luck_draw_repeat";


    /**
     * 获取防止重复抽奖的缓存key
     * @param activityId
     * @param userId
     * @return
     */
    public static String getCacheRepeatDrawKey(String activityId,String userId){
        return CACHE_KEY_LUCK_DRAW_REPEAT_PREFIX +":"+activityId+":"+userId;
    }

    /**
     * 获取 抽奖缓存key
     * @param activityId
     * @return
     */
    public static String getCacheResultKey(String activityId,String userId){
        return CACHE_KEY_LUCK_DRAW_RESULT_PREFIX +":"+activityId+":"+userId;
    }
}
