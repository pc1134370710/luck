package com.luck.service.impl;

import com.luck.service.LuckService;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @description:
 * @author: pangcheng
 * @create: 2023-03-25 14:56
 **/
public class LuckServiceImpl implements LuckService {


    @Override
    public void luck() {

        // 抽奖算法

        // 对用户进行洗牌
        List<String>  user = new ArrayList<>();
        Random random = new Random();
        for(int i = user.size()-1; i>=0;i--){
            int rand = random.nextInt(i);
//            swap(arr[i], arr[rand]);

        }
        for(int i=0;i<user.size();i++){

            int n = random.nextInt(user.size()-1)-i;
            String temp = user.get(i);
            String swap = user.get(n);
            user.remove(i);
            user.remove(n);
            user.add(i,swap);
            user.add(n,temp);
        }

        // 水塘抽样 算法

        // 权重随机抽取
        /*
            1. 计算权重总结果 sum
            2. 将所有权重 从小到大排序 list
            3. 生成一个 0-sum 之间的随机数 rand
            4. 二分在 list 集合中 找到第一个大于或者等于 随机数的rand 值
            5. 该值就是 抽奖结果
            https://www.cnblogs.com/createwell/p/13527165.html
         */




    }
}
