package com.wntime.modules.sys.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wntime.entity.AdminParametersEntity;
import com.wntime.modules.file.entity.FilesParamsEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 系统参数表
 *
 * @author buxl
 * @email
 * @date 2019-09-27 14:50:20
 */
@Mapper
public interface AdminParametersDao extends BaseMapper<AdminParametersEntity> {

    /**
     * 查询需要缓存的参数
     * @return
     */
    List<AdminParametersEntity> queryCacheParameters();

    List<FilesParamsEntity> querySoftwareParameters();

}
