

package com.wntime.modules.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wntime.common.utils.PageUtils;
import com.wntime.entity.AdminUser;
import com.wntime.modules.sys.form.AdminUserFrom;
import com.wntime.modules.sys.vo.AdminUserVo;
import com.wntime.modules.sys.vo.UserNameAndId;

import java.io.Serializable;
import java.util.List;
import java.util.Map;


/**
 * 系统用户
 */
public interface AdminUserService extends IService<AdminUser> {

    PageUtils queryPage(Map<String, Object> params);

    /**
     * 查询用户的所有权限
     *
     * @param userId 用户ID
     */
    List<String> queryAllPerms(Long userId);

    @Override
    AdminUser getById(Serializable userId);

    /**
     * 查询用户的所有菜单ID
     */
    List<Long> queryAllRightId(Long userId);

    /**
     * 根据用户名，查询系统用户
     */
    AdminUser queryByLoginName(String loginName);

    /**
     * 保存用户
     */
    void saveUser(AdminUser user, Long loginUserId);


    void addUser(AdminUserFrom userFrom,long loginUserId);
    /**
     * 修改用户
     */
    void update(AdminUser user, Long loginUserId);


    void updateUser(AdminUserFrom user, Long loginUserId);
    /**
     * 删除用户
     */
    void deleteBatch(Long[] userIds, Long userId);

    /**
     * 主要是删除用户功能，跟新角色表的is_deleted字段
     */
    int deleteUpdate(Long[] userIds, Long operationId);


    /**
     * 修改密码
     *
     * @param userId      用户ID
     * @param password    原密码
     * @param newPassword 新密码
     */
    boolean updatePassword(Long userId, String password, String newPassword);

    PageUtils queryPageList(Map<String, Object> params);

    List<AdminUser> queryUserIsExist(AdminUser user);

    List<Long> queryAllPositionId(Long positionId, Long createUserId);

    List<UserNameAndId> queryUserList();

    AdminUserVo queryUserInfo(long userId);
}
