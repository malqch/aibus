package com.wntime.fault.repo;

import com.wntime.fault.region.BusFaultInfo;
import com.wntime.modules.gemfire.key.BusDateKey;
import org.springframework.data.gemfire.repository.GemfireRepository;

public interface BusFaultInfoRepository extends GemfireRepository<BusFaultInfo, BusDateKey> {
}
