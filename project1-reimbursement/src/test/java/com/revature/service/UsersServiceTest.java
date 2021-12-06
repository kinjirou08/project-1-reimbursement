package com.revature.service;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.sql.SQLException;

import javax.security.auth.login.FailedLoginException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.revature.dao.UsersDAO;
import com.revature.models.Users;
import com.revature.services.UsersService;

public class UsersServiceTest {
	
private UsersService usersService;
UsersDAO mockUsersDao;
	
	@BeforeEach
	public void setUp () {
		this.usersService = new UsersService();
		 this.mockUsersDao = mock(UsersDAO.class);
	}
	
	/*
	 * UsersService getUsernameAndPassword() test
	 */
	
	@Test // Happy Path
	void getUsernameAndPassword_PostiveTest () throws SQLException, FailedLoginException {
			
		Users user = new Users (1, "jymm.enriquez", "p4ssw0rd", "Jymm", "Enriquez",
				"jymm.enriquez@revature.net", "Finance Manager");
		
		when(mockUsersDao.selectUserByUsernameAndPassword(eq("jymm.enriquez"), eq("p4ssw0rd"))).thenReturn(user);
		
		usersService = new UsersService(mockUsersDao);
		
		Users actual = usersService.getUserByUsernameAndPassword("jymm.enriquez", "p4ssw0rd");
		
		Users expected = new Users (1, "jymm.enriquez", "p4ssw0rd", "Jymm", "Enriquez",
				"jymm.enriquez@revature.net", "Finance Manager");
		
		Assertions.assertEquals(expected, actual);
	}
	
	@Test // Sad Path
	void getUserNameAndPassword_NegativeTest_UsersIsNull () {
		
		usersService = new UsersService(mockUsersDao);
		
		Assertions.assertThrows(FailedLoginException.class, () -> {
			usersService.getUserByUsernameAndPassword("jymm.enriquez", "p4ssw0rd");
		});
	}

}
