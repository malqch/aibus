package com.wntime.modules.sys.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wntime.common.utils.DateUtils;
import com.wntime.common.utils.MapUtils;
import com.wntime.dto.RoleOther;
import com.wntime.entity.AdminUser;
import com.wntime.modules.sys.dao.AdminUserRoleDao;
import com.wntime.modules.sys.entity.AdminUserRole;
import com.wntime.modules.sys.service.AdminUserRoleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;


/**
 * 用户与角色对应关系
 */
@Service("adminUserRoleService")
public class AdminUserRoleServiceImpl extends ServiceImpl<AdminUserRoleDao, AdminUserRole> implements AdminUserRoleService {

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveOrUpdate(Long operationId, Long userId, List<Long> roleIdList) {
        //删除现有角色
        this.baseMapper.deleteUserRoleByUserId(userId);
        //增加新角色
        Optional.ofNullable(roleIdList).ifPresent(roleIdLists->{
            roleIdLists.stream().forEach(roleId->{
                AdminUserRole adminUserRole = new AdminUserRole();
                adminUserRole.setUserId(userId);
                adminUserRole.setRoleId(roleId);
                adminUserRole.setIsDeleted("0");
                adminUserRole.setCreateDt(DateUtils.getTimestamp());
                adminUserRole.setCreateUserId(operationId);
                this.save(adminUserRole);
            });
        });
    }

    @Override
    public List<Long> queryRoleIdList(Long userId) {
        return baseMapper.queryRoleIdList(userId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteBatch(Long[] roleIds) {
        return baseMapper.deleteBatch(roleIds);
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteUpdateByUserId(Long[] userIds, Long operationId) {
//		先判断user_role中userId是否存在
        Timestamp modifyDt = DateUtils.getTimestamp();
        return baseMapper.deleteUpdateByUserId(userIds, operationId, modifyDt);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteUpdateByRoleId(Long[] roleIds, Long operationId) {
        //	先判断user_role中roleId是否存在
        Timestamp modifyDt = DateUtils.getTimestamp();
        return baseMapper.deleteUpdateByRoleId(roleIds, operationId, modifyDt);

    }

    @Override
    public Long getUserRoleId(Long userId, Long roleId) {
        return this.baseMapper.getUserRoleId(userId, roleId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer deleteUserRole(Long userId, Long operationId) {
        return this.baseMapper.deleteUserRole(userId, operationId, DateUtils.getTimestamp());
    }

    @Override
    public void addUserRole(Long loginUserId, AdminUser adminUser) {

        AdminUserRole adminUserRole = new AdminUserRole();
        adminUserRole.setUserId(adminUser.getUserId());
        adminUserRole.setRoleId(Long.valueOf(adminUser.getRoleRestaurantId()));
        adminUserRole.setIsDeleted("0");
        adminUserRole.setCreateDt(DateUtils.getTimestamp());
        adminUserRole.setCreateUserId(loginUserId);
        this.save(adminUserRole);

        //保存用户与专管员角色关系
        /*if (!StringUtils.isEmpty(adminUser.getRoleIcabAdmin())) {
            AdminUserRole adminUserRole = new AdminUserRole();
            adminUserRole.setUserId(adminUser.getUserId());
            adminUserRole.setRoleId(Long.valueOf(adminUser.getRoleIcabAdminId()));
            adminUserRole.setIsDeleted("0");
            adminUserRole.setCreateDt(DateUtils.getTimestamp());
            adminUserRole.setCreateUserId(loginUserId);
            adminUserRole.setObjectId(Long.valueOf(adminUser.getRoleIcabAdmin()));

            this.save(adminUserRole);
        }
        //保存用户与监管员角色关系
        if (!StringUtils.isEmpty(adminUser.getRoleIcabCommissioner())) {

            AdminUserRole adminUserRole = new AdminUserRole();
            adminUserRole.setUserId(adminUser.getUserId());
            adminUserRole.setRoleId(Long.valueOf(adminUser.getRoleIcabCommissionerId()));
            adminUserRole.setObjectId(Long.valueOf(adminUser.getRoleIcabCommissioner()));
            adminUserRole.setIsDeleted("0");
            adminUserRole.setCreateDt(DateUtils.getTimestamp());
            adminUserRole.setCreateUserId(loginUserId);

            this.save(adminUserRole);
        }
        //保存用户与工商所角色关系
        if (!StringUtils.isEmpty(adminUser.getRoleIcab())) {

            AdminUserRole adminUserRole = new AdminUserRole();
            adminUserRole.setUserId(adminUser.getUserId());
            adminUserRole.setRoleId(Long.valueOf(adminUser.getRoleIcabId()));
            adminUserRole.setObjectId(Long.valueOf(adminUser.getRoleIcab()));
            adminUserRole.setIsDeleted("0");
            adminUserRole.setCreateDt(DateUtils.getTimestamp());
            adminUserRole.setCreateUserId(loginUserId);

            this.save(adminUserRole);
        }
        //保存用户与商家角色关系
        if (!StringUtils.isEmpty(adminUser.getRoleRestaurant())) {

            AdminUserRole adminUserRole = new AdminUserRole();
            adminUserRole.setUserId(adminUser.getUserId());
            adminUserRole.setRoleId(Long.valueOf(adminUser.getRoleRestaurantId()));
            adminUserRole.setObjectId(Long.valueOf(adminUser.getRoleRestaurant()));
            adminUserRole.setIsDeleted("0");
            adminUserRole.setCreateDt(DateUtils.getTimestamp());
            adminUserRole.setCreateUserId(loginUserId);

            this.save(adminUserRole);

        }
        //保存用户与其它角色
        if (adminUser.getRoleOther() != null && adminUser.getRoleOther().size() > 0) {

            for (RoleOther tmp : adminUser.getRoleOther()) {

                AdminUserRole adminUserRole = new AdminUserRole();
                adminUserRole.setUserId(adminUser.getUserId());
                adminUserRole.setRoleId(Long.valueOf(tmp.getRoleId()));
                adminUserRole.setIsDeleted("0");
                adminUserRole.setCreateDt(DateUtils.getTimestamp());
                adminUserRole.setCreateUserId(loginUserId);

                this.save(adminUserRole);
            }
        }*/

    }

    @Override
    public void updateUserRole(Long loginUserId, AdminUser adminUser) {

        this.baseMapper.deleteUserRoleByUserId(adminUser.getUserId());

        //修改用户与专管员角色关系
        if (!StringUtils.isEmpty(adminUser.getRoleIcabAdmin())) {
            AdminUserRole adminUserRole = new AdminUserRole();

            adminUserRole.setUserId(adminUser.getUserId());
            adminUserRole.setRoleId(Long.valueOf(adminUser.getRoleIcabAdminId()));
            adminUserRole.setIsDeleted("0");
            adminUserRole.setCreateDt(DateUtils.getTimestamp());
            adminUserRole.setCreateUserId(loginUserId);
            adminUserRole.setObjectId(Long.valueOf(adminUser.getRoleIcabAdmin()));

            Long id = getUserRoleId(adminUserRole.getUserId(), adminUserRole.getRoleId());
            if (id != null) {
                adminUserRole.setId(id);
                this.updateById(adminUserRole);
            } else {
                this.save(adminUserRole);
            }

        }
        //修改用户与监管员角色关系
        if (!StringUtils.isEmpty(adminUser.getRoleIcabCommissioner())) {

            AdminUserRole adminUserRole = new AdminUserRole();
            adminUserRole.setUserId(adminUser.getUserId());
            adminUserRole.setRoleId(Long.valueOf(adminUser.getRoleIcabCommissionerId()));
            adminUserRole.setObjectId(Long.valueOf(adminUser.getRoleIcabCommissioner()));
            adminUserRole.setIsDeleted("0");
            adminUserRole.setCreateDt(DateUtils.getTimestamp());
            adminUserRole.setCreateUserId(loginUserId);

            Long id = getUserRoleId(adminUserRole.getUserId(), adminUserRole.getRoleId());
            if (id != null) {
                adminUserRole.setId(id);
                this.updateById(adminUserRole);
            } else {
                this.save(adminUserRole);
            }
        }
        //修改用户与工商所角色关系
        if (!StringUtils.isEmpty(adminUser.getRoleIcab())) {

            AdminUserRole adminUserRole = new AdminUserRole();
            adminUserRole.setUserId(adminUser.getUserId());
            adminUserRole.setRoleId(Long.valueOf(adminUser.getRoleIcabId()));
            adminUserRole.setObjectId(Long.valueOf(adminUser.getRoleIcab()));
            adminUserRole.setIsDeleted("0");
            adminUserRole.setCreateDt(DateUtils.getTimestamp());
            adminUserRole.setCreateUserId(loginUserId);

            Long id = getUserRoleId(adminUserRole.getUserId(), adminUserRole.getRoleId());
            if (id != null) {
                adminUserRole.setId(id);
                this.updateById(adminUserRole);
            } else {
                this.save(adminUserRole);
            }
        }
        //修改用户与商家角色关系
        if (!StringUtils.isEmpty(adminUser.getRoleRestaurant())) {

            AdminUserRole adminUserRole = new AdminUserRole();
            adminUserRole.setUserId(adminUser.getUserId());
            adminUserRole.setRoleId(Long.valueOf(adminUser.getRoleRestaurantId()));
            adminUserRole.setObjectId(Long.valueOf(adminUser.getRoleRestaurant()));
            adminUserRole.setIsDeleted("0");
            adminUserRole.setCreateDt(DateUtils.getTimestamp());
            adminUserRole.setCreateUserId(loginUserId);

            Long id = getUserRoleId(adminUserRole.getUserId(), adminUserRole.getRoleId());
            if (id != null) {
                adminUserRole.setId(id);
                this.updateById(adminUserRole);
            } else {
                this.save(adminUserRole);
            }
        }
        //修改用户与其它角色
        if (adminUser.getRoleOther() != null && adminUser.getRoleOther().size() > 0) {

            //先删除用户与角色关系
            /*Timestamp modifyDt = DateUtils.getTimestamp();
            Long[] userIds = new Long[1];
            userIds[0] = adminUser.getUserId();
            baseMapper.deleteUpdateByRoleId(userIds, loginUserId, modifyDt);*/


            for (RoleOther tmp : adminUser.getRoleOther()) {

                AdminUserRole adminUserRole = new AdminUserRole();
                adminUserRole.setUserId(adminUser.getUserId());
                adminUserRole.setRoleId(Long.valueOf(tmp.getRoleId()));
                adminUserRole.setIsDeleted("0");
                adminUserRole.setCreateDt(DateUtils.getTimestamp());
                adminUserRole.setCreateUserId(loginUserId);

                this.save(adminUserRole);

               /* Long id = getUserRoleId(adminUserRole.getUserId(), adminUserRole.getRoleId());
                if (id != null) {
                    this.updateById(adminUserRole);
                } else {
                    this.save(adminUserRole);
                }*/
            }
        }
    }

}
