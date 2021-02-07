package com.wntime.customer.controller;

import java.util.*;

import cn.hutool.core.util.PageUtil;
import com.wntime.common.annotation.SysLog;
import com.wntime.common.exception.RRException;
import com.wntime.common.utils.PageUtils;
import com.wntime.common.utils.R;
import com.wntime.common.validator.ValidatorUtils;

import com.wntime.controller.AbstractController;
import com.wntime.customer.entity.InfoBusStationEntity;
import com.wntime.customer.entity.OrderBusCompanyDetailEntity;
import com.wntime.customer.vo.AdvertlineVo;
import com.wntime.customer.vo.InfoBusCompanyVo;
import com.wntime.modules.sys.vo.UserPositionVo;
import com.wntime.service.common.service.BusCompanyService;
import com.wntime.service.common.service.CompanyLineService;
import com.wntime.service.common.util.UniqueCheckHelper;
import com.wntime.service.common.vo.BusLineVo;
import com.wntime.service.common.vo.DeleteBatchVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import com.wntime.customer.entity.InfoCompanyLineEntity;
import com.wntime.customer.service.InfoCompanyLineService;



/**
 * @desc 公交线路表
 *
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2020-08-25 14:04:05
 */
@Api(value = "公交线路", tags = {"公交线路"})
@RestController
@RequestMapping("customer/infocompanyline")
public class InfoCompanyLineController extends AbstractController {
    @Autowired
    private InfoCompanyLineService infoCompanyLineService;

    @Autowired
    private CompanyLineService companyLineService;

    @Autowired
    private BusCompanyService busCompanyService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("customer:infocompanyline:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = infoCompanyLineService.queryPage(params);

        return R.ok().put("page", page);
    }


    @GetMapping("/getCompanyLinePageByCompanyId")
    @ApiOperation(value = "获取公交公司全部公交线路", httpMethod = "GET", notes = "获取公交公司全部公交线路")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "page", value = "请求页码", required = true, dataType = "int"),
            @ApiImplicitParam(paramType = "query", name = "limit", value = "每页数量", required = true, dataType = "int"),
            @ApiImplicitParam(paramType = "query", name = "companyId", value = "公交公司ID", required = true, dataType = "String")
    })
    public R getCompanyLinePageByCompanyId(@RequestParam Map<String, Object> params) {
        // 登录用户权限下公司列表
        params.put("areaOrgIds", super.getUser().getAreaOrgIds());
        PageUtils page = infoCompanyLineService.getCompanyLinePageByCompanyId(params);
        return R.ok().put("page", page);
    }


    @GetMapping("/getCompanyLineByCompanyId/{companyId}")
    @ApiOperation(value = "获取公交公司全部公交线路", httpMethod = "GET", notes = "获取公交公司全部公交线路")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "companyId", value = "公交公司ID", required = true, dataType = "String")
    })
    public R getCompanyLineByCompanyId(@PathVariable("companyId") Long companyId) {
        logger.info("获取公交公司全部公交线路,参数:" + companyId);
        List<InfoCompanyLineEntity> list = infoCompanyLineService.getCompanyLineByCompanyId(companyId);
        return R.ok().put("list", list);
    }

    @ApiOperation(value = "获取单条公交线路", httpMethod = "GET", notes = "获取单条公交线路")
    @GetMapping("/get/{id}")
    public R getById(@PathVariable("id") Long id) {
        logger.info("获取单条公交线路,参数ID:" + id);
        InfoCompanyLineEntity infoCompanyLineEntity;
        try {
            infoCompanyLineEntity = infoCompanyLineService.getDetailById(id);
        } catch (Exception e) {
            e.printStackTrace();
            return R.error("获取单条公交线路");
        }
        return R.ok().put("data", infoCompanyLineEntity);
    }

    @GetMapping("/getCompanyLineByUser")
    @ApiOperation(value = "获取用户的全部公交线路", httpMethod = "GET", notes = "获取用户的公交线路")
    public R getCompanyLineByUser() {
        List<Long> userCompanyIdList = busCompanyService.getUserCompanyIdList(getUserId());
        List<AdvertlineVo> list = infoCompanyLineService.getCompanyLineCodeByCompanyIdList(userCompanyIdList);
        return R.ok().put("list", list);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{companyLineId}")
    @RequiresPermissions("customer:infocompanyline:info")
    public R info(@PathVariable("companyLineId") Long companyLineId){
        InfoCompanyLineEntity infoCompanyLine = infoCompanyLineService.getById(companyLineId);

        return R.ok().put("infoCompanyLine", infoCompanyLine);
    }

    /**
     * 信息
     */
    @RequestMapping("/infoDetail/{companyLineId}")
//    @RequiresPermissions("customer:infocompanyline:info")
    public R infoDetail(@PathVariable("companyLineId") Long companyLineId){
        InfoCompanyLineEntity infoCompanyLine = infoCompanyLineService.getDetailInfoById(companyLineId);

        return R.ok().put("data", infoCompanyLine);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
//    @RequiresPermissions("customer:infocompanyline:save")
    public R save(@RequestBody InfoCompanyLineEntity infoCompanyLine){
        logger.info("保存公交线路,参数:" + infoCompanyLine.toString());
        infoCompanyLine.setSchoolId(super.getUser().getAreaOrgIds().get(0));
        ValidatorUtils.validateEntity(infoCompanyLine);

        Long[] busStationIds = infoCompanyLine.getBusStationIds();
        if(busStationIds == null || busStationIds.length == 0){
            throw new RRException("公交线路必须有站点");
        }

        Map<String, Object> paramMap = new HashMap<>(4);
        paramMap.put("company_line_name",infoCompanyLine.getCompanyLineName());
        paramMap.put("company_line_code",infoCompanyLine.getCompanyLineCode());
        paramMap.put("school_id",infoCompanyLine.getSchoolId());
        paramMap.put("direction",infoCompanyLine.getDirection());
        paramMap.put("is_deleted",0);
        UniqueCheckHelper.assertIsUnique(infoCompanyLineService, paramMap, "同公交公司公交线路名称已存在！");

        infoCompanyLineService.saveCompanyLine(infoCompanyLine);

        return R.ok();
    }

    /**
     * 保存
     */
    @RequestMapping("/saveBatch")
//    @RequiresPermissions("customer:infocompanyline:save")
    public R saveBatch(@RequestBody InfoCompanyLineEntity infoCompanyLine){
        logger.info("批量保存公交线路,参数:" + infoCompanyLine.toString());
        ValidatorUtils.validateEntity(infoCompanyLine);
        List<InfoBusStationEntity> busStationList = infoCompanyLine.getBusStationList();
        if(busStationList == null || busStationList.size() == 0){
            throw new RRException("公交线路必须有站点");
        }
        Map<String, Object> paramMap = new HashMap<>(4);
        paramMap.put("company_line_name",infoCompanyLine.getCompanyLineName());
        paramMap.put("company_line_code",infoCompanyLine.getCompanyLineCode());
        paramMap.put("company_id",infoCompanyLine.getCompanyId());
        paramMap.put("is_deleted",0);
        UniqueCheckHelper.assertIsUnique(infoCompanyLineService, paramMap, "同公交公司公交线路名称已存在！");

        infoCompanyLineService.saveCompanyLineBatch(infoCompanyLine);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
//    @RequiresPermissions("customer:infocompanyline:update")
    public R update(@RequestBody InfoCompanyLineEntity infoCompanyLine){
        infoCompanyLine.setSchoolId(super.getUser().getAreaOrgIds().get(0));
        ValidatorUtils.validateEntity(infoCompanyLine);
        Long[] busStationIds = infoCompanyLine.getBusStationIds();
        if(busStationIds == null || busStationIds.length == 0){
            throw new RRException("公交线路必须有站点");
        }
        Map<String, Object> paramMap = new HashMap<>(4);
        paramMap.put("company_line_name",infoCompanyLine.getCompanyLineName());
        paramMap.put("company_line_code",infoCompanyLine.getCompanyLineCode());
        paramMap.put("school_id",infoCompanyLine.getSchoolId());
        paramMap.put("direction",infoCompanyLine.getDirection());
        paramMap.put("is_deleted",0);
        infoCompanyLine.setIsDeleted(0);
        UniqueCheckHelper.assertIsUnique(infoCompanyLineService, paramMap,"company_line_id",infoCompanyLine.getCompanyLineId(), "同公交公司公交线路名称已存在！");

        infoCompanyLineService.updateCompanyLine(infoCompanyLine);
        
        return R.ok();
    }

    /**
     * 删除
     */
    @ApiOperation(value = "单条删除@desc 公交线路表", httpMethod = "POST", notes = "@desc 公交线路表管理页面中删除@desc 公交线路表")
    @SysLog("单条删除@desc 公交线路表")
    @PostMapping("/delete/{id}")
    public R delete(@PathVariable("id") Long id) {
        logger.info("单条删除@desc 公交线路表,id:" + id);
        try {
            infoCompanyLineService.delById(id, getUserId());
        } catch (Exception e) {
            e.printStackTrace();
            return R.error(e.getMessage());
        }
        return R.ok();
    }

    @ApiOperation(value = "批量删除@desc 公交线路表", httpMethod = "POST", notes = "批量删除@desc 公交线路表")
    @SysLog("批量删除@desc 公交线路表")
    @PostMapping("/deleteBatch")
    public R deleteBatch(@RequestBody DeleteBatchVo deleteBatchVo) {
        logger.info("批量删除@desc 公交线路表,:" + Arrays.toString(deleteBatchVo.getIds()));

        if (deleteBatchVo.getIds() == null || deleteBatchVo.getIds().length == 0 || StringUtils.isEmpty(deleteBatchVo.getValidatePassword()))
            return R.error("参数不能为空!");

        //校验当前用户的密码是否正确
        if (!validatePassword(deleteBatchVo.getValidatePassword())) return R.error("密码不正确");

        try {
            infoCompanyLineService.deleteBatch(deleteBatchVo.getIds(), getUserId());
        } catch (Exception e) {
            e.printStackTrace();
            return R.error(e.getMessage());
        }

        return R.ok("批量删除成功!");
    }

    /**
     * 查询公交公司下线路（指定公司）
     */
    @GetMapping("/lineListByCompanyId")
//    @RequiresPermissions("customer:infobuscompany:info")
    @ApiOperation(value = "查询公交公司下线路（指定公司）", httpMethod = "GET", notes = "查询公交公司下线路（指定公司）")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "companyId", value = "公司ID", required = true, dataType = "long"),
            @ApiImplicitParam(paramType = "query", name = "params", value = "参数", dataType = "int")
    })
    public R getLineListByCompanyId(@RequestParam Map<String, Object> params) {
        Long companyId = Long.parseLong(String.valueOf(params.get("companyId")));
        List<BusLineVo> list = companyLineService.queryCompanyBusLineInfo(companyId);
        return R.ok().put("list", list);
    }
}
