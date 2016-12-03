package com.fs.app.portal.interceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.ModelAndView;

import com.fs.commons.app.pojo.UserInfoPojo;

public class CheckManageInterceptor extends BaseInterceptor {

	// 在Controller方法调用之前调用.当返回值为false的时候整个请求就结束了。
	// 对权限进行操作
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		UserInfoPojo user = (UserInfoPojo) request.getSession().getAttribute(
				"manage");
		if (null != user && user.getUserName().equals("admin")
				&& user.getUserPassword().equals("PASSword")) {
			super.preHandle(request, response, handler);
		} else {
			Cookie[] reqcookies = request.getCookies();
			if (reqcookies != null) {
				for (Cookie cookie : reqcookies) {
					if (cookie.getName().toString().equals("feisaadmin")) {
						String cookievalue = cookie.getValue().toString();
						String[] temp = cookievalue.split("\\^");
						if (temp.length == 2) {
							UserInfoPojo tempuser = new UserInfoPojo();
							tempuser.setUserName(temp[0]);
							tempuser.setUserPassword(temp[1]);
							if (tempuser != null) {
								request.getSession()
										.setMaxInactiveInterval(900);
								request.getSession().setAttribute("manage",
										tempuser);
								super.preHandle(request, response, handler);
							}
						}
					}
				}
			}
		}
		response.sendRedirect("login.jsp");
		return false;
	}

	// 在Controller的方法调用之后执行，但是它会在DispatcherServlet进行视图的渲染之前执行
	// 作用:对ModelAndView进行操作
	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1,
			Object arg2, ModelAndView arg3) throws Exception {
	}

	// 在Controller的方法调用之后,DispatcherServlet进行视图的渲染之后执行
	// 作用:清理资源
	@Override
	public void afterCompletion(HttpServletRequest arg0,
			HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
	}
}
