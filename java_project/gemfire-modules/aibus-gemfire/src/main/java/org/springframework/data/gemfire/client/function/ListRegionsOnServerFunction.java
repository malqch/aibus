package org.springframework.data.gemfire.client.function;

import org.apache.geode.cache.Cache;
import org.apache.geode.cache.CacheFactory;
import org.apache.geode.cache.Region;
import org.apache.geode.cache.execute.Function;
import org.apache.geode.cache.execute.FunctionContext;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 79448
 * @date 2020/9/18 9:56
 */

public class ListRegionsOnServerFunction implements Function {

    private static final long serialVersionUID = 867530169L;

    public static final String ID = ListRegionsOnServerFunction.class.getName();


    @Override
    public void execute(FunctionContext functionContext) {
        List<String> regionNames = new ArrayList<String>();

        for (Region<?, ?> region : getCache().rootRegions()) {
            regionNames.add(region.getName());
        }

        functionContext.getResultSender().lastResult(regionNames);
    }

    Cache getCache() {
        return CacheFactory.getAnyInstance();
    }


    @Override
    public String getId() {
        return this.getClass().getName();
    }


    @Override
    public boolean hasResult() {
        return true;
    }


    @Override
    public boolean isHA() {
        return false;
    }


    @Override
    public boolean optimizeForWrite() {
        return false;
    }
}
