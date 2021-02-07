package com.wntime.modules.monitor.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wntime.modules.monitor.dao.LogWeatherWarnMapper;
import com.wntime.modules.monitor.entity.LogWeatherWarn;
import com.wntime.modules.monitor.service.LogWeatherWarnService;
import org.springframework.stereotype.Service;

/**
 * @author 79448
 * @date 2020/11/5 15:13
 */
@Service
public class LogWeatherWarnServiceImpl extends ServiceImpl< LogWeatherWarnMapper,LogWeatherWarn> implements LogWeatherWarnService {
}
