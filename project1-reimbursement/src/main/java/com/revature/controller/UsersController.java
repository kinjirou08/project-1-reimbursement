package com.revature.controller;

import java.util.List;

import com.revature.dto.AddReimbursementDTO;
import com.revature.dto.UpdateReimbursementDTO;
import com.revature.models.Reimbursement;
import com.revature.services.UsersService;

import io.javalin.Javalin;
import io.javalin.http.Handler;

public class UsersController implements MapEndpoints {

	UsersService userService;

	public UsersController() {
		this.userService = new UsersService();
	}

	public Handler addReimbursement = (ctx) ->  {

		String id = ctx.pathParam("user_id");

		AddReimbursementDTO addDto = ctx.bodyAsClass(AddReimbursementDTO.class);
		
		Double classDouble = addDto.getReimbAmount();
		
		if (classDouble.toString().matches("^[a-zA-Z0-9]*$")) {
			ctx.result("Boo boo");
		}

		System.out.println(addDto.getReimbAmount());

		Reimbursement newReimbursement = this.userService.newReimbursement(id, addDto);

		ctx.json(newReimbursement);

	};

	public Handler getAllReimbursement = (ctx) -> {
		
		List<Reimbursement> listOfReimbursements = this.userService.getAllReimbursement();

		ctx.json(listOfReimbursements);
	};
	
	public Handler getAllReimbursementById = (ctx) -> {
		
		String userId = ctx.pathParam("user_id");

		List<Reimbursement> listOfReimbursements = this.userService.getAllReimbursementById(userId);

		ctx.json(listOfReimbursements);
	};

	public Handler editReimbursement = (ctx) -> {

		String userId = ctx.pathParam("user_id");
		String reimbId = ctx.pathParam("reimb_id");

		UpdateReimbursementDTO editDto = ctx.bodyAsClass(UpdateReimbursementDTO.class);

		Reimbursement reimbToBeUpdated = this.userService.editReimbursement(userId, reimbId, editDto);

		ctx.json(reimbToBeUpdated);

	};
	
	@Override
	public void mapEndpoints(Javalin app) {
		app.post("/newReimbursement/{user_id}", addReimbursement);
		app.get("/reimbursements", getAllReimbursement);
		app.get("/reimbursements/{user_id}", getAllReimbursementById);
		app.put("/reimbursements/{user_id}/update/{reimb_id}", editReimbursement);
	}

}
