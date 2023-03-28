package com.luck.manager;

import cn.hutool.core.util.RandomUtil;
import com.luck.domain.resp.LuckDrawResp;
import com.luck.entity.Activity;
import com.luck.entity.Awards;
import com.luck.entity.LuckDraw;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.Collator;
import java.util.*;

/**
 * @description:
 * @author: pangcheng
 * @create: 2023-03-25 14:56
 **/
public abstract class BasicLuckDrawService {


    /**
     *  抽奖算法
     * @param luckDraw 抽奖对象
     * @return
     */
    public final LuckDrawResp luckDraw(LuckDraw luckDraw) {

        LuckDrawResp luckDrawResp = new LuckDrawResp();

        // 校验用户是否已经重复抽奖了
        // 埋点 ,校验抽奖规则， 后续加入 用户满一定积分才可以抽奖，或者不允许重复抽奖 等
        verifyLuckDrawRule(luckDraw.getActivity());

        // 剔除奖项库存 不足的项，就是已经被抽完了
        // 概率为负数或者库存为0的数据，概率设置为0
        verifyAwardStock(luckDraw.getAwardsList());


        // 抽奖算法
        // 洗牌算法 水塘抽样 算法
        // 权重随机抽取
        /*
            1. 计算权重总结果 sum
            2. 将所有权重 从小到大排序 list
            3. 生成一个 0-sum 之间的随机数 rand
            4. 二分在 list 集合中 找到第一个大于或者等于 随机数的rand 值
            5. 该值就是 抽奖结果
            https://www.cnblogs.com/createwell/p/13527165.html
         */

//        获取该抽奖活动对应的奖项
        Integer weightSum = 0;
        // 下标要从1 开始 所以
        Integer[] weight = new Integer[luckDraw.getAwardsList().size()];
        List<Awards> awardsList = luckDraw.getAwardsList();
        // 将商品按照概率 从小到大排序
        Collections.sort(awardsList, new Comparator<Awards>() {
            @Override
            public int compare(Awards o1, Awards o2) {
                return o1.getProbability()<o2.getProbability()?1:-1;
            }
        });
        for(int i=0; i<awardsList.size() ; i++){
            weightSum+=awardsList.get(i).getProbability();
            weight[i]=awardsList.get(i).getProbability();
        }
        Integer randomInt = RandomUtil.randomInt(weightSum);
        // weight中 二分找到 第一个大于或者等于 randomInt 的下标就是中奖项

        int l =0,r = weight.length;
        while (l<r){
            int mid = l+r >>1;
            if(randomInt< weight[mid]){
                r=mid;
            }else{
                l =mid+1;
            }
        }
        // l 就是最终结果，第一个大于或者等于  randomInt 的下标

        Awards awards = awardsList.get(l);
        luckDrawResp.setActivityId(luckDraw.getActivity().getId());
        luckDrawResp.setActivityName(luckDraw.getActivity().getActivityName());
        luckDrawResp.setAwardsName(awards.getAwardsName());

        // 开始扣减库存
        afterHandle(luckDraw.getActivity(),awards);


        return luckDrawResp;
    }

    /**
     * 抽奖结束的后置操作
     * @param activity
     * @param awards
     */
    protected abstract void afterHandle(Activity activity, Awards awards);


    /**
     * 对奖项库存进行处理
     * @param awardsList
     */
    protected abstract void verifyAwardStock(List<Awards> awardsList);

    /**
     * 校验抽奖规则
     * @param activity 抽奖活动
     */
    protected abstract void verifyLuckDrawRule(Activity activity) ;

}
