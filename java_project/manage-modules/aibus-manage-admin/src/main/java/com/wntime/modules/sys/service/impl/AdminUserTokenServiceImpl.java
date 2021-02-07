package com.wntime.modules.sys.service.impl;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.toolkit.SystemClock;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wntime.common.oauth2.TokenGenerator;
import com.wntime.common.utils.R;
import com.wntime.common.utils.ShiroUtils;
import com.wntime.entity.AdminUser;
import com.wntime.modules.sys.dao.AdminRoleDao;
import com.wntime.modules.sys.dao.AdminUserTokenDao;
import com.wntime.modules.sys.entity.AdminRight;
import com.wntime.modules.sys.entity.AdminUserToken;
import com.wntime.modules.sys.service.AdminRightService;
import com.wntime.modules.sys.service.AdminUserPositionAuthService;
import com.wntime.modules.sys.service.AdminUserTokenService;
import com.wntime.modules.sys.vo.UserPositionVo;
import com.wntime.modules.sys.vo.UserRoleVo;
import org.apache.shiro.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;


@Service("adminUserTokenService")
public class AdminUserTokenServiceImpl extends ServiceImpl<AdminUserTokenDao, AdminUserToken> implements AdminUserTokenService {
    //token 过期时间
    private int expire_time;
    @Autowired
    private AdminRightService adminRightService;
    @Resource
    private AdminRoleDao adminRoleDao;
    @Resource
    private AdminUserPositionAuthService adminUserPositionAuthService;

    @Override
    public R createToken(AdminUser user) {

        //获取登录用户岗位
        List<UserPositionVo> auth;
        if (user.getUserId() == 1L) {
            UserPositionVo vo = new UserPositionVo();
            vo.setSystemAuth("manage");
            auth = Arrays.asList(vo);
        } else {
            auth = adminUserPositionAuthService.queryUserPositionAuth(user.getUserId());
        }

        //判断登录用户岗位是否包含branch、group、factory,包含则过期时间为两年
        boolean findMonitor = false;
        if (auth.stream().anyMatch(item -> item.getSystemAuth().equals("monitor")
                || item.getSystemAuth().equals("branch")
                || item.getSystemAuth().equals("group")
                || item.getSystemAuth().equals("factory")
                || item.getSystemAuth().equals("public"))) {
            findMonitor = true;
        }
        if(!findMonitor){
            //查询用户拥有的菜单
            List<AdminRight> rightList = adminRightService.getUserRightList(user.getUserId());
            if (rightList == null || rightList.size() < 1) {
                return R.error("此用户没有登录权限!");
            }
        }

        Session session = ShiroUtils.getSession();
        String token = session.getId().toString();

        //保存token到数据库
        Date now = new Date();
        Date expireTime = new Date(now.getTime() + expire_time * 60 * 1000);
        Date twoYearLater = DateUtil.offsetMonth(new Date(), 24);
        AdminUserToken tokenEntity = new AdminUserToken();
        tokenEntity.setUserId(user.getUserId());
        tokenEntity.setToken(token);
        tokenEntity.setUpdateTime(now);
        tokenEntity.setExpireTime(findMonitor ? twoYearLater : expireTime);
        this.saveOrUpdate(tokenEntity);

        List<Long> areaOrgIds = new ArrayList<Long>();
        auth.forEach(e ->{
            areaOrgIds.add(e.getAreaOrgId());
        });
        user.setAreaOrgIds(areaOrgIds);

        session.setTimeout(expire_time * 60 * 1000);
        session.setAttribute("user", user);
        session.setAttribute("positionAuth", auth);
        R r = R.ok().put("token", session.getId()).put("expire", expire_time * 60).put("positionAuth", auth);
        return r;
    }

    @Override
    public void logout(String token) {
        getBaseMapper().deleteToken(token);
        ShiroUtils.getSubject().associateWith(() -> {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            ShiroUtils.getSubject().logout();
            ShiroUtils.deleteSession(token);
        });
    }

    @Override
    public R createHomeToken(AdminUser user) {
        //验证当前用户是否已经登录到系统
        Date now = new Date();
        AdminUserToken tokenEntity = this.getById(user.getUserId());
		/*if(tokenEntity!=null){
			Session userSession = ShiroUtils.getSession(tokenEntity.getToken());
			if(userSession!=null){
				return R.error("当前用户已登录");
			}
		}
*/
        List<UserRoleVo> roleNames = adminRoleDao.queryHomeUserRoleName(user.getUserId());

        Session session = ShiroUtils.getSession();
        String token = session.getId().toString();
        session.setAttribute("user", user);
        Optional.ofNullable(roleNames).ifPresent(roles -> {
            session.setAttribute("role", roleNames.stream().filter(s -> s.getRoleName() != null).collect(Collectors.toList()));
        });
        long oneYear = 365 * 1000 * 24 * 60 * 60l;
        Date expireTime = new Date(SystemClock.now() + oneYear);
        if (tokenEntity == null) {
            tokenEntity = new AdminUserToken();
            tokenEntity.setUserId(user.getUserId());
            tokenEntity.setToken(token);
            tokenEntity.setUpdateTime(now);
            tokenEntity.setExpireTime(expireTime);
            this.save(tokenEntity);
        } else {
            ShiroUtils.deleteSession(tokenEntity.getToken());
            tokenEntity.setToken(token);
            tokenEntity.setUpdateTime(now);
            tokenEntity.setExpireTime(expireTime);
            this.updateById(tokenEntity);

        }
        session.setTimeout(oneYear);

        List<UserPositionVo> auth = adminUserPositionAuthService.queryUserPositionAuth(user.getUserId());
        session.setAttribute("positionAuth", auth);
        R r = R.ok().put("token", session.getId()).put("expire", oneYear).put("positionAuth", auth);
        return r;
    }

    @Override
    public void updateTokenTime(AdminUserToken tokenEntity) {

        List<UserPositionVo> auth = adminUserPositionAuthService.queryUserPositionAuth(tokenEntity.getUserId());
        //判断登录用户岗位是否包含branch、group、factory,包含则过期时间为两年
        boolean findMonitor = false;
        if (auth.stream().anyMatch(item -> item.getSystemAuth().equals("monitor")
                || item.getSystemAuth().equals("branch")
                || item.getSystemAuth().equals("group")
                || item.getSystemAuth().equals("factory")
                || item.getSystemAuth().equals("public"))) {
            findMonitor = true;
        }
        //保存token到数据库
        Date now = new Date();
        Date expireTime = new Date(now.getTime() + expire_time * 60 * 1000);
        Date twoYearLater = DateUtil.offsetMonth(new Date(), 24);
        tokenEntity.setUpdateTime(now);
        tokenEntity.setExpireTime(findMonitor ? twoYearLater : expireTime);
        updateById(tokenEntity);
    }


    @Value("${token.expire-time}")
    public void setExpireTime(int expireTime) {
        this.expire_time = expireTime;
    }
}
