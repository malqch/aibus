package com.wntime.advert.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wntime.advert.entity.OrderDeliveryTargetEntity;
import com.wntime.common.annotation.SysLog;
import com.wntime.common.utils.Constant;
import com.wntime.common.utils.DateUtils;
import com.wntime.common.validator.ValidatorUtils;
import com.wntime.controller.AbstractController;
import com.wntime.service.common.util.UniqueCheckHelper;
import com.wntime.service.common.vo.DeleteBatchVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import com.wntime.advert.entity.InfoAdvertiseTargetEntity;
import com.wntime.advert.service.InfoAdvertiseTargetService;
import com.wntime.common.utils.PageUtils;
import com.wntime.common.utils.R;



/**
 * @desc 广告标签表
 *
 * @author ysc
 * @email example@gmail.com
 * @date 2020-11-05 14:17:58
 */
@Api(value = "广告标签管理", tags = {"广告标签管理"})
@RestController
@RequestMapping("operation/advertiseTag")
public class InfoAdvertiseTargetController extends AbstractController {
    @Autowired
    private InfoAdvertiseTargetService infoAdvertiseTargetService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("advert:infoadvertisetarget:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = infoAdvertiseTargetService.queryPage(params);

        return R.ok().put("page", page);
    }

    /**
     * 分页列表
     */
    @ApiOperation(value = "分页查询", httpMethod = "GET", notes = "分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "page", value = "请求页码", required = true, dataType = "int"),
            @ApiImplicitParam(paramType = "query", name = "limit", value = "每页数量", required = true, dataType = "int"),
            @ApiImplicitParam(paramType = "query", name = "advertiseTargetName", value = "标签名称", required = false, dataType = "string"),
            @ApiImplicitParam(paramType = "query", name = "advertiseTargetGrope", value = "标签分类", required = false, dataType = "string")
    })
    @GetMapping("/page")
    public R page(@RequestParam Map<String, Object> params) {
        PageUtils page = infoAdvertiseTargetService.queryPage(params);

        return R.ok().put("page", page);
    }

    @ApiOperation(value = "获取投放受众人群类型", httpMethod = "GET", notes = "获取投放受众人群类型")
    @GetMapping("/age")
    public R listByGroupAge(){
        return R.ok().put("list", infoAdvertiseTargetService.listByGroup("age"));
    }

    @ApiOperation(value = "获取投放受众人群性别", httpMethod = "GET", notes = "获取投放受众人群性别")
    @GetMapping("/gender")
    public R listByGroupGender(){
        return R.ok().put("list", infoAdvertiseTargetService.listByGroup("gender"));
    }


    /**
     * 信息
     */
    @ApiOperation(value = "单条查询", httpMethod = "GET", notes = "单条查询")
    @GetMapping("/info/{id}")
    public R info(@PathVariable("id") Long id) {
        InfoAdvertiseTargetEntity entity = infoAdvertiseTargetService.getById(id);
        if(entity.getIsDeleted().equals(Constant.Deleted.DELETED.getValue())){
            return R.error("该数据已被删除");
        }
        return R.ok().put("data", entity);
    }

    /**
     * 保存
     */
    @SysLog("保存广告标签")
    @ApiOperation(value = "单条新增", httpMethod = "POST", notes = "单条新增")
    @PostMapping("/save")
    public R save(@RequestBody InfoAdvertiseTargetEntity entity) {
        ValidatorUtils.validateEntity(entity);
        QueryWrapper<InfoAdvertiseTargetEntity> QueryWrapper = new QueryWrapper<InfoAdvertiseTargetEntity>()
                .eq("is_deleted", 0)
                .eq("advertise_target_name",entity.getAdvertiseTargetName())
                .eq("advertise_target_code",entity.getAdvertiseTargetCode())
                .eq("advertise_target_grope",entity.getAdvertiseTargetGrope());

        InfoAdvertiseTargetEntity get = infoAdvertiseTargetService.getOne(QueryWrapper);
        if(get != null){
            return R.error("该广告分类的标签编码、名称已存在！");
        }

        entity.setIsDeleted(0);
        entity.setCreatedBy(getUserId());
        entity.setCreatedDate(DateUtils.getTimestamp());
        infoAdvertiseTargetService.save(entity);

        return R.ok();
    }

    /**
     * 修改
     */
    @SysLog("修改广告标签")
    @ApiOperation(value = "单条修改", httpMethod = "PUT", notes = "单条修改")
    @PutMapping("/update")
    public R update(@RequestBody InfoAdvertiseTargetEntity entity) {
        ValidatorUtils.validateEntity(entity);
        QueryWrapper<InfoAdvertiseTargetEntity> QueryWrapper = new QueryWrapper<InfoAdvertiseTargetEntity>()
                .eq("is_deleted", 0)
                .ne("advertise_target_id",entity.getAdvertiseTargetId())
                .eq("advertise_target_name",entity.getAdvertiseTargetName())
                .eq("advertise_target_code",entity.getAdvertiseTargetCode())
                .eq("advertise_target_grope",entity.getAdvertiseTargetGrope());

        InfoAdvertiseTargetEntity get = infoAdvertiseTargetService.getOne(QueryWrapper);
        if(get != null){
            return R.error("该广告分类的标签编码、名称已存在！");
        }
        entity.setModifiedBy(getUserId());
        entity.setModifiedDate(DateUtils.getTimestamp());
        infoAdvertiseTargetService.updateById(entity);

        return R.ok();
    }

    /**
     * 删除
     */
    @SysLog("删除事件标签")
    @ApiOperation(value = "单条删除", httpMethod = "DELETE", notes = "管理页面中删除")
    @DeleteMapping("/delete/{id}")
    public R delete(@PathVariable("id") Long id) {
        logger.info("单条删除,id:" + id);
        try {
            infoAdvertiseTargetService.delById(id, getUserId());
        } catch (Exception e) {
            e.printStackTrace();
            return R.error("修改失败");
        }
        return R.ok();
    }

    @SysLog("批量删除事件标签")
    @ApiOperation(value = "批量删除", httpMethod = "DELETE", notes = "批量删除")
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
            infoAdvertiseTargetService.deleteBatch(deleteBatchVo.getIds(), getUserId());
        } catch (Exception e) {
            e.printStackTrace();
            return R.error("批量删除出错!");
        }

        return R.ok("批量删除成功!");
    }

}
