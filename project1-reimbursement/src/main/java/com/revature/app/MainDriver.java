package com.revature.app;

import com.revature.controller.ExceptionMappingController;
import com.revature.controller.MapEndpoints;
import com.revature.controller.UsersController;
import com.revature.controller.ValidationController;

import io.javalin.Javalin;
import io.javalin.core.JavalinConfig;

public class MainDriver {

	public static void main(String[] args) {

		Javalin app = Javalin.create(JavalinConfig::enableCorsForAllOrigins);
		

		mapControllers(app, new ValidationController(), new UsersController());

		ExceptionMappingController exceptionMapper = new ExceptionMappingController();

		exceptionMapper.mapExceptions(app);

		app.start(8080);

	}

	public static void mapControllers(Javalin app, MapEndpoints... endpoints) {

		for (int i = 0; i < endpoints.length; i++) {
			endpoints[i].mapEndpoints(app);
		}

	}

}
