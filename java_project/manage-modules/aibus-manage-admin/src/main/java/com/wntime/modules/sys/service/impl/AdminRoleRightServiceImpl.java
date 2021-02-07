

package com.wntime.modules.sys.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wntime.common.utils.DateUtils;
import com.wntime.modules.sys.dao.AdminRoleRightDao;
import com.wntime.modules.sys.entity.AdminRoleRight;
import com.wntime.modules.sys.service.AdminRoleRightService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * 角色与菜单对应关系

 */
@Service("adminRoleRightService")
public class AdminRoleRightServiceImpl extends ServiceImpl<AdminRoleRightDao, AdminRoleRight> implements AdminRoleRightService {

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void saveOrUpdate(Long roleId, List<String> rightIdList,Long operationId) {
		//先删除角色与菜单关系
		deleteBatch(new Long[]{roleId});

		if(rightIdList.size() == 0){
			return ;
		}

		//保存角色与菜单关系
		for(String rightId : rightIdList){
			AdminRoleRight AdminRoleRight = new AdminRoleRight();
			AdminRoleRight.setRightId(Long.valueOf(rightId));
			AdminRoleRight.setRoleId(roleId);
			AdminRoleRight.setCreateUserId(operationId);
			AdminRoleRight.setCreateDt(DateUtils.getTimestamp());
			AdminRoleRight.setIsDeleted("0");
			this.save(AdminRoleRight);
		}
	}

	@Override
	public List<Long> queryRightIdList(Long roleId) {
		return baseMapper.queryRightIdList(roleId);
	}

	@Override
	public int deleteBatch(Long[] roleIds){
		return baseMapper.deleteBatch(roleIds);
	}

	@Override
	public int deleteUpdateByRoleId(Long[] roleIds, Long userId) {
		return this.baseMapper.deleteUpdateByRoleId(roleIds,userId, DateUtils.getTimestamp());
	}

	@Override
	public int deleteUpdateByRightId(Long[] rightIds, Long userId) {
		return this.baseMapper.deleteUpdateByRightId(rightIds,userId,DateUtils.getTimestamp());
	}

}
