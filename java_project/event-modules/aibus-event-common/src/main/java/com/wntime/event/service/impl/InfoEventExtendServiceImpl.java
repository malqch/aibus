package com.wntime.event.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wntime.common.utils.DateUtils;
import com.wntime.common.utils.PageUtils;
import com.wntime.common.utils.Query;
import com.wntime.event.dao.InfoEventExtendDao;
import com.wntime.event.entity.InfoEventExtendEntity;
import com.wntime.event.service.InfoEventExtendService;
import com.wntime.event.vo.InfoEventExtendDetailVo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;


@Service("infoEventExtendService")
public class InfoEventExtendServiceImpl extends ServiceImpl<InfoEventExtendDao, InfoEventExtendEntity> implements InfoEventExtendService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<InfoEventExtendEntity> page = this.page(
                new Query<InfoEventExtendEntity>().getPage(params),
                new QueryWrapper<InfoEventExtendEntity>()
        );

        return new PageUtils(page);
    }


    @Override
    public void delById(Long id, Long userId) {
        InfoEventExtendEntity entity = new InfoEventExtendEntity();
        entity.setEventExtendId(id);
        entity.setIsDeleted(1);
        entity.setModifiedBy(userId);
        entity.setModifiedDate(DateUtils.getTimestamp());
        this.updateById(entity);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteBatch(String[] ids, Long userId) {
        for (String id : ids) {
            this.delById(Long.parseLong(id), userId);
        }
    }

    @Override
    public List<InfoEventExtendDetailVo> listDetail(Long collectEventId) {
        return getBaseMapper().listDetail(collectEventId);
    }

}
