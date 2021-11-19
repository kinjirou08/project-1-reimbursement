package com.revature.app;

import com.revature.controller.ValidationController;

import io.javalin.Javalin;

public class MainDriver {

	public static void main(String[] args) {
	
		Javalin app = Javalin.create();
		
		ValidationController validateController = new ValidationController();
		
		validateController.registerEndpoints(app);
		
		app.start(8080);
		
	}

}
