package com.wntime.datasource.config;

import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionDefinition;

import javax.sql.DataSource;

public class DynamicDataSourceTransactionManager extends DataSourceTransactionManager {

    public DynamicDataSourceTransactionManager(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    protected void doBegin(Object transaction, TransactionDefinition definition) {
        //根据事务是否是只读来设定数据源
        boolean readOnly=definition.isReadOnly();
        if(readOnly){
            DynamicContextHolder.push("read");
        }else {
            DynamicContextHolder.push("write");
        }
        super.doBegin(transaction, definition);
    }

    //清理本地数据源
    @Override
    protected void doCleanupAfterCompletion(Object transaction) {
        DynamicContextHolder.poll();
        super.doCleanupAfterCompletion(transaction);
    }
}
