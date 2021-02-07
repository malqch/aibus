package com.wntime.service.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.wntime.common.annotation.SysLog;
import com.wntime.common.utils.DateUtils;
import com.wntime.common.utils.PageUtils;
import com.wntime.common.utils.R;
import com.wntime.common.validator.ValidatorUtils;

import com.wntime.controller.AbstractController;
import com.wntime.service.common.service.BusCompanyService;
import com.wntime.service.common.vo.DeleteBatchVo;
import com.wntime.service.entity.InfoBusFactoryEntity;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import com.wntime.service.entity.InfoAreaEntity;
import com.wntime.service.service.InfoAreaService;


/**
 * @author Mark
 * @desc 区域表
 * @email sunlightcs@gmail.com
 * @date 2020-08-25 14:28:17
 */
@Api(value = "区域管理", tags = {"区域管理"})
@RestController
@RequestMapping("service/infoarea")
public class InfoAreaController extends AbstractController {
    @Autowired
    private InfoAreaService infoAreaService;
    @Autowired
    private BusCompanyService busCompanyService;

    /**
     * 列表
     */
    @ApiOperation(value = "区域列表", httpMethod = "GET", notes = "获取区域列表")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "page", value = "请求页码", required = true, dataType = "int"),
            @ApiImplicitParam(paramType = "query", name = "limit", value = "每页数量", required = true, dataType = "int")
    })
    @GetMapping("/list")
    public R list(@RequestParam Map<String, Object> params) {
        logger.info("区域分页列表,参数:" + JSON.toJSONString(params));
        List<InfoAreaEntity> list = infoAreaService.queryList(params);
        return R.ok().put("list", list);
    }

    @ApiOperation(value = "根据id获取区域记录", httpMethod = "GET", notes = "根据id获取区域记录")
    @RequestMapping("/get/{id}")
    public R getById(@PathVariable("id") Long id) {
        logger.info("根据id查询,id:" + id);

        InfoAreaEntity infoAreaEntity;
        try {
            infoAreaEntity = infoAreaService.getDetailById(id);
        } catch (Exception e) {
            e.printStackTrace();
            return R.error("获取单条区域失败");
        }
        return R.ok().put("data", infoAreaEntity);
    }

    @ApiOperation(value = "选择区域", httpMethod = "GET", notes = "选择区域")
    @GetMapping("/getComboBox")
    public R queryArea(@RequestParam Map<String, Object> params) {
        logger.info("开始查询区域");
        Object result = infoAreaService.queryArea(params);
        return R.ok().put("selection", result);
    }

    /**
     * 保存
     */
    @ApiOperation(value = "保存区域", httpMethod = "POST", notes = "保存区域")
    @SysLog("保存区域信息")
    @RequestMapping("/save")
    public R save(@RequestBody InfoAreaEntity infoAreaEntity) {

        logger.info("保存公交车厂,参数:" + JSON.toJSONString(infoAreaEntity));
        ValidatorUtils.validateEntity(infoAreaEntity);

        if (!infoAreaService.isEnableInsert(null, infoAreaEntity.getParentAreaId(), infoAreaEntity.getAreaName())) {
            return R.error("该级别下有同名数据，不能添加");
        }

        infoAreaEntity.setAreaId(null);
        infoAreaEntity.setIsDeleted(0);
        infoAreaEntity.setCreatedDate(DateUtils.getTimestamp());
        infoAreaEntity.setCreatedBy(getUserId());

        try {
            infoAreaService.save(infoAreaEntity);
        } catch (Exception e) {
            e.printStackTrace();
            return R.error("保存出错");
        }
        return R.ok();
    }

    /**
     * 修改
     */
    @ApiOperation(value = "修改区域", httpMethod = "POST", notes = "修改区域")
    @SysLog("修改区域信息")
    @RequestMapping("/update")
    public R update(@RequestBody InfoAreaEntity infoAreaEntity) {

        logger.info("修改公交车厂,参数:" + JSON.toJSONString(infoAreaEntity));
        ValidatorUtils.validateEntity(infoAreaEntity);

        if (infoAreaEntity.getIsEnabled() == 0) {
            if (!infoAreaService.isEnableStop(infoAreaEntity.getAreaId())) {
                return R.error("该级别下有数据，不能停用");
            }
        }

        if (!infoAreaService.isEnableInsert(infoAreaEntity.getAreaId(), infoAreaEntity.getParentAreaId(), infoAreaEntity.getAreaName())) {
            return R.error("该级别下有同名数据，不能添加");
        }

        infoAreaEntity.setModifiedDate(DateUtils.getTimestamp());
        infoAreaEntity.setModifiedBy(getUserId());
        infoAreaEntity.setCreatedBy(null);

        try {
            infoAreaService.updateById(infoAreaEntity);
        } catch (Exception e) {
            e.printStackTrace();
            return R.error("更新出错");
        }

        return R.ok();
    }

    /**
     * 删除
     */
    @ApiOperation(value = "删除区域", httpMethod = "POST", notes = "删除区域")
    @SysLog("删除区域信息")
    @RequestMapping("/delete/{id}")
    public R delete(@PathVariable("id") Long id) {

        logger.info("删除,id:" + id);
        if (!infoAreaService.isEnableDelete(id)) {
            return R.error("不能删除有下级的数据");
        }

        boolean n = infoAreaService.delById(id);
        if (n) {
            return R.ok();
        } else {
            return R.error("删除出错");
        }
    }

    @ApiOperation(value = "批量删除区域", httpMethod = "POST", notes = "批量删除区域")
    @SysLog("批量删除区域")
    @PostMapping("/deleteBatch")
    public R deleteBatch(@RequestBody DeleteBatchVo deleteBatchVo) {
        logger.info("批量删除区域,id:" + deleteBatchVo);
        if (deleteBatchVo.getIds() == null || deleteBatchVo.getIds().length == 0 || StringUtils.isEmpty(deleteBatchVo.getValidatePassword()))
            return R.error("参数不能为空!");

        //校验当前用户的密码是否正确
        if (!validatePassword(deleteBatchVo.getValidatePassword())) return R.error("密码不正确");

        try {
            infoAreaService.deleteBatch(deleteBatchVo.getIds());
        } catch (Exception e) {
            e.printStackTrace();
            return R.error("批量删除出错!");
        }

        return R.ok("批量删除成功!");
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{areaId}")
    @RequiresPermissions("service:infoarea:info")
    public R info(@PathVariable("areaId") Long areaId) {
        InfoAreaEntity infoArea = infoAreaService.getById(areaId);

        return R.ok().put("infoArea", infoArea);
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("service:infoarea:delete")
    public R delete(@RequestBody Long[] areaIds) {
        infoAreaService.removeByIds(Arrays.asList(areaIds));

        return R.ok();
    }

    /**
     * 根据ParentAreaId获取省份/市列表
     */
    @RequestMapping("/listByParentAreaId")
//    @RequiresPermissions("service:infoarea:list")
    @ApiOperation(value = "根据上级区域ID查询列表", httpMethod = "GET", notes = "根据上级区域ID查询列表")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "parentAreaId", value = "上级区域ID", required = true, dataType = "int"),
    })
    public R listByParentAreaId(@RequestParam(required = false) Long parentAreaId) {
        List<InfoAreaEntity> list = infoAreaService.getListByParentAreaId(parentAreaId == null ? 0 : parentAreaId);
        return R.ok().put("list", list);
    }

    /**
     * 根据登录用户获取省份/市列表
     */
    @RequestMapping("/listByUserAndParentAreaId")
//    @RequiresPermissions("service:infoarea:list")
    @ApiOperation(value = "根据登录用户获取省份/市列表", httpMethod = "GET", notes = "根据登录用户获取省份/市列表")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "parentAreaId", value = "上级区域ID", required = true, dataType = "int"),
            @ApiImplicitParam(paramType = "query", name = "params", value = "参数", dataType = "int")
    })
    public R listByUserAndParentAreaId(@RequestParam(required = false) Long parentAreaId) {

        if (getUserId() == 1L) {
            // 查询所有
            return listByParentAreaId(parentAreaId);
        }
        // 获取用户权限公司
        List<Long> companyIdList = busCompanyService.getUserCompanyIdList(getUserId());

        List<InfoAreaEntity> list = infoAreaService.getListByUserAndParentAreaId((parentAreaId == null ? 0 : parentAreaId), companyIdList);
        return R.ok().put("list", list);
    }

}
