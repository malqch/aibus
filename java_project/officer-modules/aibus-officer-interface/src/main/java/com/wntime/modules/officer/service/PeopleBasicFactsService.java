package com.wntime.modules.officer.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wntime.common.utils.PageUtils;
import com.wntime.modules.officer.entity.PeopleBasicFactsEntity;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @desc 人员基础信息
 *
 * @author raute
 * @email admin@wntime.com
 * @date 2021-01-16 14:43:43
 */
public interface PeopleBasicFactsService extends IService<PeopleBasicFactsEntity> {

    PageUtils queryPage(Map<String, Object> params);

    PeopleBasicFactsEntity saveOrUpdateForIM(PeopleBasicFactsEntity entity);

    PeopleBasicFactsEntity findByIdAndName(String idNo, String name);

    PeopleBasicFactsEntity getOneInfoByIdNo(String idNo);

    /**
     * @Author Buxl
     * @Description 查询所有人员列表
     * @Date 11:43 2021/1/25
     * @Param [date]
     * @return java.util.List<com.wntime.modules.officer.entity.PeopleBasicFactsEntity>
     **/
    List<PeopleBasicFactsEntity> getAllPersonnelByTimestamp(long busId,Date date);
}

