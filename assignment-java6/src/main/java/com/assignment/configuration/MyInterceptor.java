package com.assignment.configuration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.assignment.entity.Users;
import com.assignment.service.SessionService;

@Component
@Service
public class MyInterceptor implements HandlerInterceptor {

	@Autowired
	SessionService sessionService;

	@Override
	public boolean preHandle(HttpServletRequest req, HttpServletResponse resp, Object handler) throws Exception {
		String uri = req.getRequestURI();
		Users user = sessionService.get("currentUser"); // lấy từ session

		String error = "";

		if (user == null) // chưa đăng nhập
		{
			error = "please login!";
		}
		// không đúng vai trò
		else if (!user.getRole().getDescription().equals("admin") && uri.startsWith("/admin/")) {
			 error = "access denied!";
		}
		if (error.length() > 0) {
			sessionService.set("security-uri", uri);
			resp.sendRedirect("/account/login?error=" + error);
			return false;
		}
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest req, HttpServletResponse resp, Object handler, ModelAndView modelandview)
			throws Exception {

	}

	@Override
	public void afterCompletion(HttpServletRequest req, HttpServletResponse resp, Object handler, Exception ex)
			throws Exception {
		System.out.println("afterCompletion()->" + req.getRequestURI());

	}
}
