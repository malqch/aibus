package com.wntime.fault.dao;

import com.wntime.fault.entity.InfoFaultExtendEntity;
import com.wntime.fault.entity.LogFaultAttachEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * @desc 故障附件表
 * 
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2020-08-25 13:48:11
 */
@Mapper
public interface LogFaultAttachDao extends BaseMapper<LogFaultAttachEntity> {
    List<LogFaultAttachEntity> queryPageList(Long faultDetailId);
}
