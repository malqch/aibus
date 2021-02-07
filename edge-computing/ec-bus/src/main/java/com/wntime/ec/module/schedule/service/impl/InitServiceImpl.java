package com.wntime.ec.module.schedule.service.impl;

import com.wntime.ec.common.model.Constant;
import com.wntime.ec.common.util.CommonUtil;
import com.wntime.ec.common.util.SpringContextUtil;
import com.wntime.ec.common.util.exception.BusinessException;
import com.wntime.ec.module.schedule.dao.InitDao;
import com.wntime.ec.module.schedule.service.IInitService;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.Reader;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author wing
 * @create 2020-08-28 15:08
 */
@Service
@Slf4j
public class InitServiceImpl implements IInitService {

    @Autowired
    InitDao initDao;


    @Override
    public void initTable() throws SQLException {
        List<String> initList = new ArrayList<>();

        //查询已经初始化的数据库表名
        List<String> initedTableNames = initDao.selectInitedTableNames();
        boolean notEmpty = CommonUtil.isNotEmpty(initedTableNames);

        Constant.H2Table[] values = Constant.H2Table.values();
        for (Constant.H2Table table : values) {
            String code = table.getCode().toUpperCase();
            if (notEmpty) {
                if (!initedTableNames.contains(code)) {
                    initList.add(code);
                }
            } else {
                initList.add(code);
            }
        }

        if (CommonUtil.isNotEmpty(initList)) {
            initTable(initList);
            log.info("init table finished ...");
        }
    }

    private void initTable(List<String> initList) throws SQLException {
        DataSource dataSource = SpringContextUtil.applicationContext.getBean(DataSource.class);
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new BusinessException("starting：get h2 connection error，" + e.getMessage());
        }
        ScriptRunner runner = new ScriptRunner(connection);
        Reader resourceAsReader = null;
        try {
            for (String tableName : initList) {
                resourceAsReader = Resources.getResourceAsReader("db/h2/" + tableName.toLowerCase() + ".sql");
                runner.runScript(resourceAsReader);
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new BusinessException("starting：read SQL file error，" + e.getMessage());
        } finally {
            runner.closeConnection();
            connection.close();
        }
    }

    @Override
    public void initData() throws SQLException {
        DataSource dataSource = SpringContextUtil.applicationContext.getBean(DataSource.class);
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new BusinessException("starting：get h2 connection error，" + e.getMessage());
        }
        ScriptRunner runner = new ScriptRunner(connection);
        Reader resourceAsReader = null;
        try {
            resourceAsReader = Resources.getResourceAsReader("db/h2/init_data.sql");
            runner.runScript(resourceAsReader);
        } catch (IOException e) {
            e.printStackTrace();
            throw new BusinessException("starting：read SQL file error，" + e.getMessage());
        } finally {
            runner.closeConnection();
            connection.close();
        }
    }


}
