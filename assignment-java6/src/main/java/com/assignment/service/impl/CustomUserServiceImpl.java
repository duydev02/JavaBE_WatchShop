package com.assignment.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.assignment.entity.Users;
import com.assignment.jwt.CustomUser;
import com.assignment.service.UsersService;

@Service
public class CustomUserServiceImpl implements UserDetailsService {
	
	@Autowired
	private UsersService userService;

	@Override
    public UserDetails loadUserByUsername(String username) {
		Users user = userService.findByUsername(username);
    	if (user == null || user.getIsDeleted()) {
    		System.out.println("User not found: " + username);
            throw new UsernameNotFoundException(username);
        }
    	try {
    		List<GrantedAuthority> grantList = new ArrayList<>();
    		GrantedAuthority authority = new SimpleGrantedAuthority(user.getRole().getDescription());
    		grantList.add(authority);
            return new CustomUser(user, grantList);
    	} catch(Exception e) {
    		e.printStackTrace();
    	}
    	return null;
    }
}
