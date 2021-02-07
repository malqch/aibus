package com.wntime.modules.officer.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.wntime.common.BeanMerged;
import com.wntime.modules.officer.entity.PeopleBasicFactsEntity;
import com.wntime.modules.officer.entity.SafetyOfficerEntity;
import com.wntime.modules.officer.from.AuthorizeFrom;
import com.wntime.modules.officer.service.PeopleBasicFactsService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wntime.modules.officer.entity.AuthorizeEntity;
import com.wntime.modules.officer.service.AuthorizeService;
import com.wntime.common.utils.PageUtils;
import com.wntime.common.utils.R;



/**
 * @desc 授权人
 *
 * @author raute
 * @email admin@wntime.com
 * @date 2021-01-16 14:43:43
 */
@RestController
@RequestMapping("officer/authorize")
public class AuthorizeController {
    @Autowired
    private AuthorizeService authorizeService;

    @Autowired
    PeopleBasicFactsService factsService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("officer:authorize:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = authorizeService.queryPage(params);
        List<Map<String, Object>> result = new ArrayList<>();
        for (Object o : page.getList()) {
            AuthorizeEntity entity = (AuthorizeEntity) o;
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
    @RequiresPermissions("officer:authorize:info")
    public R info(@PathVariable("id") Long id){
		AuthorizeEntity authorize = authorizeService.getById(id);
        PeopleBasicFactsEntity factsEntity = factsService.getById(authorize.getBasicId());
        return R.ok().put("authorize", BeanMerged.merge(authorize,factsEntity));
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("officer:authorize:save")
    public R save(@RequestBody AuthorizeFrom from){
        PeopleBasicFactsEntity facts = from.getPeopleBasicFacts();
        UpdateWrapper<PeopleBasicFactsEntity> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id_no",facts.getIdNo());
        facts.setCategory("被授权人");
        factsService.saveOrUpdate(facts, updateWrapper);
        facts = factsService.getOne(new QueryWrapper<>(facts));

        AuthorizeEntity entity = from.getAuthorize();

        entity.setBasicId(facts.getId());
        authorizeService.save(entity);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("officer:authorize:update")
    public R update(@RequestBody AuthorizeFrom authorize){
        authorizeService.updateById(authorize.getAuthorize());
        PeopleBasicFactsEntity facts = authorize.getPeopleBasicFacts();
        facts.setId(authorize.getBasicId());
        factsService.updateById(facts);
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("officer:authorize:delete")
    public R delete(@RequestBody Long[] ids){
		authorizeService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
