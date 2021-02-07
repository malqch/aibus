package com.wntime.service.repo;

import com.wntime.modules.gemfire.key.BusDateKey;
import com.wntime.service.region.BusRealtimeMonitor;
import org.springframework.data.gemfire.repository.GemfireRepository;

public interface BusRealtimeMonitorRepository extends GemfireRepository<BusRealtimeMonitor, BusDateKey> {
}
