package com.wntime.modules.gemfire.resolver;

import org.apache.geode.cache.EntryOperation;
import org.apache.geode.cache.PartitionResolver;
import org.apache.geode.pdx.PdxInstance;
import org.apache.geode.pdx.internal.PdxInstanceImpl;

import java.util.Objects;

/**
 * @author 79448
 * @date 2020/8/29 16:52
 */
public class BusKeyResolver implements PartitionResolver {
    /**
     * 将公交车id放在统一起
     * @param entryOperation
     * @return
     */
    @Override
    public Object getRoutingObject(EntryOperation entryOperation) {
        Object key = entryOperation.getKey();
        if(key instanceof PdxInstance){
            PdxInstanceImpl  pdxKey=(PdxInstanceImpl)key;
            return Objects.hashCode(pdxKey.getField("busId"));
        }
        return Objects.hashCode(entryOperation.getKey());
    }

    @Override
    public String getName() {
        return getClass().getName();
    }

}
