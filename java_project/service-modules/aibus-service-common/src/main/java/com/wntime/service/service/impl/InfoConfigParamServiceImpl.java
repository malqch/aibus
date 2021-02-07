package com.wntime.service.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wntime.common.utils.DateUtils;
import com.wntime.common.utils.PageUtils;
import com.wntime.datasource.annotation.DataSource;
import com.wntime.service.common.service.ConfigParamInfoService;
import com.wntime.service.common.service.ConfigParamService;
import com.wntime.service.common.vo.ConfigParamVo;
import com.wntime.service.dao.InfoConfigParamDao;
import com.wntime.service.entity.InfoConfigParamEntity;
import com.wntime.service.service.InfoConfigParamService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service("infoConfigParamService")
public class InfoConfigParamServiceImpl extends ServiceImpl<InfoConfigParamDao, InfoConfigParamEntity> implements InfoConfigParamService, ConfigParamService, ConfigParamInfoService {

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
        Page<InfoConfigParamEntity> page = new Page<>(currPage, pageSize);
        params.put("page", page);
        return new PageUtils(page.setRecords(this.baseMapper.queryPageList(params)));
    }

    @Override
    public List<InfoConfigParamEntity> getListByParamGroup(String paramGroup) {
        return this.list(new QueryWrapper<InfoConfigParamEntity>()
                .eq("param_group", paramGroup)
                .eq("is_enabled", 1)
                .eq("is_deleted", 0));
    }

    @Override
    public InfoConfigParamEntity getDetailById(Long id) {
        return this.getById(id);
    }

    @Override
    public List<InfoConfigParamEntity> getConfigBusMilesList() {
        return getBaseMapper().getConfigBusMilesList();

    }

    @Override
    public void save(InfoConfigParamEntity infoConfigParam, Long userId) {
        infoConfigParam.setConfigParamId(null);
        infoConfigParam.setIsDeleted(0);
        infoConfigParam.setCreatedDate(DateUtils.getTimestamp());
        infoConfigParam.setCreatedBy(userId);
        this.save(infoConfigParam);
    }

    @Override
    public void updateById(InfoConfigParamEntity infoConfigParam, Long userId) {
        infoConfigParam.setModifiedBy(userId);
        infoConfigParam.setModifiedDate(DateUtils.getTimestamp());
        this.updateById(infoConfigParam);
    }

    @Override
    public void delById(Long id, Long userId) {
        InfoConfigParamEntity configParamEntity = new InfoConfigParamEntity();
        configParamEntity.setConfigParamId(id);
        configParamEntity.setIsDeleted(1);
        configParamEntity.setModifiedBy(userId);
        configParamEntity.setModifiedDate(DateUtils.getTimestamp());
        this.updateById(configParamEntity);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteBatch(String[] ids, Long userId) {
        for (String id : ids) {
            this.delById(Long.parseLong(id), userId);
        }
    }

    @Override
    public InfoConfigParamEntity getFaultTypeKnowledge() {

        return getBaseMapper().getFaultTypeKnowledge();
    }

    @Override
    @DataSource("read")
    public Optional<ConfigParamVo> queryConfigByGroupCode(String group, String code) {
        return Optional.ofNullable(getBaseMapper().queryConfigParamByGroupCode(group, code));
    }
}
