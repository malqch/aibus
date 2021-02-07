package com.wntime.modules.officer.controller;

import java.util.Arrays;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wntime.modules.officer.entity.PeopleBasicFactsEntity;
import com.wntime.modules.officer.service.PeopleBasicFactsService;
import com.wntime.common.utils.PageUtils;
import com.wntime.common.utils.R;



/**
 * @desc 人员基础信息
 *
 * @author raute
 * @email admin@wntime.com
 * @date 2021-01-16 14:43:43
 */
@RestController
@RequestMapping("officer/peoplebasicfacts")
public class PeopleBasicFactsController {
    @Autowired
    private PeopleBasicFactsService peopleBasicFactsService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("officer:peoplebasicfacts:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = peopleBasicFactsService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("officer:peoplebasicfacts:info")
    public R info(@PathVariable("id") Long id){
		PeopleBasicFactsEntity peopleBasicFacts = peopleBasicFactsService.getById(id);

        return R.ok().put("peopleBasicFacts", peopleBasicFacts);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("officer:peoplebasicfacts:save")
    public R save(@RequestBody PeopleBasicFactsEntity peopleBasicFacts){
		peopleBasicFactsService.save(peopleBasicFacts);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("officer:peoplebasicfacts:update")
    public R update(@RequestBody PeopleBasicFactsEntity peopleBasicFacts){
		peopleBasicFactsService.updateById(peopleBasicFacts);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("officer:peoplebasicfacts:delete")
    public R delete(@RequestBody Long[] ids){
		peopleBasicFactsService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
