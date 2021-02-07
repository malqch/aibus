package com.wntime.event.service.impl;

import com.wntime.event.vo.LogEventAttachVo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wntime.common.utils.PageUtils;
import com.wntime.common.utils.Query;
import com.wntime.event.dao.LogEventAttachDao;
import com.wntime.event.entity.LogEventAttachEntity;
import com.wntime.event.service.LogEventAttachService;


@Service("logEventAttachService")
public class LogEventAttachServiceImpl extends ServiceImpl<LogEventAttachDao, LogEventAttachEntity> implements LogEventAttachService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<LogEventAttachEntity> page = this.page(
                new Query<LogEventAttachEntity>().getPage(params),
                new QueryWrapper<LogEventAttachEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public List<LogEventAttachVo> listAttachByDetailId(Long eventDetailId) {

        return getBaseMapper().listAttachByDetailId(eventDetailId);
    }

}
