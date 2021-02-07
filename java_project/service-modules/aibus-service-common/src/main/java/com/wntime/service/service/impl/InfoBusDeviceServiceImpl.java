package com.wntime.service.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.SystemClock;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wntime.common.utils.Constant;
import com.wntime.common.utils.DateUtils;
import com.wntime.common.utils.PageUtils;
import com.wntime.service.common.service.BusDeviceService;
import com.wntime.service.common.vo.BusDeviceStatusVo;
import com.wntime.service.dao.InfoBusDeviceDao;
import com.wntime.service.dao.LogBusDeviceDao;
import com.wntime.service.entity.InfoBusDeviceEntity;
import com.wntime.service.entity.LogBusDeviceEntity;
import com.wntime.service.form.DeviceUploadForm;
import com.wntime.service.region.BusDevice;
import com.wntime.service.region.BusDeviceKey;
import com.wntime.service.repo.BusDeviceRepository;
import com.wntime.service.service.InfoBusDeviceService;
import com.wntime.service.vo.DeviceUploadItem;
import com.wntime.service.vo.UploadDeviceVo;
import org.apache.geode.cache.CacheTransactionManager;
import org.apache.geode.cache.GemFireCache;
import org.springframework.beans.BeanUtils;
import org.springframework.data.gemfire.repository.Wrapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@Service("infoBusDeviceService")
public class InfoBusDeviceServiceImpl extends ServiceImpl<InfoBusDeviceDao, InfoBusDeviceEntity> implements InfoBusDeviceService, BusDeviceService {

    @Resource
    private BusDeviceRepository busDeviceRepository;
    @Resource
    private LogBusDeviceDao logBusDeviceDao;
    @Resource
    private GemFireCache gemFireCache;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        int currPage = 1;
        int pageSize = 20;
        if (params.get("page") != null) {
            currPage = Integer.parseInt((String) params.get("page"));
        }
        if (params.get("page") != null) {
            pageSize = Integer.parseInt((String) params.get("limit"));
        }
        Page<InfoBusDeviceEntity> page = new Page<>(currPage, pageSize);
        params.put("page", page);
        return new PageUtils(page.setRecords(this.baseMapper.queryPageList(params)));
    }

    @Override
    public List<InfoBusDeviceEntity> queryListByBusId(Map<String, Object> params) {
        if(params.get("busId") != null){
            params.put("busId",Long.parseLong(String.valueOf(params.get("busId"))));
        }
        return this.baseMapper.queryPageList(params);
    }

    @Override
    public InfoBusDeviceEntity getDetailById(Long id) {
        return this.getById(id);
    }

    @Override
    public void save(InfoBusDeviceEntity infoBusDeviceEntity, Long userId) {
        infoBusDeviceEntity.setBusDeviceId(null);
        infoBusDeviceEntity.setIsDeleted(0);
        infoBusDeviceEntity.setCreatedDate(DateUtils.getTimestamp());
        infoBusDeviceEntity.setCreatedBy(userId);
        this.save(infoBusDeviceEntity);
    }

    @Override
    public void updateById(InfoBusDeviceEntity infoBusDeviceEntity, Long userId) {
        infoBusDeviceEntity.setModifiedBy(userId);
        infoBusDeviceEntity.setModifiedDate(DateUtils.getTimestamp());
        this.updateById(infoBusDeviceEntity);
    }

    @Override
    public void delById(Long id, Long userId) {
        InfoBusDeviceEntity infoBusDeviceEntity = new InfoBusDeviceEntity();
        infoBusDeviceEntity.setBusDeviceId(id);
        infoBusDeviceEntity.setIsDeleted(1);
        infoBusDeviceEntity.setModifiedBy(userId);
        infoBusDeviceEntity.setModifiedDate(DateUtils.getTimestamp());
        this.updateById(infoBusDeviceEntity);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteBatch(String[] ids, Long userId) {
        for (String id : ids) {
            Long busDeviceId = Long.valueOf(id);
            Long busId = getBaseMapper().queryDeviceBusId(busDeviceId);
            this.delById(busDeviceId, userId);
            busDeviceRepository.deleteById(new BusDeviceKey(busId,busDeviceId));
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateDeviceStatus(InfoBusDeviceEntity infoBusDeviceEntity) {
        infoBusDeviceEntity.setModifiedDate(DateUtils.getTimestamp());
        updateById(infoBusDeviceEntity);
        int status=infoBusDeviceEntity.getDeviceStatus();
        AtomicInteger oldStatus=new AtomicInteger(-1);
//        BusDeviceKey key = new BusDeviceKey(infoBusDeviceEntity.getBusId(),infoBusDeviceEntity.getBusDeviceId());
//        Optional<BusDevice> optional = busDeviceRepository.findById(key);
//        optional.ifPresent(busDevice -> {
//            oldStatus.set(busDevice.getDeviceStatus());
//            busDevice.setDeviceStatus(status);
//            busDevice.setUpdateTime(new Date());
//            busDeviceRepository.save(new Wrapper<>(busDevice,busDevice.getKey()));
//        });

        //如果设备之前的状态是离线则记录日志
        if (oldStatus.get() != status) {
            LogBusDeviceEntity log=new LogBusDeviceEntity();
            log.setBusDeviceId(infoBusDeviceEntity.getBusDeviceId());
            log.setDeviceName(infoBusDeviceEntity.getDeviceName());
            log.setDeviceDesc(status ==1 ?"在线":"离线");
            log.setDeviceStatus(status);
            log.setCreatedDate(DateUtils.getTimestamp());
            logBusDeviceDao.insert(log);
        }
    }



    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<UploadDeviceVo> uploadDevice(DeviceUploadForm deviceUploadForm) {
        Long busId = deviceUploadForm.getBusId();
        List<InfoBusDeviceEntity> list = getBaseMapper().selectList(new QueryWrapper<InfoBusDeviceEntity>()
                .eq("bus_id", busId)
                .eq("is_deleted", Constant.Deleted.UNDELETED.getValue())
                .eq("is_enabled", Constant.Enabled.ENABLE.getValue()));
        List<InfoBusDeviceEntity> insertList = new ArrayList<>(), updateList = new ArrayList<>();
        for (DeviceUploadItem deviceUploadItem : deviceUploadForm.getDevices()) {
            Long busDeviceId = deviceUploadItem.getBusDeviceId();
            if (busDeviceId == null) {
                InfoBusDeviceEntity insertEntity = new InfoBusDeviceEntity();
                BeanUtils.copyProperties(deviceUploadItem, insertEntity);
                insertEntity.init();
                insertEntity.setBusId(busId);
                insertList.add(insertEntity);

            } else {
                Iterator<InfoBusDeviceEntity> iter = list.iterator();
                while (iter.hasNext()) {
                    InfoBusDeviceEntity infoBusDeviceEntity = iter.next();
                    if (busDeviceId.equals(infoBusDeviceEntity.getBusDeviceId())) {
                        BeanUtils.copyProperties(deviceUploadItem, infoBusDeviceEntity);
                        infoBusDeviceEntity.setModifiedDate(DateUtils.getTimestamp());
                        updateList.add(infoBusDeviceEntity);
                        iter.remove();
                        break;
                    }
                }
            }
        }
//        CacheTransactionManager txManager=gemFireCache.getCacheTransactionManager();
        getBaseMapper().deleteBach(list);
        List<UploadDeviceVo> result = new ArrayList<>();
        try {
//            txManager.begin();
            if (!insertList.isEmpty()) {
                getBaseMapper().insertBatch(insertList);
                convert(insertList, result);
            }
            if (!updateList.isEmpty()) {
                getBaseMapper().updateBatch(updateList);
                convert(updateList, result);
            }
//            for (InfoBusDeviceEntity entity : list) {
//                busDeviceRepository.deleteById(new BusDeviceKey(entity.getBusId(),entity.getBusDeviceId()));
//            }
//            txManager.commit();
        } catch (Exception e) {
            result.clear();
            throw e;
        } finally {
//            if (txManager.exists()) {
//                txManager.rollback();
//            }

        }
        return result;
    }

    private void convert(List<InfoBusDeviceEntity> list, List<UploadDeviceVo> result) {
        for (InfoBusDeviceEntity entity : list) {
            UploadDeviceVo vo = new UploadDeviceVo();
            BeanUtils.copyProperties(entity, vo);
            result.add(vo);
//            BusDevice busDevice = new BusDevice();
//            busDevice.setKey(new BusDeviceKey(entity.getBusId(),entity.getBusDeviceId()));
//            BeanUtils.copyProperties(entity, busDevice);
//            busDevice.setDeviceCode(entity.getDeviceDescCode());
//            busDevice.setUpdateTime(new Date(entity.getModifiedDate().getTime()) );
//            busDeviceRepository.save(new Wrapper<>(busDevice,busDevice.getKey()));
        }
    }

    @Override
    public Map<String, BusDeviceStatusVo> queryBusDeviceStatus(long busId) {
        List<BusDevice> busDeviceList = busDeviceRepository.queryBusDeviceByBusId(busId);
        Map<String, BusDeviceStatusVo> result = new HashMap<>();
        for(BusDevice busDevice:busDeviceList){
            BusDeviceStatusVo vo = new BusDeviceStatusVo();
            vo.setDeviceId(busDevice.getKey().getBusDeviceId());
            vo.setDeviceName(busDevice.getDeviceName());
            vo.setStatus(busDevice.getDeviceStatus());
            vo.setDeviceCode(busDevice.getDeviceCode());
            result.put(busDevice.getDeviceCode(), vo);
        }
        BusDeviceStatusVo ec = result.get("m_i_native_ec");
        if( ec!=null  && ec.getStatus()==0){
            result.values().stream().forEach(busDeviceStatusVo -> {
                busDeviceStatusVo.setStatus(0);
            });
        }
        return result;
    }
}
