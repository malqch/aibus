package com.wntime.ec.module.schedule.service;

import java.sql.SQLException;

/**
 * @author wing
 * @create 2020-08-28 15:08
 */
public interface IInitService {
    void initTable() throws SQLException;

    void initData() throws SQLException;



}
