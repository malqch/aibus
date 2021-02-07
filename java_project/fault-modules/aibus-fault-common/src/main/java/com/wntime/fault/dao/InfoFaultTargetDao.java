package com.wntime.fault.dao;

import com.wntime.fault.entity.InfoFaultTargetEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wntime.fault.entity.InfoFaultTypeEntity;
import com.wntime.fault.vo.InfoFaultTargetVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * @desc 故障标签表
 * 
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2020-08-25 13:48:11
 */
@Mapper
public interface InfoFaultTargetDao extends BaseMapper<InfoFaultTargetEntity> {

    List<InfoFaultTargetVo> queryList();

    List<InfoFaultTargetEntity> queryPageList(Map<String, Object> params);
}
