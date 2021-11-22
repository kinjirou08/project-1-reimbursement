package com.revature.services;

import java.sql.SQLException;

import javax.security.auth.login.FailedLoginException;

import com.revature.dao.UsersDAO;
import com.revature.models.Users;

public class UsersService {
	
	UsersDAO userDao;
	
	public UsersService () {
		this.userDao = new UsersDAO();
	}

	public Users getUserByUsernameAndPassword(String ersUsername, String ersPassword) throws FailedLoginException, SQLException {
		
		Users user = this.userDao.selectUserByUsernameAndPassword(ersUsername, ersPassword);
		
		if (user == null) {
			throw new FailedLoginException("Incorrect username and/or password!");
		}
		
		return user;
	}

}
