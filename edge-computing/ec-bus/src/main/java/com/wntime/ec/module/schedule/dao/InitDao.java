package com.wntime.ec.module.schedule.dao;

import com.wntime.ec.module.schedule.vo.InitedTable;

import java.util.List;

/**
 * @author wing
 * @create 2019-12-11 14:48
 */
public interface InitDao {

    List<InitedTable> selectInitedTable();

    List<String> selectInitedTableNames();
}
