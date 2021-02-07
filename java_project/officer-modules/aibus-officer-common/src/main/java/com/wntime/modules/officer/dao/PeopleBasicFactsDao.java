package com.wntime.modules.officer.dao;

import com.wntime.modules.officer.entity.PeopleBasicFactsEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.Date;
import java.util.List;

/**
 * @desc 人员基础信息
 * 
 * @author raute
 * @email admin@wntime.com
 * @date 2021-01-16 14:43:43
 */
@Mapper
public interface PeopleBasicFactsDao extends BaseMapper<PeopleBasicFactsEntity> {

    /**
     * @Author Buxl
     * @Description 查询所有人员列表
     * @Date 11:43 2021/1/25
     * @Param [date]
     * @return java.util.List<com.wntime.modules.officer.entity.PeopleBasicFactsEntity>
     **/
    List<PeopleBasicFactsEntity> getAllPersonnelByTimestamp(long busId,Date date);

}
