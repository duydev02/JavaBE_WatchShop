package com.assignment.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.assignment.dto.ChangePassword;
import com.assignment.entity.Roles;
import com.assignment.entity.Users;
import com.assignment.repository.UsersRepo;
import com.assignment.service.RolesService;
import com.assignment.service.UsersService;
import com.assignment.util.UserNotFoundException;

import net.bytebuddy.utility.RandomString;

@Service
public class UsersServiceImpl implements UsersService {

	private BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();

	@Autowired
	private UsersRepo repo;

	@Autowired
	private RolesService roleService;

	@Override
	public Users doLogin(String username, String password) {
		Users user = repo.findByUsername(username);

		if (user != null && user.getActive() == null) {
			boolean checkPassword = bcrypt.matches(password, user.getHashPassword());
			return checkPassword ? user : null;
		} else {
			return null;
		}
	}

	@Override
	@Transactional(rollbackOn = { Exception.class, Throwable.class })
	public Users save(Users user) {
		String tokenActive = RandomString.make(30);
		Roles role = roleService.findByDescription("user");
		user.setRole(role);
		user.setIsDeleted(Boolean.FALSE);
		String hashPassword = bcrypt.encode(user.getHashPassword());
		user.setHashPassword(hashPassword);
		user.setActive(tokenActive);
		return repo.saveAndFlush(user);
	}

	@Override
	public List<Users> findAll() {
		return repo.findByIsDeleted(Boolean.FALSE);
	}

	@Override
	@Transactional(rollbackOn = { Exception.class, Throwable.class })
	public void deleteLogical(String username) {
		repo.deleteLogical(username);
	}

	@Override
	public Users findByUsername(String username) {
		return repo.findByUsername(username);
	}

	@Override
	public Users findByEmail(String email) {
		return repo.findByEmail(email);
	}

	@Override
	public Users findByResetPassword(String token) {
		return repo.findByResetPassword(token);
	}

	@Override
	public Users findByActive(String activeToken) {
		return repo.findByActive(activeToken);
	}

	@Override
	@Transactional(rollbackOn = { Exception.class, Throwable.class })
	public void update(Users user) {
		if (ObjectUtils.isNotEmpty(user) && StringUtils.isEmpty(user.getHashPassword())) {
			repo.updateNonPass(user.getEmail(), user.getFullname(), user.getImgUrl(), user.getUsername());
		} else {
			String hashPassword = bcrypt.encode(user.getHashPassword());
			user.setHashPassword(hashPassword);
			repo.update(user.getEmail(), hashPassword, user.getFullname(), user.getImgUrl(), user.getUsername());
		}
	}

	@Override
	public void updateResetPassword(String token, String email) throws UserNotFoundException {
		Users user = repo.findByEmail(email);
		if (user != null) {
			user.setResetPassword(token);
			repo.save(user);
		} else {
			throw new UserNotFoundException("Could not find any customer with the email " + email);
		}
	}

	@Override
	public void updatePassword(Users user, String newPassword) {
		String hashPassword = bcrypt.encode(newPassword);
		user.setHashPassword(hashPassword);
		user.setResetPassword(null);
		repo.save(user);
	}

	@Override
	public void updateActive(String token) throws UserNotFoundException {
		Users user = repo.findByActive(token);
		if (user != null) {
			user.setActive(null);
			repo.save(user);
		} else {
			throw new UserNotFoundException("Could not find any customer with the active token");
		}
	}

	@Override
	@Transactional(rollbackOn = { Exception.class, Throwable.class })
	public void changePassword(ChangePassword changePassword, String username) {
		String currentPassword = changePassword.getCurrentPassword();
		String newPassword = changePassword.getNewPassword();
		Users user = repo.findByUsername(username);

		if (user != null && user.getActive() == null) {
			boolean checkPassword = bcrypt.matches(currentPassword, user.getHashPassword());
			if (checkPassword) {
				String hashPassword = bcrypt.encode(newPassword);
				repo.changePassword(hashPassword, username);
			}
		}
	}

	@Override
	public void change(Users user, String fullname, String newImage) {
		user.setFullname(fullname);
		user.setImgUrl(newImage);
		repo.save(user);
	}

}
