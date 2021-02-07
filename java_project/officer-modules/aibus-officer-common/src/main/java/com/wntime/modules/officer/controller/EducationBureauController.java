package com.wntime.modules.officer.controller;

import java.util.Arrays;
import java.util.Map;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wntime.modules.officer.entity.EducationBureauEntity;
import com.wntime.modules.officer.service.EducationBureauService;
import com.wntime.common.utils.PageUtils;
import com.wntime.common.utils.R;


/**
 * @author raute
 * @desc 教育局
 * @email admin@wntime.com
 * @date 2021-01-16 14:43:43
 */
@RestController
@RequestMapping("officer/educationbureau")
@Api(value = "教育局接口", tags = {"教育局"})
public class EducationBureauController {
    @Autowired
    private EducationBureauService educationBureauService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @ApiOperation(value = "教育局列表", httpMethod = "POST")
    public R list(@RequestParam(required = false) Map<String, Object> params) {
        PageUtils page = educationBureauService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("officer:educationbureau:info")
    @ApiOperation(value = "教育局信息", httpMethod = "GET")
    public R info(@PathVariable("id") Long id) {
        EducationBureauEntity educationBureau = educationBureauService.getById(id);

        return R.ok().put("educationBureau", educationBureau);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("officer:educationbureau:save")
    public R save(@RequestBody EducationBureauEntity educationBureau) {
        educationBureauService.save(educationBureau);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("officer:educationbureau:update")
    public R update(@RequestBody EducationBureauEntity educationBureau) {
        educationBureauService.updateById(educationBureau);

        return R.ok();
    }

//    /**
//     * 删除
//     */
//    @RequestMapping("/delete")
//    @RequiresPermissions("officer:educationbureau:delete")
//    public R delete(@RequestBody Long[] ids) {
//        educationBureauService.removeByIds(Arrays.asList(ids));
//
//        return R.ok();
//    }

}
