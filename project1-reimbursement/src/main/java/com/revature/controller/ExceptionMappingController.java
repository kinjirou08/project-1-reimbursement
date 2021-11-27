package com.revature.controller;

import java.security.InvalidParameterException;

import com.revature.dto.ExceptionMessageDTO;
import com.revature.exceptions.ReimbursementNotFoundExcpetion;
import com.revature.exceptions.UnauthorizedException;

import io.javalin.Javalin;

public class ExceptionMappingController {
	
	public void mapExceptions(Javalin app) {
		app.exception(InvalidParameterException.class, (e, ctx) -> {
			ctx.status(400);
			ctx.json(new ExceptionMessageDTO(e.getMessage()));
		});
		
		app.exception(ReimbursementNotFoundExcpetion.class, (e, ctx) -> {
			ctx.status(404);
			ctx.json(new ExceptionMessageDTO(e.getMessage()));
		});
		
		app.exception(UnauthorizedException.class, (e, ctx) -> {
			ctx.status(401);
			ctx.json(new ExceptionMessageDTO(e.getMessage()));
		});
	}
}
