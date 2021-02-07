

package com.wntime.modules.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wntime.common.exception.RRException;
import com.wntime.common.utils.Constant;
import com.wntime.common.utils.DateUtils;
import com.wntime.common.utils.PageUtils;
import com.wntime.common.utils.Query;
import com.wntime.modules.sys.dao.AdminPositionDao;
import com.wntime.modules.sys.dao.AdminUserPositionDao;
import com.wntime.modules.sys.entity.AdminPosition;
import com.wntime.modules.sys.form.AddAdminPositionForm;
import com.wntime.modules.sys.service.AdminPositionAuthService;
import com.wntime.modules.sys.service.AdminPositionService;
import com.wntime.modules.sys.service.AdminUserPositionService;
import com.wntime.modules.sys.service.AdminUserService;
import com.wntime.modules.sys.vo.AdminUserPositionVo;
import com.wntime.modules.sys.vo.BusinessObjectVo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 角色
 */
@Service("adminPositionService")
public class AdminPositionServiceImpl extends ServiceImpl<AdminPositionDao, AdminPosition> implements AdminPositionService {
    @Autowired
    private AdminPositionAuthService adminPositionAuthService;
    @Autowired
    private AdminUserPositionService adminUserPositionService;
    @Autowired
    private AdminUserService adminUserService;
    @Resource
    private AdminUserPositionDao adminUserPositionDao;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String roleName = (String) params.get("name");
        Long createUserId = (Long) params.get("createUserId");

        IPage<AdminPosition> page = this.page(
                new Query<AdminPosition>().getPage(params),
                new QueryWrapper<AdminPosition>()
                        .like(StringUtils.isNotBlank(roleName), "name", roleName)
                        .eq(createUserId != null, "create_user_id", createUserId).eq("is_deleted", "0")
        );

        return new PageUtils(page);
    }


    @Override
    public PageUtils queryPageList(Map<String, Object> params) {
        Integer currPage = Integer.parseInt((String) params.get("page"));
        Integer pageSize = Integer.parseInt((String) params.get("limit"));
        Page<Object> page = new Page<>(currPage, pageSize);
        params.put("page", page);

        return new PageUtils(page.setRecords(this.baseMapper.queryAdminPositionListAll(params)));
    }

    @Override
    public int queryPositionIsExist(String positionName) {
        return this.baseMapper.queryPositionIsExist(positionName, null);
    }

    @Override
    public Collection<AdminUserPositionVo> listByUser(Long userId, Long loginUserId) {
        List<AdminUserPositionVo> list = this.baseMapper.listByUser(userId, loginUserId);

        return list;

       /* List<BusinessObjectVo> objs = adminUserPositionDao.queryUserPositionAuthObject(userId);
        Map<Long, List<AdminUserPositionVo.IcabCommissioner>> business = new HashMap<>();

        Optional.ofNullable(objs).ifPresent(businessObjectVos -> {
            businessObjectVos.stream().forEach(businessObjectVo -> {
                List<AdminUserPositionVo.IcabCommissioner> vos = business.get(businessObjectVo.getPositionAuthId());
                if (vos == null) {
                    vos = new ArrayList<>();
                    vos.add(new AdminUserPositionVo.IcabCommissioner(businessObjectVo.getAreaOrgId(), businessObjectVo.getPositionId(),
                            businessObjectVo.getPositionAuthId(), businessObjectVo.getIcabName(), businessObjectVo.getBusiObjectId(),
                            businessObjectVo.getCommName(), businessObjectVo.getIsSelfonly(), businessObjectVo.getSelected()));
                } else {
                    for (AdminUserPositionVo.IcabCommissioner vo : vos) {
                        if (vo.getIcabId() == businessObjectVo.getAreaOrgId()) {
                            vo.getOptions().add(new AdminUserPositionVo.Commissioner(businessObjectVo.getAreaOrgId(), businessObjectVo.getBusiObjectId(),
                                    businessObjectVo.getCommName(), businessObjectVo.getSelected(),
                                    businessObjectVo.getPositionId(), businessObjectVo.getPositionAuthId()
                            ));
                        }
                    }
                }
                business.put(businessObjectVo.getPositionAuthId(), vos);
            });
        });
        Collection result = new ArrayList();

        Optional.ofNullable(list).ifPresent(adminUserPositionVos -> {

            Map<Long, AdminUserPositionVo> positionMap = adminUserPositionVos.stream().collect(Collectors.toMap(AdminUserPositionVo::getPositionId, v -> {
                        List<AdminUserPositionVo.IcabCommissioner> obj = business.get(v.getPositionAuthId());
                        if (obj != null) {
                            v.setBusiObjectId(obj);
                        }
                        return v;
                    },
                    (oldValue, newValue) -> {
                        List<AdminUserPositionVo.IcabCommissioner> busiObjectId = oldValue.getBusiObjectId();

                        List<AdminUserPositionVo.IcabCommissioner> newObjectId = newValue.getBusiObjectId();
                        if (newObjectId != null) {
                            busiObjectId.addAll(newObjectId);
                        }
                        return oldValue;
                    }));

            result.addAll(positionMap.values());
        });
        return result;*/
    }

    @Override
    public List<Long> queryPositionIdList(Long userId, Long createUserId) {
        return this.baseMapper.queryPositionIdList(userId, createUserId);
    }

    @Override
    public List<Long> queryOwnerCreate(Long createUserId) {
        return this.baseMapper.queryAdminPositionList(createUserId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void savePosition(AddAdminPositionForm position, Long createUserId) {
        AdminPosition positionEntity = new AdminPosition();
        BeanUtils.copyProperties(position, positionEntity);
        positionEntity.setIsDeleted("0");

        Timestamp time = DateUtils.getTimestamp();
        int count = this.baseMapper.queryPositionIsExist(position.getName(), position.getPositionId());
        //岗位是否重复
        if (count != 0) {
            throw new RRException("岗位名已存在");
        }
        if (position.getPositionId() == null) {
            positionEntity.setCreateDt(time);
            positionEntity.setCreateUserId(createUserId);
            this.save(positionEntity);
        } else {
            positionEntity.setModifyDt(time);
            positionEntity.setModifyUserId(createUserId);
            this.updateById(positionEntity);
        }

        //更新职位和组织的关系
        adminPositionAuthService.saveOrUpdate(positionEntity.getPositionId(), position.getAuth(), createUserId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(AdminPosition position, Long createUserId) {
        position.setModifyDt(DateUtils.getTimestamp());
        this.updateById(position);

        //检查数据是否越权
        checkPrems(position, createUserId);

        //更新职位和集群的关系
        //	adminPositionAuthService.saveOrUpdate(position.getPositionId(), position.getAuthIdList(),position.getCreateUserId());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteBatch(Long[] positionIds, Long userId) {
        for (Long positionId : positionIds) {
            int count = adminUserPositionService.queryPositionUsedCount(positionId);
            if (count > 0) {
                throw new RRException("用户占用该岗位，无法删除");
            }
        }


        //删除岗位
        this.deleteUpdate(positionIds, userId);

        //删除岗位授权
        adminPositionAuthService.deleteUpdateByPositionId(positionIds, userId);
    }

    @Override
    public int deleteUpdate(Long[] positionids, Long userId) {

        return this.baseMapper.deleteUpdate(positionids, userId, DateUtils.getTimestamp());
    }

//    @Override
//    @Transactional(rollbackFor = Exception.class)
//    public void deleteBatch(Long[] roleIds) {
//        //删除角色
//        this.removeByIds(Arrays.asList(roleIds));
//
//        //删除角色与菜单关联
//        adminRoleRightService.deleteBatch(roleIds);
//
//        //删除角色与用户关联
//        adminUserRoleService.deleteBatch(roleIds);
//    }


    @Override
    public List<Long> queryRoleIdList(Long createUserId) {
        return baseMapper.queryRoleIdList(createUserId);
    }


    /**
     * 检查权限是否越权
     */
    private void checkPrems(AdminPosition position, Long createUserId) {
        //如果不是超级管理员，则需要判断职的权限是否超过自己的权限
        if (createUserId == Constant.SUPER_ADMIN) {
            return;
        }


        //查询职位的集群列表
        List<Long> authIdList = adminUserService.queryAllPositionId(position.getPositionId(), createUserId);


        //判断是否越权
		/*if(!authIdList.containsAll(position.getAuthIdList())){
			throw new RRException("新增岗位的权限，已超出你的权限范围");
		}*/
    }
}
