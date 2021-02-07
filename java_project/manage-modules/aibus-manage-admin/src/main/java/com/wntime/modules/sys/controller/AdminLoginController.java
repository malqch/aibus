package com.wntime.modules.sys.controller;

import com.wntime.common.utils.R;
import com.wntime.common.utils.ShiroUtils;
import com.wntime.controller.AbstractController;
import com.wntime.entity.AdminUser;
import com.wntime.modules.sys.dao.AdminAuthObjectDao;
import com.wntime.modules.sys.entity.AdminRight;
import com.wntime.modules.sys.entity.AdminRole;
import com.wntime.modules.sys.form.SysLoginForm;
import com.wntime.modules.sys.service.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.io.IOUtils;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 登录相关
 */
@Api(value = "登录controller", tags = {"用户登录接口"})
@RestController
public class AdminLoginController extends AbstractController {
    @Autowired
    private AdminUserService adminUserService;
    @Autowired
    private AdminUserTokenService adminUserTokenService;
    @Autowired
    private AdminCaptchaService adminCaptchaService;

    @Autowired
    private AdminRoleService adminRoleService;



    @ApiOperation(value = "获取验证码", notes = "", httpMethod = "GET")
    @GetMapping("captcha.jpg")
    public void captcha(HttpServletResponse response, String uuid) throws IOException {
        response.setHeader("Cache-Control", "no-store, no-cache");
        response.setContentType("image/jpeg");

        //获取图片验证码
        BufferedImage image = adminCaptchaService.getCaptcha(uuid);

        ServletOutputStream out = response.getOutputStream();
        ImageIO.write(image, "jpg", out);
        IOUtils.closeQuietly(out);
    }

    /**
     * 登录
     */
    @ApiOperation(value = "登录", notes = "", httpMethod = "POST")
    @PostMapping("/sys/login")
    public R login(@RequestBody SysLoginForm form) throws IOException {
        boolean captcha = adminCaptchaService.validate(form.getUuid(), form.getCaptcha());
        if (!captcha) {
            return R.error("验证码不正确");
        }

        //用户信息
        AdminUser user = adminUserService.queryByLoginName(form.getLoginName());

        //账号不存在、密码错误
        if (user == null || !user.getPassword().equals(new Sha256Hash(form.getPassword(), user.getSalt()).toHex())) {
            return R.error("账号或密码不正确");
        }

        //账号锁定
        if ("0".equals(user.getIsEnabled())) {
            return R.error("账号已被锁定,请联系管理员");
        }



        //生成token，并保存到数据库
        R r = adminUserTokenService.createToken(user);

        List<AdminRole> list = adminRoleService.selectUserRole(user.getUserId());

        List<Map<String, Object>> roleMapList = new ArrayList<>();
        if (list != null && list.size() > 0) {
            List<Long> roleIds = new ArrayList<>();
            for (AdminRole adminRole : list) {
                Map<String, Object> map = new HashMap<>();
                map.put("roleId", adminRole.getRoleId());
                roleIds.add(adminRole.getRoleId());
                map.put("name", adminRole.getName());
                roleMapList.add(map);
            }
            ShiroUtils.getSession().setAttribute("roleIds", roleIds);
        }
        //设置车厂ID
//        r.put("factoryId", "1202512039172374566");       //测试车厂
        r.put("factoryId", "1304612139821957122");       //延吉车厂
        r.put("roleList", roleMapList);

        return r;
    }

    /**
     * 获取公共服务Token
     */
    @ApiOperation(value = "获取公共服务Token", notes = "获取公共服务Token", httpMethod = "POST")
    @PostMapping("/sys/public/token")
    public R loginGetToken(@RequestBody SysLoginForm form) throws IOException {
        //用户信息
        AdminUser user = adminUserService.queryByLoginName(form.getLoginName());

        //账号不存在、密码错误
        if (user == null || !user.getPassword().equals(new Sha256Hash(form.getPassword(), user.getSalt()).toHex())) {
            return R.error("账号或密码不正确");
        }

        //账号锁定
        if ("0".equals(user.getIsEnabled())) {
            return R.error("账号已被锁定,请联系管理员");
        }

        //生成token，并保存到数据库
        R r = adminUserTokenService.createToken(user);
        return r;
    }


    /**
     * 退出
     */
    @ApiOperation(value = "退出", notes = "", httpMethod = "POST")
    @PostMapping("/sys/logout")
    public R logout(@RequestHeader("token") String token) {
        adminUserTokenService.logout(token);
        return R.ok();
    }

}
