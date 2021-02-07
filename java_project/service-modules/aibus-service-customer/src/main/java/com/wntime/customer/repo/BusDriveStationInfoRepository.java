package com.wntime.customer.repo;

import com.wntime.customer.region.BusDriveStationInfo;
import org.springframework.data.gemfire.repository.GemfireRepository;

public interface BusDriveStationInfoRepository extends GemfireRepository<BusDriveStationInfo,Long> {
}
