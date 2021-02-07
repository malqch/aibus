package com.wntime.service.service;

import com.wntime.common.utils.PageUtils;
import com.wntime.common.validator.ValidatorUtils;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wntime.service.entity.InfoAreaEntity;

import java.util.List;
import java.util.Map;

/**
 * @desc 区域表
 *
 * @date 2020-08-25 14:28:17
 */
public interface InfoAreaService extends IService<InfoAreaEntity> {

    PageUtils queryPage(Map<String, Object> params);

    List<InfoAreaEntity> queryList(Map<String, Object> params);

    List<InfoAreaEntity> getChildArea(Long id);

    boolean delById(long id);

    boolean deleteBatch(String[] ids);

    InfoAreaEntity getDetailById(Long id);

    List queryArea(Map<String, Object> params);

    boolean isEnableDelete(Long id);

    boolean isEnableInsert(Long areaId,Long parentAreaId, String areaName);

    boolean isEnableStop(Long id);


    /**
     * 根据parentAreaId查询省/市列表
     * @param parentAreaId
     * @return
     */
    List<InfoAreaEntity> getListByParentAreaId(Long parentAreaId);

    /**
     * 获取某区域的所有下级区域
     * @param area
     * @return
     */
   List<InfoAreaEntity> getSubAreaList(InfoAreaEntity area);

    /**
     * 获取某区域的所有下级区域Id
     * @return
     */
    List<Long> getSubAreaIdList(Long areaId);

    /**
     * 根据登录用户、parentAreaId查询省/市列表
     * @param parentAreaId
     * @param companyIdList
     * @return
     */
    List<InfoAreaEntity> getListByUserAndParentAreaId(Long parentAreaId,List<Long> companyIdList);

    InfoAreaEntity getByAreaId(Long areaId);

    String getAreaNameByAreaId(Long areaId);
}

