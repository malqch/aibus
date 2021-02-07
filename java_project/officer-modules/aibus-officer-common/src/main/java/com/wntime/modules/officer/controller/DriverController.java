package com.wntime.modules.officer.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.wntime.common.utils.PageUtils;
import com.wntime.common.utils.R;
import com.wntime.controller.AbstractController;
import com.wntime.modules.officer.dto.DriverInfoDto;
import com.wntime.modules.officer.entity.DriverEntity;
import com.wntime.modules.officer.entity.PeopleBasicFactsEntity;
import com.wntime.modules.officer.from.DriverFrom;
import com.wntime.modules.officer.service.DriverService;
import com.wntime.modules.officer.service.PeopleBasicFactsService;
import com.wntime.modules.officer.vo.DeleteVo;
import com.wntime.modules.sys.service.AdminUserService;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;


/**
 * 驾驶员信息表
 *
 * @author raute
 * @email admin@wntime.com
 * @date 2021-01-16 14:43:43
 */
@RestController
@RequestMapping("officer/driver")
public class DriverController extends AbstractController {
    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private DriverService driverService;

    @Autowired
    PeopleBasicFactsService peopleBasicFactsService;

    @Autowired
    AdminUserService userService;

    /**
     * 列表
     */
    @RequestMapping("/list")
//    @RequiresPermissions("officer:driver:list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = driverService.queryPage(params);
        return R.ok().put("page", page);
    }

    @GetMapping("/selection")
    @ApiOperation(value = "选择司机下拉列表", httpMethod = "GET")
    public R getPrimaryDriverList(@RequestParam Map<String, Object> params) {
        logger.info("选择司机下拉列表,参数:" + JSON.toJSONString(params));
        List<DriverEntity> list = driverService.getPrimaryDriverList();
        return R.ok().put("list", list);
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id) {
        DriverInfoDto driver = driverService.queryOne(id);
        return R.ok().put("data", driver);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
//    @RequiresPermissions("officer:driver:save")
    public R save(@RequestBody DriverFrom driver) {
        driverService.saveDriverInfo(driver);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
//    @RequiresPermissions("officer:driver:update")
    public R update(@RequestBody DriverFrom driver) {
        PeopleBasicFactsEntity facts = driver.getPeopleBasicFacts();
        facts.setId(driver.getBasicId());
        facts.setModifyDt(new Date());
        facts.setModifyUserId(getUserId());
        facts.setIsDeleted("0");
        peopleBasicFactsService.saveOrUpdate(facts,new UpdateWrapper<>(facts));
        DriverEntity driverEntity = driver.getDriver();
        driverEntity.setBasicId(facts.getId());
        driverEntity.setModifyDt(new Date());
        driverEntity.setModifyUserId(getUserId());
        driverEntity.setIsDeleted("0");
        driverService.updateById(driverEntity);
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/deleteBatch")
    public R deleteBatch(@RequestBody DeleteVo vo) {

        if (validatePassword(vo.getValidatePassword())) {
            Collection<DriverEntity> driverEntities = driverService.listByIds(Arrays.asList(vo.getIds()));
            for (DriverEntity entity : driverEntities) {
                Long basicId = entity.getBasicId();
                PeopleBasicFactsEntity factsEntity = peopleBasicFactsService.getById(basicId);
                peopleBasicFactsService.removeById(basicId);
                driverService.removeById(entity.getId());
                userService.removeById(entity.getLoginUserId());
            }
        } else {
            return R.error("用户密码错误");
        }
        return R.ok();
    }

    @RequestMapping("/delete/{id}")
//    @RequiresPermissions("officer:driver:delete")
    public R delete(@PathVariable Long id) {
        DriverEntity byId = driverService.getById(id);
        if (null != byId) {
            Long basicId = byId.getBasicId();
            PeopleBasicFactsEntity factsEntity = peopleBasicFactsService.getById(basicId);
            peopleBasicFactsService.removeById(basicId);
            driverService.removeById(id);
            userService.removeById(byId.getLoginUserId());
        }
        return R.ok();
    }
}
