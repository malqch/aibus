package com.wntime.ec.module.schedule.vo;

import lombok.Data;

/**
 * @author wing
 * @create 2019-12-18 13:30
 */
@Data
public class SshQryRspVo {
    private String ip;
    private int port;
    private String username;
    private String password;
    private int sport;
}