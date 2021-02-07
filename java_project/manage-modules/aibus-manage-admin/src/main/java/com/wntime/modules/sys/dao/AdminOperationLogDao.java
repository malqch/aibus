package com.wntime.modules.sys.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wntime.modules.sys.entity.AdminOperationLog;
import org.apache.ibatis.annotations.Mapper;

/**
 * 系统日志

 */
@Mapper
public interface AdminOperationLogDao extends BaseMapper<AdminOperationLog> {

}
