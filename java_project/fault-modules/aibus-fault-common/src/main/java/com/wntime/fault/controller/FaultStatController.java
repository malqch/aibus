package com.wntime.fault.controller;

import com.wntime.common.utils.R;
import com.wntime.common.validator.Assert;
import com.wntime.controller.AbstractController;
import com.wntime.fault.service.InfoFaultLevelService;
import com.wntime.fault.service.InfoFaultTypeService;
import com.wntime.fault.vo.*;
import com.wntime.service.common.entity.InfoBusEntity;
import com.wntime.service.common.service.BusCompanyService;
import com.wntime.service.common.service.BusInfoService;
import com.wntime.service.common.util.HistogramUtil;
import com.wntime.service.common.vo.HistogramData;
import com.wntime.service.common.vo.TypeCountVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.text.MessageFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author ysc
 * 2020/8/25 16:51
 */
@Api(value = "故障分析接口", tags = {"故障分析接口"})
@RequestMapping("/fault")
@RestController
public class FaultStatController extends AbstractController {

    @Autowired
    private InfoFaultTypeService infoFaultTypeService;

    @Autowired
    private InfoFaultLevelService infoFaultLevelService;

    @Autowired
    private BusInfoService busInfoService;

    @Autowired
    private BusCompanyService busCompanyService;
    @ApiOperation(value = "公司故障类别统计分析 - 雷达图", notes = "雷达图", httpMethod = "GET")
    @GetMapping("/faultType/company/radar")
    public R faultTypeRadar(@RequestParam(value = "companyId", required = true) Long companyId) {

        List<Map<String, Object>> faultTypeCountList = infoFaultTypeService.countBusByCompanyIdGroupByFaultType(companyId);

        Assert.isEmpty(faultTypeCountList, "没有数据");

        List<Radar.RadarIndicator> indicatorList = new ArrayList<>();
        List<Integer> data = new ArrayList<>();

        for (Map<String, Object> faultTypeMap : faultTypeCountList) {
            Long faultTypeId = (Long) faultTypeMap.get("fault_type_id");
            String faultTypeName = (String) faultTypeMap.get("fault_type_name");
            int faultTypeCount = ((Long) faultTypeMap.get("fault_type_count")).intValue();
            data.add(faultTypeCount);
            indicatorList.add(new Radar.RadarIndicator(faultTypeName, -1));
        }
        // 取最大值的1.1倍取整作为雷达图的最大值
        int max = data.stream().mapToInt(i -> i).max().getAsInt();
        max = (int)Math.ceil(max * 1.1);
        int total = data.stream().mapToInt(i -> i).sum();

        for (Radar.RadarIndicator radarIndicator : indicatorList) {
            radarIndicator.setMax(max);
        }
        return R.ok().put("radar", new Radar(data, indicatorList, total));
    }

    @ApiOperation(value = "公司故障类别统计分析", notes = "售后车辆分析 - 故障", httpMethod = "GET")
    @GetMapping("/faultType/company/stat")
    public R faultTypeStat(@RequestParam(value = "companyId", required = true) Long companyId) {

        List<Map<String, Object>> faultTypeCountList = infoFaultTypeService.countBusByCompanyIdGroupByFaultType(companyId);

        Assert.isEmpty(faultTypeCountList, "没有数据");

        HistogramData histogramData = HistogramUtil
                .creatHistogramDataFromMap(faultTypeCountList, "fault_type_name", "fault_type_count");
        return R.ok().put("data", histogramData);
    }

    @ApiOperation(value = "公司故障级别统计分析", notes = "", httpMethod = "GET")
    @GetMapping("/faultLevel/company/stat")
    public R faultLevelStat(@RequestParam(value = "companyId", required = true) Long companyId) {

        List<Map<String, Object>> faultLevelCountList = infoFaultLevelService.countBusCompanyIdGroupByFaultLevel(companyId);

        Assert.isEmpty(faultLevelCountList, "没有数据");

        HistogramData histogramData = HistogramUtil
                .creatHistogramDataFromMap(faultLevelCountList, "fault_level_name", "fault_level_count");
        return R.ok().put("data", histogramData);
    }

    @ApiOperation(value = "故障排行统计（故障分析）", notes = "故障排行统计（故障分析）", httpMethod = "GET")
    @GetMapping("/type/busStat")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query",name="searchDate",value="查询日期",required = true,dataType = "string"),
            @ApiImplicitParam(paramType = "query",name="busMile",value="选择里程数",required = true,dataType = "int"),
            @ApiImplicitParam(paramType = "query",name="params",value="参数",dataType = "string")
    })
    public R getFaultTypeBusStat(@RequestParam Map<String, Object> params) {
        FaultColumnStatData histogramData = new FaultColumnStatData();
        List<Long> companyIdList = new ArrayList<>();
        companyIdList = busCompanyService.getUserCompanyIdList(getUserId());
        params.put("companyIdList", companyIdList);

//        // 查询权限公司下车辆
        List<InfoBusEntity> infoBusEntityList = busInfoService.getAllBusByAreaId(0L,companyIdList);
        if(infoBusEntityList == null || infoBusEntityList.size() == 0){
            return R.ok().put("busStatData",histogramData);
        }

        // 根据车辆列表查询故障统计
        List<FaultTypeStatVo> faultTypeCountList = infoFaultTypeService.getCountFaultTypeByBusIds(params,
                infoBusEntityList.stream().map(InfoBusEntity::getBusId).collect(Collectors.toList()));
        if(faultTypeCountList != null && faultTypeCountList.size() > 0){
            histogramData.setName("故障排行统计");
            for(FaultTypeStatVo faultTypeStatVo : faultTypeCountList){
                histogramData.setTotal(histogramData.getTotal()+ faultTypeStatVo.getFaultTypeCount());
                histogramData.getTextList().add(faultTypeStatVo.getFaultTypeName());
                histogramData.getValueList().add(faultTypeStatVo.getFaultTypeCount());
            }

            histogramData.setTotal(faultTypeCountList.stream().mapToInt(FaultTypeStatVo::getFaultTypeCount).sum());
            histogramData.setTextList(faultTypeCountList.stream().map(FaultTypeStatVo::getFaultTypeName).collect(Collectors.toList()));
            histogramData.setValueList(faultTypeCountList.stream().map(FaultTypeStatVo::getFaultTypeCount).collect(Collectors.toList()));
        }
        return R.ok().put("busStatData",histogramData);
    }

    @ApiOperation(value = "分车型查询故障统计（故障分析）", notes = "分车型查询故障统计（故障分析）", httpMethod = "GET")
    @GetMapping("/busType/stat")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query",name="searchDate",value="查询日期",required = true,dataType = "string"),
            @ApiImplicitParam(paramType = "query",name="busTypeId",value="车辆类型ID",required = true,dataType = "long"),
            @ApiImplicitParam(paramType = "query",name="params",value="参数",dataType = "string")
    })
    public R getFaultStatByBusType(@RequestParam Map<String, Object> params) {
        List<TypeCountVo> typeCountVos = new ArrayList<>();
        List<Long> companyIdList = new ArrayList<>();
        companyIdList = busCompanyService.getUserCompanyIdList(getUserId());
        params.put("companyIdList", companyIdList);

//        // 查询权限公司下车辆
        List<InfoBusEntity> infoBusEntityList = busInfoService.getAllBusByAreaId(0L,companyIdList);
        if(infoBusEntityList == null || infoBusEntityList.size() == 0){
            return R.ok().put("list",typeCountVos);
        }

        // 根据车辆列表查询故障统计
        typeCountVos = infoFaultTypeService.getCountFaultByBusType(params,
                infoBusEntityList.stream().map(InfoBusEntity::getBusId).collect(Collectors.toList()));
        return R.ok().put("list",typeCountVos);
    }

    @ApiOperation(value = "分客户、分车型故障数量统计（故障分析）", notes = "分客户、分车型故障数量统计（故障分析）", httpMethod = "GET")
    @GetMapping("/busType/company/stat")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query",name="searchDate",value="查询日期",required = true,dataType = "string"),
            @ApiImplicitParam(paramType = "query",name="params",value="参数",dataType = "string")
    })
    public R getFaultStatByCompanyBusType(@RequestParam Map<String, Object> params) {
        FaultAndTempStatData faultAndTempStatData = new FaultAndTempStatData();
        params.put("companyIdList", busCompanyService.getUserCompanyIdList(getUserId()));

        // 根据车辆列表查询故障统计
        List<FaultCompanyBusStatVo> faultCompanyBusStatVos = infoFaultTypeService.getFaultStatByCompanyBusType(params);
        if(faultCompanyBusStatVos != null && faultCompanyBusStatVos.size() >0){
            // 公司列表
            List<String> companySet = new ArrayList<>();

            // 查询结果
            Map<String,Map<String,Integer>> resultMap = new HashMap<>();
            for(FaultCompanyBusStatVo faultCompanyBusStatVo : faultCompanyBusStatVos){
                if(!companySet.contains(faultCompanyBusStatVo.getCompanyName())){
                    companySet.add(faultCompanyBusStatVo.getCompanyName());
                }

                // 车型
                if(resultMap.containsKey(faultCompanyBusStatVo.getBusTypeName())){
                    // 公司
                    resultMap.get(faultCompanyBusStatVo.getBusTypeName()).put(
                            faultCompanyBusStatVo.getCompanyName(),faultCompanyBusStatVo.getStatCount());
                }else{
                    Map<String,Integer> companyMap = new HashMap<>();
                    companyMap.put(faultCompanyBusStatVo.getCompanyName(),faultCompanyBusStatVo.getStatCount());
                    resultMap.put(faultCompanyBusStatVo.getBusTypeName(),companyMap);
                }
            }
            // 车辆类型
            Set<String> busTypeSet = resultMap.keySet();
            for(String busTypeName : busTypeSet){
                VerValueData verValueData = new VerValueData();
                verValueData.setType("bar");
                // 统计公司
                for(String companyName :companySet){
                    if(resultMap.get(busTypeName).containsKey(companyName)){
                        verValueData.getData().add(resultMap.get(busTypeName).get(companyName));
                    }else{
                        verValueData.getData().add(0);
                    }
                }
                faultAndTempStatData.getVerValueList().add(verValueData);
            }
            faultAndTempStatData.getHorizontalShowList().addAll(companySet);
            faultAndTempStatData.getVerticalShowList().addAll(busTypeSet);
        }
        return R.ok().put("list",faultAndTempStatData);
    }

    @ApiOperation(value = "分客户、分车型故障数量统计-关联温度（故障分析）", notes = "分客户、分车型故障数量统计-关联温度（故障分析）", httpMethod = "GET")
    @GetMapping("/busType/company/temperatureStat")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query",name="searchDate",value="查询日期",required = true,dataType = "string"),
            @ApiImplicitParam(paramType = "query",name="params",value="参数",dataType = "string")
    })
    public R getFaultStatByCompanyBusTypeTemp(@RequestParam Map<String, Object> params) {
        FaultAndTempStatData faultAndTempStatData = new FaultAndTempStatData();
        params.put("companyIdList", busCompanyService.getUserCompanyIdList(getUserId()));

        // 根据车辆列表查询故障统计
        List<FaultCompanyBusStatVo> faultCompanyBusStatVos = infoFaultTypeService.getFaultStatByCompanyBusType(params);
        if(faultCompanyBusStatVos != null && faultCompanyBusStatVos.size() >0){
            // 公司列表
            List<String> companySet = new ArrayList<>();

            // 查询结果
            Map<String,Map<String,Integer>> resultMap = new HashMap<>();
            for(FaultCompanyBusStatVo faultCompanyBusStatVo : faultCompanyBusStatVos){
                if(!companySet.contains(faultCompanyBusStatVo.getCompanyName())){
                    companySet.add(faultCompanyBusStatVo.getCompanyName());
                }

                // 车型 + 温度
                if(resultMap.containsKey(faultCompanyBusStatVo.getBusTypeName())){
                    // 公司
                    resultMap.get(faultCompanyBusStatVo.getBusTypeName()).put(
                            faultCompanyBusStatVo.getCompanyName(),faultCompanyBusStatVo.getStatCount());
                }else{
                    Map<String,Integer> companyMap = new HashMap<>();
                    companyMap.put(faultCompanyBusStatVo.getCompanyName(),faultCompanyBusStatVo.getStatCount());
                    resultMap.put(faultCompanyBusStatVo.getBusTypeName(),companyMap);
                }
            }
            // 车辆类型 + 温度 TODO
            List<String> busTypeSet = new ArrayList<>();
            busTypeSet.addAll(resultMap.keySet());
            busTypeSet.add("环境温度");
            VerValueData verValueTempData = new VerValueData();
            int addTemp = 1;
            for(String busTypeName : busTypeSet){
                VerValueData verValueData = new VerValueData();
                verValueData.setType("bar");
                // 循环公司
                for(String companyName :companySet){
                    if(resultMap.get(busTypeName) != null && !"环境温度".equals(resultMap.get(busTypeName)) ){
                        if(resultMap.get(busTypeName).containsKey(companyName)){
                            verValueData.getData().add(resultMap.get(busTypeName).get(companyName));
                        }else{
                            verValueData.getData().add(0);
                        }
                    }else{
                        // 环境温度 TODO
                        verValueData.getData().add(15+addTemp);
                        verValueData.setType("line");
                        addTemp++;
                    }
                }
                faultAndTempStatData.getVerValueList().add(verValueData);
            }

            faultAndTempStatData.getHorizontalShowList().addAll(companySet);
            faultAndTempStatData.getVerticalShowList().addAll(busTypeSet);
        }
        return R.ok().put("list",faultAndTempStatData);
    }
}
