package com.wntime.controller;

import com.alibaba.fastjson.JSONObject;
import com.wntime.common.utils.ShiroUtils;
import com.wntime.entity.AdminUser;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.apache.shiro.session.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * Controller公共组件
 */
public abstract class AbstractController {
    protected Logger logger = LoggerFactory.getLogger(getClass());

    protected AdminUser getUser() {
        return (AdminUser) SecurityUtils.getSubject().getPrincipal();
    }

    protected Long getUserId() {
        return getUser().getUserId();
    }


    protected Map returnResult() {
        Session session = ShiroUtils.getSession();
        Object roleId = session.getAttribute("roleId");
        Object roleName = session.getAttribute("roleName");
        Object areaId = session.getAttribute("areaId");
        Object areaName = session.getAttribute("areaName");
        Map<String, Object> r = new LinkedHashMap<>();
        Optional.ofNullable(roleId)
                .ifPresent(role -> {
                    r.put("role", String.valueOf(role));
                });
        Optional.ofNullable(roleName)
                .ifPresent(role -> {
                    r.put("roleName", role);
                });
        Optional.ofNullable(areaId)
                .ifPresent(aid -> {
                    r.put("areaId", String.valueOf(aid));
                });
        Optional.ofNullable(areaName)
                .ifPresent(aName -> {
                    r.put("areaName", aName);
                });
        return r;
    }

    protected boolean validatePassword(String password) {
        AdminUser user = getUser();
        if (user == null || !user.getPassword().equals(new Sha256Hash(password, user.getSalt()).toHex())) {
            return false;
        }
        return true;
    }
    protected Object getAuth(){
        Session session = SecurityUtils.getSubject().getSession();
        return session.getAttribute("positionAuth");
    }
}
