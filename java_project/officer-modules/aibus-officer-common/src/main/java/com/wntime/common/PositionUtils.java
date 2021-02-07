package com.wntime.common;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wntime.common.utils.SpringContextUtils;
import com.wntime.entity.AdminUser;
import com.wntime.modules.sys.entity.AdminPositionAuth;
import com.wntime.modules.sys.service.AdminPositionAuthService;
import com.wntime.modules.sys.service.AdminUserPositionService;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class PositionUtils {


    public static Set<Long> getSchoolIds(AdminUser user) {
        List<Long> positionIdList = SpringContextUtils.getBean(AdminUserPositionService.class).queryPositionIdList(user.getUserId());
        QueryWrapper<AdminPositionAuth> authQueryWrapper = new QueryWrapper<>();
        authQueryWrapper.in("position_id", positionIdList);
        authQueryWrapper.eq("is_deleted", "0");
        List<AdminPositionAuth> authVos = SpringContextUtils.getBean(AdminPositionAuthService.class).list(authQueryWrapper);
        Set<Long> longSet = authVos.stream().map(vo -> vo.getCompanyId()).collect(Collectors.toSet());
        return longSet;
    }
}
