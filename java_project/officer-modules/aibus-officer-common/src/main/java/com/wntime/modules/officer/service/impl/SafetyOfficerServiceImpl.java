package com.wntime.modules.officer.service.impl;

import com.wntime.entity.AdminUser;
import com.wntime.modules.officer.dao.SafetyOfficerInfoDao;
import com.wntime.modules.officer.dto.SafetyOfficerInfoDto;
import com.wntime.modules.officer.entity.PeopleBasicFactsEntity;
import com.wntime.modules.officer.from.SafetyOfficerFrom;
import com.wntime.modules.officer.service.PeopleBasicFactsService;
import com.wntime.modules.sys.entity.AdminRole;
import com.wntime.modules.sys.form.AdminUserFrom;
import com.wntime.modules.sys.service.AdminRoleService;
import com.wntime.modules.sys.service.AdminUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.*;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wntime.common.utils.PageUtils;
import com.wntime.common.utils.Query;

import com.wntime.modules.officer.dao.SafetyOfficerDao;
import com.wntime.modules.officer.entity.SafetyOfficerEntity;
import com.wntime.modules.officer.service.SafetyOfficerService;
import org.springframework.transaction.annotation.Transactional;

import static com.wntime.common.utils.ShiroUtils.getUserId;


@Service("safetyOfficerService")
public class SafetyOfficerServiceImpl extends ServiceImpl<SafetyOfficerDao, SafetyOfficerEntity> implements SafetyOfficerService {

    @Autowired
    SafetyOfficerInfoDao infoDao;

    @Autowired
    PeopleBasicFactsService factsService;

    @Autowired
    AdminUserService userService;

    @Autowired
    private AdminRoleService adminRoleService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {

        QueryWrapper<SafetyOfficerInfoDto> wrapper = new QueryWrapper<>();
        if (params.containsKey("fullName")) {
            wrapper.like("ipbf.full_name", params.get("fullName"));
        }
        if (params.containsKey("mobileNumber")) {
            wrapper.like("iso.mobile_number", params.get("mobileNumber"));
        }
        wrapper.eq("iso.is_deleted", "0");
        IPage<SafetyOfficerInfoDto> page = infoDao.query(new Query<SafetyOfficerInfoDto>().getPage(params), wrapper);

        return new PageUtils(page);
    }

    @Override
    public SafetyOfficerInfoDto queryOne(Long id) {

        QueryWrapper<SafetyOfficerInfoDto> wrapper = new QueryWrapper<>();
        wrapper.eq("iso.id", id)
                .eq("iso.is_deleted", "0");
        SafetyOfficerInfoDto officerInfoDto = infoDao.query(wrapper);
        return officerInfoDto;
    }

    @Override
    public List<SafetyOfficerEntity> getPrimarySafetyOfficerList() {
        return getBaseMapper().getPrimarySafetyOfficerList();
    }

    /***
     * @Author Buxl
     * @Description 根据证件号查询安全员信息
     * @Date 15:02 2021/1/23
     * @Param [idNo]
     * @return com.wntime.modules.officer.entity.SafetyOfficerEntity
     **/
    @Override
    public SafetyOfficerEntity getSafetyOfficerByIdNo(String idNo){
        return getBaseMapper().getSafetyOfficerByIdNo(idNo);
    }
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveSafetyOfficer(SafetyOfficerFrom from) {
        PeopleBasicFactsEntity facts = from.getPeopleBasicFacts();
        facts.setCategory("安全员");
        facts.setCreateDt(new Date());
        facts.setCreateUserId(getUserId());
        facts.setIsDeleted("0");
        factsService.save(facts);
        facts = factsService.getOne(new QueryWrapper<>(facts));
        SafetyOfficerEntity entity = from.getSafetyOfficer();
        entity.setBasicId(facts.getId());
        entity.setCreateDt(new Date());
        entity.setCreateUserId(getUserId());
        entity.setIsDeleted(0);

        AdminUserFrom userFrom = new AdminUserFrom();
        userFrom.setUserName(from.getFullName());
        userFrom.setLoginName(from.getMobileNumber());
        userFrom.setPassword(from.getMobileNumber());

        AdminRole adminRole = adminRoleService.getOne(new QueryWrapper<AdminRole>().eq("role_code","ba"));
        if(adminRole != null){
            userFrom.setRoleIdList(Arrays.asList(adminRole.getRoleId()));
        }

        userFrom.setIsDeleted("0");
        userFrom.setMobile(from.getMobileNumber());
        userService.addUser(userFrom, getUserId());
        AdminUser user = userService.queryByLoginName(userFrom.getLoginName());

        entity.setLoginUserId(user.getUserId());
        save(entity);
    }

    @Override
    public boolean removeById(Serializable id) {
        SafetyOfficerEntity entity = getById(id);
        factsService.removeById(entity.getBasicId());
        userService.deleteUpdate(new Long[]{entity.getLoginUserId()},getUserId());
        baseMapper.deleteById(id);
        return false;
    }

    @Override
    public boolean removeByIds(Collection<? extends Serializable> idList) {
        for (Serializable id : idList) {
            removeById(id);
        }
        return true;
    }
}