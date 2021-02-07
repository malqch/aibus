package com.wntime.modules.officer.service.impl;

import com.wntime.common.utils.R;
import com.wntime.common.utils.ShiroUtils;
import com.wntime.entity.AdminUser;
import com.wntime.modules.officer.dao.DriverInfoDao;
import com.wntime.modules.officer.dto.DriverInfoDto;
import com.wntime.modules.officer.entity.PeopleBasicFactsEntity;
import com.wntime.modules.officer.from.DriverFrom;
import com.wntime.modules.officer.service.PeopleBasicFactsService;
import com.wntime.modules.sys.entity.AdminRole;
import com.wntime.modules.sys.form.AdminUserFrom;
import com.wntime.modules.sys.service.AdminRoleService;
import com.wntime.modules.sys.service.AdminUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wntime.common.utils.PageUtils;
import com.wntime.common.utils.Query;

import com.wntime.modules.officer.dao.DriverDao;
import com.wntime.modules.officer.entity.DriverEntity;
import com.wntime.modules.officer.service.DriverService;
import org.springframework.transaction.annotation.Transactional;

import static com.wntime.common.utils.ShiroUtils.getUserId;


@Service("driverService")
public class DriverServiceImpl extends ServiceImpl<DriverDao, DriverEntity> implements DriverService {

    @Autowired
    PeopleBasicFactsService factsService;

    @Autowired
    AdminUserService userService;

    @Autowired
    private AdminRoleService adminRoleService;

    @Autowired
    DriverInfoDao driverInfoDao;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        QueryWrapper<DriverInfoDto> wrapper = new QueryWrapper<>();
        if (params.containsKey("fullName")){
            wrapper.like("ipbf.full_name",params.get("fullName"));
        }
        if (params.containsKey("mobileNumber")){
            wrapper.like("idr.mobile_number",params.get("mobileNumber"));
        }
        IPage<DriverInfoDto> page = driverInfoDao.query(
                new Query<DriverInfoDto>().getPage(params), wrapper
        );

        return new PageUtils(page);
    }

    @Override
    public List<DriverEntity> getPrimaryDriverList() {
        return getBaseMapper().getPrimaryDriverList();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveDriverInfo(DriverFrom driver) {

        PeopleBasicFactsEntity basicFacts = driver.getPeopleBasicFacts();
        basicFacts.setCreateDt(new Date());
        basicFacts.setCreateUserId(getUserId());
        basicFacts.setIsDeleted("0");
        factsService.save(basicFacts);
        DriverEntity entity = driver.getDriver();
        entity.setBasicId(basicFacts.getId());
        entity.setCreateDt(new Date());
        entity.setCreateUserId(getUserId());

        AdminUserFrom userFrom = new AdminUserFrom();
        userFrom.setUserName(driver.getFullName());
        userFrom.setLoginName(driver.getMobileNumber());
        userFrom.setPassword(driver.getMobileNumber());
        userFrom.setMobile(driver.getMobileNumber());

        AdminRole adminRole = adminRoleService.getOne(new QueryWrapper<AdminRole>().eq("role_code","ba"));
        if(adminRole != null){
            userFrom.setRoleIdList(Arrays.asList(adminRole.getRoleId()));
        }

        userFrom.setIsDeleted("0");

        userService.addUser(userFrom, getUserId());
        AdminUser user = userService.queryByLoginName(userFrom.getLoginName());
        entity.setLoginUserId(user.getUserId());
        entity.setIsDeleted("0");
        save(entity);
    }

    @Override
    public DriverInfoDto queryOne(Long id) {
        QueryWrapper<DriverInfoDto> wrapper = new QueryWrapper<>();
        wrapper.eq("idr.id", id)
                .eq("idr.is_deleted", "0");
        return driverInfoDao.query(wrapper);
    }

    /***
     * @Author Buxl
     * @Description 根据证件号查询司机信息
     * @Date 14:13 2021/1/23
     * @Param [idNo]
     * @return com.wntime.modules.officer.entity.DriverEntity
     **/
    @Override
    public DriverEntity getDriverByIdNo(String idNo) {
        return getBaseMapper().getDriverByIdNo(idNo);
    }


}