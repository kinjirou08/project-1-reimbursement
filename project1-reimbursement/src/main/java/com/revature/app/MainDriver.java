package com.revature.app;

import com.revature.controller.UserController;

import io.javalin.Javalin;

public class MainDriver {

	public static void main(String[] args) {
	
		Javalin app = Javalin.create();
		
		UserController userController = new UserController();
		
		userController.registerEndpoints(app);
		
		app.start(8080);
		
	}

}
