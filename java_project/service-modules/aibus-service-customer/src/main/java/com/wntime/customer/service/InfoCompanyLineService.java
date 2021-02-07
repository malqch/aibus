package com.wntime.customer.service;

import com.wntime.common.utils.PageUtils;
import com.wntime.common.validator.ValidatorUtils;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wntime.customer.entity.InfoCompanyLineEntity;
import com.wntime.customer.vo.AdvertlineVo;

import java.util.List;
import java.util.Map;

/**
 * @desc 公交线路表
 *
 * @date 2020-08-25 14:04:05
 */
public interface InfoCompanyLineService extends IService<InfoCompanyLineEntity> {

    void delById(Long id, Long userId);

    void deleteBatch(String[] ids, Long userId);

    PageUtils queryPage(Map<String, Object> params);

    InfoCompanyLineEntity getDetailById(Long id);

    InfoCompanyLineEntity getDetailInfoById(Long id);

    List<InfoCompanyLineEntity> getCompanyLineByCompanyId(Long companyId);

    PageUtils getCompanyLinePageByCompanyId(Map<String, Object> params);

    void updateCompanyLine(InfoCompanyLineEntity infoCompanyLineEntity);

    void saveCompanyLine(InfoCompanyLineEntity infoCompanyLineEntity);

    void saveCompanyLineBatch(InfoCompanyLineEntity infoCompanyLineEntity);

    List<AdvertlineVo> getCompanyLineCodeByCompanyIdList(List<Long> companyIdList);
}

