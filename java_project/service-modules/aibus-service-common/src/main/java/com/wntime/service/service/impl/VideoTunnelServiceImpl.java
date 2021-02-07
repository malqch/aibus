package com.wntime.service.service.impl;

import com.wntime.common.exception.RRException;
import com.wntime.service.common.service.VideoTunnelService;
import com.wntime.service.common.vo.TunnelConfig;
import com.wntime.service.common.vo.TunnelVo;
import com.wntime.service.region.BusTunnelPort;
import com.wntime.service.region.TunnelPort;
import com.wntime.service.repo.BusTunnelPortRepository;
import com.wntime.service.repo.TunnelPortRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * 视频通道服务
 */
@Service
public class VideoTunnelServiceImpl implements VideoTunnelService {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    @Resource
    private BusTunnelPortRepository busTunnelPortRepository;
    @Resource
    private TunnelPortRepository tunnelPortRepository;

    @Resource
    private JdbcTemplate jdbcTemplate;


    /**
     * 公交绑定端口
     *
     * @param busId
     * @return
     */
    @Override
    @CacheEvict(cacheNames = "cache_tunnel_config",key = "#busId")
    public TunnelVo bind(long busId, long userId,String deviceCode) {
        //查询公交是否绑定了端口
        Optional<BusTunnelPort> optional = busTunnelPortRepository.findById(busId);
        BusTunnelPort tunnel=null;
        if (optional.isPresent()) {
            tunnel = optional.get();
            tunnel.addUserId(userId);
            busTunnelPortRepository.save(tunnel);
        } else {
            //从端口池里去拿端口
            List<Integer> ports = tunnelPortRepository.queryUnusedPort();
            if(ports==null || ports.isEmpty()){throw new RRException("获取视频通道失败");}
            Random r=new Random();
            int portIndex = r.nextInt(ports.size());
            int port=ports.get(portIndex);

            tunnel = new BusTunnelPort();
            tunnel.setBusId(busId);
            //绑定端口
            tunnel.setPort(port);
            tunnel.addUserId(userId);
            busTunnelPortRepository.save(tunnel);
            tunnelPortRepository.save(new TunnelPort(port, true));
        }
        TunnelConfig tunnelConfig=getConfig();
        String url = tunnelConfig.getUrl()
                .replace("IP", tunnelConfig.getIp())
                .replace("PORT", String.valueOf(tunnel.getPort()))
                .replace("DEVICE",deviceCode);
        return new TunnelVo(tunnel.getPort(),url);
    }


    /**
     * 公交解绑端口
     *
     * @param busId
     */
    @Override
    @CacheEvict(cacheNames = "cache_tunnel_config",key = "#busId")
    public void unbind(long busId, long userId) {
        //查询公交是否有绑定接口
        Optional<BusTunnelPort> optional = busTunnelPortRepository.findById(busId);
        optional.ifPresent(tunnel -> {
            Set<Long> userIds = tunnel.getUserIds();
            //如果删除此用户id成功 且无用户绑定到改公交的通道上就删除此通道
            if (userIds.remove(userId) ) {
                busTunnelPortRepository.save(tunnel);
                if(userIds.isEmpty()){
                    busTunnelPortRepository.deleteById(busId);
                    tunnelPortRepository.save(new TunnelPort(tunnel.getPort()));
                }
            }
        });
    }

    /**
     * 检测公交是否绑定端口
     *
     * @param busId
     */
    @Override
    public TunnelVo checkTunnel(long busId) {
        //查询公交绑定端口表
        Optional<BusTunnelPort> optional = busTunnelPortRepository.findById(busId);
        int port=0;
        if (optional.isPresent()) {
            port=optional.get().getPort();
        }
        TunnelConfig config = getConfig();
        return new TunnelVo(port,config.getIp(),config.getPassword(),config.getUsername(),config.getPort());
    }

    /**
     * 初始化所有端口
     */
    //@PostConstruct
    public void generatePort() {
        TunnelConfig tunnelConfig=getConfig();
        long count = tunnelPortRepository.count();
        if (count == 0) {
            int min = tunnelConfig.getMinPort();
            int max = tunnelConfig.getMaxPort();
            List<TunnelPort> list = new ArrayList<>();
            for (int i = min; i < max; i++) {
                list.add(new TunnelPort(i));
            }
            tunnelPortRepository.saveAll(list);
        }
    }
    @Override
    public TunnelConfig getConfig(){
        TunnelConfig tunnelConfig=new TunnelConfig();
//        jdbcTemplate.query("SELECT param_name,param_value,param_code,param_char from info_config_param where param_group='tunnel_config' and is_deleted=0 and is_enabled=1", new RowMapper<Object>() {
//            @Override
//            public Object mapRow(ResultSet resultSet, int i) throws SQLException {
//                String name = resultSet.getString("param_name");
//                int min = resultSet.getInt("param_value");
//                int max = resultSet.getInt("param_code");
//                String url = resultSet.getString("param_char");
//                if("通道服务器".equals(name)){
//                    tunnelConfig.setIp(url);
//                    tunnelConfig.setMinPort(min);
//                    tunnelConfig.setMaxPort(max);
//                }else if("实时视频".equals(name)){
//                    tunnelConfig.setUrl(url);
//                }else if("ssh密码".equals(name)){
//                    tunnelConfig.setPassword(url);
//                }else if("ssh用户名端口".equals(name)){
//                    tunnelConfig.setPort(max);
//                    tunnelConfig.setUsername(url);
//                }
//                return null;
//            }
//        });
        return tunnelConfig;
    }

    @Override
    public void updateBusTunnelPort(long busId) {
        Optional<BusTunnelPort> optional = busTunnelPortRepository.findById(busId);
        optional.ifPresent(tunnel -> {
            busTunnelPortRepository.save(tunnel);
        });
    }
}
