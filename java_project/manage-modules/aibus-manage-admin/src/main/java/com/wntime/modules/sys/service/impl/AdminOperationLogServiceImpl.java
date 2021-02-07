

package com.wntime.modules.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wntime.common.utils.PageUtils;
import com.wntime.common.utils.Query;
import com.wntime.modules.sys.dao.AdminOperationLogDao;
import com.wntime.modules.sys.entity.AdminOperationLog;
import com.wntime.modules.sys.service.AdminOperationLogService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Map;


@Service("adminOperationLogService")
public class AdminOperationLogServiceImpl extends ServiceImpl<AdminOperationLogDao, AdminOperationLog> implements AdminOperationLogService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String username = (String)params.get("name");
        String queryDt = (String) params.get("queryDt");
        Timestamp queryDtStart = null;
        Timestamp queryDtEnd = null;
        if(StringUtils.isNotBlank(queryDt)){
            String[] split = queryDt.split(",");
            queryDtStart = Timestamp.valueOf(split[0]);
            queryDtEnd = Timestamp.valueOf(split[1]);
        }

        IPage<AdminOperationLog> page = this.page(
            new Query<AdminOperationLog>().getPage(params),
            new QueryWrapper<AdminOperationLog>()
                    .like(StringUtils.isNotBlank(username),"admin_operation_log.username", username)
                    .or()
                    .like(StringUtils.isNotBlank(username),"admin_operation_log.operation", username)
                    .between(StringUtils.isNotBlank(queryDt),"admin_operation_log.create_dt",queryDtStart,queryDtEnd)
                    .orderBy(true,false,"create_dt")
        );

        return new PageUtils(page);
    }
}
