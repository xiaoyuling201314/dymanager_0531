package com.dayuan.intercept;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.aspectj.lang.annotation.Aspect;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * 权限拦截器
 * @author lu
 *
 */
@Aspect
public class AuthInterceptor extends HandlerInterceptorAdapter {
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		Object sysObject=request.getSession().getAttribute("userSession");
		String contextPath=request.getContextPath();
		String url=request.getServletPath().toString();
		if(sysObject==null){
			response.sendRedirect(contextPath+"/login.jsp");
			return false;
		}
		return super.preHandle(request, response, handler);
	}

	
}
