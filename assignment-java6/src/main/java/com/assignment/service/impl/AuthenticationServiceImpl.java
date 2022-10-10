package com.assignment.service.impl;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.assignment.constant.SessionConstant;
import com.assignment.entity.Users;
import com.assignment.jwt.CustomUser;
import com.assignment.jwt.JwtTokenProvider;
import com.assignment.service.AuthenticationService;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
	
	@Autowired
	private AuthenticationManager authenManager;
	
    @Autowired
    private JwtTokenProvider tokenProvider;

	@Override
	public Users doLogin(String username, String password, HttpSession session) throws Exception {
		UsernamePasswordAuthenticationToken authenInfo = new UsernamePasswordAuthenticationToken(username, password);
		Authentication authentication = authenManager.authenticate(authenInfo);
		
		CustomUser customUser = (CustomUser) authentication.getPrincipal();
		Users userResponse = customUser.getUser();
		
		if (userResponse.getActive() != null) {
			return null;
		}

		SecurityContextHolder.getContext().setAuthentication(authentication);
        
    	session.setAttribute(SessionConstant.JWT, tokenProvider.generateToken(customUser));
		return userResponse;
	}
}
