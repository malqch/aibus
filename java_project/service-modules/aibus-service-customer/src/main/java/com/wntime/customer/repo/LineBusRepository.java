package com.wntime.customer.repo;

import com.wntime.customer.region.LineBus;
import org.springframework.data.gemfire.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface LineBusRepository extends CrudRepository<LineBus,Long> {

    @Query("select * from /line_bus where companyLineId=$1")
    List<LineBus> queryLineBusesByCompanyLineId(long companyLineId);
}
