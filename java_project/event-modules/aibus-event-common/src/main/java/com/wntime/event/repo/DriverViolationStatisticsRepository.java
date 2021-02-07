package com.wntime.event.repo;

import com.wntime.event.region.DriverViolationStatistics;
import com.wntime.modules.gemfire.key.BusDateKey;
import org.springframework.data.gemfire.repository.GemfireRepository;

public interface DriverViolationStatisticsRepository extends GemfireRepository<DriverViolationStatistics, BusDateKey> {
}
