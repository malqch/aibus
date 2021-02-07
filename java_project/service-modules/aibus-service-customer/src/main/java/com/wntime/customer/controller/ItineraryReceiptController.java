package com.wntime.customer.controller;

import java.util.Arrays;
import java.util.Map;

import com.wntime.customer.entity.ItineraryReceiptEntity;
import com.wntime.customer.service.ItineraryReceiptService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wntime.common.utils.PageUtils;
import com.wntime.common.utils.R;



/**
 * ${comments}
 *
 * @author buxl
 * @email 
 * @date 2021-01-21 16:26:42
 */
@RestController
@RequestMapping("generator/itineraryreceipt")
public class ItineraryReceiptController {
    @Autowired
    private ItineraryReceiptService itineraryReceiptService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("generator:itineraryreceipt:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = itineraryReceiptService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{itineraryId}")
    @RequiresPermissions("generator:itineraryreceipt:info")
    public R info(@PathVariable("itineraryId") Long itineraryId){
		ItineraryReceiptEntity itineraryReceipt = itineraryReceiptService.getById(itineraryId);

        return R.ok().put("itineraryReceipt", itineraryReceipt);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("generator:itineraryreceipt:save")
    public R save(@RequestBody ItineraryReceiptEntity itineraryReceipt){
		itineraryReceiptService.save(itineraryReceipt);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("generator:itineraryreceipt:update")
    public R update(@RequestBody ItineraryReceiptEntity itineraryReceipt){
		itineraryReceiptService.updateById(itineraryReceipt);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("generator:itineraryreceipt:delete")
    public R delete(@RequestBody Long[] itineraryIds){
		itineraryReceiptService.removeByIds(Arrays.asList(itineraryIds));

        return R.ok();
    }

}
