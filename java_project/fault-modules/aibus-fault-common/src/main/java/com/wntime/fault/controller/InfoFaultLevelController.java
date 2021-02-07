package com.wntime.fault.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.wntime.common.annotation.SysLog;
import com.wntime.common.utils.DateUtils;
import com.wntime.common.utils.PageUtils;
import com.wntime.common.utils.R;
import com.wntime.common.validator.ValidatorUtils;

import com.wntime.controller.AbstractController;
import com.wntime.fault.entity.InfoFaultTypeEntity;
import com.wntime.fault.form.DeleteFaultBatchVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import com.wntime.fault.entity.InfoFaultLevelEntity;
import com.wntime.fault.service.InfoFaultLevelService;



/**
 * @desc 故障级别表
 *
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2020-08-25 13:48:11
 */
@RestController
@RequestMapping("fault/infofaultlevel")
@Api(value = "故障级别管理", tags = {"故障级别管理"})
public class InfoFaultLevelController extends AbstractController {
    @Autowired
    private InfoFaultLevelService infoFaultLevelService;

    /**
     * 列表分页
     */
    @RequestMapping("/page")
    @SysLog("分页查询@desc 故障级别表")
//    @RequiresPermissions("fault:infofaulttype:list")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "page", value = "请求页码", required = true, dataType = "int"),
            @ApiImplicitParam(paramType = "query", name = "limit", value = "每页数量", required = true, dataType = "int"),
            @ApiImplicitParam(paramType = "query", name = "faultLevelName", value = "故障级别名称", required = false, dataType = "string"),
            @ApiImplicitParam(paramType = "query", name = "params", value = "参数", dataType = "string")
    })
    public R page(@RequestParam Map<String, Object> params) {
        PageUtils page = infoFaultLevelService.queryPage(params);

        return R.ok().put("page", page);
    }

    /**
     * 列表
     */
    @RequestMapping("/list")
    @SysLog("查询列表@desc 故障级别表")
//    @RequiresPermissions("fault:infofaultlevel:list")
    @ApiOperation(value = "获取故障等级列表", notes = "", httpMethod = "GET")
    public R list(){
        QueryWrapper<InfoFaultLevelEntity> QueryWrapper = new QueryWrapper<InfoFaultLevelEntity>()
                .eq("is_deleted", 0).eq("is_enabled",1);

        List<InfoFaultLevelEntity> list = infoFaultLevelService.list(QueryWrapper);
        return R.ok().put("list", list);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{faultLevelId}")
    @SysLog("查询详细@desc 故障级别表")
//    @RequiresPermissions("fault:infofaultlevel:info")
    public R info(@PathVariable("faultLevelId") Long faultLevelId){
        InfoFaultLevelEntity infoFaultLevel = infoFaultLevelService.getById(faultLevelId);

        return R.ok().put("data", infoFaultLevel);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @SysLog("保存@desc 故障级别表")
//    @RequiresPermissions("fault:infofaultlevel:save")
    @ApiOperation(value = "保存故障级别", httpMethod = "POST", notes = "保存故障级别")
    public R save(@RequestBody InfoFaultLevelEntity infoFaultLevel){
        try {
            ValidatorUtils.validateEntity(infoFaultLevel);
            QueryWrapper<InfoFaultLevelEntity> QueryWrapper = new QueryWrapper<InfoFaultLevelEntity>()
                    .eq("is_deleted", 0)
                    .eq("fault_level_code",infoFaultLevel.getFaultLevelCode());

            InfoFaultLevelEntity get = infoFaultLevelService.getOne(QueryWrapper);
            if(get != null){
                return R.error("故障级别[编码:"+infoFaultLevel.getFaultLevelCode()+"]数据已存在，保存失败。");
            }

            infoFaultLevel.setIsDeleted(0);
            infoFaultLevel.setCreatedBy(getUserId());
            infoFaultLevel.setCreatedDate(DateUtils.getTimestamp());
            infoFaultLevelService.save(infoFaultLevel);


        } catch (Exception e) {
            e.printStackTrace();
            return R.error("保存故障类型失败");
        }
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @SysLog("修改@desc 故障级别表")
//    @RequiresPermissions("fault:infofaultlevel:update")
    @ApiOperation(value = "修改故障级别", httpMethod = "POST", notes = "修改故障级别")
    public R update(@RequestBody InfoFaultLevelEntity infoFaultLevel){
        try {
            ValidatorUtils.validateEntity(infoFaultLevel);

            QueryWrapper<InfoFaultLevelEntity> QueryWrapper = new QueryWrapper<InfoFaultLevelEntity>()
                    .eq("is_deleted", 0)
                    .ne("fault_level_id",infoFaultLevel.getFaultLevelId())
                    .eq("fault_level_code",infoFaultLevel.getFaultLevelCode());

            InfoFaultLevelEntity get = infoFaultLevelService.getOne(QueryWrapper);
            if(get != null){
                return R.error("故障级别[编码:"+infoFaultLevel.getFaultLevelCode()+"]数据已存在，保存失败。");
            }
            infoFaultLevel.setModifiedBy(getUserId());
            infoFaultLevel.setModifiedDate(DateUtils.getTimestamp());
            infoFaultLevelService.updateById(infoFaultLevel);
        } catch (Exception e) {
            e.printStackTrace();
            return R.error("修改故障类型失败");
        }
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("fault:infofaultlevel:delete")
    public R delete(@RequestBody Long[] faultLevelIds){
        infoFaultLevelService.removeByIds(Arrays.asList(faultLevelIds));

        return R.ok();
    }

    /**
     * 删除单条
     */
    @ApiOperation(value = "单条删除故障级别", httpMethod = "POST", notes = "单条删除故障级别")
    @SysLog("单条删除故障级别")
    @PostMapping("/delete/{id}")
    public R delete(@PathVariable("id") Long id) {
        try {
            List<Long> ids  =  new ArrayList<>();
            ids.add(id);
            infoFaultLevelService.deleteBatch(ids, getUserId());
        } catch (Exception e) {
            e.printStackTrace();
            return R.error("单条删除故障类型失败");
        }
        return R.ok();
    }

    /**
     * 批量删除
     */
    @PostMapping("/deleteBatch")
//    @RequiresPermissions("fault:infofaulttype:delete")
    @SysLog("批量删除@desc 故障级别表")
    @ApiOperation(value = "批量删除故障级别", httpMethod = "POST", notes = "批量删除故障级别")
    public R deleteBatch( @RequestBody DeleteFaultBatchVo deleteFaultBatchVo) {
        try {
            if (deleteFaultBatchVo.getIds() == null || deleteFaultBatchVo.getIds().length == 0
                    || StringUtils.isEmpty(deleteFaultBatchVo.getValidatePassword())){
                return R.error("参数不能为空!");
            }

            //校验当前用户的密码是否正确
            if (!validatePassword(deleteFaultBatchVo.getValidatePassword())) {
                return R.error("密码不正确");
            }

            infoFaultLevelService.deleteBatch(Arrays.asList(deleteFaultBatchVo.getIds()) ,getUserId());
        } catch (Exception e) {
            e.printStackTrace();
            return R.error("删除故障类型失败");
        }

        return R.ok();
    }

}
