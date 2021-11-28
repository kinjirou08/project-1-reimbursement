package com.revature.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.revature.dto.ExceptionMessageDTO;
import com.revature.dto.LoginCredentialsDTO;
import com.revature.dto.NewUsersDTO;
import com.revature.models.Users;
import com.revature.services.UsersService;

import io.javalin.Javalin;
import io.javalin.http.Handler;

public class ValidationController implements MapEndpoints{
	
	UsersService userService;
	
	public ValidationController () {
		this.userService = new UsersService();
	}
	
	private Handler login = (ctx) -> {
	
		LoginCredentialsDTO login = ctx.bodyAsClass(LoginCredentialsDTO.class);		
		Users user = this.userService.getUserByUsernameAndPassword(login.getErsUsername(), login.getErsPassword());
		
		HttpServletRequest req = ctx.req;
		HttpSession session = req.getSession();
		
		session.setAttribute("validateduser", user);
			
		ctx.json(user);
		
	};	
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
		
	private Handler logout = (ctx) -> {
		HttpServletRequest req = ctx.req;
		
		req.getSession().invalidate();
	};
	
	private Handler checkLoginStatus = (ctx) -> {
		HttpSession session = ctx.req.getSession();
		
		if (!(session.getAttribute("validateduser") == null)) {
			ctx.json(session.getAttribute("validateduser"));
			ctx.status(200);
		} else {
			ctx.json(new ExceptionMessageDTO("You're not logged in"));
			ctx.status(401);
		}
	};
	
	public Handler signup = (ctx) -> {
		
		NewUsersDTO newUser = ctx.bodyAsClass(NewUsersDTO.class);
		
		Users user = this.userService.newUser(newUser);
		
		ctx.json(user);
		
	};
	
	@Override
	public void mapEndpoints(Javalin app) {
		app.post("/login", login);
		app.post("/logout", logout);
		app.get("loginStatus", checkLoginStatus);
		app.post("/signup", signup);
		
	}

}
