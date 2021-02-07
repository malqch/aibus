package com.wntime.modules.gemfire.listener;

import org.apache.geode.cache.*;
import org.apache.geode.cache.util.CacheListenerAdapter;
import org.apache.geode.pdx.PdxInstance;
import org.apache.geode.pdx.WritablePdxInstance;

import java.util.Optional;

/**
 * @author 79448
 * @date 2020/9/16 10:31
 */
public class BusAlarmStatusListener extends CacheListenerAdapter implements Declarable {

    private Cache cache= CacheFactory.getAnyInstance();

    @Override
    public void afterInvalidate(EntryEvent event) {
        Long key = (Long) event.getKey();
        Region<Object, Object> region = cache.getRegion("bus_info");
        Object bus = region.get(key);
        Optional.ofNullable(bus).filter(PdxInstance.class::isInstance).map(PdxInstance.class::cast).ifPresent(instance->{
            WritablePdxInstance writer = instance.createWriter();
            int status = (int) instance.getField("status");
            if(status == 1){
                writer.setField("status",0);
                region.put(key,writer);
            }
        });
    }
}
