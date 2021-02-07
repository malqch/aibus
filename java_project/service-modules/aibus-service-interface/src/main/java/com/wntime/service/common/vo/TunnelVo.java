package com.wntime.service.common.vo;

public class TunnelVo {
    private int sport;
    private String ip;
    private String password;
    private String username;
    private int port;


    public TunnelVo(int port, String ip) {
        this.sport = port;
        this.ip = ip;
    }

    public TunnelVo(int sport, String ip, String password) {
        this.sport = sport;
        this.ip = ip;
        this.password=password;
    }

    public TunnelVo(int sport, String ip, String password, String username, int port) {
        this.sport = sport;
        this.ip = ip;
        this.password = password;
        this.username = username;
        this.port = port;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getSport() {
        return sport;
    }

    public void setSport(int sport) {
        this.sport = sport;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
