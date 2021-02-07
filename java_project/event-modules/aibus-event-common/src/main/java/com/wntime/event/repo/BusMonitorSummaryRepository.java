package com.wntime.event.repo;

import com.wntime.event.region.BusMonitorSummary;
import com.wntime.modules.gemfire.key.BusDateKey;
import org.springframework.data.gemfire.repository.GemfireRepository;

public interface BusMonitorSummaryRepository extends GemfireRepository<BusMonitorSummary, BusDateKey> {
}
