package com.wntime.modules.officer.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.wntime.common.BeanMerged;
import com.wntime.common.utils.PageUtils;
import com.wntime.common.utils.R;
import com.wntime.controller.AbstractController;
import com.wntime.modules.officer.dto.SafetyOfficerInfoDto;
import com.wntime.modules.officer.entity.PeopleBasicFactsEntity;
import com.wntime.modules.officer.entity.SafetyOfficerEntity;
import com.wntime.modules.officer.from.SafetyOfficerFrom;
import com.wntime.modules.officer.service.PeopleBasicFactsService;
import com.wntime.modules.officer.service.SafetyOfficerService;
import com.wntime.modules.officer.vo.DeleteVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;


/**
 * @author raute
 * @desc 校车安全员
 * @email admin@wntime.com
 * @date 2021-01-16 14:43:43
 */
@RestController
@RequestMapping("officer/safetyofficer")
@Api(value = "安全员信息接口", tags = {"安全员信息接口"})
public class SafetyOfficerController extends AbstractController {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private SafetyOfficerService safetyOfficerService;
    @Autowired
    PeopleBasicFactsService factsService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("officer:safetyofficer:list")
    @ApiOperation(value = "安全员列表接口", httpMethod = "POST")
    public R list(@RequestBody Map<String, Object> params) {
        PageUtils page = safetyOfficerService.queryPage(params);

        return R.ok().put("page", page);
    }

    @GetMapping("/selection")
    @ApiOperation(value = "选择安全员下拉列表", httpMethod = "GET")
    public R getPrimarySafetyOfficerList(@RequestParam Map<String, Object> params) {
        logger.info("选择安全员下拉列表,参数:" + JSON.toJSONString(params));
        List<SafetyOfficerEntity> list = safetyOfficerService.getPrimarySafetyOfficerList();
        return R.ok().put("list", list);
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @ApiOperation(value = "安全员详情接口", httpMethod = "GET")
    //@RequiresPermissions("officer:safetyofficer:info")
    public R info(@PathVariable("id") Long id) {
        SafetyOfficerInfoDto dto = safetyOfficerService.queryOne(id);
        return R.ok().put("data", dto);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @ApiOperation(value = "安全员保存接口", httpMethod = "POST")
//    @RequiresPermissions("officer:safetyofficer:save")
    public R save(@RequestBody SafetyOfficerFrom from) {
        safetyOfficerService.saveSafetyOfficer(from);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @ApiOperation(value = "安全员更新接口", httpMethod = "POST")
//    @RequiresPermissions("officer:safetyofficer:update")
    public R update(@RequestBody SafetyOfficerFrom safetyOfficer) {
        SafetyOfficerEntity officer = safetyOfficer.getSafetyOfficer();
        officer.setModifyDt(new Date());
        officer.setModifyUserId(getUserId());
        safetyOfficerService.updateById(officer);
        PeopleBasicFactsEntity facts = safetyOfficer.getPeopleBasicFacts();
        facts.setModifyUserId(getUserId());
        facts.setModifyDt(new Date());
        facts.setId(safetyOfficer.getBasicId());
        factsService.updateById(facts);
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/deleteBatch")
    @ApiOperation(value = "安全员删除接口", httpMethod = "POST")
    public R deleteBatch(@RequestBody DeleteVo vo) {
        if (!validatePassword(vo.getValidatePassword())) {
            return R.error("用户密码不正确");
        }
        safetyOfficerService.removeByIds(Arrays.asList(vo.getIds()));
        return R.ok();
    }


    /**
     * 删除
     */
    @RequestMapping("/delete/{id}")
    @ApiOperation(value = "安全员删除接口", httpMethod = "POST")
    public R delete(@PathVariable Long id) {

        safetyOfficerService.removeById(id);
        return R.ok();
    }

}
