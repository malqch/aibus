package com.wntime.customer.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wntime.common.annotation.SysLog;
import com.wntime.common.utils.*;
import com.wntime.common.validator.Assert;
import com.wntime.common.validator.ValidatorUtils;
import com.wntime.customer.entity.InfoBusCompanyEntity;
import com.wntime.customer.service.InfoBusCompanyService;
import com.wntime.controller.AbstractController;
import com.wntime.customer.vo.*;
import com.wntime.service.common.service.BusCompanyService;
import com.wntime.service.common.util.UniqueCheckHelper;
import com.wntime.service.common.vo.DeleteBatchVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;
import java.util.Map;


/**
 * @author Mark
 * 公交公司表
 * @email sunlightcs@gmail.com
 * @date 2020-08-25 14:04:05
 */
@Api(value = "公交公司controller", tags = {"公交公司管理"})
@RestController
@RequestMapping("customer/infobuscompany")
public class InfoBusCompanyController extends AbstractController {
    @Autowired
    private InfoBusCompanyService infoBusCompanyService;
    @Autowired
    private BusCompanyService busCompanyService;

    @Value("${wntime.service.upload.companySnapshotFilePath}")
    private String imageDir;

    @Value("${wntime.common.dataPath}")
    private String commonDataPath;

    @Value("${server.webUrl}")
    private String webUrl;

    /**
     * 列表
     */
    @GetMapping("/list")
    @ApiOperation(value = "公交公司表分页列表", httpMethod = "GET", notes = "公交公司表管理页面中Grid获取数据接口")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "page", value = "请求页码", required = true, dataType = "int"),
            @ApiImplicitParam(paramType = "query", name = "limit", value = "每页数量", required = true, dataType = "int")
    })
    public R list(@RequestParam Map<String, Object> params) {
        logger.info("公交公司表分页列表,参数:" + JSON.toJSONString(params));
        // 登录用户权限下公司列表
        params.put("schoolIdList", super.getUser().getAreaOrgIds());
        PageUtils page = infoBusCompanyService.queryPage(params);
        List<InfoBusCompanyVo> list = page.getList();
        for (InfoBusCompanyVo item : list) {
            item.setCompanySnapshot(webUrl + "/" + item.getCompanySnapshot());
        }
        return R.ok().put("page", page);
    }

    @GetMapping("/selection")
    @ApiOperation(value = "公交公司列表", httpMethod = "GET")
    public R simpleList(@RequestParam Map<String, Object> params) {
        logger.info("公交公司表列表,参数:" + JSON.toJSONString(params));
        // 登录用户权限下公司列表
        QueryWrapper queryWrapper = new QueryWrapper<InfoBusCompanyEntity>()
                .eq("is_enabled", Constant.Enabled.ENABLE.getValue())
                .eq("is_deleted", Constant.Deleted.UNDELETED.getValue())
                .eq("is_clique", "0");
//                .in("company_id",busCompanyService.getUserCompanyIdList(getUserId()));
        List<InfoBusCompanyEntity> list = infoBusCompanyService.list(queryWrapper);
        return R.ok().put("list", list);
    }

    @ApiOperation(value = "获取单条公交公司表", httpMethod = "GET", notes = "获取单条公交公司表")
    @GetMapping("/get/{id}")
    public R getById(@PathVariable("id") Long id) {
        logger.info("获取单条公交公司表,参数ID:" + id);
        InfoBusCompanyVo infoBusCompanyVo;
        try {
            infoBusCompanyVo = infoBusCompanyService.getDetailById(id);
            infoBusCompanyVo.setCompanySnapshot(webUrl + "/" + infoBusCompanyVo.getCompanySnapshot());
        } catch (Exception e) {
            e.printStackTrace();
            return R.error("获取单条公交公司表失败");
        }
        return R.ok().put("data", infoBusCompanyVo);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    @SysLog("保存公交公司表")
    @ApiOperation(value = "添加公交公司表", httpMethod = "POST", notes = "公交公司表管理页面中添加公交公司表")
    public R save(InfoBusCompanyEntity infoBusCompanyEntity) {
        logger.info("保存公交公司表,参数:" + infoBusCompanyEntity.toString());
        infoBusCompanyEntity.setSchoolId(super.getUser().getAreaOrgIds().get(0));
        ValidatorUtils.validateEntity(infoBusCompanyEntity);
        UniqueCheckHelper.assertIsUnique(infoBusCompanyService, "company_code", infoBusCompanyEntity.getCompanyCode(), "公司统一信用代码已存在！");

        MultipartFile file = infoBusCompanyEntity.getCompanySnapshotFile();
        String fileName;
        if (file != null && !file.isEmpty()) {
            logger.info("保存文件:" + file.getOriginalFilename());
            fileName = FileUtil.saveFile(file, imageDir);
            if (fileName == null) {
                return R.error("保存文件失败:" + file.getName());
            }
            infoBusCompanyEntity.setCompanySnapshot("image" + imageDir.substring(commonDataPath.length()) + "/" + fileName);
        }

        try {
            infoBusCompanyService.save(infoBusCompanyEntity, getUserId());
        } catch (Exception e) {
            e.printStackTrace();
            return R.error("保存公交公司表失败");
        }
        return R.ok();
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    @SysLog("修改公交公司表")
    @ApiOperation(value = "修改公交公司表", httpMethod = "POST", notes = "公交公司表管理页面中修改公交公司表")
    public R update(InfoBusCompanyEntity infoBusCompanyEntity) {
        logger.info("修改公交公司表,参数:" + infoBusCompanyEntity.toString());
        infoBusCompanyEntity.setSchoolId(super.getUser().getAreaOrgIds().get(0));
        ValidatorUtils.validateEntity(infoBusCompanyEntity);
        UniqueCheckHelper.assertIsUnique(infoBusCompanyService, "company_code", infoBusCompanyEntity.getCompanyCode(), "company_id",infoBusCompanyEntity.getCompanyId(),"公司统一信用代码已存在！");

        //去除webUrl前缀
        String companySnapshot = infoBusCompanyEntity.getCompanySnapshot();
        if(companySnapshot != null && companySnapshot.startsWith(webUrl)){
            infoBusCompanyEntity.setCompanySnapshot(companySnapshot.replace(webUrl + "/",""));
        }

        MultipartFile file = infoBusCompanyEntity.getCompanySnapshotFile();
        String fileName;
        if (file != null && !file.isEmpty()) {
            logger.info("保存文件:" + file.getOriginalFilename());
            fileName = FileUtil.saveFile(file, imageDir);
            if (fileName == null) {
                return R.error("保存文件失败:" + file.getOriginalFilename());
            }
            infoBusCompanyEntity.setCompanySnapshot("image" + imageDir.substring(commonDataPath.length()) + "/" + fileName);
            InfoBusCompanyEntity old = infoBusCompanyService.getById(infoBusCompanyEntity.getCompanyId());
            if (!StringUtils.isEmpty(old.getCompanySnapshot()) && old.getCompanySnapshot().length() > 5) {
                String oldPath = commonDataPath + old.getCompanySnapshot().substring(5);
                logger.info("删除文件:" + oldPath);
                cn.hutool.core.io.FileUtil.del(oldPath);
            }
        }

        try {
            infoBusCompanyService.updateById(infoBusCompanyEntity, getUserId());
        } catch (Exception e) {
            e.printStackTrace();
            return R.error("修改公交公司表失败");
        }
        return R.ok();
    }

    /**
     * 删除
     */
    @ApiOperation(value = "单条删除公交公司表", httpMethod = "POST", notes = "公交公司表管理页面中删除公交公司表")
    @SysLog("单条删除公交公司表")
    @PostMapping("/delete/{id}")
    public R delete(@PathVariable("id") Long id) {
        logger.info("单条删除公交公司表,id:" + id);
        try {
            infoBusCompanyService.delById(id, getUserId());
        } catch (Exception e) {
            e.printStackTrace();
            return R.error("修改公交公司表失败");
        }
        return R.ok();
    }

    @ApiOperation(value = "批量删除公交公司表", httpMethod = "POST", notes = "批量删除公交公司表")
    @SysLog("批量删除公交公司表")
    @PostMapping("/deleteBatch")
    public R deleteBatch(@RequestBody DeleteBatchVo deleteBatchVo) {
        logger.info("批量删除公交公司表,:" + Arrays.toString(deleteBatchVo.getIds()));

        if (deleteBatchVo.getIds() == null || deleteBatchVo.getIds().length == 0 || StringUtils.isEmpty(deleteBatchVo.getValidatePassword()))
            return R.error("参数不能为空!");

        //校验当前用户的密码是否正确
        if (!validatePassword(deleteBatchVo.getValidatePassword())) return R.error("密码不正确");

        try {
            infoBusCompanyService.deleteBatch(deleteBatchVo.getIds(), getUserId());
        } catch (Exception e) {
            e.printStackTrace();
            return R.error("批量删除出错!");
        }

        return R.ok("批量删除成功!");
    }


    /**
     * 信息
     */
    @ApiOperation(value = "获取公交公司信息", notes = "", httpMethod = "GET", response = InfoBusCompanyEntityWithAreaVO.class)
    @RequestMapping("/info/{companyId}")
    //@RequiresPermissions("customer:infobuscompany:info")
    public R info(@PathVariable("companyId") Long companyId) {
        InfoBusCompanyEntityWithAreaVO infoBusCompany = infoBusCompanyService.getByIdWithArea(companyId);

        return R.ok().put("data", infoBusCompany);
    }


    /**
     * 查询客户统计信息（指定地区）数量、交付总量、地标信息
     */
    @GetMapping("/companyStat")
//    @RequiresPermissions("customer:infobuscompany:info")
    @ApiOperation(value = "查询客户统计信息（指定地区）", httpMethod = "GET", notes = "查询客户统计信息（指定地区）")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "areaId", value = "区域ID", required = true, dataType = "int"),
            @ApiImplicitParam(paramType = "query", name = "params", value = "参数", dataType = "int")
    })
    public R getCompanyStat(@RequestParam Map<String, Object> params) {
        params.put("companyIdList", busCompanyService.getUserCompanyIdList(getUserId()));
        List<StatisticsResultVo> result = infoBusCompanyService.getCompanyStatistics(params);
        return R.ok().put("list", result);
    }

    /**
     * 根据选择地区查询客户列表(包括总订单量,总交付量）
     */
    @GetMapping("/companyAndStatList")
//    @RequiresPermissions("customer:infobuscompany:info")
    @ApiOperation(value = "根据选择地区查询客户列表(总订单量,总交付量）", httpMethod = "GET", notes = "根据选择地区查询客户列表(总订单量,总交付量）")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "areaId", value = "区域ID", required = true, dataType = "int"),
            @ApiImplicitParam(paramType = "query", name = "params", value = "参数", dataType = "int")
    })
    public R getCompanyStatListByAreaId(@RequestParam Map<String, Object> params) {
        params.put("companyIdList", busCompanyService.getUserCompanyIdList(getUserId()));
        List<InfoBusCompanyVo> list = infoBusCompanyService.getCompanyStatListByAreaId(params);
        return R.ok().put("list", list);
    }

    /**
     * 根据选择地区查询客户列表
     */
    @GetMapping("/companyList")
//    @RequiresPermissions("customer:infobuscompany:info")
    @ApiOperation(value = "根据选择地区查询客户列表", httpMethod = "GET", notes = "根据选择地区查询客户列表")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "areaId", value = "区域ID", required = true, dataType = "int"),
            @ApiImplicitParam(paramType = "query", name = "params", value = "参数", dataType = "int")
    })
    public R getCompanyListByAreaId(@RequestParam Map<String, Object> params) {
        params.put("companyIdList", busCompanyService.getUserCompanyIdList(getUserId()));
        List<InfoBusCompanyVo> list = infoBusCompanyService.getCompanyListByAreaId(params);
        Assert.isEmpty(list, "没有数据");
        return R.ok().put("list", list);
    }

    /**
     * 查询客户列表
     */
    @GetMapping("/companyListAll")
//    @RequiresPermissions("customer:infobuscompany:info")
    @ApiOperation(value = "查询客户列表", httpMethod = "GET", notes = "客户列表")
    public R getCompanyListAll(@RequestParam Map<String, Object> params) {
        params.put("schoolIdList", super.getUser().getAreaOrgIds());
        List<InfoBusCompanyEntity> list = infoBusCompanyService.getCompanyListAll(params);
        return R.ok().put("list", list);
    }

    /**
     * 查询客户列表
     */
    @GetMapping("/companyCliqueListAll")
//    @RequiresPermissions("customer:infobuscompany:info")
    @ApiOperation(value = "查询客户集团列表", httpMethod = "GET", notes = "客户集团列表")
    public R getCompanyCliqueListAll() {
        QueryWrapper queryWrapper = new QueryWrapper<InfoBusCompanyEntity>()
                .eq("is_enabled", Constant.Enabled.ENABLE.getValue())
                .eq("is_deleted", Constant.Deleted.UNDELETED.getValue())
                .eq("is_clique",'1');
        List<InfoBusCompanyEntity> list = infoBusCompanyService.list(queryWrapper);
        return R.ok().put("list", list);
    }

    /**
     * 查询客户总订单量,总交付量（指定区域:分车型）InfoBusCompanyDao
     */
    @GetMapping("/busType/deliveryStat")
//    @RequiresPermissions("customer:infobuscompany:info")
    @ApiOperation(value = "查询客户总订单量,总交付量（指定区域:分车型）", httpMethod = "GET", notes = "查询客户总订单量,总交付量（指定区域:分车型）")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "areaId", value = "区域ID", required = true, dataType = "int"),
            @ApiImplicitParam(paramType = "query", name = "params", value = "参数", dataType = "int")
    })
    public R getCompanyDeliveryStatListByAreaId(@RequestParam Map<String, Object> params) {
        params.put("companyIdList", busCompanyService.getUserCompanyIdList(getUserId()));
        List<InfoBusCompanyStatVo> list = infoBusCompanyService.getCompanyBusTypeDeliveryListByAreaId(params);
        return R.ok().put("list", list);
    }

    /**
     * 查询客户总交付量,出保量（指定区域:分车型）
     */
    @GetMapping("/busType/outDateStat")
//    @RequiresPermissions("customer:infobuscompany:info")
    @ApiOperation(value = "查询客户总交付量,出保量（指定区域:分车型）", httpMethod = "GET", notes = "查询客户总交付量,出保量（指定区域:分车型）")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "areaId", value = "区域ID", required = true, dataType = "int"),
            @ApiImplicitParam(paramType = "query", name = "params", value = "参数", dataType = "int")
    })
    public R getCompanyOutDateStatByAreaId(@RequestParam Map<String, Object> params) {
        params.put("companyIdList", busCompanyService.getUserCompanyIdList(getUserId()));
        List<InfoBusCompanyStatVo> list = infoBusCompanyService.getCompanyBusTypeOutDateListByAreaId(params);
        return R.ok().put("list", list);
    }

    /**
     * 查询公司总订单量,总交付量（指定公司:分车型）
     */
    @GetMapping("/busType/delivery/stat")
//    @RequiresPermissions("customer:infobuscompany:info")
    @ApiOperation(value = "查询公司总订单量,总交付量（指定公司:分车型）", httpMethod = "GET", notes = "查询公司总订单量,总交付量（指定公司:分车型）")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "companyId", value = "公司ID", required = true, dataType = "int"),
            @ApiImplicitParam(paramType = "query", name = "params", value = "参数", dataType = "int")
    })
    public R getCompanyDeliveryStatListByCompanyId(@RequestParam Map<String, Object> params) {
        List<InfoCompanyBusTypeStatVo> list = infoBusCompanyService.getCompanyBusTypeDeliveryListByCompanyId(params);
        return R.ok().put("list", list);
    }

    /**
     * 查询公司交付量,出保量（指定公司:分车型）
     */
    @GetMapping("/busType/outDate/stat")
//    @RequiresPermissions("customer:infobuscompany:info")
    @ApiOperation(value = "查询公司交付量,出保量（指定公司:分车型）", httpMethod = "GET", notes = "查询公司交付量,出保量（指定公司:分车型）")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "companyId", value = "公司ID", required = true, dataType = "int"),
            @ApiImplicitParam(paramType = "query", name = "params", value = "参数", dataType = "int")
    })
    public R getCompanyOutDateStatByCompanyId(@RequestParam Map<String, Object> params) {
        List<InfoCompanyBusTypeStatVo> list = infoBusCompanyService.getCompanyBusTypeOutDateListByCompanyId(params);
        return R.ok().put("list", list);
    }

    /**
     * 根据用户查询公交公司列表
     */
    @RequestMapping("/user/companies")
    @ApiOperation(value = "根据用户查询客户列表", httpMethod = "GET", notes = "根据用户查询客户列表")
    public R getCompanyListBySeller() {

        return R.ok().put("list", infoBusCompanyService.listByIds(busCompanyService.getUserCompanyIdList(getUserId())));
    }

    /**
     * 根据用户获取有数据的基本信息
     */
    @GetMapping("/user/companies/valid")
    @ApiOperation(value = "根据用户获取有数据的基本信息", httpMethod = "GET", notes = "根据用户获取有数据的基本信息")
    public R getValidCompanyListBySeller() {
        ValidCompanyVO validCompanyVO = infoBusCompanyService.getValidCompanyListByUserId(getUserId());
        Assert.isNull(validCompanyVO, "该用户没有有数据的客户");
        return R.ok().put("data", validCompanyVO);
    }
}
