package com.wntime.advert.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wntime.advert.vo.AdvertisePositionVo;
import com.wntime.common.utils.PageUtils;
import com.wntime.advert.entity.InfoAdvertisePositionEntity;

import java.util.List;
import java.util.Map;

/**
 * ${comments}
 *
 * @author ysc
 * @email example@gmail.com
 * @date 2020-11-05 14:17:58
 */
public interface InfoAdvertisePositionService extends IService<InfoAdvertisePositionEntity> {

    PageUtils queryPage(Map<String, Object> params);

    List<AdvertisePositionVo> listAll();

    AdvertisePositionVo getOne(Long id);

    AdvertisePositionVo getOneByCode(String code);
}

