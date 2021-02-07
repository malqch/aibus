package com.wntime.event.repo;

import com.wntime.event.region.DriverViolation;
import org.springframework.data.repository.CrudRepository;

public interface DriverViolationRepository extends CrudRepository<DriverViolation,Long> {
}
