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

import com.wntime.modules.officer.entity.SchoolEntity;
import com.wntime.modules.officer.service.SchoolService;
import com.wntime.common.utils.PageUtils;
import com.wntime.common.utils.R;



/**
 * @desc 学校信息
 *
 * @author raute
 * @email admin@wntime.com
 * @date 2021-01-16 14:43:43
 */
@RestController
@RequestMapping("officer/school")
@Api(value = "学校接口", tags = {"学校"})
public class SchoolController {
    @Autowired
    private SchoolService schoolService;

    /**
     * 列表
     */
    @RequestMapping("/list")
//    @RequiresPermissions("officer:school:list")
    @ApiOperation(value = "学校列表", httpMethod = "POST")
    public R list(@RequestParam(required = false) Map<String, Object> params){
        PageUtils page = schoolService.queryPage(params);
        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
//    @RequiresPermissions("officer:school:info")
    @ApiOperation(value = "学校详细信息", httpMethod = "GET")
    public R info(@PathVariable("id") Long id){
		SchoolEntity school = schoolService.getById(id);
        return R.ok().put("school", school);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @ApiOperation(value = "保存一个学校", httpMethod = "POST")
//    @RequiresPermissions("officer:school:save")
    public R save(@RequestBody SchoolEntity school){
		schoolService.save(school);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody SchoolEntity school){
		schoolService.updateById(school);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids){
		schoolService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
