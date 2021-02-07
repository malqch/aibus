package com.wntime.service.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wntime.common.exception.RRException;
import com.wntime.service.common.service.BusMotorTypeInfoService;
import com.wntime.service.common.vo.BusInfoMotorTypeVo;
import com.wntime.service.dao.InfoBusMotorDao;
import com.wntime.service.dao.InfoBusTypeDao;
import com.wntime.service.entity.InfoBusBatteryEntity;
import com.wntime.service.entity.InfoBusMotorEntity;
import com.wntime.service.entity.InfoBusTypeEntity;
import com.wntime.service.service.InfoBusBatteryService;
import com.wntime.service.service.InfoBusMotorService;
import com.wntime.service.service.InfoBusTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wntime.common.utils.DateUtils;
import com.wntime.common.utils.PageUtils;
import com.wntime.service.dao.InfoMotorTypeDao;
import com.wntime.service.entity.InfoMotorTypeEntity;
import com.wntime.service.service.InfoMotorTypeService;

import javax.annotation.Resource;

/**
 * @desc 电机类型表
 *
 * @date 2020-08-31 11:07:44
 */
@Service("infoMotorTypeService")
public class InfoMotorTypeServiceImpl extends ServiceImpl<InfoMotorTypeDao, InfoMotorTypeEntity>
        implements InfoMotorTypeService, BusMotorTypeInfoService {

    @Resource
    private InfoBusMotorDao infoBusMotorDao;

    @Resource
    private InfoBusTypeDao infoBusTypeDao;

    @Override
    public BusInfoMotorTypeVo queryByCode(String code) {
        return this.baseMapper.queryByCode(code);
    }

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
            Page<InfoMotorTypeEntity> page = new Page<>(currPage, pageSize);
            params.put("page", page);
            return new PageUtils(page.setRecords(this.baseMapper.queryPageList(params)));
        }

        @Override
        public InfoMotorTypeEntity getDetailById(Long id) {
            return this.getById(id);
        }

    @Override
    public List<InfoMotorTypeEntity> getAll() {
        return this.list(new QueryWrapper<InfoMotorTypeEntity>().eq("is_deleted", 0).eq("is_enabled", 1));
    }

    @Override
        public void save(InfoMotorTypeEntity infoMotorTypeEntity, Long userId) {
            infoMotorTypeEntity.setMotorTypeId(null);
            infoMotorTypeEntity.setIsDeleted(0);
            infoMotorTypeEntity.setCreatedDate(DateUtils.getTimestamp());
            infoMotorTypeEntity.setCreatedBy(userId);
            this.save(infoMotorTypeEntity);
        }

        @Override
        public void updateById(InfoMotorTypeEntity infoMotorTypeEntity, Long userId) {
            // 关联检查
            if(infoMotorTypeEntity.getIsEnabled() == 0){
                deletePermitCheck( infoMotorTypeEntity.getMotorTypeId());
            }

            infoMotorTypeEntity.setModifiedBy(userId);
            infoMotorTypeEntity.setModifiedDate(DateUtils.getTimestamp());
            this.updateById(infoMotorTypeEntity);
        }

    /**
     * 关联删除检查
     * @param id
     */
    void deletePermitCheck(Long id){
            // 车型电机类型检查
            QueryWrapper<InfoBusTypeEntity> QueryWrapperType = new QueryWrapper<InfoBusTypeEntity>()
                    .eq("is_deleted", 0)
                    .eq("motor_type_id",id);

            List<InfoBusTypeEntity> getListType = infoBusTypeDao.selectList(QueryWrapperType);
            if(getListType != null && getListType.size() > 0){
                throw new RRException("该电机类型存在关联[公交车型]配置，删除或禁用失败。");
            }

            // 车辆电机类型检查
            QueryWrapper<InfoBusMotorEntity> QueryWrapper = new QueryWrapper<InfoBusMotorEntity>()
                    .eq("is_deleted", 0)
                    .eq("motor_type_id",id);

            List<InfoBusMotorEntity> getList = infoBusMotorDao.selectList(QueryWrapper);
            if(getList != null && getList.size() > 0){
                throw new RRException("该电机类型存在关联[车辆电机]配置，删除或禁用失败。");
            }
        }

        @Override
        public void delById(Long id, Long userId) {
            // 关联检查
            deletePermitCheck(id);
            InfoMotorTypeEntity infoMotorTypeEntity = new InfoMotorTypeEntity();
            infoMotorTypeEntity.setMotorTypeId(id);
            infoMotorTypeEntity.setIsDeleted(1);
            infoMotorTypeEntity.setModifiedBy(userId);
            infoMotorTypeEntity.setModifiedDate(DateUtils.getTimestamp());
            this.updateById(infoMotorTypeEntity);
        }

        @Transactional(rollbackFor = Exception.class)
        @Override
        public void deleteBatch(String[] ids, Long userId) {
            for (String id : ids) {
                this.delById(Long.parseLong(id), userId);
            }
        }

}
