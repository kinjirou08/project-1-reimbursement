package com.revature.controller;

import java.util.List;

import com.revature.dto.AddReimbursementDTO;
import com.revature.models.Reimbursement;
import com.revature.services.UsersService;

import io.javalin.Javalin;
import io.javalin.http.Handler;

public class UsersController implements MapEndpoints {
	
	UsersService userService;
	
	public UsersController () {
		this.userService = new UsersService();
	}
	
	public Handler addReimbursement = (ctx) -> {
		
		String id = ctx.pathParam("user_id");
		
		AddReimbursementDTO addDto = ctx.bodyAsClass(AddReimbursementDTO.class);
		
		Reimbursement newReimbursement = this.userService.newReimbursement(id, addDto);
		
		ctx.json(newReimbursement);
		
	};

	public Handler getAllReimbursement = (ctx) -> {
		
		List<Reimbursement> listOfReimbursements = this.userService.getAllReimbursement();
		
		ctx.json(listOfReimbursements);
	};
	
	@Override
	public void mapEndpoints(Javalin app) {
		app.post("/newReimbursement/{user_id}", addReimbursement);
		app.get("/reimbursements", getAllReimbursement);
	}

}
