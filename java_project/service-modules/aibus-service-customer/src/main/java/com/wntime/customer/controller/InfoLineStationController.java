package com.wntime.customer.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.wntime.common.utils.PageUtils;
import com.wntime.common.utils.R;
import com.wntime.common.validator.ValidatorUtils;

import com.wntime.controller.AbstractController;
import com.wntime.customer.service.InfoBusCompanyService;
import com.wntime.customer.vo.CompanyLineWithStationVo;
import com.wntime.customer.vo.LogisticCompanyLineVo;
import com.wntime.service.common.service.BusCompanyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wntime.customer.entity.InfoLineStationEntity;
import com.wntime.customer.service.InfoLineStationService;



/**
 * @desc 线路车站表
 *
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2020-08-25 14:23:25
 */
@Api(value = "线路车站管理", tags = {"线路车站管理"})
@RestController
@RequestMapping("customer/infolinestation")
public class InfoLineStationController extends AbstractController {
    @Autowired
    private InfoLineStationService infoLineStationService;
    @Autowired
    private BusCompanyService busCompanyService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("customer:infolinestation:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = infoLineStationService.queryPage(params);

        return R.ok().put("page", page);
    }

    @ApiOperation(value = "获取线路的车站信息(上行下行)", httpMethod = "GET", notes = "获取线路的车站信息(上行下行)")
    @RequestMapping("/listByLineCode")
    public R listByLineCode(@RequestParam String companyLineCode){
        List<CompanyLineWithStationVo> list = infoLineStationService.listByLineCode(companyLineCode);

        return R.ok().put("list", list);
    }

    @ApiOperation(value = "获取用户的所有线路的车站信息(上行下行)", httpMethod = "GET", notes = "获取用户的所有线路的车站信息(上行下行)")
    @RequestMapping("/listByUser")
    public R listByUser(){
        List<Long> userCompanyIdList = busCompanyService.getUserCompanyIdList(getUserId());
        List<LogisticCompanyLineVo> list = infoLineStationService.listByCompanyIdList(userCompanyIdList);

        return R.ok().put("stationList", list);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{lineStationId}")
    @RequiresPermissions("customer:infolinestation:info")
    public R info(@PathVariable("lineStationId") Long lineStationId){
        InfoLineStationEntity infoLineStation = infoLineStationService.getById(lineStationId);

        return R.ok().put("infoLineStation", infoLineStation);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("customer:infolinestation:save")
    public R save(@RequestBody InfoLineStationEntity infoLineStation){
        infoLineStationService.save(infoLineStation);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("customer:infolinestation:update")
    public R update(@RequestBody InfoLineStationEntity infoLineStation){
        ValidatorUtils.validateEntity(infoLineStation);
        infoLineStationService.updateById(infoLineStation);
        
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("customer:infolinestation:delete")
    public R delete(@RequestBody Long[] lineStationIds){
        infoLineStationService.removeByIds(Arrays.asList(lineStationIds));

        return R.ok();
    }

}
