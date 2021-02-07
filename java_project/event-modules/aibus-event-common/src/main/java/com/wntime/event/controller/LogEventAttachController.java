package com.wntime.event.controller;

import java.util.Arrays;
import java.util.Map;

import com.wntime.common.utils.PageUtils;
import com.wntime.common.utils.R;
import com.wntime.common.validator.ValidatorUtils;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wntime.event.entity.LogEventAttachEntity;
import com.wntime.event.service.LogEventAttachService;



/**
 * @desc 事件附件表
 *
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2020-08-25 13:34:24
 */
@RestController
@RequestMapping("event/logeventattach")
public class LogEventAttachController {
    @Autowired
    private LogEventAttachService logEventAttachService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("event:logeventattach:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = logEventAttachService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{eventAttachId}")
    @RequiresPermissions("event:logeventattach:info")
    public R info(@PathVariable("eventAttachId") Long eventAttachId){
        LogEventAttachEntity logEventAttach = logEventAttachService.getById(eventAttachId);

        return R.ok().put("logEventAttach", logEventAttach);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("event:logeventattach:save")
    public R save(@RequestBody LogEventAttachEntity logEventAttach){
        logEventAttachService.save(logEventAttach);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("event:logeventattach:update")
    public R update(@RequestBody LogEventAttachEntity logEventAttach){
        ValidatorUtils.validateEntity(logEventAttach);
        logEventAttachService.updateById(logEventAttach);
        
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("event:logeventattach:delete")
    public R delete(@RequestBody Long[] eventAttachIds){
        logEventAttachService.removeByIds(Arrays.asList(eventAttachIds));

        return R.ok();
    }

}
