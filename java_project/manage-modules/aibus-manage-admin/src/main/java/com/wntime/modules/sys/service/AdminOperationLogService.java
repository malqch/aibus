

package com.wntime.modules.sys.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.wntime.common.utils.PageUtils;
import com.wntime.modules.sys.entity.AdminOperationLog;

import java.util.Map;


/**
 * 系统日志

 */
public interface AdminOperationLogService extends IService<AdminOperationLog> {

    PageUtils queryPage(Map<String, Object> params);

}
