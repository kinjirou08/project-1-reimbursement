package com.revature.controller;

import io.javalin.Javalin;
import io.javalin.http.Handler;

public class UserController {
	
	private Handler login = (ctx) -> {
		
		String username = ctx.formParam("username");
		String password = ctx.formParam("password");
		
		System.out.println(username + " " + password);
		
		ctx.result(username + " " + password);
		
	};
	
	public void registerEndpoints(Javalin app) {
		app.post("/login", login);
	}

}
