

package com.wntime.modules.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wntime.modules.sys.entity.AdminRoleRight;

import java.util.List;


/**
 * 角色与菜单对应关系

 */
public interface AdminRoleRightService extends IService<AdminRoleRight> {

	void saveOrUpdate(Long roleId, List<String> rightIdList, Long operationId);

	/**
	 * 根据角色ID，获取菜单ID列表
	 */
	List<Long> queryRightIdList(Long roleId);

	/**
	 * 根据角色ID数组，批量删除
	 */
	int deleteBatch(Long[] roleIds);

	/**
	 *根据角色ID更新字段来进行删除role_right关系
	 */
    int deleteUpdateByRoleId(Long[] roleIds, Long userId);

	int deleteUpdateByRightId(Long[] rightIds, Long userId);
}
