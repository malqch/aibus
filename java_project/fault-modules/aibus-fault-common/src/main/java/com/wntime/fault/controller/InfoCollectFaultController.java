package com.wntime.fault.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wntime.common.annotation.SysLog;
import com.wntime.common.utils.DateUtils;
import com.wntime.common.utils.PageUtils;
import com.wntime.common.utils.R;
import com.wntime.common.validator.ValidatorUtils;

import com.wntime.controller.AbstractController;
import com.wntime.fault.entity.InfoFaultLevelEntity;
import com.wntime.fault.entity.InfoFaultSuggestionEntity;
import com.wntime.fault.form.DeleteFaultBatchVo;
import com.wntime.fault.service.InfoFaultSuggestionService;
import com.wntime.service.common.vo.DeleteBatchVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import com.wntime.fault.entity.InfoCollectFaultEntity;
import com.wntime.fault.service.InfoCollectFaultService;



/**
 * @desc 故障采集表
 *
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2020-08-25 13:48:11
 */
@RestController
@RequestMapping("fault/infocollectfault")
@Api(value = "故障采集管理", tags = {"故障采集管理"})
public class InfoCollectFaultController extends AbstractController {
    @Autowired
    private InfoCollectFaultService infoCollectFaultService;
    @Autowired
    private InfoFaultSuggestionService infoFaultSuggestionService;

    /**
     * 列表分页
     */
    @RequestMapping("/page")
//    @RequiresPermissions("fault:infofaulttype:list")
    @SysLog("查询分页@desc 故障采集表")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "page", value = "请求页码", required = true, dataType = "int"),
            @ApiImplicitParam(paramType = "query", name = "limit", value = "每页数量", required = true, dataType = "int"),
            @ApiImplicitParam(paramType = "query", name = "collectFault", value = "采集故障", required = false, dataType = "string"),
            @ApiImplicitParam(paramType = "query", name = "faultTypeId", value = "类型ID", required = false, dataType = "long"),
            @ApiImplicitParam(paramType = "query", name = "faultTargetId", value = "标签ID", required = false, dataType = "long"),
            @ApiImplicitParam(paramType = "query", name = "faultLevelId", value = "级别ID", required = false, dataType = "long"),
            @ApiImplicitParam(paramType = "query", name = "params", value = "参数", dataType = "string")
    })
    @ApiOperation(value = "分页查询", httpMethod = "GET", notes = "分页查询")
    public R page(@RequestParam Map<String, Object> params) {
        PageUtils page = infoCollectFaultService.queryPage(params);
        return R.ok().put("page", page);
    }

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("fault:infocollectfault:list")
    @SysLog("查询列表@desc 故障采集表")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = infoCollectFaultService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{collectFaultId}")
//    @RequiresPermissions("fault:infocollectfault:info")
    @SysLog("故障采集详细@desc 故障采集表")
    @ApiOperation(value = "查询详细信息", httpMethod = "GET", notes = "查询详细信息集")
    public R info(@PathVariable("collectFaultId") Long collectFaultId){
        InfoCollectFaultEntity infoCollectFault = infoCollectFaultService.getDetailById(collectFaultId);
        return R.ok().put("data", infoCollectFault);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @SysLog("保存@desc 故障采集表")
//    @RequiresPermissions("fault:infocollectfault:save")
    @ApiOperation(value = "保存故障采集", httpMethod = "POST", notes = "保存故障采集")
    public R save(@RequestBody InfoCollectFaultEntity infoCollectFault){
        try {
            ValidatorUtils.validateEntity(infoCollectFault);

            QueryWrapper<InfoCollectFaultEntity> QueryWrapperGet = new QueryWrapper<InfoCollectFaultEntity>()
                    .eq("is_deleted", 0)
                    .eq("fault_level_id",infoCollectFault.getFaultLevelId())
                    .eq("fault_type_id",infoCollectFault.getFaultTypeId())
                    .eq("fault_target_id",infoCollectFault.getFaultTargetId());

            InfoCollectFaultEntity get = infoCollectFaultService.getOne(QueryWrapperGet);
            if(get != null){
                return R.error("故障采集[故障类型、故障标签、故障级别]数据已存在，保存失败。");
            }

            infoCollectFault.setIsDeleted(0);
            infoCollectFault.setCreatedBy(getUserId());
            infoCollectFault.setCreatedDate(DateUtils.getTimestamp());
            infoCollectFaultService.save(infoCollectFault);

            // 保存故障方案
            QueryWrapper<InfoFaultSuggestionEntity> QueryWrapper = new QueryWrapper<InfoFaultSuggestionEntity>()
                    .eq("fault_target_id", infoCollectFault.getFaultTargetId())
                    .eq("is_deleted", 0);
            InfoFaultSuggestionEntity  infoFaultSuggestionEntity = infoFaultSuggestionService.getOne(QueryWrapper);
            if(infoFaultSuggestionEntity == null){
                infoFaultSuggestionEntity = new InfoFaultSuggestionEntity();
                infoFaultSuggestionEntity.setFaultTargetId(infoCollectFault.getFaultTargetId());
                infoFaultSuggestionEntity.setSuggestionContent(infoCollectFault.getSuggestionContent());
                infoFaultSuggestionEntity.setCreatedBy(getUserId());
                infoFaultSuggestionEntity.setCreatedDate(DateUtils.getTimestamp());
                infoFaultSuggestionEntity.setIsDeleted(0);
                infoFaultSuggestionEntity.setIsEnabled(1);
                infoFaultSuggestionService.save(infoFaultSuggestionEntity);
            }else{
                infoFaultSuggestionEntity.setFaultTargetId(infoCollectFault.getFaultTargetId());
                infoFaultSuggestionEntity.setSuggestionContent(infoCollectFault.getSuggestionContent());
                infoFaultSuggestionEntity.setCreatedBy(getUserId());
                infoFaultSuggestionEntity.setCreatedDate(DateUtils.getTimestamp());
                infoFaultSuggestionEntity.setIsDeleted(0);
                infoFaultSuggestionEntity.setIsEnabled(1);
                infoFaultSuggestionService.updateById(infoFaultSuggestionEntity);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return R.error("保存故障采集失败");
        }
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @SysLog("修改@desc 故障采集表")
    @ApiOperation(value = "修改故障采集", httpMethod = "POST", notes = "修改故障采集")
//    @RequiresPermissions("fault:infocollectfault:update")
    public R update(@RequestBody InfoCollectFaultEntity infoCollectFault){

        try {
            ValidatorUtils.validateEntity(infoCollectFault);

            QueryWrapper<InfoCollectFaultEntity> QueryWrapperGet = new QueryWrapper<InfoCollectFaultEntity>()
                    .eq("is_deleted", 0)
                    .ne("collect_fault_id",infoCollectFault.getCollectFaultId())
                    .eq("fault_level_id",infoCollectFault.getFaultLevelId())
                    .eq("fault_type_id",infoCollectFault.getFaultTypeId())
                    .eq("fault_target_id",infoCollectFault.getFaultTargetId());

            InfoCollectFaultEntity get = infoCollectFaultService.getOne(QueryWrapperGet);
            if(get != null){
                return R.error("故障采集[故障类型、故障标签、故障级别]数据已存在，保存失败。");
            }

            infoCollectFault.setModifiedBy(getUserId());
            infoCollectFault.setModifiedDate(DateUtils.getTimestamp());
            infoCollectFaultService.updateById(infoCollectFault);

            // 修改故障方案
            QueryWrapper<InfoFaultSuggestionEntity> QueryWrapper = new QueryWrapper<InfoFaultSuggestionEntity>()
                    .eq("fault_target_id", infoCollectFault.getFaultTargetId())
                    .eq("is_deleted", 0);
            InfoFaultSuggestionEntity  infoFaultSuggestionEntity = infoFaultSuggestionService.getOne(QueryWrapper);
            if(infoFaultSuggestionEntity == null){
                infoFaultSuggestionEntity = new InfoFaultSuggestionEntity();
                infoFaultSuggestionEntity.setFaultTargetId(infoCollectFault.getFaultTargetId());
                infoFaultSuggestionEntity.setSuggestionContent(infoCollectFault.getSuggestionContent());
                infoFaultSuggestionEntity.setCreatedBy(getUserId());
                infoFaultSuggestionEntity.setCreatedDate(DateUtils.getTimestamp());
                infoFaultSuggestionEntity.setIsDeleted(0);
                infoFaultSuggestionEntity.setIsEnabled(1);
                infoFaultSuggestionService.save(infoFaultSuggestionEntity);
            }else{
                infoFaultSuggestionEntity.setFaultTargetId(infoCollectFault.getFaultTargetId());
                infoFaultSuggestionEntity.setSuggestionContent(infoCollectFault.getSuggestionContent());
                infoFaultSuggestionEntity.setCreatedBy(getUserId());
                infoFaultSuggestionEntity.setCreatedDate(DateUtils.getTimestamp());
                infoFaultSuggestionEntity.setIsDeleted(0);
                infoFaultSuggestionEntity.setIsEnabled(1);
                infoFaultSuggestionService.updateById(infoFaultSuggestionEntity);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return R.error("修改故障采集失败");
        }

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("fault:infocollectfault:delete")
    public R delete(@RequestBody Long[] collectFaultIds){
        infoCollectFaultService.removeByIds(Arrays.asList(collectFaultIds));
        return R.ok();
    }
    /**
     * 删除
     */
    @ApiOperation(value = "单条删除", httpMethod = "DELETE", notes = "单条删除")
    @SysLog("单条@desc 故障采集表")
    @DeleteMapping("/delete/{id}")
    public R delete(@PathVariable("id") Long id) {
        logger.info("单条删除,id:" + id);
        try {
            infoCollectFaultService.delById(id, getUserId());
        } catch (Exception e) {
            e.printStackTrace();
            return R.error("修改失败");
        }
        return R.ok();
    }

    @ApiOperation(value = "批量删除", httpMethod = "DELETE", notes = "批量删除")
    @SysLog("批量删除@desc 故障采集表")
    @DeleteMapping("/list/delete")
    public R deleteBatch(@RequestBody DeleteBatchVo deleteBatchVo) {
        logger.info("批量删除,:" + Arrays.toString(deleteBatchVo.getIds()));

        if (deleteBatchVo.getIds() == null || deleteBatchVo.getIds().length == 0 || StringUtils.isEmpty(deleteBatchVo.getValidatePassword())) {
            return R.error("参数不能为空!");
        }

        //校验当前用户的密码是否正确
        if (!validatePassword(deleteBatchVo.getValidatePassword())) {
            return R.error("密码不正确");
        }
        try {
            infoCollectFaultService.deleteBatch(deleteBatchVo.getIds(), getUserId());
        } catch (Exception e) {
            e.printStackTrace();
            return R.error("批量删除出错!");
        }

        return R.ok("批量删除成功!");
    }

}
