

package com.wntime.common.utils;

import com.wntime.common.exception.RRException;
import com.wntime.entity.AdminUser;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Shiro工具类
 *

 */
public class ShiroUtils {

	private final static Map<String,Session> sessions=new ConcurrentHashMap<>();

	public static Session getSession(String token){
		return sessions.get(token);
	}

	public static void putSession(String token,Session session){
		sessions.put(token,session);
	}

	public static void deleteSession(String token){
		Session session = sessions.get(token);
		if(session!=null){
			sessions.remove(token);
			session.stop();
		}
	}

	public static Session getSession() {
		return SecurityUtils.getSubject().getSession();
	}

	public static Subject getSubject() {
		return SecurityUtils.getSubject();
	}

	public static AdminUser getUserEntity() {
		return (AdminUser)SecurityUtils.getSubject().getPrincipal();
	}

	public static Long getUserId() {
		return getUserEntity().getUserId();
	}
	
	public static void setSessionAttribute(Object key, Object value) {
		getSession().setAttribute(key, value);
	}

	public static Object getSessionAttribute(Object key) {
		return getSession().getAttribute(key);
	}

	public static boolean isLogin() {
		return SecurityUtils.getSubject().getPrincipal() != null;
	}

	public static String getKaptcha(String key) {
		Object kaptcha = getSessionAttribute(key);
		if(kaptcha == null){
			throw new RRException("验证码已失效");
		}
		getSession().removeAttribute(key);
		return kaptcha.toString();
	}

}
