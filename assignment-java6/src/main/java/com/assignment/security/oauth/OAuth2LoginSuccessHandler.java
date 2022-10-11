package com.assignment.security.oauth;

import java.io.IOException;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.assignment.constant.SessionConstant;
import com.assignment.entity.AuthenticationProvider;
import com.assignment.entity.Users;
import com.assignment.service.UsersService;

@Component
public class OAuth2LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

	@Autowired
	private UsersService usersService;
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		CustomOAuth2User oAuth2User = (CustomOAuth2User) authentication.getPrincipal();
		String email = oAuth2User.getEmail();
		String fullname = oAuth2User.getFullName();
		Users user = usersService.findByEmail(email);
		Users userResponse = null;
		Random r = new Random( System.currentTimeMillis() );
		Integer randomPassword = 10000 + r.nextInt(20000);
		if (user == null) {
			userResponse = usersService.createNewUserAfterOAuthLoginSuccess(email, fullname, randomPassword, AuthenticationProvider.GOOGLE);
		} else {
			userResponse = usersService.updateCustomerAfterOAuthLoginSuccess(user, fullname, AuthenticationProvider.GOOGLE);
		}
		
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpSession session = httpRequest.getSession();
		session.setAttribute(SessionConstant.CURRENT_USER, userResponse);
		
		super.onAuthenticationSuccess(request, response, authentication);
	}

}
