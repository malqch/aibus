package com.wntime.event.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import java.util.Map;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wntime.common.utils.DateUtils;
import com.wntime.common.utils.PageUtils;
import com.wntime.event.dao.InfoEventDescriptionDao;
import com.wntime.event.entity.InfoEventDescriptionEntity;
import com.wntime.event.service.InfoEventDescriptionService;

/**
 * ${comments}
 *
 * @date 2020-09-01 15:22:13
 */
@Service("infoEventDescriptionService")
public class InfoEventDescriptionServiceImpl extends ServiceImpl<InfoEventDescriptionDao, InfoEventDescriptionEntity> implements InfoEventDescriptionService {

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
            Page<InfoEventDescriptionEntity> page = new Page<>(currPage, pageSize);
            params.put("page", page);
            return new PageUtils(page.setRecords(this.baseMapper.queryPageList(params)));
        }

        @Override
        public InfoEventDescriptionEntity getDetailById(Long id) {
            return this.getById(id);
        }

        @Override
        public void save(InfoEventDescriptionEntity infoEventDescriptionEntity, Long userId) {
            infoEventDescriptionEntity.setEventDescriptionId(null);
            infoEventDescriptionEntity.setCreatedDate(DateUtils.getTimestamp());
            infoEventDescriptionEntity.setCreatedBy(userId);
            this.save(infoEventDescriptionEntity);
        }

        @Override
        public void updateById(InfoEventDescriptionEntity infoEventDescriptionEntity, Long userId) {
            infoEventDescriptionEntity.setModifiedBy(userId);
            infoEventDescriptionEntity.setModifiedDate(DateUtils.getTimestamp());
            this.updateById(infoEventDescriptionEntity);
        }

        @Override
        public void delById(Long id, Long userId) {
            getBaseMapper().deleteById(id);
        }

        @Transactional(rollbackFor = Exception.class)
        @Override
        public void deleteBatch(String[] ids, Long userId) {
            for (String id : ids) {
                this.delById(Long.parseLong(id), userId);
            }
        }

}
