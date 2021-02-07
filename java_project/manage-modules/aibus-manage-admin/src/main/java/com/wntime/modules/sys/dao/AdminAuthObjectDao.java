package com.wntime.modules.sys.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wntime.modules.sys.entity.AdminAuthObject;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface AdminAuthObjectDao extends BaseMapper<AdminAuthObject> {

    List<Map<String,Object>> getBusFactoryList();

    int deleteUpdateAdminAuthObject(@Param("positionAuthId")long positionAuthId,@Param("busiObjectId")long busiObjectId,@Param("userId")long userId);

    int updateUserPositionId(@Param("positionAuthId")long positionAuthId,@Param("busiObjectId")long busiObjectId,@Param("userPositionId")long userPositionId);

    int deleteUpdateUserPositionAuthObject(@Param("userIds")Long[] userIds,@Param("operationId")Long operationId);
}
