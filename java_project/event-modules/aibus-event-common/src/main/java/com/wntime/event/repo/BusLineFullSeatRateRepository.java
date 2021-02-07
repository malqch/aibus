package com.wntime.event.repo;

import com.wntime.event.region.BusLineFullSeatRate;
import com.wntime.event.region.BusLineFullSeatRateKey;
import org.springframework.data.gemfire.repository.GemfireRepository;
import org.springframework.data.gemfire.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface BusLineFullSeatRateRepository extends GemfireRepository<BusLineFullSeatRate, BusLineFullSeatRateKey> {

    @Query("select * from /bus_line_full_seat_rate  where companyId=$1 and key.serviceDate = $2 order by fullSeatRate")
    List<BusLineFullSeatRate> queryBusLineFullSeatRateRankByCompanyId(long companyId, LocalDate date);
}
