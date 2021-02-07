package com.wntime.service.common.service;

import com.wntime.service.common.vo.AreaVo;
import com.wntime.service.common.vo.BusCompanyBaseInfoVo;

import java.util.List;

public interface BusCompanyService {

    List<BusCompanyBaseInfoVo> queryCompanyList(long companyId);

    /**
     * 查询登录用户岗位下公司
     * 管理员：所有
     * 普通用户：岗位下公司
     * @return
     */
    List<Long> getUserCompanyIdList(Long userId);

    int queryCompanyBusCount(long companyId);

    AreaVo queryCompanyAreaInfo(long companyId);
}
