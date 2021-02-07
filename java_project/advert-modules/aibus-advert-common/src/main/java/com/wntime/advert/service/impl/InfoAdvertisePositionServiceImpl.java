package com.wntime.advert.service.impl;

import com.wntime.advert.entity.InfoAdvertiseTargetEntity;
import com.wntime.advert.vo.AdvertisePositionVo;
import com.wntime.service.common.service.ConfigParamInfoService;
import com.wntime.service.common.vo.ConfigParamVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wntime.common.utils.PageUtils;
import com.wntime.common.utils.Query;

import com.wntime.advert.dao.InfoAdvertisePositionDao;
import com.wntime.advert.entity.InfoAdvertisePositionEntity;
import com.wntime.advert.service.InfoAdvertisePositionService;


@Service("infoAdvertisePositionService")
public class InfoAdvertisePositionServiceImpl extends ServiceImpl<InfoAdvertisePositionDao, InfoAdvertisePositionEntity> implements InfoAdvertisePositionService {

    @Autowired
    private ConfigParamInfoService configParamInfoService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<InfoAdvertisePositionEntity> page = new Query<InfoAdvertisePositionEntity>().getPage(params);
        params.put("page", page);
        page.setRecords(this.baseMapper.listWithUser(params));
        return new PageUtils(page);
    }

    @Override
    public List<AdvertisePositionVo> listAll() {
        List<AdvertisePositionVo> list = getBaseMapper().listAll();
        Optional<ConfigParamVo> fileNum = configParamInfoService.queryConfigByGroupCode("advertiseFileCheck", "fileNum");
        Optional<ConfigParamVo> videoFileSize = configParamInfoService.queryConfigByGroupCode("advertiseFileCheck", "videoFileSize");
        Optional<ConfigParamVo> videoFileType = configParamInfoService.queryConfigByGroupCode("advertiseFileCheck", "videoFileType");
        Optional<ConfigParamVo> videoFileTime = configParamInfoService.queryConfigByGroupCode("advertiseFileCheck", "videoFileTime");
        Optional<ConfigParamVo> pictureFileSize = configParamInfoService.queryConfigByGroupCode("advertiseFileCheck", "pictureFileSize");
        Optional<ConfigParamVo> pictureFileType = configParamInfoService.queryConfigByGroupCode("advertiseFileCheck", "pictureFileType");
        Optional<ConfigParamVo> pictureFileTime = configParamInfoService.queryConfigByGroupCode("advertiseFileCheck", "pictureFileTime");

        for (AdvertisePositionVo advertisePositionVo : list) {
            advertisePositionVo.setFileNum(fileNum.get().getParamValue().intValue());
            advertisePositionVo.setVideoFileSize(videoFileSize.get().getParamValue().intValue());
            advertisePositionVo.setVideoFileSizeStr(videoFileSize.get().getParamChar());
            advertisePositionVo.setVideoFileType(videoFileType.get().getParamChar());
            advertisePositionVo.setVideoFileTime(videoFileTime.get().getParamValue().intValue());
            advertisePositionVo.setVideoFileTimeStr(videoFileTime.get().getParamChar());
            advertisePositionVo.setPictureFileSize(pictureFileSize.get().getParamValue().intValue());
            advertisePositionVo.setPictureFileSizeStr(pictureFileSize.get().getParamChar());
            advertisePositionVo.setPictureFileType(pictureFileType.get().getParamChar());
            advertisePositionVo.setPictureFileTime(pictureFileTime.get().getParamValue().intValue());
            advertisePositionVo.setPictureFileTimeStr(pictureFileTime.get().getParamChar());
        }
        return list;
    }

    @Override
    public AdvertisePositionVo getOne(Long id) {
        AdvertisePositionVo advertisePositionVo = getAdvertisePositionVo(getBaseMapper().getOne(id));
        return advertisePositionVo;
    }

    @Override
    public AdvertisePositionVo getOneByCode(String code) {
        AdvertisePositionVo advertisePositionVo = getAdvertisePositionVo(getBaseMapper().getOneByCode(code));
        return advertisePositionVo;
    }

    private AdvertisePositionVo getAdvertisePositionVo(AdvertisePositionVo advertisePositionVo) {

        Optional<ConfigParamVo> fileNum = configParamInfoService.queryConfigByGroupCode("advertiseFileCheck", "fileNum");
        Optional<ConfigParamVo> videoFileSize = configParamInfoService.queryConfigByGroupCode("advertiseFileCheck", "videoFileSize");
        Optional<ConfigParamVo> videoFileType = configParamInfoService.queryConfigByGroupCode("advertiseFileCheck", "videoFileType");
        Optional<ConfigParamVo> videoFileTime = configParamInfoService.queryConfigByGroupCode("advertiseFileCheck", "videoFileTime");
        Optional<ConfigParamVo> pictureFileSize = configParamInfoService.queryConfigByGroupCode("advertiseFileCheck", "pictureFileSize");
        Optional<ConfigParamVo> pictureFileType = configParamInfoService.queryConfigByGroupCode("advertiseFileCheck", "pictureFileType");
        Optional<ConfigParamVo> pictureFileTime = configParamInfoService.queryConfigByGroupCode("advertiseFileCheck", "pictureFileTime");

        advertisePositionVo.setFileNum(fileNum.get().getParamValue().intValue());
        advertisePositionVo.setVideoFileSize(videoFileSize.get().getParamValue().intValue());
        advertisePositionVo.setVideoFileSizeStr(videoFileSize.get().getParamChar());
        advertisePositionVo.setVideoFileType(videoFileType.get().getParamChar());
        advertisePositionVo.setVideoFileTime(videoFileTime.get().getParamValue().intValue());
        advertisePositionVo.setVideoFileTimeStr(videoFileTime.get().getParamChar());
        advertisePositionVo.setPictureFileSize(pictureFileSize.get().getParamValue().intValue());
        advertisePositionVo.setPictureFileSizeStr(pictureFileSize.get().getParamChar());
        advertisePositionVo.setPictureFileType(pictureFileType.get().getParamChar());
        advertisePositionVo.setPictureFileTime(pictureFileTime.get().getParamValue().intValue());
        advertisePositionVo.setPictureFileTimeStr(pictureFileTime.get().getParamChar());
        return advertisePositionVo;
    }

}