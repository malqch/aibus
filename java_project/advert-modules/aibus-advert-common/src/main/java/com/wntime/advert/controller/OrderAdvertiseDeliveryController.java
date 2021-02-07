package com.wntime.advert.controller;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import com.alibaba.fastjson.JSON;
import com.wntime.advert.constant.AdvertiseConstant;
import com.wntime.advert.service.InfoAdvertisePositionService;
import com.wntime.advert.service.InfoAdvertiseTargetService;
import com.wntime.advert.service.OrderDeliveryAreaService;
import com.wntime.advert.util.FileHelper;
import com.wntime.advert.vo.*;
import com.wntime.common.annotation.SysLog;
import com.wntime.common.utils.DateUtils;
import com.wntime.common.validator.Assert;
import com.wntime.controller.AbstractController;
import com.wntime.service.common.service.ConfigParamInfoService;
import com.wntime.service.entity.InfoConfigParamEntity;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.wntime.advert.entity.OrderAdvertiseDeliveryEntity;
import com.wntime.advert.service.OrderAdvertiseDeliveryService;
import com.wntime.common.utils.R;
import org.springframework.web.multipart.MultipartFile;


/**
 * @author ysc
 * @desc 广告投放单
 * @email example@gmail.com
 * @date 2020-11-05 14:17:58
 */
@Api(value = "广告管理", tags = {"广告管理"})
@RestController
@RequestMapping("operation/advertise")
public class OrderAdvertiseDeliveryController extends AbstractController {

    @Autowired
    private OrderAdvertiseDeliveryService orderAdvertiseDeliveryService;

    @Autowired
    private OrderDeliveryAreaService orderDeliveryAreaService;

    @Autowired
    private ConfigParamInfoService configParamInfoService;

    @Autowired
    private InfoAdvertiseTargetService advertiseTargetService;

    @Autowired
    private InfoAdvertisePositionService advertisePositionService;

    @Autowired
    private FileHelper fileHelper;



    /**
     * 列表
     */
    @ApiOperation(value = "广告列表获取", httpMethod = "GET", notes = "根据code不同返回的数据不同")
    @GetMapping("/list")
    public R list(@RequestParam(required = false) String advertiseNo, @RequestParam String code, @RequestParam String group) {
        List<AdvertiseCardVo> advertiseList = orderAdvertiseDeliveryService.list(advertiseNo, group, code, getUser());

        return R.ok().put("list", advertiseList);
    }

    @ApiOperation(value = "广告投放人员tab获取", httpMethod = "GET", notes = "")
    @GetMapping("/delivery/tab")
    public R getDeliveryTab() {
        List<InfoConfigParamEntity> list = configParamInfoService.getListByParamGroup("advertiseDeliveryTab");
        List<ItemVo> tabList = list.stream()
                .sorted(Comparator.comparingDouble(InfoConfigParamEntity::getParamValue))
                .map(ItemVo::new).collect(Collectors.toList());
        return R.ok().put("list", tabList);
    }

    @ApiOperation(value = "广告审核人员tab获取", httpMethod = "GET", notes = "")
    @GetMapping("/audit/tab")
    public R getAuditTab() {
        List<InfoConfigParamEntity> list = configParamInfoService.getListByParamGroup("advertiseAuditTab");
        List<ItemVo> tabList = list.stream()
                .sorted(Comparator.comparingDouble(InfoConfigParamEntity::getParamValue))
                .map(ItemVo::new).collect(Collectors.toList());
        return R.ok().put("list", tabList);
    }

    @ApiOperation(value = "广告插播人员tab获取", httpMethod = "GET", notes = "")
    @GetMapping("/interrupt/tab")
    public R getInterruptTab() {
        List<InfoConfigParamEntity> list = configParamInfoService.getListByParamGroup("advertiseInterruptTab");
        List<ItemVo> tabList = list.stream()
                .sorted(Comparator.comparingDouble(InfoConfigParamEntity::getParamValue))
                .map(ItemVo::new).collect(Collectors.toList());
        return R.ok().put("list", tabList);
    }

    @ApiOperation(value = "获取投放类型", httpMethod = "GET", notes = "")
    @GetMapping("/deliveryType")
    public R getDeliveryType() {
        List<InfoConfigParamEntity> list = configParamInfoService.getListByParamGroup("advertiseDeliveryType");
        List<AdvertiseDeliveryTypeVo> tabList = list.stream()
                .map(AdvertiseDeliveryTypeVo::new).collect(Collectors.toList());
        return R.ok().put("list", tabList);
    }

    @ApiOperation(value = "获取违规类型列表", httpMethod = "GET", notes = "")
    @GetMapping("/checkItem/list")
    public R getCheckItem() {
        List<InfoConfigParamEntity> list = configParamInfoService.getListByParamGroup("advertiseViolateType");
        List<ItemVo> checkItemList = list.stream()
                .map(ItemVo::new).collect(Collectors.toList());
        return R.ok().put("list", checkItemList);
    }

    @SysLog("提交审核结果")
    @ApiOperation(value = "提交审核结果", httpMethod = "POST", notes = "")
    @PostMapping("/audit")
    public R audit(@RequestBody AuditVo auditVo) {
        orderAdvertiseDeliveryService.audit(auditVo,getUserId());
        return R.ok();
    }

    @SysLog("确认审核结果")
    @ApiOperation(value = "确认审核结果", httpMethod = "POST", notes = "")
    @RequestMapping("/confirm")
    public R confirm(@RequestParam("advertiseId") Long advertiseId) {
        OrderAdvertiseDeliveryEntity advertiseDeliveryEntity = orderAdvertiseDeliveryService.getById(advertiseId);
        Assert.isNull(advertiseDeliveryEntity,"没有对应的广告！");
        advertiseDeliveryEntity.setCheckStatus(AdvertiseConstant.DELIVERY);
        advertiseDeliveryEntity.setModifiedBy(getUserId());
        advertiseDeliveryEntity.setModifiedDate(DateUtils.getTimestamp());
        orderAdvertiseDeliveryService.updateById(advertiseDeliveryEntity);
        return R.ok();
    }

    /**
     * 信息
     */
    @ApiOperation(value = "获取广告信息", httpMethod = "GET", notes = "")
    @GetMapping("/info")
    public R info(@RequestParam("advertiseId") Long advertiseId) {
        AdvertiseInfoVo advertiseInfoVo = orderAdvertiseDeliveryService.getVo(advertiseId);
        return R.ok().put("data", advertiseInfoVo);
    }

    /**
     * 详情
     */
    @ApiOperation(value = "获取广告详情", httpMethod = "GET", notes = "")
    @GetMapping("/detail")
    public R detail(@RequestParam("advertiseId") Long advertiseId) {
        AdvertiseDetailVo advertiseDetailVo = orderAdvertiseDeliveryService.getDetailVo(advertiseId);
        return R.ok().put("data", advertiseDetailVo);
    }

    /**
     * 保存
     */
    @SysLog("投放广告")
    @ApiOperation(value = "广告投放接口", httpMethod = "POST", notes = "")
    @PostMapping("/save")
    public R save(@RequestParam("data") String data, @RequestParam("rectangleFiles") MultipartFile[] rectangleFiles, @RequestParam("squareFiles") MultipartFile[] squareFiles) {

        AdvertiseVo advertiseVo = null;
        try {
            advertiseVo = JSON.parseObject(data, AdvertiseVo.class);
        }catch (Exception e){
            e.printStackTrace();
            return R.error("数据格式异常！");
        }
        advertiseVo.setIsInterrupt(AdvertiseConstant.NOT_INTERRUPT);
        advertiseVo.setCheckStatus(AdvertiseConstant.AUDIT);
        orderAdvertiseDeliveryService.saveVo(advertiseVo,rectangleFiles,squareFiles,getUserId());
        return R.ok();
    }

    /**
     * 修改
     */
    @SysLog("修改广告")
    @ApiOperation(value = "广告修改接口", httpMethod = "POST", notes = "")
    @PostMapping("/update")
    public R update(@RequestParam("data") String data, @RequestParam("rectangleFiles") MultipartFile[] rectangleFiles, @RequestParam("squareFiles") MultipartFile[] squareFiles) {

        AdvertiseVo advertiseVo = null;
        try {
            advertiseVo = JSON.parseObject(data, AdvertiseVo.class);
        }catch (Exception e){
            e.printStackTrace();
            return R.error("数据格式异常！");
        }
        OrderAdvertiseDeliveryEntity oldValue = orderAdvertiseDeliveryService.getById(advertiseVo.getAdvertiseDeliveryId());
        advertiseVo.setAdvertiseNo(oldValue.getAdvertiseNo());
        advertiseVo.setCheckStatus(AdvertiseConstant.AUDIT);
        advertiseVo.setIsInterrupt(oldValue.getIsInterrupt());
        orderAdvertiseDeliveryService.updateVo(advertiseVo,rectangleFiles,squareFiles,getUserId());
        return R.ok();
    }


    /**
     * 信息
     */
    @ApiOperation(value = "插播者广告信息获取", httpMethod = "GET", notes = "")
    @GetMapping("/interrupt/info")
    public R infoInterrupt(@RequestParam("advertiseId") Long advertiseId) {
        AdvertiseInfoVo advertiseInfoVo = orderAdvertiseDeliveryService.getInterruptVo(advertiseId);
        return R.ok().put("data", advertiseInfoVo);
    }

    /**
     * 保存
     */
    @SysLog("插播广告")
    @ApiOperation(value = "插播者广告投放接口", httpMethod = "POST", notes = "")
    @PostMapping("/interrupt/save")
    public R saveInterrupt(@RequestParam("data") String data, @RequestParam("rectangleFiles") MultipartFile[] rectangleFiles, @RequestParam("squareFiles") MultipartFile[] squareFiles) {

        AdvertiseVo advertiseVo = null;
        try {
            advertiseVo = JSON.parseObject(data, AdvertiseVo.class);
        }catch (Exception e){
            e.printStackTrace();
            return R.error("数据格式异常！");
        }
        advertiseVo.setCheckStatus(AdvertiseConstant.AUDIT);
        orderAdvertiseDeliveryService.saveInterruptVo(advertiseVo,rectangleFiles,squareFiles,getUserId());
        return R.ok();
    }

    /**
     * 修改
     */
    @SysLog("修改插播广告")
    @ApiOperation(value = "插播者广告修改接口", httpMethod = "POST", notes = "")
    @PostMapping("/interrupt/update")
    public R updateInterrupt(@RequestParam("data") String data, @RequestParam("rectangleFiles") MultipartFile[] rectangleFiles, @RequestParam("squareFiles") MultipartFile[] squareFiles) {

        AdvertiseVo advertiseVo = null;
        try {
            advertiseVo = JSON.parseObject(data, AdvertiseVo.class);
        }catch (Exception e){
            e.printStackTrace();
            return R.error("数据格式异常！");
        }
        OrderAdvertiseDeliveryEntity byId = orderAdvertiseDeliveryService.getById(advertiseVo.getAdvertiseDeliveryId());
        advertiseVo.setAdvertiseNo(byId.getAdvertiseNo());
        advertiseVo.setCheckStatus(AdvertiseConstant.AUDIT);
        orderAdvertiseDeliveryService.updateInterruptVo(advertiseVo,rectangleFiles,squareFiles,getUserId());
        return R.ok();
    }

    /**
     * 删除
     */
    @SysLog("下线广告")
    @ApiOperation(value = "广告投放完接口", httpMethod = "POST", notes = "投放完或者紧急下线使用")
    @RequestMapping("/offline")
    public R offline(@RequestParam Long advertiseId) {
        OrderAdvertiseDeliveryEntity advertiseDeliveryEntity = orderAdvertiseDeliveryService.getById(advertiseId);
        Assert.isNull(advertiseDeliveryEntity,"没有找到对应的广告");
        //设置成下线
        advertiseDeliveryEntity.setCheckStatus(9);
        advertiseDeliveryEntity.setModifiedBy(getUserId());
        advertiseDeliveryEntity.setModifiedDate(DateUtils.getTimestamp());
        orderAdvertiseDeliveryService.updateById(advertiseDeliveryEntity);
        //清空广告文件目录
        fileHelper.deleteDir(fileHelper.getRelativeDir(getUserId(),advertiseDeliveryEntity.getAdvertiseNo()));
        return R.ok();
    }

    /**
     * 删除
     */
    @SysLog("删除广告")
    @ApiOperation(value = "删除广告接口", httpMethod = "POST", notes = "")
    @RequestMapping("/delete")
    public R delete(@RequestParam Long advertiseId) {
        orderAdvertiseDeliveryService.delete(advertiseId,getUserId());
        return R.ok();
    }

}
