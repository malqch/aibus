package com.wntime.service.repo;

import com.wntime.service.region.BusDevice;
import com.wntime.service.region.BusDeviceKey;
import org.springframework.data.gemfire.repository.GemfireRepository;
import org.springframework.data.gemfire.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface BusDeviceRepository extends GemfireRepository<BusDevice, BusDeviceKey> {

    @Query("select * from /bus_device where busId = $1")
    List<BusDevice> queryBusDeviceByBusId(long busId);
}
