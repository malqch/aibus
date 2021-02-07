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

import com.wntime.advert.entity.OrderAdvertiseAttachEntity;
import com.wntime.advert.service.OrderAdvertiseAttachService;
import com.wntime.common.utils.PageUtils;
import com.wntime.common.utils.R;



/**
 * @desc 广告附件表
 *
 * @author ysc
 * @email example@gmail.com
 * @date 2020-11-05 14:17:58
 */
@RestController
@RequestMapping("advert/orderadvertiseattach")
public class OrderAdvertiseAttachController {
    @Autowired
    private OrderAdvertiseAttachService orderAdvertiseAttachService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("advert:orderadvertiseattach:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = orderAdvertiseAttachService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{advertiseAttachId}")
    @RequiresPermissions("advert:orderadvertiseattach:info")
    public R info(@PathVariable("advertiseAttachId") Long advertiseAttachId){
		OrderAdvertiseAttachEntity orderAdvertiseAttach = orderAdvertiseAttachService.getById(advertiseAttachId);

        return R.ok().put("orderAdvertiseAttach", orderAdvertiseAttach);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("advert:orderadvertiseattach:save")
    public R save(@RequestBody OrderAdvertiseAttachEntity orderAdvertiseAttach){
		orderAdvertiseAttachService.save(orderAdvertiseAttach);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("advert:orderadvertiseattach:update")
    public R update(@RequestBody OrderAdvertiseAttachEntity orderAdvertiseAttach){
		orderAdvertiseAttachService.updateById(orderAdvertiseAttach);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("advert:orderadvertiseattach:delete")
    public R delete(@RequestBody Long[] advertiseAttachIds){
		orderAdvertiseAttachService.removeByIds(Arrays.asList(advertiseAttachIds));

        return R.ok();
    }

}
