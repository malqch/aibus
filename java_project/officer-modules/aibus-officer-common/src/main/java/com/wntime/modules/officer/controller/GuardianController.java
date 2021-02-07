package com.wntime.modules.officer.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.wntime.common.BeanMerged;
import com.wntime.modules.officer.entity.AuthorizeEntity;
import com.wntime.modules.officer.entity.PeopleBasicFactsEntity;
import com.wntime.modules.officer.entity.SafetyOfficerEntity;
import com.wntime.modules.officer.from.GuardianFrom;
import com.wntime.modules.officer.service.PeopleBasicFactsService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wntime.modules.officer.entity.GuardianEntity;
import com.wntime.modules.officer.service.GuardianService;
import com.wntime.common.utils.PageUtils;
import com.wntime.common.utils.R;



/**
 * @desc 监护人
 *
 * @author raute
 * @email admin@wntime.com
 * @date 2021-01-16 14:43:43
 */
@RestController
@RequestMapping("officer/guardian")
public class GuardianController {
    @Autowired
    private GuardianService guardianService;

    @Autowired
    PeopleBasicFactsService factsService;

    /**
     * 列表
     */
    @RequestMapping("/list")
//    @RequiresPermissions("officer:guardian:list")
    public R list(@RequestBody Map<String, Object> params){
        PageUtils page = guardianService.queryPage(params);
        List<Map<String, Object>> result = new ArrayList<>();
        for (Object o : page.getList()) {
            GuardianEntity entity = (GuardianEntity) o;
            PeopleBasicFactsEntity factsEntity = factsService.getById(entity.getBasicId());
            Map<String, Object> merge = BeanMerged.merge(entity, factsEntity);
            result.add(merge);
        }
        page.setList(result);
        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
//    @RequiresPermissions("officer:guardian:info")
    public R info(@PathVariable("id") Long id){
		GuardianEntity guardian = guardianService.getById(id);

        return R.ok().put("guardian", guardian);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("officer:guardian:save")
    public R save(@RequestBody GuardianFrom from){
        PeopleBasicFactsEntity facts = from.getPeopleBasicFacts();
        UpdateWrapper<PeopleBasicFactsEntity> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id_no",facts.getIdNo());
        facts.setCategory("监护人");
        factsService.saveOrUpdate(facts, updateWrapper);
        facts = factsService.getOne(new QueryWrapper<>(facts));

        GuardianEntity entity = from.getGuardian();

        entity.setBasicId(facts.getId());
        guardianService.save(entity);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("officer:guardian:update")
    public R update(@RequestBody GuardianFrom from){
        guardianService.updateById(from.getGuardian());
        PeopleBasicFactsEntity facts = from.getPeopleBasicFacts();
        facts.setId(from.getBasicId());
        factsService.updateById(facts);
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("officer:guardian:delete")
    public R delete(@RequestBody Long[] ids){
		guardianService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
