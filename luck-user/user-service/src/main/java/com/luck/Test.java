package com.luck;

import com.alibaba.fastjson.JSON;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * @description:
 * @author: pangcheng
 * @time: 2023/3/21 15:30
 */
public class Test {

    public static void main(String[] args) {

        // 快速失败机制
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        for (int i=0;i<list.size();i++){
            if(i==2){
                list.remove(i);
                --i;
            }
        }
        System.out.println(list);

        CountDownLatch countDownLatch= new CountDownLatch(2);
        countDownLatch.countDown();
        new Thread(()->{
            for(int i=1;i<10000006;i++){
                list.add(i);
//                System.out.println(list.toString());
                JSON.toJSONString(list);
            }

        }).start();

        new Thread(()->{
            for(int i=11;i<1000000006;i++){
                list.add(i);
            }
//            System.out.println(list.toString());
        }).start();






    }


}
