package com.revature.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.revature.dto.LoginCredentialsDTO;
import com.revature.models.Users;
import com.revature.services.UsersService;

import io.javalin.Javalin;
import io.javalin.http.Handler;

public class ValidationController {
	
	UsersService userService;
	
	public ValidationController () {
		this.userService = new UsersService();
	}
	
	private Handler login = (ctx) -> {
	
		LoginCredentialsDTO login = ctx.bodyAsClass(LoginCredentialsDTO.class);
		
		Users user = this.userService.getUserByUsernameAndPassword(login.getErsUsername(), login.getErsPassword());
		
		HttpServletRequest req = ctx.req;
		
		HttpSession session = req.getSession();
		session.setAttribute("validateduser",user);
		
		ctx.result("Redirecting...");
		
		
//		if (username.equals("username") && password.equals("password")) {			
//			if (req.getSession(false) != null) { //not necessarily needed
//				ctx.result("You're already logged in!");// not necessarily needed
//			} else { 			
//				HttpSession session = req.getSession();
//				session.setAttribute("currentuser", username);
//				ctx.result("Success!");
//			}
//		} else {
//			ctx.result("Invalid username and/or password");
//		}
		
		//req.getSession().invalidate(); ---> to invalidate that session
		//ctx.redirect("/empHomePage");
		
	};
	
	public void registerEndpoints(Javalin app) {
		app.post("/login", login);
	}

}
