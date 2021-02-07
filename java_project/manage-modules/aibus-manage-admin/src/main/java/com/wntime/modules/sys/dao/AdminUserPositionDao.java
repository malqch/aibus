

package com.wntime.modules.sys.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wntime.entity.AdminUser;
import com.wntime.modules.sys.entity.AdminUserPosition;
import com.wntime.modules.sys.vo.AdminUserPositionVo;
import com.wntime.modules.sys.vo.BusinessObjectVo;
import com.wntime.modules.sys.vo.UserPositionVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

/**
 * 用户与角色对应关系

 */
@Mapper
public interface AdminUserPositionDao extends BaseMapper<AdminUserPosition> {

	/**
	 * 根据用户ID，获取角色ID列表
	 */
	List<Long> queryPositionIdList(Long userId);


	/**
	 * 根据角色ID数组，批量删除
	 */
	int deleteBatch(Long[] positionIds);

	/**
	 *
	 * 根据职位ID数组，批量更新字段来进行删除
	 */
	int deleteUpdateByPositionId(@Param("positionIds") Long[] positionIds, @Param(value = "modifyUserId") Long modifyUserId, @Param(value = "modifyDt") Timestamp modifyDt);
	/**
	 *
	 * 根据用户ID数组，批量更新字段来进行删除
	 */
	int deleteUpdateByUserId(@Param("userIds") Long[] userIds, @Param(value = "modifyUserId") Long modifyUserId, @Param(value = "modifyDt") Timestamp modifyDt);

	List<UserPositionVo> queryUserPositionAuth(@Param("userId")long userId);

	List<UserPositionVo> queryUserPositionAuths(@Param("userId")long userId,@Param("auths")List<UserPositionVo> auth);

	List<UserPositionVo> queryUserPositionAuthCount(@Param("userId")long userId, @Param("positionAuthId")long positionAuthId);

	List<AdminUserPositionVo> queryPositionWithUserId(@Param("userId")Long userId);

	List<BusinessObjectVo> queryUserPositionAuthObject(@Param("userId")Long userId);

	int deleteUpdateBusiObject(@Param("userId")Long userId,@Param("positionId")long positionId,@Param("modifyUserId")long modifyUserId);

	int queryCountByPositionId(@Param("positionId")long positionId);

	List<AdminUser> getByOrderSells(@Param("companyId")long companyId);
}
