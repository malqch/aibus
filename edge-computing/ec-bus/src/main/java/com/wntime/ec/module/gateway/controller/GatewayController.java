package com.wntime.ec.module.gateway.controller;

import com.wntime.ec.common.util.BaseController;
import com.wntime.ec.common.util.param.http.HttpRspParam;
import com.wntime.ec.module.schedule.service.IInitService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;

/**
 * 网关，提供给外部系统接口
 */
@Slf4j
@Api(value = "提供给外部系统接口", tags = "网关接口")
@RestController
@RequestMapping("/gateway")
@SuppressWarnings("all")
public class GatewayController extends BaseController {

    @Value("${application.version}")
    private String applicationVersion;


    @Autowired
    IInitService initService;


    /**
     * 检查程序是否活着
     */
    @RequestMapping("/ping")
    @ApiOperation(value = "检查程序是否活着", notes = "检查程序是否活着", response = String.class)
    public HttpRspParam ping() {
//        gatewayService.ping();
        return success("alive");
    }


    /**
     * 初始化数据
     */
    @RequestMapping("/initData")
    @ApiOperation(value = "初始化数据", notes = "初始化数据", response = String.class)
    public HttpRspParam initData() throws SQLException {
        initService.initData();
        return success("init data success");
    }


    /**
     * 获取当前版本
     */
    @RequestMapping("/version")
    @ApiOperation(value = "应用版本", notes = "应用版本", response = String.class)
    public HttpRspParam version() {
        return success(applicationVersion);
    }


}
