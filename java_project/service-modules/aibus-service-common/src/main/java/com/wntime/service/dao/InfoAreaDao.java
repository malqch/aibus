package com.wntime.service.dao;

import com.wntime.service.entity.InfoAreaEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @desc 区域表
 * 
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2020-08-25 14:28:17
 */
@Mapper
public interface InfoAreaDao extends BaseMapper<InfoAreaEntity> {

    boolean delById(long id);

    List<InfoAreaEntity> queryList(Map<String, Object> params);

    List<InfoAreaEntity> getChildArea(Long id);

    List<Object> queryArea(Map<String, Object> params);

    InfoAreaEntity getDetailById(Long id);

    Integer isEnableDelete(Long id);

    Integer isEnableInsert(Long areaId,Long parentAreaId, String areaName);

    Integer isEnableStop(Long id);


    // 根据ParentAreaId查询省份、市 列表
    List<InfoAreaEntity> getListByParentAreaId(@Param(value = "parentAreaId") Long parentAreaId);

    // 根据公司列表查区域列表
    List<InfoAreaEntity> getAreaByCompanyList(@Param(value = "companyIdList") List<Long> companyIdList);

    // 根据登录用户权限、ParentAreaId查询区域列表
    List<InfoAreaEntity> getAreaListByUserAndParentAreaId(@Param(value = "parentAreaId") Long parentAreaId,List<Long> areaIdList);

    // 根据Id查询区域信息
    InfoAreaEntity getByAreaId(@Param(value = "areaId") Long areaId);

    // 根据Id查询区域名称 省份 + 市
    String getAreaNameByAreaId(@Param(value = "areaId") Long areaId);
}
