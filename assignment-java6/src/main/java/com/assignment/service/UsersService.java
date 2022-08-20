package com.assignment.service;

import java.sql.SQLException;
import java.util.List;

import com.assignment.dto.ChangePassword;
import com.assignment.entity.Users;
import com.assignment.util.UserNotFoundException;

public interface UsersService {

	Users findByUsername(String username);
	
	Users findByEmail(String email);
	
	Users findByResetPassword(String token);
	
	Users findByActive(String activeToken);

	Users doLogin(String username, String password);

	Users save(Users user) throws SQLException;

	List<Users> findAll();

	void deleteLogical(String username);

	void update(Users user);

	void updateResetPassword(String token, String email) throws Exception;
	
	void updatePassword(Users user, String newPassword);

	void updateActive(String token) throws UserNotFoundException;
	
	void changePassword(ChangePassword changePassword, String username);
	
	void change(Users user, String fullname, String newImage);
}
