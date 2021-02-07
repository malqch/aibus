package com.wntime.advert.controller;

import java.util.Arrays;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wntime.advert.entity.LogCheckItemEntity;
import com.wntime.advert.service.LogCheckItemService;
import com.wntime.common.utils.PageUtils;
import com.wntime.common.utils.R;



/**
 * @desc 审核违规表
 *
 * @author ysc
 * @email example@gmail.com
 * @date 2020-11-05 14:17:58
 */
@RestController
@RequestMapping("advert/logcheckitem")
public class LogCheckItemController {
    @Autowired
    private LogCheckItemService logCheckItemService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("advert:logcheckitem:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = logCheckItemService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{checkItemId}")
    @RequiresPermissions("advert:logcheckitem:info")
    public R info(@PathVariable("checkItemId") Long checkItemId){
		LogCheckItemEntity logCheckItem = logCheckItemService.getById(checkItemId);

        return R.ok().put("logCheckItem", logCheckItem);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("advert:logcheckitem:save")
    public R save(@RequestBody LogCheckItemEntity logCheckItem){
		logCheckItemService.save(logCheckItem);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("advert:logcheckitem:update")
    public R update(@RequestBody LogCheckItemEntity logCheckItem){
		logCheckItemService.updateById(logCheckItem);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("advert:logcheckitem:delete")
    public R delete(@RequestBody Long[] checkItemIds){
		logCheckItemService.removeByIds(Arrays.asList(checkItemIds));

        return R.ok();
    }

}
