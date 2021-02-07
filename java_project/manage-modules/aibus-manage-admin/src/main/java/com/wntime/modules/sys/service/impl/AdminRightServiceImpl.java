

package com.wntime.modules.sys.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wntime.common.utils.Constant;
import com.wntime.common.utils.MapUtils;
import com.wntime.modules.sys.dao.AdminRightDao;
import com.wntime.modules.sys.entity.AdminRight;
import com.wntime.modules.sys.service.AdminRightService;
import com.wntime.modules.sys.service.AdminRoleRightService;
import com.wntime.modules.sys.service.AdminUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


@Service("adminRightService")
public class AdminRightServiceImpl extends ServiceImpl<AdminRightDao, AdminRight> implements AdminRightService {
	@Autowired
	private AdminUserService adminUserService;
	@Autowired
	private AdminRoleRightService adminRoleRightService;

	@Override
	public List<AdminRight> queryListParentId(Long parentId, List<Long> menuIdList) {
		List<AdminRight> menuList = queryListParentId(parentId);
		if(menuIdList == null){
			return menuList;
		}

		List<AdminRight> userMenuList = new ArrayList<>();
		for(AdminRight menu : menuList){
			if(menuIdList.contains(menu.getRightId())){
				userMenuList.add(menu);
			}
		}
		return userMenuList;
	}

	@Override
	public List<AdminRight> list(){
		QueryWrapper<AdminRight> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("is_deleted","0").eq("is_enabled","1");
		return this.baseMapper.selectList(queryWrapper);
	}


	@Override
	public AdminRight getById(Serializable rightId){

		QueryWrapper<AdminRight> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("right_id", rightId);
		queryWrapper.eq("is_deleted","0").eq("is_enabled","1");
		return this.baseMapper.selectOne(queryWrapper);
	}
	@Override
	public List<AdminRight> queryListParentId(Long parentId) {
		return baseMapper.queryListParentId(parentId);
	}

	@Override
	public List<AdminRight> queryNotButtonList(Long createUserId) {
		return baseMapper.queryNotButtonList(createUserId);
	}

	@Override
	public List<AdminRight> getUserRightList(Long userId) {
		//系统管理员，拥有最高权限
		if(userId == Constant.SUPER_ADMIN){
			return getAllMenuList(null);
		}

		//用户菜单列表
		List<Long> menuIdList = adminUserService.queryAllRightId(userId);
		return getAllMenuList(menuIdList);
	}

	@Override
	public void delete(Long rightId){
		//删除菜单
		this.deleteUpdate(rightId);
		//删除菜单与角色关联
		adminRoleRightService.removeByMap(new MapUtils().put("right_id", rightId));
	}

	@Override
	public void deleteUpdate(Long rightId) {
		this.baseMapper.deleteUpdate(rightId);
	}

	@Override
	public List<AdminRight> queryRightIsExist(AdminRight right) {
		return this.baseMapper.queryRightIsExist(right);
	}

	@Override
	public List<AdminRight> queryCatalogList() {
		return this.baseMapper.queryCatalogList();
	}

	@Override
	public List<AdminRight> queryRightPathIsExist(String path) {
		return this.baseMapper.queryRightPathIsExist(path);
	}

	@Override
	public List<AdminRight> queryNotButtonListByUser(Long userId) {
		return this.baseMapper.queryNotButtonListByUser(userId);
	}

	/**
	 * 获取所有菜单列表
	 */
	private List<AdminRight> getAllMenuList(List<Long> menuIdList){
		//查询根菜单列表
		List<AdminRight> menuList = queryListParentId(0L, menuIdList);
		//递归获取子菜单
		getMenuTreeList(menuList, menuIdList);

		return menuList;
	}

	/**
	 * 递归
	 */
	private List<AdminRight> getMenuTreeList(List<AdminRight> menuList, List<Long> menuIdList){
		List<AdminRight> subMenuList = new ArrayList<>();

		for(AdminRight entity : menuList){
			//目录
			if(entity.getType() == Constant.MenuType.CATALOG.getValue()){
				entity.setList(getMenuTreeList(queryListParentId(entity.getRightId(), menuIdList), menuIdList));
			}
			subMenuList.add(entity);
		}

		return subMenuList;
	}
}
