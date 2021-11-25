package com.revature.controller;

import java.io.InputStream;
import java.util.List;

import org.apache.tika.Tika;

import com.revature.dto.AddReimbursementDTO;
import com.revature.models.Reimbursement;
import com.revature.models.Users;
import com.revature.services.AuthorizationService;
import com.revature.services.UsersService;

import io.javalin.Javalin;
import io.javalin.http.Handler;
import io.javalin.http.UploadedFile;

public class UsersController implements MapEndpoints {

	UsersService userService;
	AuthorizationService authService;

	public UsersController() {
		this.userService = new UsersService();
		this.authService = new AuthorizationService();
	}

	public Handler addReimbursement = (ctx) ->  {
		
		Users user = (Users) ctx.req.getSession().getAttribute("validateduser");
		this.authService.authorizeEmployeeandFinanceManager(user);

		AddReimbursementDTO addDto = ctx.bodyAsClass(AddReimbursementDTO.class);

		Reimbursement newReimbursement = this.userService.newReimbursement(user, addDto);

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

		String reimbId = ctx.pathParam("reimb_id");
		String reimbStatus = ctx.formParam("reimbStatus");

		Reimbursement reimbToBeUpdated = this.userService.editReimbursement(user, reimbId, reimbStatus);

		ctx.json(reimbToBeUpdated);

	};
	private Handler getReceiptFromReimbursementById = (ctx) -> {
		// protect endpoint
		Users user = (Users) ctx.req.getSession().getAttribute("validateduser");
		this.authService.authorizeFinanceManager(user);
		
		String reimbId = ctx.pathParam("id");
		
		InputStream receipt = this.userService.getReceiptFromReimbursementById(user, reimbId);
		
		Tika tika = new Tika();
		String mimeType = tika.detect(receipt);
		
		ctx.contentType(mimeType); 
		ctx.result(receipt); 
	};

	@Override
	public void mapEndpoints(Javalin app) {
		app.post("/newReimbursement", addReimbursement);
		app.get("/reimbursements", getAllReimbursement);
		app.get("/reimbursements/{user_id}", getAllReimbursementById);
		app.patch("/reimbursements/{reimb_id}/update", editReimbursement);
		app.get("/reimbursements/{id}/image", getReceiptFromReimbursementById);
	}

}
