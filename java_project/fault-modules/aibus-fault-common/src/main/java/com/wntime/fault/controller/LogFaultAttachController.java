package com.wntime.fault.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.wntime.common.annotation.SysLog;
import com.wntime.common.utils.PageUtils;
import com.wntime.common.utils.R;
import com.wntime.common.validator.ValidatorUtils;

import com.wntime.fault.entity.InfoFaultExtendEntity;
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

import com.wntime.fault.entity.LogFaultAttachEntity;
import com.wntime.fault.service.LogFaultAttachService;



/**
 * @desc 故障附件表
 *
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2020-08-25 13:48:11
 */
@RestController
@RequestMapping("fault/logfaultattach")
@Api(value = "故障附件管理", tags = {"故障附件管理"})
public class LogFaultAttachController {
    @Autowired
    private LogFaultAttachService logFaultAttachService;

    /**
     * 列表分页
     */
    @RequestMapping("/listByCollectEventId")
    @SysLog("指定故障日志的附件列表@desc 故障附件表")
//    @RequiresPermissions("fault:infofaulttype:list")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "faultDetailId", value = "日志ID", required = true, dataType = "long"),
            @ApiImplicitParam(paramType = "query", name = "params", value = "参数", dataType = "string")
    })
    public R listByCollectEventId(@RequestParam Map<String, Object> params) {
        List<LogFaultAttachEntity> list = logFaultAttachService.queryListByEventId(
                Long.parseLong(String.valueOf(params.get("faultDetailId"))));
        return R.ok().put("list", list);
    }

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("fault:logfaultattach:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = logFaultAttachService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{faultAttachId}")
    @RequiresPermissions("fault:logfaultattach:info")
    public R info(@PathVariable("faultAttachId") Long faultAttachId){
        LogFaultAttachEntity logFaultAttach = logFaultAttachService.getById(faultAttachId);

        return R.ok().put("logFaultAttach", logFaultAttach);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("fault:logfaultattach:save")
    public R save(@RequestBody LogFaultAttachEntity logFaultAttach){
        logFaultAttachService.save(logFaultAttach);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("fault:logfaultattach:update")
    public R update(@RequestBody LogFaultAttachEntity logFaultAttach){
        ValidatorUtils.validateEntity(logFaultAttach);
        logFaultAttachService.updateById(logFaultAttach);
        
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("fault:logfaultattach:delete")
    public R delete(@RequestBody Long[] faultAttachIds){
        logFaultAttachService.removeByIds(Arrays.asList(faultAttachIds));

        return R.ok();
    }

}
