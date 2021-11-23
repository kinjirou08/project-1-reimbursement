package com.revature.controller;

import java.util.List;

import com.revature.dto.AddReimbursementDTO;
import com.revature.dto.UpdateReimbursementDTO;
import com.revature.models.Reimbursement;
import com.revature.models.Users;
import com.revature.services.AuthorizationService;
import com.revature.services.UsersService;

import io.javalin.Javalin;
import io.javalin.http.Handler;

public class UsersController implements MapEndpoints {

	UsersService userService;
	AuthorizationService authService;

	public UsersController() {
		this.userService = new UsersService();
		this.authService = new AuthorizationService();
	}

	public Handler addReimbursement = (ctx) ->  {
		
		Users user = (Users) ctx.req.getSession().getAttribute("validateduser");
		this.authService.authorizeEmployee(user);

		//String id = ctx.pathParam("user_id");
		AddReimbursementDTO addDto = ctx.bodyAsClass(AddReimbursementDTO.class);

		Reimbursement newReimbursement = this.userService.newReimbursement(user.getErsUserId(), addDto);

		ctx.json(newReimbursement);

	};

	public Handler getAllReimbursement = (ctx) -> {
		
		Users user = (Users) ctx.req.getSession().getAttribute("validateduser");
		this.authService.authorizeFinanceManager(user);
		
		List<Reimbursement> listOfReimbursements = this.userService.getAllReimbursement();

		ctx.json(listOfReimbursements);
	};
	
	public Handler getAllReimbursementById = (ctx) -> {
		
		Users user = (Users) ctx.req.getSession().getAttribute("validateduser");
		this.authService.authorizeEmployee(user);
		
		String userId = ctx.pathParam("user_id");

		List<Reimbursement> listOfReimbursements = this.userService.getAllReimbursementById(userId);

		ctx.json(listOfReimbursements);
	};

	public Handler editReimbursement = (ctx) -> {
		
		Users user = (Users) ctx.req.getSession().getAttribute("validateduser");
		this.authService.authorizeFinanceManager(user);

		String userId = ctx.pathParam("user_id");
		String reimbId = ctx.pathParam("reimb_id");

		UpdateReimbursementDTO editDto = ctx.bodyAsClass(UpdateReimbursementDTO.class);

		Reimbursement reimbToBeUpdated = this.userService.editReimbursement(userId, reimbId, editDto);

		ctx.json(reimbToBeUpdated);

	};
	
	@Override
	public void mapEndpoints(Javalin app) {
		app.post("/newReimbursement", addReimbursement);
		app.get("/reimbursements", getAllReimbursement);
		app.get("/reimbursements/{user_id}", getAllReimbursementById);
		app.put("/reimbursements/{user_id}/update/{reimb_id}", editReimbursement);
	}

}
