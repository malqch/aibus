package com.wntime.service.service.impl;

import com.wntime.common.exception.RRException;
import com.wntime.common.utils.R;
import com.wntime.common.validator.Assert;
import com.wntime.service.common.service.AreaInfoService;
import com.wntime.service.entity.InfoBusTypeEntity;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wntime.common.utils.PageUtils;
import com.wntime.common.utils.Query;
import com.wntime.service.dao.InfoAreaDao;
import com.wntime.service.entity.InfoAreaEntity;
import com.wntime.service.service.InfoAreaService;
import org.springframework.transaction.annotation.Transactional;


@Service("infoAreaService")
public class InfoAreaServiceImpl extends ServiceImpl<InfoAreaDao, InfoAreaEntity> implements InfoAreaService, AreaInfoService {

    @Override
    public List<InfoAreaEntity> queryList(Map<String, Object> params) {
        Map<String, Object> map = new HashMap<>();
        return this.baseMapper.queryList(map);
    }

    @Override
    public List<InfoAreaEntity> getChildArea(Long id) {
        return this.baseMapper.getChildArea(id);
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean update(InfoAreaEntity infoAreaEntity) {
        return this.updateById(infoAreaEntity);
    }

    @Override
    public boolean delById(long id) {
        return this.baseMapper.delById(id);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean deleteBatch(String[] ids) {
        for (String id : ids) {
            if (this.baseMapper.isEnableDelete(Long.parseLong(id)) > 1) throw new RRException("不能删除有下级的数据");
            this.baseMapper.delById(Long.parseLong(id));
        }
        return true;
    }

    @Override
    public InfoAreaEntity getDetailById(Long id) {
        return this.baseMapper.getDetailById(id);
    }

    @Override
    public List queryArea(Map<String, Object> params) {
        return this.baseMapper.queryArea(params);
    }

    @Override
    public boolean isEnableDelete(Long id) {
        return this.baseMapper.isEnableDelete(id) <= 0;
    }

    @Override
    public boolean isEnableInsert(Long areaId, Long parentAreaId, String areaName) {
        return this.baseMapper.isEnableInsert(areaId, parentAreaId, areaName) <= 0;
    }

    @Override
    public boolean isEnableStop(Long id) {
        return this.baseMapper.isEnableStop(id) <= 0;
    }

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<InfoAreaEntity> page = this.page(
                new Query<InfoAreaEntity>().getPage(params),
                new QueryWrapper<InfoAreaEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public List<InfoAreaEntity> getListByParentAreaId(Long parentAreaId) {
        return getBaseMapper().getListByParentAreaId(parentAreaId);
    }

    @Override
    public List<InfoAreaEntity> getListByUserAndParentAreaId(Long parentAreaId, List<Long> companyIdList) {
        Assert.isEmpty(companyIdList,"用户权限公司为空");
        List<Long> totalAreaIdList = new ArrayList<>(10);
        List<InfoAreaEntity> areaList = getBaseMapper().getAreaByCompanyList(companyIdList);
        totalAreaIdList.addAll(areaList.stream().map(InfoAreaEntity::getAreaId).collect(Collectors.toList()));
        for (InfoAreaEntity area : areaList) {
            totalAreaIdList.addAll(getParentAreaList(area).stream().map(InfoAreaEntity::getAreaId).collect(Collectors.toList()));
        }
        return getBaseMapper().getAreaListByUserAndParentAreaId(parentAreaId, totalAreaIdList);
    }

    public String getAreaFullName(Long areaId){
        InfoAreaEntity area = getBaseMapper().getByAreaId(areaId);
        List<InfoAreaEntity> list = getParentAreaList(area);
        Collections.reverse(list);
        list.add(area);
        Optional<String> fullName = list.stream().map(InfoAreaEntity::getAreaName).reduce((a, b) -> a + " " + b);
        return fullName.orElse("");
    }

    public List<InfoAreaEntity> getParentAreaList(InfoAreaEntity area){
        List<InfoAreaEntity> list = new ArrayList<>();
        return getParentAreaList(area,list);
    }
    public List<InfoAreaEntity> getParentAreaList(InfoAreaEntity area,List<InfoAreaEntity> list){
        InfoAreaEntity parentArea = getBaseMapper().getByAreaId(area.getParentAreaId());
        if(parentArea != null){
            list.add(parentArea);
            getParentAreaList(parentArea, list);
        }
        return list;
    }

    @Override
    public List<Long> getSubAreaIdList(Long areaId) {
        InfoAreaEntity area = new InfoAreaEntity();
        area.setAreaId(areaId);
        return getSubAreaList(area).stream().map(InfoAreaEntity::getAreaId).collect(Collectors.toList());
    }

    public List<InfoAreaEntity> getSubAreaList(InfoAreaEntity area){
        List<InfoAreaEntity> list = new ArrayList<>();
        return getSubAreaList(area,list);
    }

    private List<InfoAreaEntity> getSubAreaList(InfoAreaEntity area,List<InfoAreaEntity> list){
        List<InfoAreaEntity> subAreaList = getBaseMapper().getListByParentAreaId(area.getAreaId());
        if(!subAreaList.isEmpty()){
            list.addAll(subAreaList);
            for (InfoAreaEntity subArea : subAreaList) {
                getSubAreaList(subArea, list);
            }
        }
        return list;
    }
    @Override
    public Long getInfoAreaIdByName(String areaName) {
        QueryWrapper<InfoAreaEntity> QueryWrapperType = new QueryWrapper<InfoAreaEntity>()
                .eq("is_deleted", 0)
                .eq("is_enabled", 1)
                .eq("area_name ",areaName);
        InfoAreaEntity infoAreaEntity = this.getOne(QueryWrapperType);
        if(infoAreaEntity == null){
            return 0L;
        }
        return infoAreaEntity.getAreaId();
    }

    @Override
    public InfoAreaEntity getByAreaId(Long areaId) {
        return getBaseMapper().getByAreaId(areaId);
    }

    @Override
    public String getAreaNameByAreaId(Long areaId) {
        return getBaseMapper().getAreaNameByAreaId(areaId);
    }
}
