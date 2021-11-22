package com.revature.app;

import com.revature.controller.MapEndpoints;
import com.revature.controller.UsersController;
import com.revature.controller.ValidationController;

import io.javalin.Javalin;

public class MainDriver {

	public static void main(String[] args) {
	
		Javalin app = Javalin.create();
		
		mapControllers(app, new ValidationController(), new UsersController());
			
		app.start(8080);
		
	}
	
	public static void mapControllers(Javalin app, MapEndpoints... endpoints) {
		
		for (int i = 0; i < endpoints.length; i ++) {
			endpoints[i].mapEndpoints(app);
		}
		
	}

}
