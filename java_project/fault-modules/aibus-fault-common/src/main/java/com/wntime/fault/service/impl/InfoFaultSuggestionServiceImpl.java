package com.wntime.fault.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wntime.common.utils.PageUtils;
import com.wntime.common.utils.Query;
import com.wntime.fault.dao.InfoFaultSuggestionDao;
import com.wntime.fault.entity.InfoFaultSuggestionEntity;
import com.wntime.fault.service.InfoFaultSuggestionService;


@Service("infoFaultSuggestionService")
public class InfoFaultSuggestionServiceImpl extends ServiceImpl<InfoFaultSuggestionDao, InfoFaultSuggestionEntity> implements InfoFaultSuggestionService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<InfoFaultSuggestionEntity> page = this.page(
                new Query<InfoFaultSuggestionEntity>().getPage(params),
                new QueryWrapper<InfoFaultSuggestionEntity>()
        );

        return new PageUtils(page);
    }

}
