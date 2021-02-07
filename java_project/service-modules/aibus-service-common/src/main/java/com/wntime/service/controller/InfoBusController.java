package com.wntime.service.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wntime.common.annotation.SysLog;
import com.wntime.common.exception.RRException;
import com.wntime.common.utils.DateUtils;
import com.wntime.common.utils.PageUtils;
import com.wntime.common.utils.R;
import com.wntime.common.validator.Assert;
import com.wntime.common.validator.ValidatorUtils;
import com.wntime.controller.AbstractController;
import com.wntime.service.common.constant.BusStatusConstant;
import com.wntime.service.common.entity.InfoBusEntity;
import com.wntime.service.common.service.BusCompanyService;
import com.wntime.service.common.util.UniqueCheckHelper;
import com.wntime.service.common.vo.BusStatusVO;
import com.wntime.service.common.vo.DeleteBatchVo;
import com.wntime.service.common.vo.StatisticsResultVo;
import com.wntime.service.service.InfoBusService;
import com.wntime.service.vo.AfterSalesBusInfoVO;
import com.wntime.service.vo.AfterSalesMaintainBusInfoVO;
import com.wntime.service.vo.BusVo;
import com.wntime.service.vo.InputItem;
import com.wntime.util.DateUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.*;


/**
 * @author Mark
 * @desc 公交车表
 * @email sunlightcs@gmail.com
 * @date 2020-08-25 14:28:17
 */
@Api(value = "公交车管理", tags = {"公交车管理"})
@RestController
@RequestMapping("service/bus")
public class InfoBusController extends AbstractController {
    @Autowired
    private InfoBusService infoBusService;

    @Autowired
    private BusCompanyService busCompanyService;

    /**
     * 列表
     */
    @GetMapping("/list")
    @ApiOperation(value = "@desc 公交车表分页列表", httpMethod = "GET", notes = "@desc 公交车表管理页面中Grid获取数据接口")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "page", value = "请求页码", required = true, dataType = "int"),
            @ApiImplicitParam(paramType = "query", name = "limit", value = "每页数量", required = true, dataType = "int")
    })
    public R list(@RequestParam Map<String, Object> params) {
        logger.info("@desc 公交车表分页列表,参数:" + JSON.toJSONString(params));
        // 登录用户权限下公司列表
//        params.put("schoolIdList", busCompanyService.getUserCompanyIdList(getUserId()));
        params.put("schoolIdList", super.getUser().getAreaOrgIds());
        PageUtils page = infoBusService.queryPage(params);
        return R.ok().put("page", page);
    }

    @ApiOperation(value = "获取全部公交车表", httpMethod = "GET", notes = "获取全部公交车表")
    @GetMapping("/getComboBox")
    public R getComboBox() {
        logger.info("获取全部公交车表");
        List<InfoBusEntity> result = infoBusService.getAll();
        return R.ok().put("selection", result);
    }

    @ApiOperation(value = "获取全部公交车表（未交付）", httpMethod = "GET", notes = "获取全部公交车表（未交付）")
    @GetMapping("/getComboBoxNoDelivery")
    public R getComboBoxNoDelivery() {
        logger.info("获取全部公交车表");
        List<InfoBusEntity> result = infoBusService.getVinNoDeliveryList();
        return R.ok().put("selection", result);
    }

    /**
     * 获取VIN列表
     */
    @ApiOperation(value = "获取VIN列表", httpMethod = "GET", notes = "")
    @GetMapping("/vin/list")
    public R getVinList(@RequestParam(value = "busStatus", required = false) Long busStatus, @RequestParam(value = "companyId", required = false) Long companyId) {

        List<String> vinList = infoBusService.getVinListByBusStatusAndCompanyId(busStatus, companyId);
        return R.ok().put("list", vinList);
    }

    /**
     * 获取公司车辆VIN码
     */
    @ApiOperation(value = "获取公司车辆VIN码", httpMethod = "GET", notes = "获取公司车辆VIN码")
    @GetMapping("/vin/listByCompanyId")
    public R getVinListByCompanyId(@RequestParam(value = "companyId", required = false) Long companyId) {

        List<String> vinList = infoBusService.getVinListByCompanyId(companyId);
        return R.ok().put("list", vinList);
    }

    /**
     * 获取公司车辆车牌号
     */
    @ApiOperation(value = "获取公司车辆车牌号", httpMethod = "GET", notes = "获取公司车辆车牌号")
    @GetMapping("/plateCode/listByCompanyId")
    public R getPlateCodeListByCompanyId(@RequestParam(value = "companyId", required = false) Long companyId) {

        List<String> vinList = infoBusService.getPlateCodeListByCompanyId(companyId);
        return R.ok().put("list", vinList);
    }

    /**
     * 获取车辆状态列表
     */
    @ApiOperation(value = "获取车辆状态列表", httpMethod = "GET", notes = "")
    @GetMapping("/status/list")
    public R getBusStatusList() {

        List<BusStatusVO> statusList = infoBusService.getBusStatusList();
        return R.ok().put("list", statusList);
    }

    /**
     * 修改车辆状态
     */
    @ApiOperation(value = "修改车辆状态", httpMethod = "PUT", notes = "")
    @PutMapping("/{busId}/status/{busStatusId}")
    public R changeBusStatus(@PathVariable Long busId, @PathVariable Long busStatusId) {

        int result = infoBusService.changeBusStatus(busId, busStatusId);
        if (result == 1) {
            return R.ok();
        } else {
            return R.error("修改车辆状态失败");
        }
    }


    /**
     * 根据状态获取车辆信息分页列表
     */
    @ApiOperation(value = "根据状态获取车辆信息分页列表", httpMethod = "GET", notes = "")
    @GetMapping("/status/page")
    public R list(@RequestParam(required = false) Long busStatusId, @RequestParam Long companyId,
                  @RequestParam(required = false) String vin, @RequestParam Integer page, @RequestParam Integer limit) {

        if (busStatusId == null) {
            PageUtils<AfterSalesMaintainBusInfoVO> busPage = infoBusService.getMaintainBusPageAndCompanyIdAndVin(null, companyId, vin, page, limit);
            return R.ok().put("page", busPage);
        }
        BusStatusVO busStatus = infoBusService.getBusStatusById(busStatusId);
        Assert.isNull(busStatus, "车辆状态不合法");
        switch (busStatus.getBusStatusCode()) {
            case BusStatusConstant.NORMAL:
            case BusStatusConstant.WAIT:
            case BusStatusConstant.COMPLETE: {
                PageUtils<AfterSalesMaintainBusInfoVO> busPage = infoBusService.getMaintainBusPageAndCompanyIdAndVin(busStatusId, companyId, vin, page, limit);
                return R.ok().put("page", busPage);
            }
            case BusStatusConstant.MAINTENANCE: {
                PageUtils<AfterSalesMaintainBusInfoVO> busPage = infoBusService.getMaintainBusPageAndCompanyIdAndVin(busStatusId, companyId, vin, page, limit);
                return R.ok().put("page", busPage);
            }
            default: {
                return R.error("暂不支持的车辆状态");
            }
        }
    }

    /**
     * 获取车辆基本信息
     */
    @ApiOperation(value = "获取车辆基本信息", httpMethod = "GET", notes = "")
    @GetMapping("/{busId}/base/info")
    public R getBusBaseInfo(@PathVariable Long busId) {

        InfoBusEntity entity = infoBusService.getById(busId);
        Assert.isNull(entity, "没有对应车辆信息");
        BusStatusVO busStatus = infoBusService.getBusStatusById(entity.getBusStatus());
        Assert.isNull(busStatus, "车辆状态不应为空");

        AfterSalesMaintainBusInfoVO afterSalesMaintainBusInfo = infoBusService.getAfterSalesMaintainBusInfo(entity.getVinCode());
        List<InputItem> inputItemList = transformToItemList(afterSalesMaintainBusInfo);
        inputItemList.add(new InputItem("maintenanceTime", "操作时间", afterSalesMaintainBusInfo.getMaintenanceTime(), false));
        inputItemList.add(new InputItem("maintenanceDesc", "操作备注", afterSalesMaintainBusInfo.getMaintenanceDesc(), false));
        Long maintenanceId = afterSalesMaintainBusInfo.getMaintenanceId();

        return R.ok()
                .put("list", inputItemList)
                .put("busId", afterSalesMaintainBusInfo.getBusId())
                .put("maintenanceId", maintenanceId)
                .put("busStatusId", afterSalesMaintainBusInfo.getBusStatusId())
                .put("busStatusCode", afterSalesMaintainBusInfo.getBusStatusCode())
                .put("busStatusName", afterSalesMaintainBusInfo.getBusStatusName());
    }


    private List<InputItem> transformToItemList(AfterSalesBusInfoVO afterSalesBusInfo) {
        List<InputItem> list = new ArrayList<>();
        list.add(new InputItem("vinCode", "VIN码", afterSalesBusInfo.getVinCode(), false));
        list.add(new InputItem("plateCode", "车牌号", afterSalesBusInfo.getPlateCode(), false));
        list.add(new InputItem("busTypeName", "车型", afterSalesBusInfo.getBusTypeName(), false));
        list.add(new InputItem("companyLineName", "所属线路", afterSalesBusInfo.getCompanyLineName(), false));
        list.add(new InputItem("companyName", "所属公司", afterSalesBusInfo.getCompanyName(), false));
        list.add(new InputItem("companyAddress", "公司地址", afterSalesBusInfo.getCompanyAddress(), false));
        list.add(new InputItem("companyPerson", "联系人", afterSalesBusInfo.getCompanyPerson(), false));
        list.add(new InputItem("companyPhone", "联系电话", afterSalesBusInfo.getCompanyPhone(), false));
        list.add(new InputItem("companyEmail", "邮箱", afterSalesBusInfo.getCompanyEmail(), false));

        return list;
    }

    /**
     * 获取车辆维修信息
     */
    @ApiOperation(value = "获取车辆维修信息", httpMethod = "GET", notes = "")
    @GetMapping("/{busId}/maintain/info")
    public R getBusMaintainInfo(@PathVariable Long busId) {

        InfoBusEntity entity = infoBusService.getById(busId);
        Assert.isNull(entity, "没有对应车辆信息");

        AfterSalesMaintainBusInfoVO afterSalesMaintainBusInfo = infoBusService.getAfterSalesMaintainBusInfo(entity.getVinCode());
        List<InputItem> inputItemList = new ArrayList<>();
        inputItemList.add(new InputItem("vinCode", "VIN码", afterSalesMaintainBusInfo.getVinCode(), false));
        inputItemList.add(new InputItem("busTypeName", "车型", afterSalesMaintainBusInfo.getBusTypeName(), false));
        inputItemList.add(new InputItem("companyName", "所属公司", afterSalesMaintainBusInfo.getCompanyName(), false));
        inputItemList.add(new InputItem("maintenanceTime", "维修时间", afterSalesMaintainBusInfo.getMaintenanceTime(), false));
        inputItemList.add(new InputItem("maintenanceDesc", "维修信息", afterSalesMaintainBusInfo.getMaintenanceDesc(), true));

        return R.ok()
                .put("list", inputItemList)
                .put("busId", afterSalesMaintainBusInfo.getBusId())
                .put("busStatusId", afterSalesMaintainBusInfo.getBusStatusId())
                .put("busStatusCode", afterSalesMaintainBusInfo.getBusStatusCode())
                .put("busStatusName", afterSalesMaintainBusInfo.getBusStatusName())
                .put("maintenanceId", afterSalesMaintainBusInfo.getMaintenanceId());
    }


    private static Map<String, Object> transformToChineseMap(AfterSalesBusInfoVO busInfo) {

        Map<String, Object> dataMap = new LinkedHashMap<>();
        dataMap.put("VIN码", busInfo.getVinCode());
        dataMap.put("所属公交公司", busInfo.getCompanyName());
        dataMap.put("联系人", busInfo.getCompanyPerson());
        dataMap.put("联系方式", busInfo.getCompanyPhone());
        dataMap.put("车辆状态", busInfo.getBusStatusName());
        return dataMap;
    }


    @ApiOperation(value = "获取单条@desc 公交车表", httpMethod = "GET", notes = "获取单条@desc 公交车表")
    @GetMapping("/get/{id}")
    public R getById(@PathVariable("id") Long id) {
        logger.info("获取单条@desc 公交车表,参数ID:" + id);
        InfoBusEntity infoBusEntity;
        try {
            infoBusEntity = infoBusService.getDetailById(id);
        } catch (Exception e) {
            e.printStackTrace();
            return R.error("获取单条@desc 公交车表失败");
        }
        return R.ok().put("data", infoBusEntity);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    @SysLog("保存@desc 公交车表")
    @ApiOperation(value = "添加@desc 公交车表", httpMethod = "POST", notes = "@desc 公交车表管理页面中添加@desc 公交车表")
    public R save(@RequestBody InfoBusEntity infoBusEntity) {
        logger.info("保存@desc 公交车表,参数:" + JSON.toJSONString(infoBusEntity));
        ValidatorUtils.validateEntity(infoBusEntity);
        UniqueCheckHelper.assertIsUnique(infoBusService, "vin_code", infoBusEntity.getVinCode(), "车辆vin码已存在！");

        try {
            infoBusEntity.setBusId(null);
            infoBusEntity.setRegistrationDate(StringUtils.isEmpty(infoBusEntity.getRegistrationDateStr())
                    ? null : DateUtil.string2Date(infoBusEntity.getRegistrationDateStr(),"yyyy-MM-dd"));
            infoBusEntity.setSchoolId(super.getUser().getAreaOrgIds().get(0));
            infoBusEntity.setCreatedBy(getUserId());
            infoBusEntity.setIsDeleted(0);
            infoBusEntity.setCreatedDate(DateUtils.getTimestamp());
            infoBusService.saveBus(infoBusEntity);
        } catch (Exception e) {
            e.printStackTrace();
            return R.error("保存@desc 公交车表失败");
        }
        return R.ok();
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    @SysLog("修改@desc 公交车表")
    @ApiOperation(value = "修改@desc 公交车表", httpMethod = "POST", notes = "@desc 公交车表管理页面中修改@desc 公交车表")
    public R update(@RequestBody InfoBusEntity infoBusEntity) {
        logger.info("修改@desc 公交车表,参数:" + JSON.toJSONString(infoBusEntity));
        ValidatorUtils.validateEntity(infoBusEntity);
        UniqueCheckHelper.assertIsUnique(infoBusService, "vin_code", infoBusEntity.getVinCode(), "bus_id", infoBusEntity.getBusId(), "车辆vin码已存在！");
        infoBusEntity.setModifiedBy(getUserId());
        infoBusEntity.setModifiedDate(DateUtils.getTimestamp());
        infoBusService.updateBus(infoBusEntity);
        return R.ok();
    }

    /**
     * 删除
     */
    @ApiOperation(value = "单条删除@desc 公交车表", httpMethod = "POST", notes = "@desc 公交车表管理页面中删除@desc 公交车表")
    @SysLog("单条删除@desc 公交车表")
    @PostMapping("/delete/{id}")
    public R delete(@PathVariable("id") Long id) {
        logger.info("单条删除@desc 公交车表,id:" + id);
        infoBusService.delById(id, getUserId());
        return R.ok();
    }

    @ApiOperation(value = "批量删除@desc 公交车表", httpMethod = "POST", notes = "批量删除@desc 公交车表")
    @SysLog("批量删除@desc 公交车表")
    @PostMapping("/deleteBatch")
    public R deleteBatch(@RequestBody DeleteBatchVo deleteBatchVo) {
        logger.info("批量删除@desc 公交车表,:" + Arrays.toString(deleteBatchVo.getIds()));

        if (deleteBatchVo.getIds() == null || deleteBatchVo.getIds().length == 0 || StringUtils.isEmpty(deleteBatchVo.getValidatePassword()))
            return R.error("参数不能为空!");

        //校验当前用户的密码是否正确
        if (!validatePassword(deleteBatchVo.getValidatePassword())) return R.error("密码不正确");

        try {
            infoBusService.deleteBatch(deleteBatchVo.getIds(), getUserId());
        } catch (Exception e) {
            e.printStackTrace();
            return R.error("批量删除出错!"+ e.getMessage());
        }

        return R.ok("批量删除成功!");
    }

    /**
     * 查询统计事件数量（指定地区：可燃气体、重点人员、交通违章、特种卡违规）
     */
    @GetMapping("/eventStat")
//    @RequiresPermissions("customer:infobuscompany:info")
    @ApiOperation(value = "查询统计事件数量（指定地区：可燃物、重点人员、交通违章、特种卡违规）", httpMethod = "GET", notes = "查询统计事件数量（指定地区：可燃气体、重点人员、交通违章、特种卡违规）")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "areaId", value = "区域ID", required = true, dataType = "int"),
            @ApiImplicitParam(paramType = "query", name = "params", value = "参数", dataType = "int")
    })
    public R getBusEventStatByAreaId(@RequestParam Map<String, Object> params) {
        params.put("companyIdList", busCompanyService.getUserCompanyIdList(getUserId()));
        List<StatisticsResultVo> result = infoBusService.getBusEventStatByAreaId(params);
        return R.ok().put("list", result);
    }

    @GetMapping("/vin/{vin}/check")
    public R chechVinIfUnique(@PathVariable("vin") String vinCode) {

        BusVo busVo = infoBusService.queryBusByVin(vinCode);
        if (busVo != null) {
            throw new RRException("vin码已存在");
        }
        return R.ok();
    }

}
