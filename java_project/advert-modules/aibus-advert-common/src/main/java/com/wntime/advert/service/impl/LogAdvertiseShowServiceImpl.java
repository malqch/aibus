package com.wntime.advert.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wntime.common.utils.DateUtils;
import com.wntime.service.common.entity.LogBusServiceEntity;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wntime.common.utils.PageUtils;
import com.wntime.common.utils.Query;

import com.wntime.advert.dao.LogAdvertiseShowDao;
import com.wntime.advert.entity.LogAdvertiseShowEntity;
import com.wntime.advert.service.LogAdvertiseShowService;
import org.springframework.transaction.annotation.Transactional;


@Service("logAdvertiseShowService")
public class LogAdvertiseShowServiceImpl extends ServiceImpl<LogAdvertiseShowDao, LogAdvertiseShowEntity> implements LogAdvertiseShowService {

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
        Page<LogAdvertiseShowEntity> page = new Page<>(currPage, pageSize);
        params.put("page", page);

        if(params.get("startTime") != null&& StringUtils.isNotEmpty(String.valueOf(params.get("startTime")))){
            params.put("startTime", DateUtils.stringToDate(String.valueOf(params.get("startTime")),DateUtils.DATE_TIME_PATTERN));
        }else{
            params.put("startTime",null);
        }

        if(params.get("endTime") != null&& StringUtils.isNotEmpty(String.valueOf(params.get("endTime")))){
            params.put("endTime", DateUtils.stringToDate(String.valueOf(params.get("endTime")),DateUtils.DATE_TIME_PATTERN) );
        }else{
            params.put("endTime",null);
        }
        return new PageUtils(page.setRecords(getBaseMapper().queryPageList(params)));
    }

}