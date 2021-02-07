package com.wntime.event.repo;

import com.wntime.event.region.BusAlarmInfo;
import com.wntime.modules.gemfire.key.BusDateKey;
import org.springframework.data.gemfire.repository.GemfireRepository;

/**
 * @author 79448
 * @date 2020/8/28 20:23
 */
public interface BusAlarmInfoRepository extends GemfireRepository<BusAlarmInfo, BusDateKey> {
}
