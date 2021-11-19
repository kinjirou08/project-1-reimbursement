package com.revature.services;

import javax.security.auth.login.FailedLoginException;

import com.revature.dao.UsersDAO;
import com.revature.models.Users;

public class UsersService {
	
	UsersDAO userDao;
	
	public UsersService () {
		this.userDao = new UsersDAO();
	}

	public Users getUserByUsernameAndPassword(String ers_username, String ers_password) throws FailedLoginException {
		
		Users user = this.userDao.selectUserByUsernameAndPassword(ers_username, ers_password);
		
		if (user == null) {
			throw new FailedLoginException("Incorrect username and/or password!");
		}
		
		return user;
	}

}
