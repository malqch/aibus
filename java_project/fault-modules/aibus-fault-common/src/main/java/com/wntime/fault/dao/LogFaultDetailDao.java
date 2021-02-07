package com.wntime.fault.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wntime.fault.entity.LogFaultDetailEntity;
import com.wntime.fault.vo.FaultCountVo;
import com.wntime.fault.vo.FaultInfoItem;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @desc 故障日志表
 * 
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2020-08-25 13:48:11
 */
@Mapper
public interface LogFaultDetailDao extends BaseMapper<LogFaultDetailEntity> {
    LogFaultDetailEntity getDetailById(Long id);

    List<LogFaultDetailEntity> queryPageList(Map<String, Object> params);

    List<FaultCountVo> queryFaultCoutGroupCode(@Param("busId")long busId);

    List<FaultInfoItem> queryBusFaultList(@Param("busId")long busId);
}
