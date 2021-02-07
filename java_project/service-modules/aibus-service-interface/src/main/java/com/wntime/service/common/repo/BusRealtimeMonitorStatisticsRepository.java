package com.wntime.service.common.repo;

import com.wntime.modules.gemfire.key.BusDateKey;
import com.wntime.service.common.region.BusRealtimeMonitorStatistics;
import org.springframework.data.gemfire.repository.GemfireRepository;

public interface BusRealtimeMonitorStatisticsRepository extends GemfireRepository<BusRealtimeMonitorStatistics, BusDateKey> {
}
