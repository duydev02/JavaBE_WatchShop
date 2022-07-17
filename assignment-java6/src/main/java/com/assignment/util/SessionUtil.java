package com.assignment.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.util.ObjectUtils;

import com.assignment.constant.SessionConstant;
import com.assignment.dto.CartDto;

public class SessionUtil {

	private SessionUtil() {
		
	}
	
	public static void validateCart(HttpSession session) {
		if(ObjectUtils.isEmpty(session.getAttribute(SessionConstant.CURRENT_CART))) {
			session.setAttribute(SessionConstant.CURRENT_CART, new CartDto());
		}
	}
	
	public static void validateMaxSizePage(HttpSession session) {
		if(ObjectUtils.isEmpty(session.getAttribute("maxSizePage"))) {
			session.setAttribute("maxSizePage", 3);
		}
	}
	
	public static CartDto getCurrentCart(HttpSession session) {
		return (CartDto) session.getAttribute(SessionConstant.CURRENT_CART);
	}
	
	public static Integer getMaxSizePage(HttpSession session) {
		return (Integer) session.getAttribute("maxSizePage");
	}
	
	public static void setMaxSizePage(Integer maxSizePage ,HttpSession session) {
		session.setAttribute("maxSizePage", maxSizePage);
	}
	
    public static String getSiteURL(HttpServletRequest request) {
        String siteURL = request.getRequestURL().toString();
        return siteURL.replace(request.getServletPath(), "");
    }
	
}
