package com.wntime.customer.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wntime.common.utils.Constant;
import com.wntime.common.utils.DateUtils;
import com.wntime.common.utils.PageUtils;
import com.wntime.common.validator.Assert;
import com.wntime.customer.dao.InfoBusCompanyDao;
import com.wntime.customer.entity.InfoBusCompanyEntity;
import com.wntime.customer.service.InfoBusCompanyService;
import com.wntime.customer.vo.*;
import com.wntime.datasource.annotation.DataSource;
import com.wntime.service.common.service.AreaInfoService;
import com.wntime.service.common.service.BusCompanyService;
import com.wntime.service.common.vo.AreaVo;
import com.wntime.service.common.vo.BusCompanyBaseInfoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;


@Service("infoBusCompanyService")
public class InfoBusCompanyServiceImpl extends ServiceImpl<InfoBusCompanyDao, InfoBusCompanyEntity> implements InfoBusCompanyService, BusCompanyService {

    @Autowired
    private AreaInfoService areaInfoService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        int currPage = 1;
        int pageSize = 20;
        if (params.get("page") != null) {
            currPage = Integer.parseInt((String) params.get("page"));
        }
        if (params.get("page") != null) {
            pageSize = Integer.parseInt((String) params.get("limit"));
        }

        Page<InfoBusCompanyVo> page = new Page<>(currPage, pageSize);
        params.put("page", page);
        return new PageUtils(page.setRecords(this.baseMapper.queryPageList(params)));
    }

    @Override
    public InfoBusCompanyVo getDetailById(Long id) {
        return this.baseMapper.getDetailById(id);
    }

    @Override
    public void save(InfoBusCompanyEntity infoBusCompanyEntity, Long userId) {
        if(infoBusCompanyEntity.getIsClique() == 1){
            infoBusCompanyEntity.setParentCompanyId(null);
        }
        infoBusCompanyEntity.setCompanyId(null);
        infoBusCompanyEntity.setIsDeleted(0);
        infoBusCompanyEntity.setCreatedDate(DateUtils.getTimestamp());
        infoBusCompanyEntity.setCreatedBy(userId);
        this.save(infoBusCompanyEntity);
    }

    @Override
    public void updateById(InfoBusCompanyEntity infoBusCompanyEntity, Long userId) {
        if(infoBusCompanyEntity.getIsClique() == 1){
            infoBusCompanyEntity.setParentCompanyId(null);
        }
        infoBusCompanyEntity.setModifiedBy(userId);
        infoBusCompanyEntity.setModifiedDate(DateUtils.getTimestamp());
        this.updateById(infoBusCompanyEntity);
    }

    @Override
    public void delById(Long id, Long userId) {
        InfoBusCompanyEntity infoBusCompanyEntity = new InfoBusCompanyEntity();
        infoBusCompanyEntity.setCompanyId(id);
        infoBusCompanyEntity.setIsDeleted(1);
        infoBusCompanyEntity.setModifiedBy(userId);
        infoBusCompanyEntity.setModifiedDate(DateUtils.getTimestamp());
        this.updateById(infoBusCompanyEntity);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteBatch(String[] ids, Long userId) {
        for (String id : ids) {
            this.delById(Long.parseLong(id), userId);
        }
    }

    @Override
    public InfoBusCompanyEntityWithAreaVO getByIdWithArea(Serializable id) {
        return getBaseMapper().selectByIdWithArea(id);
    }

    @Override
    public List<StatisticsResultVo> getCompanyStatistics(Map<String, Object> params){
        Long areaId = Long.parseLong(String.valueOf(params.get("areaId")));
        List<Long> companyIdList = (List<Long>)params.get("companyIdList");
        // 获取该地区、子地区下所有公司
        List<InfoBusCompanyVo> subList = getCompanyListByAreaId(params);
        companyIdList.clear();
        companyIdList.addAll(subList.stream().map(InfoBusCompanyVo::getCompanyId).collect(Collectors.toList()));
        List<StatisticsResultVo> statisticsResultVos = new ArrayList<>();
        // 查询客户数量
        StatisticsResultVo statisticsResultVoCom = new StatisticsResultVo();
        statisticsResultVoCom.setName("当前客户数");
//        statisticsResultVoCom.setValue( String.valueOf(getBaseMapper().getCompanyCountByAreaId(areaId,companyIdList)));
        statisticsResultVoCom.setValue(String.valueOf(subList == null ? 0 : subList.size()));
        statisticsResultVos.add(statisticsResultVoCom);

        // 查询汽车交付量
        StatisticsResultVo statisticsResultVoBus = new StatisticsResultVo();
        statisticsResultVoBus.setName("汽车交付量");
        statisticsResultVoBus.setValue(String.valueOf(getBaseMapper().getBusDeliveryCountByAreaId(companyIdList)));
        statisticsResultVos.add(statisticsResultVoBus);

        // 查询当前地区
        StatisticsResultVo statisticsResultVoArea = new StatisticsResultVo();
        statisticsResultVoArea.setName("当前地区");
        statisticsResultVoArea.setValue(areaInfoService.getAreaFullName(areaId));
        statisticsResultVos.add(statisticsResultVoArea);
        return statisticsResultVos;
    }

    @Override
    public List<InfoBusCompanyVo> getCompanyStatListByAreaId(Map<String, Object> params) {
        List<Long> companyIdList = (List<Long>)params.get("companyIdList");
        Long areaId = Long.parseLong(String.valueOf(params.get("areaId")));
        Assert.isNull(areaId,"地区不能为空");
        List<Long> areaIdList = areaInfoService.getSubAreaIdList(areaId);
        areaIdList.add(areaId);
        List<InfoBusCompanyVo> list =getBaseMapper().getCompanyStatListByAreaId(areaIdList,companyIdList);
        return list;
    }

    @Override
    public List<InfoBusCompanyVo> getCompanyListByAreaId(Map<String, Object> params) {
        Long areaId = Long.parseLong(String.valueOf(params.get("areaId")));
        Assert.isNull(areaId,"地区不能为空");
        List<Long> areaIdList = areaInfoService.getSubAreaIdList(areaId);
        areaIdList.add(areaId);
        List<Long> companyIdList = (List<Long>)params.get("companyIdList");
        List<InfoBusCompanyVo> list =getBaseMapper().getCompanyListByAreaIdList(areaIdList,companyIdList);
        return list;
    }

    @Override
    public List<InfoBusCompanyEntity> getCompanyListAll(Map<String, Object> params) {
        List<Long> schoolIdList = (List<Long>)params.get("schoolIdList");
        QueryWrapper<InfoBusCompanyEntity> QueryWrapper = new QueryWrapper<InfoBusCompanyEntity>()
                .eq("is_deleted", 0).eq("is_enabled",1)
                .in("school_id",schoolIdList);
        return getBaseMapper().selectList(QueryWrapper);
    }

    @Override
    public List<InfoBusCompanyStatVo> getCompanyBusTypeDeliveryListByAreaId(Map<String, Object> params) {
        Long areaId = Long.parseLong(String.valueOf(params.get("areaId")));
        List<Long> companyIdList = (List<Long>)params.get("companyIdList");
        Assert.isNull(areaId,"地区不能为空");
        List<Long> areaIdList = areaInfoService.getSubAreaIdList(areaId);
        areaIdList.add(areaId);
        // 查询客户列表
        List<InfoBusCompanyStatVo> list =getBaseMapper().getCompanyBaseInfoListByAreaId(areaIdList,companyIdList);
        // 查询各车型订单总量、交付量
        if(list != null && list.size() > 0){
            for(InfoBusCompanyStatVo infoBusCompanyStatVo:list){
                // 订单总量,交付量
//                params.put("companyId",infoBusCompanyStatVo.getCompanyId());
                infoBusCompanyStatVo.setOrderData(getBaseMapper().getDeliveryStatByBusTypeAndAreaId(infoBusCompanyStatVo.getCompanyId()));
            }
        }

        return list;
    }

    @Override
    public List<InfoBusCompanyStatVo> getCompanyBusTypeOutDateListByAreaId(Map<String, Object> params) {
        Long areaId = Long.parseLong(String.valueOf(params.get("areaId")));
        List<Long> companyIdList = (List<Long>)params.get("companyIdList");
        List<Long> areaIdList = areaInfoService.getSubAreaIdList(areaId);
        areaIdList.add(areaId);
        // 查询客户列表
        List<InfoBusCompanyStatVo> list =getBaseMapper().getCompanyBaseInfoListByAreaId(areaIdList,companyIdList);
        // 查询各车型订单交付总量、出保量
        if(list != null && list.size() > 0){
            for(InfoBusCompanyStatVo infoBusCompanyStatVo:list){
                // 订单交付总量,出保量
//                params.put("companyId",infoBusCompanyStatVo.getCompanyId());
                infoBusCompanyStatVo.setOrderData(getBaseMapper().getOutDateStatByBusTypeAndAreaId(infoBusCompanyStatVo.getCompanyId()));
            }
        }

        return list;
    }

    @Override
    public List<InfoCompanyBusTypeStatVo> getCompanyBusTypeDeliveryListByCompanyId(Map<String, Object> params) {
        Long companyId = Long.parseLong(String.valueOf(params.get("companyId")));
        // 查询各车型订单总量、交付量
        List<InfoCompanyBusTypeStatVo> list =getBaseMapper().getDeliveryStatByBusTypeAndAreaId(companyId);
        return list;
    }

    @Override
    public List<InfoCompanyBusTypeStatVo> getCompanyBusTypeOutDateListByCompanyId(Map<String, Object> params) {
        Long companyId = Long.parseLong(String.valueOf(params.get("companyId")));
        // 查询交付量、出保量
        List<InfoCompanyBusTypeStatVo> list =getBaseMapper().getOutDateStatByBusTypeAndAreaId(companyId);
        return list;
    }

    @Override
    public ValidCompanyVO getValidCompanyListByUserId(Long userId) {
        return getBaseMapper().getValidCompanyListByUserId(userId);
    }

    @Override
    @DataSource("read")
    /**
     * 查询公司的子公司 如果是子公司则返回自身
     * @param companyId
     * @return
     */
    public List<BusCompanyBaseInfoVo> queryCompanyList(long companyId) {
        InfoBusCompanyEntity company = getBaseMapper().selectById(companyId);
        //如果公司为空 或者公司已删除 或公司禁用
        if(company==null || company.getIsDeleted()== Constant.Deleted.DELETED.getValue() || company.getIsEnabled()==Constant.Enabled.DISABLE.getValue()){
            return Collections.emptyList();
        }
        if(company.getIsClique()==1){
            return getBaseMapper().queryChildrenCompanyList(companyId);
        }else {
            BusCompanyBaseInfoVo vo=new BusCompanyBaseInfoVo();
            vo.setCompanyId(company.getCompanyId());
            vo.setCompanyName(company.getCompanyName());
            return Arrays.asList(vo);
        }
    }

    /**
     * 查询登录用户岗位下公司
     * 管理员：所有
     * 普通用户：岗位下公司
     * @return
     */
    @Override
    public List<Long> getUserCompanyIdList(Long userId) {
        List<Long> idList = new ArrayList<>();
        if (userId == 1L){
            List<InfoBusCompanyEntity> list = getBaseMapper().selectList(null);
            if(list != null && list.size() > 0){
                idList = list.stream().map(InfoBusCompanyEntity::getCompanyId).collect(Collectors.toList());
            }
        }else{
            idList = getBaseMapper().getUserCompanyIdList(userId);
        }
        return idList;
    }

    @Override
    @DataSource("read")
    public int queryCompanyBusCount(long companyId) {
        return getBaseMapper().queryCompanyBusCount(companyId);
    }

    @Override
    @DataSource("read")
    public AreaVo queryCompanyAreaInfo(long companyId) {
        ValidCompanyVO vo = getBaseMapper().queryCompanyAreaName(companyId);
        return vo==null?null:new AreaVo(vo.getAreaId(),vo.getAreaName());
    }

}
