package com.wntime.customer.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wntime.common.annotation.SysLog;
import com.wntime.common.utils.Constant;
import com.wntime.common.utils.DateUtils;
import com.wntime.common.utils.R;
import com.wntime.common.validator.ValidatorUtils;
import com.wntime.controller.AbstractController;
import com.wntime.customer.entity.PlanBusServiceEntity;
import com.wntime.customer.service.PlanBusServiceService;
import com.wntime.customer.vo.PlanBusServiceVo;
import com.wntime.service.common.vo.DeleteBatchVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


/**
 * @desc 营运计划表
 *
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2020-08-25 14:00:24
 */
@Api(value = "营运计划管理", tags = {"营运计划管理"})
@RestController
@RequestMapping("customer/plan-bus-service")
public class PlanBusServiceController extends AbstractController {
    @Autowired
    private PlanBusServiceService planBusServiceService;

    /**
     * 分页列表
     */
  /*  @ApiOperation(value = "分页查询", httpMethod = "GET", notes = "分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "page", value = "请求页码", required = true, dataType = "int"),
            @ApiImplicitParam(paramType = "query", name = "limit", value = "每页数量", required = true, dataType = "int"),
            @ApiImplicitParam(paramType = "query", name = "companyId", value = "公司ID", required = false, dataType = "int"),
            @ApiImplicitParam(paramType = "query", name = "comp", value = "公司ID", required = false, dataType = "string")    })
    @GetMapping("/page")
    public R page(@RequestParam Map<String, Object> params) {
        PageUtils page = planBusServiceService.queryPage(params);

        return R.ok().put("page", page);
    }*/

    /**
     * 列表
     */
    @ApiOperation(value = "列表查询带公交车信息(有isEnable=0的数据)", httpMethod = "GET", notes = "列表查询")
    @GetMapping("/detail/list")
    public R listWithBus(@RequestParam(required = false) Long companyLineId) {

        return R.ok().put("list", planBusServiceService.listDetail(companyLineId));
    }

    /**
     * 列表
     */
    @ApiOperation(value = "列表查询", httpMethod = "GET", notes = "列表查询")
    @GetMapping("/list")
    public R list(@RequestParam(required = false) Long companyLineId) {
        QueryWrapper<PlanBusServiceEntity> queryWrapper = new QueryWrapper<PlanBusServiceEntity>()
                .eq("is_enabled",Constant.Enabled.ENABLE.getValue())
                .eq("is_deleted", Constant.Deleted.UNDELETED.getValue());
        if(companyLineId != null){
            queryWrapper.eq("company_line_id",companyLineId);
        }
        return R.ok().put("list", planBusServiceService.list(
                queryWrapper));
    }

    /**
     * 获取时间段内无计划的公交车列表
     */
    @ApiOperation(value = "获取时间段内无计划的公交车列表", httpMethod = "GET", notes = "获取时间段内无计划的公交车列表")
    @GetMapping("/no-plan/bus/list")
    public R notPlannedBusList(
                  @RequestParam Long companyId,
                  @RequestParam Long companyLineId,
                  @RequestParam String direction,
                  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") @RequestParam Date beginDate,
                  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") @RequestParam Date endDate) {
        return R.ok().put("data", planBusServiceService.listBusNotPlannedInTimeRange(
                super.getUser().getAreaOrgIds().get(0),direction
                ,companyId,companyLineId,beginDate, endDate));
    }

    /**
     * 判断公交车在时间段内有无计划
     */
    @ApiOperation(value = "判断公交车在时间段内有无计划", httpMethod = "GET", notes = "判断公交车在时间段内有无计划")
    @GetMapping("/info/planned")
    public R info(@RequestParam Long busId,
                  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") @RequestParam Date beginDate,
                  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") @RequestParam Date endDate) {
        return R.ok().put("data", planBusServiceService.isBusPlannedInTimeRange(busId, beginDate, endDate));
    }


    /**
     * 信息
     */
    @ApiOperation(value = "单条查询", httpMethod = "GET", notes = "单条查询")
    @GetMapping("/info/{id}")
    public R info(@PathVariable("id") Long id) {
        PlanBusServiceEntity entity = planBusServiceService.getDetailById(id);
        if(entity.getIsDeleted().equals(Constant.Deleted.DELETED.getValue())){
            return R.error("该数据已被删除");
        }
        return R.ok().put("data", entity);
    }

    /**
     * 保存
     */
    @SysLog("保存营运计划")
    @ApiOperation(value = "单条新增", httpMethod = "POST", notes = "单条新增")
    @PostMapping("/save")
    public R save(@RequestBody PlanBusServiceEntity entity) {
        ValidatorUtils.validateEntity(entity);
//        if(planBusServiceService.isBusPlannedInTimeRange(entity.getBusId(),entity.getBeginDate(),entity.getEndDate())){
//            return R.error("该车辆在这段时间已有计划");
//        }
        List<PlanBusServiceVo> planBusServiceVoList = planBusServiceService.listPlanInTimeRangeByBus(entity.getCompanyLineId(), entity.getDirection(),  entity.getBusId(), entity.getBeginDate(), entity.getEndDate());
        if(! planBusServiceVoList.isEmpty()){
            return R.error("该车辆在这段时间已有计划").put("list",planBusServiceVoList);
        }
        entity.setIsDeleted(0);
        entity.setCreatedBy(getUserId());
        entity.setCreatedDate(DateUtils.getTimestamp());
        planBusServiceService.savePlanBusService(entity);

        return R.ok();
    }

    /**
     * 修改
     */
    @SysLog("修改营运计划")
    @ApiOperation(value = "单条修改", httpMethod = "PUT", notes = "单条修改")
    @PutMapping("/update")
    public R update(@RequestBody PlanBusServiceEntity entity) {
        ValidatorUtils.validateEntity(entity);
//        if(planBusServiceService.isBusPlannedInTimeRangeExceptPlanId(entity.getPlanServiceId(),entity.getBusId(),entity.getBeginDate(),entity.getEndDate())){
//            return R.error("该车辆在这段时间已有计划");
//        }
        List<PlanBusServiceVo> planBusServiceVoList = planBusServiceService.listPlanInTimeRangeByBus(entity.getCompanyLineId(), entity.getDirection(),  entity.getBusId(), entity.getBeginDate(), entity.getEndDate());
        //去除当前计划
        planBusServiceVoList = planBusServiceVoList.stream()
                .filter(pbs -> ! pbs.getPlanServiceId().equals(entity.getPlanServiceId()))
                .collect(Collectors.toList());

        if(! planBusServiceVoList.isEmpty()){
            return R.error("该车辆在这段时间已有计划").put("list",planBusServiceVoList);
        }
        entity.setModifiedBy(getUserId());
        entity.setModifiedDate(DateUtils.getTimestamp());
        planBusServiceService.updatePlanBusService(entity);

        return R.ok();
    }

    /**
     * 删除
     */
    @SysLog("删除营运计划")
    @ApiOperation(value = "单条删除", httpMethod = "DELETE", notes = "管理页面中删除")
    @DeleteMapping("/delete/{id}")
    public R delete(@PathVariable("id") Long id) {
        logger.info("单条删除,id:" + id);
        try {
            planBusServiceService.delById(id, getUserId());
        } catch (Exception e) {
            e.printStackTrace();
            return R.error("修改失败");
        }
        return R.ok();
    }

    @SysLog("批量删除营运计划")
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
            planBusServiceService.deleteBatch(deleteBatchVo.getIds(), getUserId());
        } catch (Exception e) {
            e.printStackTrace();
            return R.error("批量删除出错!");
        }

        return R.ok("批量删除成功!");
    }

}
