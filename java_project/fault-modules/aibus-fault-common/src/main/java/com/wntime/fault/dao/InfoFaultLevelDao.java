package com.wntime.fault.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wntime.fault.entity.InfoFaultLevelEntity;
import com.wntime.fault.entity.InfoFaultTypeEntity;
import com.wntime.fault.vo.InfoFaultLevelVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * @desc 故障级别表
 * 
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2020-08-25 13:48:11
 */
@Mapper
public interface InfoFaultLevelDao extends BaseMapper<InfoFaultLevelEntity> {

    List<InfoFaultLevelEntity> queryPageList(Map<String, Object> params);

    List<Map<String, Object>> countBusGroupByFaultLevel(Long companyId);

    List<InfoFaultLevelVo> queryList();

    void updateBatch(List<Long> ids, Long userId);
}
