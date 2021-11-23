package com.revature.services;

import com.revature.models.Users;

public class AuthorizationService {
	
	public void authorizeEmployee(Users user) throws UnauthorizedException {
		// If the user is not either a regular role or admin role
		if (user == null || !user.getErsRole().equals("Employee")) {
			throw new UnauthorizedException("You must be an Employee to access this resource");
		}
	}	
	public void authorizeFinanceManager(Users user) throws UnauthorizedException {
		if (user == null || !user.getErsRole().equals("Finance Manager")) {
			throw new UnauthorizedException("You must be a Finance Manager to access this resource");
		}
	}

}
