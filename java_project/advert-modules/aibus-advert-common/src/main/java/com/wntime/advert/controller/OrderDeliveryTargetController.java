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

import com.wntime.advert.entity.OrderDeliveryTargetEntity;
import com.wntime.advert.service.OrderDeliveryTargetService;
import com.wntime.common.utils.PageUtils;
import com.wntime.common.utils.R;



/**
 * @desc 投放标签表
 *
 * @author ysc
 * @email example@gmail.com
 * @date 2020-11-05 14:17:58
 */
@RestController
@RequestMapping("advert/orderdeliverytarget")
public class OrderDeliveryTargetController {
    @Autowired
    private OrderDeliveryTargetService orderDeliveryTargetService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("advert:orderdeliverytarget:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = orderDeliveryTargetService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{deliveryTargetId}")
    @RequiresPermissions("advert:orderdeliverytarget:info")
    public R info(@PathVariable("deliveryTargetId") Long deliveryTargetId){
		OrderDeliveryTargetEntity orderDeliveryTarget = orderDeliveryTargetService.getById(deliveryTargetId);

        return R.ok().put("orderDeliveryTarget", orderDeliveryTarget);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("advert:orderdeliverytarget:save")
    public R save(@RequestBody OrderDeliveryTargetEntity orderDeliveryTarget){
		orderDeliveryTargetService.save(orderDeliveryTarget);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("advert:orderdeliverytarget:update")
    public R update(@RequestBody OrderDeliveryTargetEntity orderDeliveryTarget){
		orderDeliveryTargetService.updateById(orderDeliveryTarget);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("advert:orderdeliverytarget:delete")
    public R delete(@RequestBody Long[] deliveryTargetIds){
		orderDeliveryTargetService.removeByIds(Arrays.asList(deliveryTargetIds));

        return R.ok();
    }

}
