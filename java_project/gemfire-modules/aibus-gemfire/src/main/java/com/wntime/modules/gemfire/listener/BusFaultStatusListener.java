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
public class BusFaultStatusListener extends CacheListenerAdapter implements Declarable {

    private Cache cache= CacheFactory.getAnyInstance();

    @Override
    public void afterInvalidate(EntryEvent event) {
        Object value = event.getOldValue();
        Region<Object, Object> region = cache.getRegion("bus_status_summary");

        Optional.ofNullable(value).filter(PdxInstance.class::isInstance)
                .map(PdxInstance.class::cast).ifPresent(pdxInstance -> {
            long companyId = (long) pdxInstance.getField("companyId");
            Object statusSummary = region.get(companyId);
            Optional.ofNullable(statusSummary).filter(PdxInstance.class::isInstance).map(PdxInstance.class::cast).ifPresent(instance->{
                WritablePdxInstance writer = instance.createWriter();
                int faultCount = (int) instance.getField("faultCount");
                int normalCount = (int) instance.getField("normalCount");
                writer.setField("faultCount",faultCount-1);
                writer.setField("normalCount",normalCount+1);
                region.put(companyId,writer);
            });
        });
        Long key = (Long) event.getKey();
        Region<Object, Object> busRegion = cache.getRegion("bus_info");
        Object bus = busRegion.get(key);
        Optional.ofNullable(bus).filter(PdxInstance.class::isInstance).map(PdxInstance.class::cast).ifPresent(instance->{
            WritablePdxInstance writer = instance.createWriter();
            int status = (int) instance.getField("status");
            if(status == 2){
                writer.setField("status",0);
                busRegion.put(key,writer);
            }
        });
    }
}
