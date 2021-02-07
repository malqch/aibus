

package com.wntime.modules.sys.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wntime.modules.sys.entity.AdminRoleRight;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.sql.Timestamp;
import java.util.List;

;

/**
 * 角色与菜单对应关系

 */
@Mapper
public interface AdminRoleRightDao extends BaseMapper<AdminRoleRight> {

	/**
	 * 根据角色ID，获取菜单ID列表
	 */
	List<Long> queryRightIdList(Long roleId);

	/**
	 * 根据角色ID数组，批量删除
	 */
	int deleteBatch(Long[] roleIds);
	/**
	 * 根据角色ID数组，批量更新字段来删除
	 */
	int deleteUpdateByRoleId(@Param(value = "roleIds") Long[] roleIds, @Param(value = "modifyUserId") Long modifyUserId, @Param(value = "modifyDt") Timestamp modifyDt);
    int deleteUpdateByRightId(@Param(value = "rightIds") Long[] rightIds, @Param(value = "modifyUserId") Long modifyUserId, @Param(value = "modifyDt") Timestamp modifyDt);
}
