package com.revature.services;

import com.revature.exceptions.UnauthorizedException;
import com.revature.models.Users;

public class AuthorizationService {
	
	public void authorizeEmployeeandFinanceManager(Users user) throws UnauthorizedException {
		if (user == null || !(user.getErsRole().equals("Employee") || user.getErsRole().equals("Finance Manager"))) {
			throw new UnauthorizedException("You must be an Finance Manager/Employee to access this resource");
		}
	}
	
	public void authorizeEmployee(Users user) throws UnauthorizedException {
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
