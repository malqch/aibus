package com.wntime.modules.officer.dao;

import com.wntime.modules.officer.entity.AuthorizeEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * @desc 授权人
 * 
 * @author raute
 * @email admin@wntime.com
 * @date 2021-01-16 14:43:43
 */
@Mapper
public interface AuthorizeDao extends BaseMapper<AuthorizeEntity> {
	
}
