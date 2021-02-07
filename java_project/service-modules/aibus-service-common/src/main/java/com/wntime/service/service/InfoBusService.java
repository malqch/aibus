package com.wntime.service.service;

import com.wntime.common.utils.PageUtils;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wntime.service.common.entity.InfoBusEntity;
import com.wntime.service.common.vo.StatisticsResultVo;
import com.wntime.service.common.vo.BusStatusVO;
import com.wntime.service.vo.AfterSalesBusInfoVO;
import com.wntime.service.vo.AfterSalesMaintainBusInfoVO;
import com.wntime.service.vo.BusVo;
import com.wntime.service.vo.ProgressBarItem;

import java.util.List;
import java.util.Map;

/**
 * @desc 公交车表
 *
 * @date 2020-08-25 14:28:17
 */
public interface InfoBusService extends IService<InfoBusEntity> {

    PageUtils queryPage(Map<String, Object> params);

    InfoBusEntity getDetailById(Long id);

    void delById(Long id, Long userId);

    void deleteBatch(String[] ids, Long userId);

    List<InfoBusEntity> getAll();

    /**
     * 查询统计事件数量（指定地区：可燃气体、重点人员、交通违章、特种卡违规）
     * @param params
     * @return
     */
    List<StatisticsResultVo> getBusEventStatByAreaId(Map<String, Object> params);

    List<String> getVinListByFaultTypeIdAndCompanyId(Long faultTypeId, Long companyId);

    List<String> getVinListByBusStatusAndCompanyId(Long busStatus, Long companyId);

    List<String> getVinListByCompanyId(Long companyId);

    List<String> getPlateCodeListByCompanyId(Long companyId);

    List<InfoBusEntity> getVinNoDeliveryList();

    void saveBus(InfoBusEntity infoBusEntity);

    void updateBus(InfoBusEntity infoBusEntity);

    PageUtils<AfterSalesBusInfoVO> getBusPageByStatusAndCompanyIdAndVin(Long busStatusId, Long companyId, String vin, Integer currentPage, Integer pageSize);

    PageUtils<AfterSalesMaintainBusInfoVO> getMaintainBusPageAndCompanyIdAndVin(Long busStatusId, Long companyId, String vin, Integer currentPage, Integer pageSize);

    AfterSalesMaintainBusInfoVO getAfterSalesMaintainBusInfo(String vin);

    AfterSalesBusInfoVO getAfterSalesBusInfo(String vin);

    List<BusStatusVO> getBusStatusList();

    BusStatusVO getBusStatusByCode(String busStatusCode);

    BusStatusVO getBusStatusById(Long busStatusId);

    int changeBusStatus(Long busId, Long busStatusId);

    BusVo queryBusByVin(String vinCode);

    long getBusCountByCompanyId(Long companyId);

    List<ProgressBarItem> getBusStatus(Long busCompanyId);

    List<Map<String, Object>> countByRunStatusAndCompanyId(Long companyId);

    List<Map<String, Object>> countByBusStatusAndCompanyId(Long companyId);
}

