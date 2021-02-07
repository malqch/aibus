

package com.wntime.common.aspect;

import com.google.gson.Gson;
import com.wntime.common.annotation.SysLog;
import com.wntime.common.utils.DateUtils;
import com.wntime.common.utils.HttpContextUtils;
import com.wntime.common.utils.IPUtils;
import com.wntime.entity.AdminUser;
import com.wntime.modules.sys.entity.AdminOperationLog;
import com.wntime.modules.sys.service.AdminOperationLogService;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;


/**
 * 系统日志，切面处理类
 *
 */
@Aspect
@Component
public class SysLogAspect {
	@Autowired
	private AdminOperationLogService adminOperationLogService;

	@Pointcut("@annotation(com.wntime.common.annotation.SysLog)")
	public void logPointCut() {

	}

	@Around("logPointCut()")
	public Object around(ProceedingJoinPoint point) throws Throwable {
		long beginTime = System.currentTimeMillis();
		//执行方法
		Object result = point.proceed();
		//执行时长(毫秒)
		long time = System.currentTimeMillis() - beginTime;

		//保存日志
		saveSysLog(point, time);

		return result;
	}

	private void saveSysLog(ProceedingJoinPoint joinPoint, long time) {
		MethodSignature signature = (MethodSignature) joinPoint.getSignature();
		Method method = signature.getMethod();

		AdminOperationLog adminOperationLog = new AdminOperationLog();
		SysLog syslog = method.getAnnotation(SysLog.class);
		if(syslog != null){
			//注解上的描述
			adminOperationLog.setOperation(syslog.value());
		}

		//请求的方法名
		String className = joinPoint.getTarget().getClass().getName();
		String methodName = signature.getName();
		adminOperationLog.setRequestMethod(className + "." + methodName + "()");

		//请求的参数
		Object[] args = joinPoint.getArgs();
		try{
			String params = new Gson().toJson(args);
			if(StringUtils.isNotEmpty(params) && params.length() > 4000){
				params = params.substring(0,4000) + "...";
			}
			adminOperationLog.setRequestParams(params);
		}catch (Exception e){

		}

		//获取request
		HttpServletRequest request = HttpContextUtils.getHttpServletRequest();
		//设置IP地址
		adminOperationLog.setRequestAddress(IPUtils.getIpAddr(request));

		//用户名
		AdminUser adminUser = ((AdminUser) SecurityUtils.getSubject().getPrincipal());
		adminOperationLog.setUsername(adminUser.getUserName());

		adminOperationLog.setExecutedTime(time);
		adminOperationLog.setCreateDt(DateUtils.getTimestamp());
		adminOperationLog.setCreateUserId(adminUser.getUserId());
		//保存系统日志
		adminOperationLogService.save(adminOperationLog);
	}
}
