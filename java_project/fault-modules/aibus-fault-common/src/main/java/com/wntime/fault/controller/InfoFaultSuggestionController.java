package com.wntime.fault.controller;

import java.util.Arrays;
import java.util.Map;

import com.wntime.common.annotation.SysLog;
import com.wntime.common.utils.PageUtils;
import com.wntime.common.utils.R;
import com.wntime.common.validator.ValidatorUtils;

import io.swagger.annotations.Api;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wntime.fault.entity.InfoFaultSuggestionEntity;
import com.wntime.fault.service.InfoFaultSuggestionService;



/**
 * @desc 故障方案表
 *
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2020-08-25 13:48:11
 */
@RestController
@RequestMapping("fault/infofaultsuggestion")
public class InfoFaultSuggestionController {
    @Autowired
    private InfoFaultSuggestionService infoFaultSuggestionService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @SysLog("查询列表@desc 故障方案表")
    @RequiresPermissions("fault:infofaultsuggestion:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = infoFaultSuggestionService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{faultSuggestionId}")
    @RequiresPermissions("fault:infofaultsuggestion:info")
    public R info(@PathVariable("faultSuggestionId") Long faultSuggestionId){
        InfoFaultSuggestionEntity infoFaultSuggestion = infoFaultSuggestionService.getById(faultSuggestionId);

        return R.ok().put("infoFaultSuggestion", infoFaultSuggestion);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("fault:infofaultsuggestion:save")
    public R save(@RequestBody InfoFaultSuggestionEntity infoFaultSuggestion){
        infoFaultSuggestionService.save(infoFaultSuggestion);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("fault:infofaultsuggestion:update")
    public R update(@RequestBody InfoFaultSuggestionEntity infoFaultSuggestion){
        ValidatorUtils.validateEntity(infoFaultSuggestion);
        infoFaultSuggestionService.updateById(infoFaultSuggestion);
        
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("fault:infofaultsuggestion:delete")
    public R delete(@RequestBody Long[] faultSuggestionIds){
        infoFaultSuggestionService.removeByIds(Arrays.asList(faultSuggestionIds));

        return R.ok();
    }

}
