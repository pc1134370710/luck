package com.luck.controller;


import com.luck.constant.CommonEnum;
import com.luck.constant.Constant;
import com.luck.constant.LuckDrawConstant;
import com.luck.constant.MqConstant;
import com.luck.domain.req.AddActivityReq;
import com.luck.domain.resp.LuckDrawResp;
import com.luck.manager.RedisLuckDrawService;
import com.luck.model.UserAuth;
import com.luck.pojo.LuckDrawMessage;
import com.luck.resp.R;
import com.luck.service.IActivityService;
import com.luck.utils.RedisUtils;
import com.luck.utils.UserInfoThreadLocal;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.SendStatus;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author pc
 * @since 2023-02-28
 */
@RestController
@RequestMapping("/activity")
@Api(value = "抽奖活动接口")
public class ActivityController {

    @Autowired
    private IActivityService activityService;

    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    @Autowired
    private RedisLuckDrawService redisLuckDrawService;


    @GetMapping("/luckDraw/{id}")
    @ApiOperation(value = "抽奖接口")
    public R<Object> luckDraw(@PathVariable("id") String id){
        UserAuth userAuth = UserInfoThreadLocal.get();
        LuckDrawMessage luckDrawMessage = new LuckDrawMessage();
        luckDrawMessage.setActivityId(id);
        luckDrawMessage.setUserAuth(userAuth);
        SendResult sendResult = rocketMQTemplate.syncSend(MqConstant.LUCK_DRAW, luckDrawMessage);
        if(SendStatus.SEND_OK.name().equals(sendResult.getSendStatus().name())){
            return R.OK();
        }
        return R.ERROR(CommonEnum.LUCK_DRAW_ERROR);
    }

    @GetMapping("/getLuckDrawResult/{activityId}")
    @ApiOperation(value = "根据活动id获取抽奖结果")
    public R<LuckDrawResp> getLuckDrawResult(@PathVariable String activityId){
        UserAuth userAuth = UserInfoThreadLocal.get();
        return redisLuckDrawService.getLuckDrawResult(activityId,userAuth);
    }


    @PostMapping("/addActivity")
    @ApiOperation(value = "添加抽奖活动")
    public R<Object> addActivity(@RequestBody AddActivityReq addActivityReq){
        activityService.addActivity(addActivityReq);
        return R.OK();
    }

    @GetMapping("/delActivity/{id}")
    @ApiOperation(value = "删除抽奖活动")
    public R<Object> delActivity(@PathVariable("id") String id){
        activityService.removeById(id);
        return R.OK();
    }
}

