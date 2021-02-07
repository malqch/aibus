

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
import com.wntime.datasource.annotation.DataSource;
import com.wntime.entity.AdminUser;
//import com.wntime.modules.info.service.IcabCommissionerService;
import com.wntime.modules.sys.dao.AdminUserDao;
import com.wntime.modules.sys.form.AdminUserFrom;
import com.wntime.modules.sys.service.*;
import com.wntime.modules.sys.vo.AdminPositionAuthInfoVo;
import com.wntime.modules.sys.vo.AdminUserVo;
import com.wntime.modules.sys.vo.UserNameAndId;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.*;


/**
 * 系统用户
 */
@Service("adminUserService")
public class AdminUserServiceImpl extends ServiceImpl<AdminUserDao, AdminUser> implements AdminUserService {
    @Autowired
    private AdminUserRoleService adminUserRoleService;
    @Autowired
    private AdminRoleService adminRoleService;
    //@Resource
//    private IcabCommissionerService icabCommissionerService;
    @Autowired
    private AdminPositionService adminPositionService;
    @Autowired
    private AdminUserPositionService adminUserPositionService;
    @Resource
    private AdminPositionAuthService adminPositionAuthService;

    @Override
    public AdminUser getById(Serializable userId) {

        QueryWrapper<AdminUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        queryWrapper.eq("is_deleted", "0").eq("is_enabled", "1");
        return this.baseMapper.selectOne(queryWrapper);
    }

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        //根据创建人来查询
        String username = (String) params.get("name");
        Long createUserId = (Long) params.get("createUserId");
        IPage<AdminUser> page = this.page(
                new Query<AdminUser>().getPage(params),
                new QueryWrapper<AdminUser>().eq("is_deleted", "0").eq("is_enabled", "1")
                        .like(StringUtils.isNotBlank(username), "user_name", username)
                        .eq(createUserId != null, "create_user_id", createUserId)
        );

        return new PageUtils(page);
    }

    //多表关联查询
    @Override
    public PageUtils queryPageList(Map<String, Object> params) {

        Integer currPage = Integer.parseInt((String) params.get("page"));
        Integer pageSize = Integer.parseInt((String) params.get("limit"));
        Page<Object> page = new Page<>(currPage, pageSize);
        params.put("page", page);

        return new PageUtils(page.setRecords(this.baseMapper.queryAdminUserList(params)));
    }

    @Override
    public List<AdminUser> queryUserIsExist(AdminUser user) {
        return this.baseMapper.queryUserIsExist(user);
    }

    @Override
    public List<Long> queryAllPositionId(Long positionId, Long createUserId) {
        return this.baseMapper.queryAllPositionId(positionId, createUserId);
    }

    @Override
    public List<String> queryAllPerms(Long userId) {
        return baseMapper.queryAllPerms(userId);
    }

    @Override
    public List<Long> queryAllRightId(Long userId) {
        return baseMapper.queryAllMenuId(userId);
    }

    @Override
    public AdminUser queryByLoginName(String loginName) {
        return baseMapper.queryByLoginName(loginName);
    }

    @Override
    @Transactional
    @Deprecated
    public void saveUser(AdminUser user, Long loginUserId) {
        user.setCreateDt(DateUtils.getTimestamp());
        //sha256加密
        String salt = RandomStringUtils.randomAlphanumeric(20);
        user.setPassword(new Sha256Hash(user.getPassword(), salt).toHex());
        user.setSalt(salt);
        this.save(user);

        //检查角色是否越权
        //checkRole(user,createUserId);

        //检查职位是否越权
        //checkPosition(user,createUserId);

        //保存用户与角色关系
        adminUserRoleService.addUserRole(loginUserId, user);
        //adminUserRoleService.saveOrUpdate(createUserId, user.getUserId(), user.getRoleIdList());

        //保存用户与职位关系
        //adminUserPositionService.saveOrUpdate(user.getCreateUserId(), user.getUserId(), user.getPositionIdList());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addUser(AdminUserFrom user, long loginUserId) {
        AdminUser existUser = this.baseMapper.queryByLoginName(user.getLoginName());
        if (existUser != null) {
            throw new RRException("用户名已存在！");
        }
        AdminUser adminUser = new AdminUser();
        BeanUtils.copyProperties(user, adminUser);
        adminUser.setUserId(null);
        adminUser.setCreateDt(DateUtils.getTimestamp());
        //sha256加密
        String salt = RandomStringUtils.randomAlphanumeric(20);
        adminUser.setPassword(new Sha256Hash(user.getPassword(), salt).toHex());
        adminUser.setSalt(salt);
        adminUser.setIsDeleted("0");
        adminUser.setIsEnabled("1");
        adminUser.setCreateUserId(loginUserId);
        this.save(adminUser);

        //保存用户与角色关系
        adminUserRoleService.saveOrUpdate(loginUserId, adminUser.getUserId(), user.getRoleIdList());
        //保存用户与岗位
        adminUserPositionService.saveOrUpdate(loginUserId, adminUser.getUserId(), user.getPositionIdList());

    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateUser(AdminUserFrom user, Long loginUserId) {
        AdminUser adminUser = this.queryByLoginName(user.getLoginName());
        BeanUtils.copyProperties(user, adminUser);
        adminUser.setModifyDt(DateUtils.getTimestamp());
        adminUser.setModifyUserId(loginUserId);
        if (StringUtils.isBlank(user.getPassword())) {
            adminUser.setPassword(null);
        } else {
            //查出salt
            adminUser.setPassword(new Sha256Hash(user.getPassword(), adminUser.getSalt()).toHex());
        }
        this.updateById(adminUser);

        //保存用户与角色关系
        adminUserRoleService.saveOrUpdate(loginUserId, adminUser.getUserId(), user.getRoleIdList());
        //保存用户与职位关系
        adminUserPositionService.saveOrUpdate(loginUserId, user.getUserId(), user.getPositionIdList());
    }

    @Override
    @Transactional
    public void update(AdminUser user, Long loginUserId) {
        if (StringUtils.isBlank(user.getPassword())) {
            user.setPassword(null);
        } else {
            //查出salt
            AdminUser adminUser = this.queryByLoginName(user.getLoginName());
            user.setPassword(new Sha256Hash(user.getPassword(), adminUser.getSalt()).toHex());
        }
        this.updateById(user);

        //检查角色是否越权
        //checkRole(user, createUserId);
        //checkPosition(user, createUserId);
        //保存用户与角色关系
        //adminUserRoleService.saveOrUpdate(user.getCreateUserId(), user.getUserId(), user.getRoleIdList());
        //保存用户与职位关系
        //adminUserPositionService.saveOrUpdate(user.getCreateUserId(), user.getUserId(), user.getPositionIdList());

        adminUserRoleService.updateUserRole(loginUserId, user);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteBatch(Long[] userIds, Long userId) {
        //this.removeByIds(Arrays.asList(userId));
        //此时不是真的删除，只是在数据库中更改字段，变为不可用
        //1、修改gfm_admin_user字段
        //更新user表
        this.deleteUpdate(userIds, userId);
        //删除用户角色
        adminUserRoleService.deleteUpdateByUserId(userIds, userId);
        //删除用户岗位
        adminUserPositionService.deleteUpdateByUserId(userIds, userId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteUpdate(Long[] userIds, Long operationId) {
        return this.baseMapper.deleteUpdateByUserId(userIds, operationId, DateUtils.getTimestamp());
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updatePassword(Long userId, String password, String newPassword) {
        AdminUser userEntity = new AdminUser();
        userEntity.setPassword(newPassword);
        return this.update(userEntity,
                new QueryWrapper<AdminUser>().eq("user_id", userId).eq("password", password));
    }


    @Override
    @DataSource("read")
    public List<UserNameAndId> queryUserList() {
        return baseMapper.queryUserNameIds();
    }

    @Override
    public AdminUserVo queryUserInfo(long userId) {
        return this.baseMapper.queryUserById(userId).orElseThrow(() -> new RRException("查询用户不存在"));
    }

    /**
     * 检查角色是否越权
     */
    private void checkRole(AdminUser user, Long createUserId) {
        if (user.getRoleIdList() == null || user.getRoleIdList().size() == 0) {
            return;
        }
        //如果不是超级管理员，则需要判断用户的角色是否自己创建
        if (createUserId == Constant.SUPER_ADMIN) {
            return;
        }

        //查询登录用户下所有的角色，包括自己创建和自己拥有的
        List<Long> roleIdList = adminRoleService.queryRoleIdList(user.getUserId(), createUserId);

        //判断是否越权
        if (!roleIdList.containsAll(user.getRoleIdList())) {
            throw new RRException("用户所选角色，不是本人创建");
        }
    }

    /**
     * 检查职位是否越权
     */
    private void checkPosition(AdminUser user, Long createUserId) {
        if (user.getPositionIdList() == null || user.getPositionIdList().size() == 0) {
            return;
        }
        //如果不是超级管理员，则需要判断用户的职位是否自己创建或者自己拥有的
        if (createUserId == Constant.SUPER_ADMIN) {
            return;
        }

        //查询用户创建和自己拥有的角色列表
        List<Long> positionIdList = adminPositionService.queryPositionIdList(user.getUserId(), createUserId);
        //List<Long> roleIdList = adminRoleService.queryRoleIdList(user.getCreateUserId());

        //判断是否越权
        if (!positionIdList.containsAll(user.getPositionIdList())) {
            throw new RRException("用户所选岗位，不是本人创建或本人没有该岗位");
        }
    }


    public static void main(String[] args) {
        System.out.println(new Sha256Hash("123456", "123454321w").toHex());
    }
}
