package com.luck.controller;


import com.luck.domain.req.AddActivityReq;
import com.luck.resp.R;
import com.luck.service.IActivityService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author pc
 * @since 2023-03-28
 */
@RestController
@RequestMapping("/activity")
@Api(value = "抽奖活动接口")
public class ActivityController {

    @Autowired
    private IActivityService activityService;

    @GetMapping("/luckDraw/{id}")
    @ApiOperation(value = "抽奖接口")
    public R<Object> luckDraw(@PathVariable("id") String id){

        return R.OK();
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

