

package com.wntime.modules.sys.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wntime.common.utils.DateUtils;
import com.wntime.common.utils.MapUtils;
import com.wntime.entity.AdminUser;
import com.wntime.modules.sys.dao.AdminAuthObjectDao;
import com.wntime.modules.sys.dao.AdminUserPositionDao;
import com.wntime.modules.sys.entity.AdminAuthObject;
import com.wntime.modules.sys.entity.AdminUserPosition;
import com.wntime.modules.sys.entity.AdminUserRole;
import com.wntime.modules.sys.form.AdminUserFrom;
import com.wntime.modules.sys.service.AdminUserPositionService;
import com.wntime.modules.sys.vo.AdminUserPositionVo;
import com.wntime.modules.sys.vo.UserPositionVo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toCollection;


/**
 * 用户与职位对应关系
 */
@Service("adminUserPositionService")
public class AdminUserPositionServiceImpl extends ServiceImpl<AdminUserPositionDao, AdminUserPosition> implements AdminUserPositionService {

    @Resource
    private AdminAuthObjectDao adminAuthObjectDao;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveOrUpdate(Long operationId, Long userId, List<AdminUserFrom.PositionObjectId> positionIdList) {
        //先删除用户与岗位关系
        Timestamp time = DateUtils.getTimestamp();
        this.baseMapper.deleteUpdateByUserId(new Long[]{userId}, operationId, time);

        if (positionIdList == null || positionIdList.size() == 0) {
            return;
        }

        //增加新岗位
        Optional.of(positionIdList).ifPresent(pList -> pList.forEach(item -> {
            AdminUserPosition adminUserPosition = new AdminUserPosition();
            adminUserPosition.setUserId(userId);
            adminUserPosition.setPositionId(item.getPositionId());
            adminUserPosition.setIsDeleted("0");
            adminUserPosition.setCreateDt(time);
            adminUserPosition.setCreateUserId(operationId);
            this.save(adminUserPosition);
        }));
    }

    @Override
    public List<Long> queryPositionIdList(Long userId) {
        return baseMapper.queryPositionIdList(userId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteBatch(Long[] roleIds) {
        return baseMapper.deleteBatch(roleIds);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteUpdateByUserId(Long[] userIds, Long operationId) {
        Timestamp modifyDt = DateUtils.getTimestamp();
        return baseMapper.deleteUpdateByUserId(userIds, operationId, modifyDt);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteUpdateByPositionId(Long[] roleIds, Long operationId) {
        Timestamp modifyDt = DateUtils.getTimestamp();
        return baseMapper.deleteUpdateByPositionId(roleIds, operationId, modifyDt);

    }

    @Override
    public List<AdminUserPositionVo> queryUserPosition(long userId) {
        return this.baseMapper.queryPositionWithUserId(userId);
    }

    @Override
    public int queryPositionUsedCount(long positionId) {
        return this.baseMapper.queryCountByPositionId(positionId);
    }

    @Override
    public List<AdminUser> getByOrderSells(Long companyId) {
        return this.baseMapper.getByOrderSells(companyId);
    }

}
