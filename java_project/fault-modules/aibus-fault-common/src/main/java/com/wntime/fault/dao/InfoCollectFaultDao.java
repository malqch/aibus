package com.wntime.fault.dao;

import com.wntime.fault.entity.InfoCollectFaultEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wntime.fault.entity.InfoFaultTargetEntity;
import com.wntime.fault.vo.InfoCollectFaultVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * @desc 故障采集表
 * 
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2020-08-25 13:48:11
 */
@Mapper
public interface InfoCollectFaultDao extends BaseMapper<InfoCollectFaultEntity> {

    List<InfoCollectFaultVo> queryList();

    InfoCollectFaultEntity getDetailById(Long id);

    List<InfoCollectFaultEntity> queryPageList(Map<String, Object> params);
}
