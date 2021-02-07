package com.wntime.service.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wntime.common.utils.PageUtils;
import com.wntime.service.common.entity.InfoUpdateListEntity;

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
public interface InfoUpdateListService extends IService<InfoUpdateListEntity> {

    PageUtils queryPageList(Map<String, Object> params);

    List<Object> getDetailById(Long id);

    List<Object> queryUpdateType(Map<String, Object> params);

    boolean updateData(String updateType, Long userId, Timestamp modifiedDate);

    boolean delById(Long id);

    boolean deleteBatch(String[] ids);

    boolean isEnableInsert(String name);

    boolean isEnableUpdate(long id, String name);

    void updatePublishStatus(String updateType);

    boolean isTableUpdate(String tableName,Date date);

    boolean isTableUpdate1(String tableName,Date date);

}

