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

import com.wntime.advert.entity.OrderDeliveryAreaEntity;
import com.wntime.advert.service.OrderDeliveryAreaService;
import com.wntime.common.utils.PageUtils;
import com.wntime.common.utils.R;



/**
 * @desc 投放范围表
 *
 * @author ysc
 * @email example@gmail.com
 * @date 2020-11-05 14:17:58
 */
@RestController
@RequestMapping("advert/orderdeliveryarea")
public class OrderDeliveryAreaController {
    @Autowired
    private OrderDeliveryAreaService orderDeliveryAreaService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("advert:orderdeliveryarea:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = orderDeliveryAreaService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{deliveryAreaId}")
    @RequiresPermissions("advert:orderdeliveryarea:info")
    public R info(@PathVariable("deliveryAreaId") Long deliveryAreaId){
		OrderDeliveryAreaEntity orderDeliveryArea = orderDeliveryAreaService.getById(deliveryAreaId);

        return R.ok().put("orderDeliveryArea", orderDeliveryArea);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("advert:orderdeliveryarea:save")
    public R save(@RequestBody OrderDeliveryAreaEntity orderDeliveryArea){
		orderDeliveryAreaService.save(orderDeliveryArea);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("advert:orderdeliveryarea:update")
    public R update(@RequestBody OrderDeliveryAreaEntity orderDeliveryArea){
		orderDeliveryAreaService.updateById(orderDeliveryArea);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("advert:orderdeliveryarea:delete")
    public R delete(@RequestBody Long[] deliveryAreaIds){
		orderDeliveryAreaService.removeByIds(Arrays.asList(deliveryAreaIds));

        return R.ok();
    }

}
