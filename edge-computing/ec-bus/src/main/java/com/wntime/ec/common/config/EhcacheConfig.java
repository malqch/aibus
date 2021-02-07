package com.wntime.ec.common.config;

import com.wntime.ec.common.util.EhcacheUtil;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.config.CacheConfiguration;
import net.sf.ehcache.config.Configuration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

@org.springframework.context.annotation.Configuration
public class EhcacheConfig {

    //缓存名称
    @Value("${ehcache.cache-ec}")
    private String cacheEc;

//    @Value("${ehcache.cache-path}")
//    private String cachePath;

    /**
     * 获取缓存管理器
     *
     * @return
     */
    @Bean("ehCacheManager")
    public CacheManager getCacheManager() {
        // default cache
        CacheConfiguration defaultCacheConfig = new CacheConfiguration();
        defaultCacheConfig.setName("default");
        defaultCacheConfig.setEternal(false);//是否永不过期，为false则过期需要通过timeToIdleSeconds，timeToLiveSeconds判断
        defaultCacheConfig.setMemoryStoreEvictionPolicy("LFU");//最少使用
//        defaultCacheConfig.setTimeToIdleSeconds(0);           //一小时不访问就过期
//        defaultCacheConfig.setTimeToLiveSeconds(0);           //缓存最多存在时间
        defaultCacheConfig.setTimeToIdleSeconds(60 * 60 * 5);       //5小时不访问就过期
//        defaultCacheConfig.setTimeToLiveSeconds(60 * 60 * 5); //缓存最多存在时间
//        defaultCacheConfig.setMaxEntriesLocalHeap(1);
        defaultCacheConfig.setMaxBytesLocalHeap(1024 * 1024 * 50L);  //50MB
//        defaultCacheConfig.setOverflowToDisk(false);//内存中过多则存入硬盘
//        defaultCacheConfig.setDiskPersistent(false);//重启服务后是否恢复缓存

        // ecCache
        CacheConfiguration ecCacheConfig = new CacheConfiguration();
        ecCacheConfig.setName(cacheEc);
        ecCacheConfig.setEternal(false);//是否永不过期，为false则过期需要通过timeToIdleSeconds，timeToLiveSeconds判断
        ecCacheConfig.setMemoryStoreEvictionPolicy("LFU");   //最少使用
//        cacheAgentConfig.setTimeToIdleSeconds(0);             //一小时不访问就过期
//        cacheAgentConfig.setTimeToLiveSeconds(0);             //缓存最多存在时间
        ecCacheConfig.setTimeToIdleSeconds(60 * 60 * 5);     //5小时不访问就过期
//        cacheAgentConfig.setTimeToLiveSeconds(60 * 60 * 5);   //缓存最多存在时间
//        cacheAgentConfig.setMaxEntriesLocalHeap(1);
        ecCacheConfig.setMaxBytesLocalHeap(1024 * 1024 * 500L);  //500MB
//        cacheAgentConfig.setOverflowToDisk(false);//内存中过多则存入硬盘
//        cacheAgentConfig.setDiskPersistent(false);//重启服务后是否恢复缓存


        // 设置ehcache配置文件，获取CacheManager
        Configuration configuration = new Configuration();
        configuration.addDefaultCache(defaultCacheConfig);
        configuration.addCache(ecCacheConfig);

//        DiskStoreConfiguration diskStoreConfiguration = new DiskStoreConfiguration();
//        diskStoreConfiguration.setPath(cachePath);
//        configuration.addDiskStore(diskStoreConfiguration);
        CacheManager cacheManager = CacheManager.newInstance(configuration);

        // 将CacheManager注册为bean，供缓存工具类使用
        return cacheManager;
    }


    @Bean
    public EhcacheUtil getCacheUtil() {
        EhcacheUtil ehcacheUtil = new EhcacheUtil();
        ehcacheUtil.setCacheManager(getCacheManager());
        return ehcacheUtil;
    }
}