

package com.wntime.modules.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wntime.common.utils.PageUtils;
import com.wntime.entity.AdminUser;
import com.wntime.modules.sys.entity.AdminPosition;
import com.wntime.modules.sys.form.AddAdminPositionForm;
import com.wntime.modules.sys.vo.AdminUserPositionVo;

import java.util.Collection;
import java.util.List;
import java.util.Map;


/**
 * 职位

 */
public interface AdminPositionService extends IService<AdminPosition> {

	PageUtils queryPage(Map<String, Object> params);

	void savePosition(AddAdminPositionForm position,Long createUserId);

	void update(AdminPosition position, Long createUserId);

	void deleteBatch(Long[] positionIds, Long userId);


	/**
	 *通过更新字段来删除角色
	 */
	int deleteUpdate(Long[] positionIds, Long userId);

	/**
	 * 查询用户创建的职位ID列表
	 */
	List<Long> queryRoleIdList(Long createUserId);

    PageUtils queryPageList(Map<String, Object> params);

    int queryPositionIsExist(String positionName);

	Collection<AdminUserPositionVo> listByUser(Long userId,Long loginUserId);

    List<Long> queryPositionIdList(Long userId, Long createUserId);
	//查询只是用户查询的
	List<Long> queryOwnerCreate(Long createUserId);


}
