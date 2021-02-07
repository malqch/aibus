package com.wntime.ec.module.schedule.vo;

import lombok.Data;

/**
 * @author wing
 * @create 2019-12-16 10:48
 * H2 数据库初始化数据库 对象封装
 */
@Data
public class InitedTable {
   private String tableCatalog;
   private String tableSchema;
   private String tableName;
   private String tableType;
   private String storageType;
   private String sql;
   private String remarks;
}
