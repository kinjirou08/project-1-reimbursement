package com.revature.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.revature.exceptions.UnauthorizedException;
import com.revature.models.Users;
import com.revature.services.AuthorizationService;

class AuthorizationServiceTest {
	
	private AuthorizationService authService;
	
	@BeforeEach
	public void setUp () {
		this.authService = new AuthorizationService();
	}
	
	/*
	 * authorizeEmployeeAndFinanceManager() method
	 */
	
	@Test
	void authorizeEmployeeAndFinanceManager_positveTest_userIsEmployee () throws UnauthorizedException {
		
		Users user = new Users(1, "jymm_enriquez", "jymm123", "Jymm", "Enriquez", "jymm.enriquez@gmail.com", "Employee");
		
		this.authService.authorizeEmployeeandFinanceManager(user);		
	}
	
	@Test
	void authorizeEmployeeAndFinanceManager_positveTest_userIsFinanceManager () throws UnauthorizedException {
		
		Users user = new Users(1, "jymm_enriquez", "jymm123", "Jymm", "Enriquez", "jymm.enriquez@gmail.com", "Finance Manager");
		
		this.authService.authorizeEmployeeandFinanceManager(user);		
	}
	
	@Test
	void authorizeEmployeeAndFinanceManager_negativeTest_userIsNotFinanceManagerOrEmployee () throws UnauthorizedException {
		
		Users user = new Users(1, "jymm_enriquez", "jymm123", "Jymm", "Enriquez", "jymm.enriquez@gmail.com", "Guest");
		
		Assertions.assertThrows(UnauthorizedException.class, () -> {
			this.authService.authorizeEmployeeandFinanceManager(user);
		});		
	}
	
	@Test
	void authorizeEmployeeAndFinanceManager_positveTest_userIsNull () throws UnauthorizedException {
		
		Assertions.assertThrows(UnauthorizedException.class, () -> {
			this.authService.authorizeEmployeeandFinanceManager(null);
		});		
	}
	
	/*
	 * authorizeEmployee() method
	 */
	
	@Test
	void authorizeEmployee_positveTest_userIsEmployee () throws UnauthorizedException {
		
		Users user = new Users(1, "jymm_enriquez", "jymm123", "Jymm", "Enriquez", "jymm.enriquez@gmail.com", "Employee");
		
		this.authService.authorizeEmployee(user);	
	}
	
	@Test
	void authorizeEmployee_negativeTest_userIsNotAnEmployee () throws UnauthorizedException {
		
		Users user = new Users(1, "jymm_enriquez", "jymm123", "Jymm", "Enriquez", "jymm.enriquez@gmail.com", "Finance Manager");
		
		Assertions.assertThrows(UnauthorizedException.class, () -> {
			this.authService.authorizeEmployee(user);
		});	
	}
	
	@Test
	void authorizeEmployee_negativeTest_userIsNull () throws UnauthorizedException {
			
		Assertions.assertThrows(UnauthorizedException.class, () -> {
			this.authService.authorizeEmployee(null);
		});
	}
	
	/*
	 * authorizeFinanceManager() method
	 */
	
	@Test
	void authorizeFinanceManager_positveTest_userIsFinanceManager () throws UnauthorizedException {
		
		Users user = new Users(1, "jymm_enriquez", "jymm123", "Jymm", "Enriquez", "jymm.enriquez@gmail.com", "Finance Manager");
		
		this.authService.authorizeFinanceManager(user);	
	}
	
	@Test
	void authorizeFinanceManager_negativeTest_userIsNotAFinanceManager () throws UnauthorizedException {
		
		Users user = new Users(1, "jymm_enriquez", "jymm123", "Jymm", "Enriquez", "jymm.enriquez@gmail.com", "Employee");
		
		Assertions.assertThrows(UnauthorizedException.class, () -> {
			this.authService.authorizeFinanceManager(user);
		});	
	}
	
	@Test
	void authorizeFinanceManager_negativeTest_userIsNull () throws UnauthorizedException {
			
		Assertions.assertThrows(UnauthorizedException.class, () -> {
			this.authService.authorizeFinanceManager(null);
		});
	}
}
