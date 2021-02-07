

package com.wntime.modules.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wntime.entity.AdminUser;
import com.wntime.modules.sys.entity.AdminUserPosition;
import com.wntime.modules.sys.form.AdminUserFrom;
import com.wntime.modules.sys.vo.AdminUserPositionVo;
import com.wntime.modules.sys.vo.UserPositionVo;

import java.util.List;
import java.util.Set;


/**
 * 用户与角色对应关系

 */
public interface AdminUserPositionService extends IService<AdminUserPosition> {

	void saveOrUpdate(Long operationId, Long userId, List<AdminUserFrom.PositionObjectId> positionIdList);

	/**
	 * 根据用户ID，获取岗位ID列表
	 */
	List<Long> queryPositionIdList(Long userId);

	/**
	 * 根据角色ID数组，批量删除
	 */
	int deleteBatch(Long[] positionIds);

	List<AdminUser> getByOrderSells(Long companyId);

	/**
	 *
	 * 根据用户ID 来更新用户和角色的is_deleted字段
	 */
	int deleteUpdateByUserId(Long[] userIds, Long operationId);

	/**
	 *
	 * 根据用户ID 来更新用户和角色的is_deleted字段
	 */
	int deleteUpdateByPositionId(Long[] positionIds, Long operationId);

	List<AdminUserPositionVo> queryUserPosition(long userId);

	int queryPositionUsedCount(long positionId);
}
