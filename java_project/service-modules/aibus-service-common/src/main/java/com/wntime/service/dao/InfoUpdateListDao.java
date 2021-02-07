package com.wntime.service.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wntime.service.common.entity.InfoUpdateListEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 更新信息表
 *
 * @author peishuaipeng
 * @date 2019-12-13 10:40:34
 */
@Mapper
public interface InfoUpdateListDao extends BaseMapper<InfoUpdateListEntity> {

    List<Object> queryPageList(Map<String, Object> params);

    List<Object> getDetailById(Long id);

    List<Object> queryUpdateType(Map<String, Object> params);

    boolean delById(Long id);

    Integer isEnableInsert(String name);

    Integer isEnableUpdate(long id, String name);

    void updatePublishStatus(String updateType);

    Integer updateData(String updateType, Long userId, Timestamp modifiedDate);

    boolean isTableUpdate(@Param("tableName") String tableName,@Param("date") Date date);

    boolean isTableUpdate1(@Param("tableName") String tableName,@Param("date") Date date);
}
