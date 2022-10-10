package com.assignment.service;

import javax.servlet.http.HttpSession;

import com.assignment.entity.Users;

public interface AuthenticationService {
	
	Users doLogin(String username, String password, HttpSession session) throws Exception;
}
