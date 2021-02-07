package com.wntime.modules.officer.dao;

import com.wntime.modules.officer.entity.DriverEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 驾驶员信息表
 * 
 * @author raute
 * @email admin@wntime.com
 * @date 2021-01-16 14:43:43
 */
@Mapper
public interface DriverDao extends BaseMapper<DriverEntity> {

    List<DriverEntity> getPrimaryDriverList();
    
    /***
     * @Author Buxl
     * @Description 根据证件号查询司机信息
     * @Date 14:14 2021/1/23
     * @Param [idNo]
     * @return com.wntime.modules.officer.entity.DriverEntity
     **/
    DriverEntity getDriverByIdNo(String idNo);
}
