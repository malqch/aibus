package com.wntime.service.repo;

import com.wntime.service.region.TunnelPort;
import org.springframework.data.gemfire.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TunnelPortRepository extends CrudRepository<TunnelPort,Integer> {
    @Query("select port from /tunnel_port where used = false")
    List<Integer> queryUnusedPort();
}
