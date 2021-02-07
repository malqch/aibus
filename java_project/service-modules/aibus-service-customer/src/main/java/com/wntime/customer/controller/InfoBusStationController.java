package com.wntime.customer.controller;

import java.util.*;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wntime.common.annotation.SysLog;
import com.wntime.common.exception.RRException;
import com.wntime.common.utils.PageUtils;
import com.wntime.common.utils.R;
import com.wntime.common.validator.Assert;
import com.wntime.common.validator.ValidatorUtils;

import com.wntime.controller.AbstractController;
import com.wntime.customer.vo.BusStationDetailVO;
import com.wntime.customer.vo.InfoCompanyInitVo;
import com.wntime.event.serive.LogEventDetailStatService;
import com.wntime.service.common.service.BusCompanyService;
import com.wntime.service.common.vo.DeleteBatchVo;
import com.wntime.service.common.vo.StatisticsResultVo;
import com.wntime.util.DistanceUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import com.wntime.customer.entity.InfoBusStationEntity;
import com.wntime.customer.service.InfoBusStationService;


/**
 * @author Mark
 * @desc 公交车站表
 * @email sunlightcs@gmail.com
 * @date 2020-08-25 14:00:25
 */
@Api(value = "公交车站管理", tags = {"公交车站管理"})
@RestController
@RequestMapping("customer/infobusstation")
public class InfoBusStationController extends AbstractController {
    @Autowired
    private InfoBusStationService infoBusStationService;
    @Autowired
    private LogEventDetailStatService logEventDetailStatService;
    @Autowired
    private BusCompanyService busCompanyService;

    /**
     * 列表
     */
    @GetMapping("/list")
    @ApiOperation(value = "@desc 公交车站表分页列表", httpMethod = "GET", notes = "@desc 公交车站表管理页面中Grid获取数据接口")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "page", value = "请求页码", required = true, dataType = "int"),
            @ApiImplicitParam(paramType = "query", name = "limit", value = "每页数量", required = true, dataType = "int")
    })
    public R list(@RequestParam Map<String, Object> params) {
        logger.info("@desc 公交车站表分页列表,参数:" + JSON.toJSONString(params));
        // 登录用户权限下公司列表
        params.put("schoolIdList", super.getUser().getAreaOrgIds());
        PageUtils page = infoBusStationService.queryPage(params);
        return R.ok().put("page", page);
    }

    @GetMapping("/listAll")
    @ApiOperation(value = "获取所有公交车站", httpMethod = "GET", notes = "获取所有公交车站")
    public R listAll() {
        logger.info("获取所有公交车站");
        HashMap<String, Object> map = new HashMap<>();
        map.put("is_deleted", 0);
        map.put("is_enabled", 1);
        return R.ok().put("list", infoBusStationService.listByMap(map));
    }

    @GetMapping("/listAllByCompanyId")
    @ApiOperation(value = "获取所有公交车站(按公司)", httpMethod = "GET", notes = "获取所有公交车站(按公司)")
    public R listAllByCompanyId(@RequestParam(value = "companyId",required = false) Long companyId) {
        logger.info("获取公司地区下所有公交车站");
        return R.ok().put("list", infoBusStationService.getStationAllByCompanyArea(companyId));
    }

    /**
     * 获取公交公司的营运车站列表
     */
    @ApiOperation(value = "获取公交公司的营运车站列表", httpMethod = "GET", notes = "")
    @RequestMapping("/company/{companyId}/stations")
    public R getStationListByCompanyId(@PathVariable Long companyId) {

        List<InfoBusStationEntity> busStationList = infoBusStationService.getStationListByCompanyId(companyId);
        return R.ok().put("list", busStationList);
    }

    /**
     * 获取公交线路的营运车站列表
     */
    @ApiOperation(value = "获取公交线路的营运车站列表", httpMethod = "GET", notes = "")
    @RequestMapping("/companyLine/{companyLineId}")
    public R getStationListByCompanyLineId(@PathVariable Long companyLineId) {

        List<InfoBusStationEntity> busStationList = infoBusStationService.getStationListByCompanyLineId(companyLineId);
        return R.ok().put("list", busStationList);
    }
    /**
     * 获取车站详细信息
     */
    @ApiOperation(value = "获取车站详细信息", httpMethod = "GET", notes = "")
    @RequestMapping("/detail")
    public R getBusStationDetail(@RequestParam Long busStationId,
                                 @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") @RequestParam Date startTime,
                                 @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") @RequestParam Date endTime) {

        BusStationDetailVO busStationDetailVO = infoBusStationService.getBusStationDetailById(busStationId);
        Assert.isNull(busStationDetailVO, "获取不到车站数据");
        //获取pm2.5数据
        try {
            List<Map<String, Object>> dataPoints = logEventDetailStatService.getPm_2_5ByStationId(busStationId, startTime, endTime);
            OptionalDouble dataValue = dataPoints.stream().mapToDouble(map -> (Double) map.get("data_value")).average();
            busStationDetailVO.setPm_2_5(dataValue.orElse(-1));
        } catch (RRException e) {
            busStationDetailVO.setPm_2_5(-1.0);
        }

        return R.ok().put("data", busStationDetailVO);
    }

    @ApiOperation(value = "获取单条@desc 公交车站表", httpMethod = "GET", notes = "获取单条@desc 公交车站表")
    @GetMapping("/get/{id}")
    public R getById(@PathVariable("id") Long id) {
        logger.info("获取单条@desc 公交车站表,参数ID:" + id);
        InfoBusStationEntity infoBusStationEntity;
        try {
            infoBusStationEntity = infoBusStationService.getDetailById(id);
        } catch (Exception e) {
            e.printStackTrace();
            return R.error("获取单条@desc 公交车站表失败");
        }
        return R.ok().put("data", infoBusStationEntity);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    @SysLog("保存@desc 公交车站表")
    @ApiOperation(value = "添加@desc 公交车站表", httpMethod = "POST", notes = "@desc 公交车站表管理页面中添加@desc 公交车站表")
    public R save(@RequestBody InfoBusStationEntity infoBusStationEntity) {
        logger.info("保存@desc 公交车站表,参数:" + JSON.toJSONString(infoBusStationEntity));
        infoBusStationEntity.setSchoolId(super.getUser().getAreaOrgIds().get(0));
        ValidatorUtils.validateEntity(infoBusStationEntity);
        try {
            QueryWrapper<InfoBusStationEntity> QueryWrapper = new QueryWrapper<InfoBusStationEntity>()
                    .eq("is_deleted", 0)
                    .eq("area_id",infoBusStationEntity.getAreaId())
                    .eq("school_id",super.getUser().getAreaOrgIds().get(0))
                    .eq("bus_station_name",infoBusStationEntity.getBusStationName())
                    .eq("bus_station_code",infoBusStationEntity.getBusStationCode());

            // 经纬度比较相差 0.0005
            List<InfoBusStationEntity> getList = infoBusStationService.list(QueryWrapper);
            if(getList != null){
                for(InfoBusStationEntity entity : getList){
                    // 计算GPS距离
                    double distance = DistanceUtil.getShortestDistance(entity.getBusStationLongitude(),entity.getBusStationLatitude(),
                            infoBusStationEntity.getBusStationLongitude(), infoBusStationEntity.getBusStationLatitude());
                    if(distance <= infoBusStationEntity.getBusStationDeviation()){
                        return R.error("公交车站[名称:"+infoBusStationEntity.getBusStationName()+"]在该区域已存在，保存失败。");
                    }
                }
            }
            infoBusStationService.save(infoBusStationEntity, getUserId());
        } catch (Exception e) {
            e.printStackTrace();
            return R.error("保存@desc 公交车站表失败");
        }
        return R.ok();
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    @SysLog("修改@desc 公交车站表")
    @ApiOperation(value = "修改@desc 公交车站表", httpMethod = "POST", notes = "@desc 公交车站表管理页面中修改@desc 公交车站表")
    public R update(@RequestBody InfoBusStationEntity infoBusStationEntity) {
        logger.info("修改@desc 公交车站表,参数:" + JSON.toJSONString(infoBusStationEntity));
        infoBusStationEntity.setSchoolId(super.getUser().getAreaOrgIds().get(0));
        ValidatorUtils.validateEntity(infoBusStationEntity);
        try {
            QueryWrapper<InfoBusStationEntity> QueryWrapper = new QueryWrapper<InfoBusStationEntity>()
                    .eq("is_deleted", 0)
                    .ne("bus_station_id",infoBusStationEntity.getBusStationId())
                    .eq("area_id",infoBusStationEntity.getAreaId())
                    .eq("school_id",super.getUser().getAreaOrgIds().get(0))
                    .eq("bus_station_name",infoBusStationEntity.getBusStationName())
                    .eq("bus_station_code",infoBusStationEntity.getBusStationCode());

            // 经纬度比较相差 0.0005
            List<InfoBusStationEntity> getList = infoBusStationService.list(QueryWrapper);
            if(getList != null){
                for(InfoBusStationEntity entity : getList){
                    // 计算GPS距离
                    double distance = DistanceUtil.getShortestDistance(entity.getBusStationLongitude(),entity.getBusStationLatitude(),
                            infoBusStationEntity.getBusStationLongitude(), infoBusStationEntity.getBusStationLatitude());
                    if(distance <= infoBusStationEntity.getBusStationDeviation()){
                        return R.error("公交车站[名称:"+infoBusStationEntity.getBusStationName()+"]在该区域已存在，保存失败。");
                    }
                }
            }
            infoBusStationService.updateById(infoBusStationEntity, getUserId());
        } catch (Exception e) {
            e.printStackTrace();
            return R.error(e.getMessage());
        }
        return R.ok();
    }

    /**
     * 删除
     */
    @ApiOperation(value = "单条删除@desc 公交车站表", httpMethod = "POST", notes = "@desc 公交车站表管理页面中删除@desc 公交车站表")
    @SysLog("单条删除@desc 公交车站表")
    @PostMapping("/delete/{id}")
    public R delete(@PathVariable("id") Long id) {
        logger.info("单条删除@desc 公交车站表,id:" + id);
        try {
            infoBusStationService.delById(id, getUserId());
        } catch (Exception e) {
            e.printStackTrace();
            return R.error(e.getMessage());
        }
        return R.ok();
    }

    @ApiOperation(value = "批量删除@desc 公交车站表", httpMethod = "POST", notes = "批量删除@desc 公交车站表")
    @SysLog("批量删除@desc 公交车站表")
    @PostMapping("/deleteBatch")
    public R deleteBatch(@RequestBody DeleteBatchVo deleteBatchVo) {
        logger.info("批量删除@desc 公交车站表,:" + Arrays.toString(deleteBatchVo.getIds()));

        if (deleteBatchVo.getIds() == null || deleteBatchVo.getIds().length == 0 || StringUtils.isEmpty(deleteBatchVo.getValidatePassword()))
            return R.error("参数不能为空!");

        //校验当前用户的密码是否正确
        if (!validatePassword(deleteBatchVo.getValidatePassword())) return R.error("密码不正确");

        try {
            infoBusStationService.deleteBatch(deleteBatchVo.getIds(), getUserId());
        } catch (Exception e) {
            e.printStackTrace();
            return R.error(e.getMessage());
        }

        return R.ok("批量删除成功!");
    }

    /**
     * 根据初始化查询关联：车辆、公司、城市、省份
     */
    @GetMapping("/initInfoByBusStation")
//    @RequiresPermissions("customer:infobuscompany:info")
    @ApiOperation(value = "根据初始化查询关联：车辆、公司、城市、省份", httpMethod = "GET", notes = "根据初始化查询关联：车辆、公司、城市、省份")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "eventTargetCode",
                    value = "采集数据分类（trafficEvent,healthEvent,envEvent）", required = false, dataType = "string"),
            @ApiImplicitParam(paramType = "query", name = "params", value = "参数", dataType = "int")
    })
    public R getInitInfoByBusStation(@RequestParam Map<String, Object> params) {
        String eventTargetCode = "envEvent";
        if (params.get("eventTargetCode") != null && !StringUtils.isEmpty(params.get("eventTargetCode"))) {
            eventTargetCode = String.valueOf(params.get("eventTargetCode"));
        }
        InfoCompanyInitVo infoCompanyInitVo = infoBusStationService.getInitInfoByBusStation(eventTargetCode, busCompanyService.getUserCompanyIdList(getUserId()));
        return R.ok().put("info", infoCompanyInitVo);
    }
}
