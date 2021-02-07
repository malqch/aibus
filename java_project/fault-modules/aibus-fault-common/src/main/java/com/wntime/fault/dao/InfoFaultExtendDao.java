package com.wntime.fault.dao;

import com.wntime.fault.entity.InfoCollectFaultEntity;
import com.wntime.fault.entity.InfoFaultExtendEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wntime.fault.vo.InfoFaultExtendVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * @desc 故障扩展表
 * 
 * @author Mark
 * @email sunlightcs@gmail.comInfoFaultExtendDao
 * @date 2020-08-25 13:48:11
 */
@Mapper
public interface InfoFaultExtendDao extends BaseMapper<InfoFaultExtendEntity> {

    List<InfoFaultExtendVo> queryList();

    List<InfoFaultExtendEntity> queryPageList(Map<String, Object> params);
}
