

package com.wntime.modules.sys.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wntime.common.utils.DateUtils;
import com.wntime.datasource.annotation.DataSource;
import com.wntime.modules.sys.dao.AdminPositionAuthDao;
import com.wntime.modules.sys.entity.AdminPositionAuth;
import com.wntime.modules.sys.form.PositionAuthFrom;
import com.wntime.modules.sys.service.AdminPositionAuthService;
import com.wntime.modules.sys.vo.AdminPositionAuthInfoVo;
import com.wntime.modules.sys.vo.AdminPositionAuthVo;
import com.wntime.modules.sys.vo.BusiObjectVo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;


/**
 * 角色与菜单对应关系
 */
@Service("adminPositionAuthService")
public class AdminPositionAuthServiceImpl extends ServiceImpl<AdminPositionAuthDao, AdminPositionAuth> implements AdminPositionAuthService {

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveOrUpdate(Long positionId, List<PositionAuthFrom> authIdList, Long operationId) {
        //先删除岗位和授权
        deleteBatch(new Long[]{positionId});

        if (authIdList.size() == 0) {
            return;
        }

        //保存岗位和授权
        for (PositionAuthFrom auth : authIdList) {
            AdminPositionAuth adminPositionAuth = new AdminPositionAuth();
            adminPositionAuth.setPositionId(positionId);
            adminPositionAuth.setCreateUserId(operationId);
            adminPositionAuth.setCreateDt(DateUtils.getTimestamp());
            adminPositionAuth.setIsDeleted("0");
            adminPositionAuth.setCompanyId(auth.getOrganizeId());
            this.save(adminPositionAuth);
        }
    }

    @Override
    public List<AdminPositionAuthInfoVo> queryAuthIdList(Long postitionId) {
        return baseMapper.queryAuthIdList(postitionId);
    }

    @Override
    public int deleteBatch(Long[] positionIds) {
        return baseMapper.deleteBatch(positionIds);
    }

    @Override
    public int deleteUpdateByPositionId(Long[] positionIds, Long userId) {
        return this.baseMapper.deleteUpdateByPositionId(positionIds, userId, DateUtils.getTimestamp());
    }

    @Override
    public int deleteUpdateByAuthId(Long[] authIds, Long userId) {
        return this.baseMapper.deleteUpdateByAuthId(authIds, userId, DateUtils.getTimestamp());
    }

    @Override
    public List<AdminPositionAuthVo> queryPositionAuthWithUserId(Long positionId) {

        List<AdminPositionAuthVo> list = null;
        if (positionId == null) {
            list = this.baseMapper.queryOrganizeList();
        } else {
            list = this.baseMapper.queryPositionAuthWithPositionId(positionId);
        }
        return list;
    }

    @Override
    public List<AdminPositionAuthVo> queryPositionAuthWithClique(Map<String, Object> params) {
        if(params.get("isClique") != null){
            params.put("isClique",Integer.parseInt(String.valueOf(params.get("isClique"))));
        }
        List<AdminPositionAuthVo> list = this.baseMapper.queryOrganizeListWithClique(params);
        return list;
    }

    @Override
    public List<Long> queryAreaOrgId(Set<Long> positionId, int linkType) {
        return this.baseMapper.queryAreaOrgIdByPositionIdAndLinkType(positionId, linkType);
    }

}
