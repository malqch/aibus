package com.wntime.service.region;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.gemfire.expiration.TimeToLiveExpiration;
import org.springframework.data.gemfire.mapping.annotation.Region;

import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@Region("bus_tunnel_port")
@TimeToLiveExpiration(timeout = "60")
public class BusTunnelPort {
    @Id
    private Long busId;
    private int port;
    private Set<Long> userIds;


    public void addUserId(long userId){
        if(userIds==null)userIds=new HashSet<>();
        userIds.add(userId);
    }
}
