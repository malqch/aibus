package com.wntime.service.dao;

import com.wntime.service.common.vo.ConfigParamVo;
import com.wntime.service.entity.InfoConfigParamEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author psp
 * @desc 配置参数表
 */
@Mapper
public interface InfoConfigParamDao extends BaseMapper<InfoConfigParamEntity> {

    List<InfoConfigParamEntity> queryPageList(Map<String, Object> params);

    InfoConfigParamEntity getFaultTypeKnowledge();

    List<InfoConfigParamEntity> getConfigBusMilesList();

    ConfigParamVo queryConfigParamByGroupCode(@Param("group")String group,@Param("code")String code);

    ConfigParamVo queryConfigParamByGroupValue(@Param("group")String group,@Param("value")double value);

    List<ConfigParamVo> queryParamsByGroup(@Param("group")String group);
}
