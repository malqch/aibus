

package com.wntime.modules.sys.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.wntime.modules.sys.entity.AdminRight;

import java.io.Serializable;
import java.util.List;


/**
 * 菜单管理

 */
public interface AdminRightService extends IService<AdminRight> {

	/**
	 * 根据父菜单，查询子菜单
	 * @param parentId 父菜单ID
	 * @param menuIdList  用户菜单ID
	 */
	List<AdminRight> queryListParentId(Long parentId, List<Long> menuIdList);


	List<AdminRight> list();

	/**
	 * 根据父菜单，查询子菜单
	 * @param parentId 父菜单ID
	 */
	List<AdminRight> queryListParentId(Long parentId);

	@Override
	AdminRight getById(Serializable parentrightId);

	/**
	 * 获取不包含按钮的菜单列表
	 */
	List<AdminRight> queryNotButtonList(Long createUserId);

	/**
	 * 获取用户菜单列表
	 */
	List<AdminRight> getUserRightList(Long userId);

	/**
	 * 删除
	 */
	void delete(Long rightId);
	/**
	 * 通过修改字段来进行修改
	 */
	void deleteUpdate(Long rightId);

	List<AdminRight> queryRightIsExist(AdminRight right);

	List<AdminRight> queryCatalogList();

	List<AdminRight> queryRightPathIsExist(String path);

    List<AdminRight> queryNotButtonListByUser(Long userId);
}
