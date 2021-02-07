

package com.wntime.modules.sys.controller;

import com.wntime.common.utils.PageUtils;
import com.wntime.common.utils.R;
import com.wntime.modules.sys.entity.AdminOperationLog;
import com.wntime.modules.sys.service.AdminOperationLogService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


/**
 * 系统日志

 */
@Api(value = "系统日志接口",tags = {"系统日志接口"})
@Controller
@RequestMapping("/sys/log")
public class AdminOperationLogController {
	@Autowired
	private AdminOperationLogService adminOperationLogService;

	/**
	 * 列表
	 */
	@ApiOperation(value = "获取日志列表",httpMethod = "GET",notes = "")
	@ResponseBody
	@GetMapping("/list")
//	@RequiresPermissions("sys:log:list")
	@ApiImplicitParams({
			@ApiImplicitParam(paramType="query", name = "page", value = "请求页码", required = true, dataType = "int"),
			@ApiImplicitParam(paramType="query", name = "limit", value = "每页数量", required = true, dataType = "int"),
			@ApiImplicitParam(paramType="query", name = "userName", value = "用户名", required = true, dataType = "String"),
			@ApiImplicitParam(paramType="query", name = "params", value = "用户名", required = false, dataType = "String")
	})
	public R list(@RequestParam Map<String, Object> params){

		PageUtils page = adminOperationLogService.queryPage(params);
		if(page!=null&&page.getList().size()>0){

			((AdminOperationLog)page.getList().get(0)).getOperation();
		}
		return R.ok().put("page", page);
	}

}
