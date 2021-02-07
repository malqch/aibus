package com.wntime.advert.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wntime.advert.vo.*;
import com.wntime.common.utils.PageUtils;
import com.wntime.advert.entity.OrderAdvertiseDeliveryEntity;
import com.wntime.entity.AdminUser;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * @desc 广告投放单
 *
 * @author ysc
 * @email example@gmail.com
 * @date 2020-11-05 14:17:58
 */
public interface OrderAdvertiseDeliveryService extends IService<OrderAdvertiseDeliveryEntity> {

    PageUtils queryPage(Map<String, Object> params);

    List<AdvertiseCardVo> list(String advertiseNo, String group, String code, AdminUser user);

    void saveVo(AdvertiseVo advertiseVo, MultipartFile[] rectangleFiles, MultipartFile[] squareFiles, Long userId);

    void updateVo(AdvertiseVo advertiseVo, MultipartFile[] rectangleFiles, MultipartFile[] squareFiles,Long userId);

    AdvertiseInfoVo getVo(Long advertiseId);

    AdvertiseDetailVo getDetailVo(Long advertiseId);

    void audit(AuditVo auditVo, Long userId);

    void delete(Long advertiseId,Long userId);

    void saveInterruptVo(AdvertiseVo advertiseVo, MultipartFile[] rectangleFiles, MultipartFile[] squareFiles, Long userId);

    void updateInterruptVo(AdvertiseVo advertiseVo, MultipartFile[] rectangleFiles, MultipartFile[] squareFiles, Long userId);

    AdvertiseInfoVo getInterruptVo(Long advertiseId);

}

