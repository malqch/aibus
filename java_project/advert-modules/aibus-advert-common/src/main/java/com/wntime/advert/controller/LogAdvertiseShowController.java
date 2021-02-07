package com.wntime.advert.controller;

import java.util.Arrays;
import java.util.Map;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wntime.advert.entity.LogAdvertiseShowEntity;
import com.wntime.advert.service.LogAdvertiseShowService;
import com.wntime.common.utils.PageUtils;
import com.wntime.common.utils.R;



/**
 * @desc 广告播放日志
 *
 * @author ysc
 * @email example@gmail.com
 * @date 2020-11-05 14:17:58
 */
@RestController
@RequestMapping("advert/logadvertiseshow")
@Api(value = "广告播放日志管理", tags = {"广告播放日志管理"})
public class LogAdvertiseShowController {
    @Autowired
    private LogAdvertiseShowService logAdvertiseShowService;

    /**
     * 列表
     */
    @RequestMapping("/list")
//    @RequiresPermissions("advert:logadvertiseshow:list")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "page", value = "请求页码", required = true, dataType = "int"),
            @ApiImplicitParam(paramType = "query", name = "limit", value = "每页数量", required = true, dataType = "int"),
            @ApiImplicitParam(paramType = "query", name = "advertiseNo", value = "广告单号", required = false, dataType = "string"),
            @ApiImplicitParam(paramType = "query", name = "positionDesc", value = "广告位描述", required = false, dataType = "string"),
            @ApiImplicitParam(paramType = "query", name = "startTime", value = "开始时间", required = false, dataType = "string"),
            @ApiImplicitParam(paramType = "query", name = "endTime", value = "结束时间", required = false, dataType = "string"),
            @ApiImplicitParam(paramType = "query", name = "params", value = "参数", dataType = "string")
    })
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = logAdvertiseShowService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{advertiseShowId}")
    @RequiresPermissions("advert:logadvertiseshow:info")
    public R info(@PathVariable("advertiseShowId") Long advertiseShowId){
		LogAdvertiseShowEntity logAdvertiseShow = logAdvertiseShowService.getById(advertiseShowId);

        return R.ok().put("logAdvertiseShow", logAdvertiseShow);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("advert:logadvertiseshow:save")
    public R save(@RequestBody LogAdvertiseShowEntity logAdvertiseShow){
		logAdvertiseShowService.save(logAdvertiseShow);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("advert:logadvertiseshow:update")
    public R update(@RequestBody LogAdvertiseShowEntity logAdvertiseShow){
		logAdvertiseShowService.updateById(logAdvertiseShow);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("advert:logadvertiseshow:delete")
    public R delete(@RequestBody Long[] advertiseShowIds){
		logAdvertiseShowService.removeByIds(Arrays.asList(advertiseShowIds));

        return R.ok();
    }

}
