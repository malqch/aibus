package com.wntime.event.repo;

import com.wntime.event.region.EventType;
import org.springframework.data.repository.CrudRepository;

public interface EventTypeRepository extends CrudRepository<EventType,Long> {
}
