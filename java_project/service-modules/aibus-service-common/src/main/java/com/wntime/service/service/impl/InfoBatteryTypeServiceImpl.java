package com.wntime.service.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wntime.common.exception.RRException;
import com.wntime.service.common.service.BusBatteryTypeInfoService;
import com.wntime.service.common.vo.BusInfoBatteryTypeVo;
import com.wntime.service.dao.InfoBusBatteryDao;
import com.wntime.service.dao.InfoBusTypeDao;
import com.wntime.service.entity.InfoBusBatteryEntity;
import com.wntime.service.entity.InfoBusTypeEntity;
import com.wntime.service.entity.InfoMotorTypeEntity;
import com.wntime.service.service.InfoBusBatteryService;
import com.wntime.service.service.InfoBusTypeService;
import com.wntime.service.service.InfoMotorTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wntime.common.utils.DateUtils;
import com.wntime.common.utils.PageUtils;
import com.wntime.service.dao.InfoBatteryTypeDao;
import com.wntime.service.entity.InfoBatteryTypeEntity;
import com.wntime.service.service.InfoBatteryTypeService;

import javax.annotation.Resource;

/**
 * @desc 电池类型表
 *
 * @date 2020-08-31 14:57:41
 */
@Service("infoBatteryTypeService")
public class InfoBatteryTypeServiceImpl extends ServiceImpl<InfoBatteryTypeDao, InfoBatteryTypeEntity>
        implements InfoBatteryTypeService , BusBatteryTypeInfoService {

    @Resource
    private InfoBusBatteryDao infoBusBatteryDao;

    @Resource
    private InfoBusTypeDao infoBusTypeDao;

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
            Page<InfoBatteryTypeEntity> page = new Page<>(currPage, pageSize);
            params.put("page", page);
            return new PageUtils(page.setRecords(this.baseMapper.queryPageList(params)));
        }

        @Override
        public InfoBatteryTypeEntity getDetailById(Long id) {
            return this.getById(id);
        }

    @Override
    public List<InfoBatteryTypeEntity> getAll() {
        return this.list(new QueryWrapper<InfoBatteryTypeEntity>().eq("is_deleted", 0).eq("is_enabled", 1));
    }

    @Override
    public BusInfoBatteryTypeVo queryByCode(String code) {
        return this.baseMapper.queryByCode(code);
    }

    @Override
        public void save(InfoBatteryTypeEntity infoBatteryTypeEntity, Long userId) {
            infoBatteryTypeEntity.setBatteryTypeId(null);
            infoBatteryTypeEntity.setIsDeleted(0);
            infoBatteryTypeEntity.setCreatedDate(DateUtils.getTimestamp());
            infoBatteryTypeEntity.setCreatedBy(userId);
            this.save(infoBatteryTypeEntity);
        }

        @Override
        public void updateById(InfoBatteryTypeEntity infoBatteryTypeEntity, Long userId) {
            // 关联检查
            if(infoBatteryTypeEntity.getIsEnabled() == 0){
                deletePermitCheck( infoBatteryTypeEntity.getBatteryTypeId());
            }
            infoBatteryTypeEntity.setModifiedBy(userId);
            infoBatteryTypeEntity.setModifiedDate(DateUtils.getTimestamp());
            this.updateById(infoBatteryTypeEntity);
        }

        /**
         * 关联删除检查
         * @param id
         */
        void deletePermitCheck(Long id){
            // 车型电池类型检查
            QueryWrapper<InfoBusTypeEntity> QueryWrapperType = new QueryWrapper<InfoBusTypeEntity>()
                    .eq("is_deleted", 0)
                    .eq("battery_type_id",id);

            List<InfoBusTypeEntity> getListType = infoBusTypeDao.selectList(QueryWrapperType);
            if(getListType != null && getListType.size() > 0){
                throw new RRException("该电池类型存在关联[公交车型]配置，删除失败。");
            }

            // 车辆电池类型检查
            QueryWrapper<InfoBusBatteryEntity> QueryWrapper = new QueryWrapper<InfoBusBatteryEntity>()
                    .eq("is_deleted", 0)
                    .eq("battery_type_id",id);

            List<InfoBusBatteryEntity> getList = infoBusBatteryDao.selectList(QueryWrapper);
            if(getList != null && getList.size() > 0){
                throw new RRException("该电池类型存在关联[车辆电池]配置，删除失败。");
            }
        }
        @Override
        public void delById(Long id, Long userId) {
            // 关联检查
            deletePermitCheck(id);
            InfoBatteryTypeEntity infoBatteryTypeEntity = new InfoBatteryTypeEntity();
            infoBatteryTypeEntity.setBatteryTypeId(id);
            infoBatteryTypeEntity.setIsDeleted(1);
            infoBatteryTypeEntity.setModifiedBy(userId);
            infoBatteryTypeEntity.setModifiedDate(DateUtils.getTimestamp());
            this.updateById(infoBatteryTypeEntity);
        }

        @Transactional(rollbackFor = Exception.class)
        @Override
        public void deleteBatch(String[] ids, Long userId) {
            for (String id : ids) {
                this.delById(Long.parseLong(id), userId);
            }
        }

}
