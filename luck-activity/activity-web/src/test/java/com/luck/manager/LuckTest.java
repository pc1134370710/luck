package com.luck.manager;

import com.luck.ActivityApplication;
import com.luck.domain.resp.LuckDrawResp;
import com.luck.entity.Activity;
import com.luck.entity.Awards;
import com.luck.pojo.LuckDrawContext;
import com.luck.model.UserAuth;
import com.luck.utils.RedisUtils;
import com.luck.utils.UserInfoThreadLocal;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.concurrent.CountDownLatch;

/**
 * @description:
 * @author: pangcheng
 * @time: 2023/2/26 10:17
 */
@SpringBootTest(classes = ActivityApplication.class)
public class LuckTest {


    @Autowired
    private RedisLuckDrawService redisLuckDrawService;
    @Autowired
    private RedisUtils redisUtils ;


    @Test
    public void Luck() throws InterruptedException {


        Activity activity = new Activity();
        activity.setId("1");
        activity.setActivityName("测试项目");

        Awards awards = new Awards();
        awards.setId("aaa");
        awards.setPkId("1");
        awards.setStock(10);
        awards.setCnt(10);
        awards.setProbability(90);
        awards.setAwardsName("一等奖");


        Awards awards2 = new Awards();
        awards2.setId("bbb");
        awards2.setPkId("2");
        awards2.setStock(10);
        awards2.setCnt(10);
        awards2.setProbability(15);
        awards2.setAwardsName("2等奖");

        Awards awards3 = new Awards();
        awards3.setId("ccc");
        awards3.setStock(10);
        awards3.setCnt(10);
        awards3.setProbability(10);
        awards3.setAwardsName("谢谢参与");
        // 库存预热
        init(awards,"1");
        init(awards2,"1");
        init(awards3,"1");

        CountDownLatch countDownLatch = new CountDownLatch(2);

        new Thread(()->{
            try {
                UserAuth userAuth  = new UserAuth();
                userAuth.setUserId("1");
                UserInfoThreadLocal.set(userAuth);
                LuckDrawContext luckDrawContext = new LuckDrawContext();
                luckDrawContext.setActivity(activity);
                luckDrawContext.setAwardsList(Arrays.asList(awards,awards2,awards3));
                LuckDrawResp luckDrawResp = redisLuckDrawService.luckDraw(luckDrawContext);
                System.out.println(luckDrawResp);
                countDownLatch.countDown();
            }catch (Exception e){
                e.printStackTrace();
            }

        }).start();
        new Thread(()->{
            try {
                UserAuth userAuth  = new UserAuth();
                userAuth.setUserId("1");
                UserInfoThreadLocal.set(userAuth);
                LuckDrawContext luckDrawContext = new LuckDrawContext();
                luckDrawContext.setActivity(activity);
                luckDrawContext.setAwardsList(Arrays.asList(awards,awards2,awards3));
                LuckDrawResp luckDrawResp = redisLuckDrawService.luckDraw(luckDrawContext);
                System.out.println(luckDrawResp);
                countDownLatch.countDown();
            }catch (Exception e){
                e.printStackTrace();
            }
        }).start();
        countDownLatch.await();
    }

    private void init(Awards awards,String ayId){
        redisUtils.set(ayId+awards.getId(),awards.getStock());
    }


}
