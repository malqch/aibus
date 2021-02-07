package com.wntime.fault.controller;

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
import com.wntime.fault.entity.InfoFaultSuggestionEntity;
import com.wntime.service.common.vo.DeleteBatchVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import com.wntime.fault.entity.InfoFaultExtendEntity;
import com.wntime.fault.service.InfoFaultExtendService;



/**
 * @desc 故障扩展表
 *
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2020-08-25 13:48:11
 */
@RestController
@RequestMapping("fault/infofaultextend")
@Api(value = "故障扩展管理", tags = {"故障扩展管理"})
public class InfoFaultExtendController  extends AbstractController {
    @Autowired
    private InfoFaultExtendService infoFaultExtendService;

    /**
     * 列表分页
     */
    @RequestMapping("/page")
    @SysLog("分页查询@desc 故障扩展表")
//    @RequiresPermissions("fault:infofaulttype:list")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "page", value = "请求页码", required = true, dataType = "int"),
            @ApiImplicitParam(paramType = "query", name = "limit", value = "每页数量", required = true, dataType = "int"),
            @ApiImplicitParam(paramType = "query", name = "collectFaultId", value = "故障采集ID", required = true, dataType = "long"),
            @ApiImplicitParam(paramType = "query", name = "params", value = "参数", dataType = "string")
    })
    public R page(@RequestParam Map<String, Object> params) {
        PageUtils page = infoFaultExtendService.queryPage(params);
        return R.ok().put("page", page);
    }

    /**
     * 列表
     */
    @RequestMapping("/listByCollectEventId")
//    @RequiresPermissions("fault:infofaulttype:list")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "collectFaultId", value = "故障采集ID", required = true, dataType = "long")
    })
    public R listByCollectEventId(@RequestParam Map<String, Object> params) {
        params.put("collectFaultId",Long.parseLong(String.valueOf(params.get("collectFaultId"))));
        List<InfoFaultExtendEntity> list = infoFaultExtendService.queryList(params);
        return R.ok().put("list", list);
    }

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("fault:infofaultextend:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = infoFaultExtendService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{faultExtendId}")
    @SysLog("查询详细@desc 故障扩展表")
//    @RequiresPermissions("fault:infofaultextend:info")
    public R info(@PathVariable("faultExtendId") Long faultExtendId){
        InfoFaultExtendEntity infoFaultExtend = infoFaultExtendService.getById(faultExtendId);

        return R.ok().put("data", infoFaultExtend);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @SysLog("保存desc 故障扩展表")
//    @RequiresPermissions("fault:infofaultextend:save")
    @ApiOperation(value = "保存故障采集扩展", httpMethod = "POST", notes = "保存故障采集扩展")
    public R save(@RequestBody InfoFaultExtendEntity infoFaultExtend){
        try {
            ValidatorUtils.validateEntity(infoFaultExtend);
            // 保存故障扩展
            QueryWrapper<InfoFaultExtendEntity> QueryWrapper = new QueryWrapper<InfoFaultExtendEntity>()
                    .eq("collect_fault_id", infoFaultExtend.getCollectFaultId())
                    .eq("fault_target_id", infoFaultExtend.getFaultTargetId())
                    .eq("is_deleted",0)
                    .eq("is_enabled",1);
            InfoFaultExtendEntity  infoFaultExtendEntityGet = infoFaultExtendService.getOne(QueryWrapper);
            if(infoFaultExtendEntityGet == null){
                infoFaultExtend.setIsDeleted(0);
                infoFaultExtend.setCreatedBy(getUserId());
                infoFaultExtend.setCreatedDate(DateUtils.getTimestamp());
                infoFaultExtendService.save(infoFaultExtend);
            }else{
                return R.error("故障采集中该扩展标签数据已存在，保存失败。");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return R.error("保存故障采集扩展失败");
        }

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @SysLog("修改@desc 故障扩展表")
//    @RequiresPermissions("fault:infofaultextend:update")
    @ApiOperation(value = "修改故障采集扩展", httpMethod = "POST", notes = "修改故障采集扩展")
    public R update(@RequestBody InfoFaultExtendEntity infoFaultExtend){
        try {
            ValidatorUtils.validateEntity(infoFaultExtend);
            // 保存故障扩展
            QueryWrapper<InfoFaultExtendEntity> QueryWrapper = new QueryWrapper<InfoFaultExtendEntity>()
                    .notIn("fault_extend_id",infoFaultExtend.getFaultExtendId())
                    .eq("collect_fault_id", infoFaultExtend.getCollectFaultId())
                    .eq("fault_target_id", infoFaultExtend.getFaultTargetId())
                    .eq("is_deleted",0)
                    .eq("is_enabled",1);
            InfoFaultExtendEntity  infoFaultExtendEntityGet = infoFaultExtendService.getOne(QueryWrapper);
            if(infoFaultExtendEntityGet == null){
                infoFaultExtend.setModifiedBy(getUserId());
                infoFaultExtend.setModifiedDate(DateUtils.getTimestamp());
                infoFaultExtendService.updateById(infoFaultExtend);
            }else{
                return R.error("故障采集中该扩展标签数据已存在，修改失败。");
            }

        } catch (Exception e) {
            e.printStackTrace();
            return R.error("修改故障采集扩展失败");
        }
        
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("fault:infofaultextend:delete")
    public R delete(@RequestBody Long[] faultExtendIds){
        infoFaultExtendService.removeByIds(Arrays.asList(faultExtendIds));

        return R.ok();
    }

    /**
     * 删除
     */
    @ApiOperation(value = "单条删除", httpMethod = "DELETE", notes = "单条删除")
    @SysLog("单条删除@desc 故障扩展表")
    @DeleteMapping("/delete/{id}")
    public R delete(@PathVariable("id") Long id) {
        logger.info("单条删除,id:" + id);
        try {
            infoFaultExtendService.delById(id, getUserId());
        } catch (Exception e) {
            e.printStackTrace();
            return R.error("修改失败");
        }
        return R.ok();
    }

    @ApiOperation(value = "批量删除", httpMethod = "DELETE", notes = "批量删除")
    @SysLog("批量删除@desc 故障扩展表")
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
            infoFaultExtendService.deleteBatch(deleteBatchVo.getIds(), getUserId());
        } catch (Exception e) {
            e.printStackTrace();
            return R.error("批量删除出错!");
        }

        return R.ok("批量删除成功!");
    }

}
