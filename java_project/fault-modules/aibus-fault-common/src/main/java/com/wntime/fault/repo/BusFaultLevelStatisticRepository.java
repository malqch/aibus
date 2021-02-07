package com.wntime.fault.repo;

import com.wntime.fault.region.BusFaultLevelStatistic;
import com.wntime.modules.gemfire.key.BusDateKey;
import org.springframework.data.gemfire.repository.GemfireRepository;

public interface BusFaultLevelStatisticRepository extends GemfireRepository<BusFaultLevelStatistic, BusDateKey> {
}
