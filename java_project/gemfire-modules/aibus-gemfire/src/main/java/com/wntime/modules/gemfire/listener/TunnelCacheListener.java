package com.wntime.modules.gemfire.listener;

import org.apache.geode.cache.*;
import org.apache.geode.cache.util.CacheListenerAdapter;
import org.apache.geode.pdx.PdxInstance;
import org.apache.geode.pdx.WritablePdxInstance;

import java.util.Optional;

public class TunnelCacheListener extends CacheListenerAdapter implements Declarable {

    private Cache cache= CacheFactory.getAnyInstance();
    @Override
    public void afterInvalidate(EntryEvent event) {
        Object value = event.getOldValue();
        Optional.ofNullable(value).filter(PdxInstance.class::isInstance)
                .map(PdxInstance.class::cast).ifPresent(pdxInstance -> {
            int port = (int) pdxInstance.getField("port");
            Region<Object, Object> region = cache.getRegion("/tunnel_port");
            Object tunnelPort = region.get(port);
            Optional.ofNullable(tunnelPort).filter(PdxInstance.class::isInstance).map(PdxInstance.class::cast).ifPresent(instance->{
                WritablePdxInstance writer = instance.createWriter();
                writer.setField("used",false);
                region.put(port,writer);
            });
        });
    }
}
