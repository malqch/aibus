

package com.wntime.modules.sys.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wntime.modules.sys.entity.AdminUserRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.sql.Timestamp;
import java.util.List;

/**
 * 用户与角色对应关系
 */
@Mapper
public interface AdminUserRoleDao extends BaseMapper<AdminUserRole> {

    /**
     * 根据用户ID，获取角色ID列表
     */
    List<Long> queryRoleIdList(Long userId);


    /**
     * 根据角色ID数组，批量删除
     */
    int deleteBatch(Long[] roleIds);

    /**
     * 根据角色ID数组，批量更新字段来进行删除
     */
    int deleteUpdateByRoleId(@Param(value = "roleIds") Long[] roleIds, @Param(value = "modifyUserId") Long modifyUserId, @Param(value = "modifyDt") Timestamp modifyDt);

    /**
     * 根据用户ID数组，批量更新字段来进行删除
     */
    int deleteUpdateByUserId(@Param(value = "userIds") Long[] userIds, @Param(value = "modifyUserId") Long modifyUserId, @Param(value = "modifyDt") Timestamp modifyDt);

    Long getUserRoleId(Long userId, Long roleId);

    Long deleteUserRoleByUserId(Long userId);

    Integer deleteUserRole(@Param(value = "userId") Long userId, @Param(value = "operationId") Long operationId, @Param(value = "modifyDt") Timestamp modifyDt);

}
