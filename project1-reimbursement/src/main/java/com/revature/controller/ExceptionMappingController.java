package com.revature.controller;

import java.security.InvalidParameterException;

import com.revature.dto.ExceptionMessageDTO;
import com.revature.exceptions.ReimbursementNotFoundExcpetion;

import io.javalin.Javalin;

public class ExceptionMappingController {
	
	public void mapExceptions(Javalin app) {
		app.exception(InvalidParameterException.class, (e, ctx) -> {
			ctx.status(400);
			ctx.json(new ExceptionMessageDTO(e));
		});
		
		app.exception(ReimbursementNotFoundExcpetion.class, (e, ctx) -> {
			ctx.status(404);
			ctx.json(new ExceptionMessageDTO(e));
		});
	}
}
