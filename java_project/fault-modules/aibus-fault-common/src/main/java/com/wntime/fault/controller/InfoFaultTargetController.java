package com.wntime.fault.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wntime.common.annotation.SysLog;
import com.wntime.common.utils.Constant;
import com.wntime.common.utils.DateUtils;
import com.wntime.common.utils.PageUtils;
import com.wntime.common.utils.R;
import com.wntime.common.validator.ValidatorUtils;

import com.wntime.controller.AbstractController;
import com.wntime.fault.entity.InfoFaultLevelEntity;
import com.wntime.fault.entity.InfoFaultTypeEntity;
import com.wntime.service.common.vo.DeleteBatchVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import com.wntime.fault.entity.InfoFaultTargetEntity;
import com.wntime.fault.service.InfoFaultTargetService;



/**
 * @desc 故障标签表
 *
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2020-08-25 13:48:11
 */
@RestController
@RequestMapping("fault/infofaulttarget")
@Api(value = "故障标签管理", tags = {"故障标签管理"})
public class InfoFaultTargetController extends AbstractController {
    @Autowired
    private InfoFaultTargetService infoFaultTargetService;

    /**
     * 列表分页
     */
    @RequestMapping("/page")
    @SysLog("分页查询@desc 故障标签表")
//    @RequiresPermissions("fault:infofaulttype:list")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "page", value = "请求页码", required = true, dataType = "int"),
            @ApiImplicitParam(paramType = "query", name = "limit", value = "每页数量", required = true, dataType = "int"),
            @ApiImplicitParam(paramType = "query", name = "faultTargetName", value = "故障标签名称", required = false, dataType = "string"),
            @ApiImplicitParam(paramType = "query", name = "faultTargetGrope", value = "故障标签分类", required = false, dataType = "string"),
            @ApiImplicitParam(paramType = "query", name = "params", value = "参数", dataType = "string")
    })
    public R page(@RequestParam Map<String, Object> params) {
        PageUtils page = infoFaultTargetService.queryPage(params);
        return R.ok().put("page", page);
    }

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("fault:infofaulttarget:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = infoFaultTargetService.queryPage(params);

        return R.ok().put("page", page);
    }

    /**
     * 主标签列表
     */
    @RequestMapping("/listMaster")
    @SysLog("查询主标签列表@desc 故障标签表")
//    @RequiresPermissions("fault:infofaulttarget:list")
    public R listMaster(){
        QueryWrapper<InfoFaultTargetEntity> QueryWrapper = new QueryWrapper<InfoFaultTargetEntity>()
                .eq("is_deleted", 0).eq("is_enabled",1).eq("fault_target_grope","master");

        List<InfoFaultTargetEntity> list = infoFaultTargetService.list(QueryWrapper);

        return R.ok().put("list", list);
    }

    /**
     * 子标签列表
     */
    @ApiOperation(value = "子标签列表查询", httpMethod = "GET", notes = "子标签列表查询")
    @GetMapping("/listSub")
    @SysLog("查询子标签列表@desc 故障标签表")
    public R subList(@RequestParam(required = false) String targetGrope) {
        QueryWrapper<InfoFaultTargetEntity> queryWrapper = new QueryWrapper<InfoFaultTargetEntity>()
                .ne("fault_target_grope","master")
                .eq("is_enabled", Constant.Enabled.ENABLE.getValue())
                .eq("is_deleted", Constant.Deleted.UNDELETED.getValue());

        if(targetGrope != null && !"".equals(targetGrope)){
            queryWrapper.eq("event_target_grope",targetGrope);
        }
        return R.ok().put("list", infoFaultTargetService.list(queryWrapper));
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{faultTargetId}")
    @SysLog("查询详细@desc 故障标签表")
//    @RequiresPermissions("fault:infofaulttarget:info")
    @ApiOperation(value = "查询故障标签", httpMethod = "POST", notes = "查询故障标签")
    public R info(@PathVariable("faultTargetId") Long faultTargetId){
        InfoFaultTargetEntity infoFaultTarget = infoFaultTargetService.getById(faultTargetId);

        return R.ok().put("data", infoFaultTarget);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @SysLog("保存@desc 故障标签表")
    @ApiOperation(value = "新增", httpMethod = "POST", notes = "新增")
//    @RequiresPermissions("fault:infofaulttarget:save")
    public R save(@RequestBody InfoFaultTargetEntity infoFaultTarget){
        try {
            ValidatorUtils.validateEntity(infoFaultTarget);

            if("master".equals(infoFaultTarget.getFaultTargetGrope())){
                QueryWrapper<InfoFaultTargetEntity> QueryWrapper = new QueryWrapper<InfoFaultTargetEntity>()
                        .eq("is_deleted", 0)
                        .eq("fault_target_name",infoFaultTarget.getFaultTargetName())
                        .eq("fault_target_grope",infoFaultTarget.getFaultTargetGrope());
                InfoFaultTargetEntity get = infoFaultTargetService.getOne(QueryWrapper);
                if(get != null){
                    return R.error("故障标签[分类编码:"+infoFaultTarget.getFaultTargetGrope()+
                            ",标签名称:"+infoFaultTarget.getFaultTargetName()+"]数据已存在，保存失败。");
                }
            }else{
                QueryWrapper<InfoFaultTargetEntity> QueryWrapper = new QueryWrapper<InfoFaultTargetEntity>()
                        .eq("is_deleted", 0)
                        .eq("fault_target_code",infoFaultTarget.getFaultTargetCode())
                        .eq("fault_target_grope",infoFaultTarget.getFaultTargetGrope());
                InfoFaultTargetEntity get = infoFaultTargetService.getOne(QueryWrapper);
                if(get != null){
                    return R.error("故障标签[分类编码:"+infoFaultTarget.getFaultTargetGrope()+
                            ",标签编码:"+infoFaultTarget.getFaultTargetCode()+"]数据已存在，保存失败。");
                }
            }

            infoFaultTarget.setIsDeleted(0);
            infoFaultTarget.setCreatedBy(getUserId());
            infoFaultTarget.setCreatedDate(DateUtils.getTimestamp());
            infoFaultTargetService.save(infoFaultTarget);
        } catch (Exception e) {
            e.printStackTrace();
            return R.error("保存故障标签失败");
        }

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @SysLog("修改@desc 故障标签表")
//    @RequiresPermissions("fault:infofaulttarget:update")
    @ApiOperation(value = "修改", httpMethod = "POST", notes = "修改")
    public R update(@RequestBody InfoFaultTargetEntity infoFaultTarget){
        try {
            ValidatorUtils.validateEntity(infoFaultTarget);
            if("master".equals(infoFaultTarget.getFaultTargetGrope())){
                QueryWrapper<InfoFaultTargetEntity> QueryWrapper = new QueryWrapper<InfoFaultTargetEntity>()
                        .eq("is_deleted", 0)
                        .ne("fault_target_id",infoFaultTarget.getFaultTargetId())
                        .eq("fault_target_name",infoFaultTarget.getFaultTargetName())
                        .eq("fault_target_grope",infoFaultTarget.getFaultTargetGrope());
                InfoFaultTargetEntity get = infoFaultTargetService.getOne(QueryWrapper);
                if(get != null){
                    return R.error("故障标签[分类编码:"+infoFaultTarget.getFaultTargetGrope()+
                            ",标签名称:"+infoFaultTarget.getFaultTargetName()+"]数据已存在，保存失败。");
                }
            }else{
                QueryWrapper<InfoFaultTargetEntity> QueryWrapper = new QueryWrapper<InfoFaultTargetEntity>()
                        .eq("is_deleted", 0)
                        .ne("fault_target_id",infoFaultTarget.getFaultTargetId())
                        .eq("fault_target_code",infoFaultTarget.getFaultTargetCode())
                        .eq("fault_target_grope",infoFaultTarget.getFaultTargetGrope());
                InfoFaultTargetEntity get = infoFaultTargetService.getOne(QueryWrapper);
                if(get != null){
                    return R.error("故障标签[分类编码:"+infoFaultTarget.getFaultTargetGrope()+
                            ",标签编码:"+infoFaultTarget.getFaultTargetCode()+"]数据已存在，保存失败。");
                }
            }
            infoFaultTarget.setModifiedBy(getUserId());
            infoFaultTarget.setModifiedDate(DateUtils.getTimestamp());
            infoFaultTargetService.updateById(infoFaultTarget);
        } catch (Exception e) {
            e.printStackTrace();
            return R.error("修改故障标签失败");
        }

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("fault:infofaulttarget:delete")
    public R delete(@RequestBody Long[] faultTargetIds){
        infoFaultTargetService.removeByIds(Arrays.asList(faultTargetIds));

        return R.ok();
    }

    /**
     * 删除
     */
    @ApiOperation(value = "单条删除", httpMethod = "DELETE", notes = "单条删除")
    @DeleteMapping("/delete/{id}")
    @SysLog("删除单条@desc 故障标签表")
    public R delete(@PathVariable("id") Long id) {
        logger.info("单条删除,id:" + id);
        try {
            infoFaultTargetService.delById(id, getUserId());
        } catch (Exception e) {
            e.printStackTrace();
            return R.error("修改失败");
        }
        return R.ok();
    }

    @ApiOperation(value = "批量删除", httpMethod = "DELETE", notes = "批量删除")
    @SysLog("批量删除@desc 故障标签表")
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
            infoFaultTargetService.deleteBatch(deleteBatchVo.getIds(), getUserId());
        } catch (Exception e) {
            e.printStackTrace();
            return R.error("批量删除出错!");
        }

        return R.ok("批量删除成功!");
    }



}
