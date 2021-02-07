package com.wntime.fault.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.wntime.common.annotation.SysLog;
import com.wntime.common.utils.DateUtils;
import com.wntime.common.utils.PageUtils;
import com.wntime.common.utils.R;
import com.wntime.common.validator.ValidatorUtils;
import com.wntime.controller.AbstractController;
import com.wntime.fault.entity.InfoFaultLevelEntity;
import com.wntime.fault.entity.InfoFaultTypeEntity;
import com.wntime.fault.form.DeleteFaultBatchVo;
import com.wntime.fault.service.InfoFaultTypeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;


/**
 * @author Mark
 * @desc 故障类型表
 * @email sunlightcs@gmail.com
 * @date 2020-08-25 13:48:11
 */
@Api(value = "故障类型管理", tags = {"故障类型管理"})
@RestController
@RequestMapping("fault/faultType")
public class InfoFaultTypeController  extends AbstractController {
    @Autowired
    private InfoFaultTypeService infoFaultTypeService;

    /**
     * 列表分页
     */
    @RequestMapping("/page")
//    @RequiresPermissions("fault:infofaulttype:list")
    @SysLog("分页查询@desc 故障类型表")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "page", value = "请求页码", required = true, dataType = "int"),
            @ApiImplicitParam(paramType = "query", name = "limit", value = "每页数量", required = true, dataType = "int"),
            @ApiImplicitParam(paramType = "query", name = "faultTypeName", value = "故障类型名称", required = false, dataType = "string"),
            @ApiImplicitParam(paramType = "query", name = "params", value = "参数", dataType = "string")
    })
    public R page(@RequestParam Map<String, Object> params) {
        PageUtils page = infoFaultTypeService.queryPage(params);

        return R.ok().put("page", page);
    }

    /**
     * 列表
     */
    @ApiOperation(value = "获取故障类型列表", notes = "", httpMethod = "GET")
    @GetMapping("/list")
    @SysLog("查询列表@desc 故障类型表")
    public R simpleList() {
        LambdaQueryWrapper<InfoFaultTypeEntity> lambdaQueryWrapper = Wrappers
                .<InfoFaultTypeEntity>lambdaQuery()
                .eq(InfoFaultTypeEntity::getIsEnabled, 1)
                .eq(InfoFaultTypeEntity::getIsDeleted, 0);

        List<InfoFaultTypeEntity> list = infoFaultTypeService.list(lambdaQueryWrapper);
        return R.ok().put("list", list);
    }


    /**
     * 信息
     */
    @GetMapping("/info/{faultTypeId}")
    @SysLog("查询详细@desc 故障类型表")
//    @RequiresPermissions("fault:infofaulttype:info")
    @ApiOperation(value = "查询故障类型", httpMethod = "POST", notes = "查询故障类型")
    public R info(@PathVariable("faultTypeId") Long faultTypeId) {
        InfoFaultTypeEntity infoFaultType = infoFaultTypeService.getById(faultTypeId);

        return R.ok().put("data", infoFaultType);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
//    @RequiresPermissions("fault:infofaulttype:save")
    @SysLog("保存@desc 故障类型表")
    @ApiOperation(value = "保存故障类型", httpMethod = "POST", notes = "保存故障类型")
    public R save(@RequestBody InfoFaultTypeEntity infoFaultType) {
        try {
            ValidatorUtils.validateEntity(infoFaultType);

            QueryWrapper<InfoFaultTypeEntity> QueryWrapper = new QueryWrapper<InfoFaultTypeEntity>()
                    .eq("is_deleted", 0)
                    .eq("fault_type_code",infoFaultType.getFaultTypeCode());

            InfoFaultTypeEntity get = infoFaultTypeService.getOne(QueryWrapper);
            if(get != null){
                return R.error("故障类型[编码:"+infoFaultType.getFaultTypeCode()+"]数据已存在，保存失败。");
            }

            infoFaultType.setIsDeleted(0);
            infoFaultType.setCreatedBy(getUserId());
            infoFaultType.setCreatedDate(DateUtils.getTimestamp());
            infoFaultTypeService.save(infoFaultType);
        } catch (Exception e) {
            e.printStackTrace();
            return R.error("保存故障类型失败");
        }

        return R.ok();
    }

    /**
     * 修改
     */
    @PostMapping("/update")
//    @RequiresPermissions("fault:infofaulttype:update")
    @SysLog("修改@desc 故障类型表")
    @ApiOperation(value = "修改故障类型", httpMethod = "POST", notes = "修改故障类型")
    public R update(@RequestBody InfoFaultTypeEntity infoFaultType) {
        try {
            ValidatorUtils.validateEntity(infoFaultType);
            QueryWrapper<InfoFaultTypeEntity> QueryWrapper = new QueryWrapper<InfoFaultTypeEntity>()
                    .eq("is_deleted", 0)
                    .ne("fault_type_id",infoFaultType.getFaultTypeId())
                    .eq("fault_type_code",infoFaultType.getFaultTypeCode());

            InfoFaultTypeEntity get = infoFaultTypeService.getOne(QueryWrapper);
            if(get != null){
                return R.error("故障类型[编码:"+infoFaultType.getFaultTypeCode()+"]数据已存在，修改失败。");
            }

            infoFaultType.setModifiedBy(getUserId());
            infoFaultType.setModifiedDate(DateUtils.getTimestamp());
            infoFaultTypeService.updateById(infoFaultType);
        } catch (Exception e) {
            e.printStackTrace();
            return R.error("修改故障类型失败");
        }

        return R.ok();
    }

    /**
     * 删除
     */
    @ApiOperation(value = "单条删除故障类型", httpMethod = "POST", notes = "单条删除故障类型")
    @SysLog("单条删除@desc 故障类型表")
    @PostMapping("/delete/{id}")
    public R delete(@PathVariable("id") Long id) {
        try {
            List<Long> ids  =  new ArrayList<>();
            ids.add(id);
            infoFaultTypeService.deleteBatch(ids, getUserId());
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
    @SysLog("批量删除@desc 故障类型表")
    @ApiOperation(value = "批量删除故障类型", httpMethod = "POST", notes = "批量删除故障类型")
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

            infoFaultTypeService.deleteBatch(Arrays.asList(deleteFaultBatchVo.getIds()) ,getUserId());
        } catch (Exception e) {
            e.printStackTrace();
            return R.error("删除故障类型失败");
        }

        return R.ok();
    }

}
