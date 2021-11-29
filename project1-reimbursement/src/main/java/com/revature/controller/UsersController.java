package com.revature.controller;

import java.io.InputStream;
import java.util.List;

import org.apache.tika.Tika;

import com.revature.dto.ExceptionMessageDTO;
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

	public Handler addReimbursement = (ctx) -> {

		Users user = (Users) ctx.req.getSession().getAttribute("validateduser");
		this.authService.authorizeEmployeeandFinanceManager(user);

		//AddReimbursementDTO addDto = ctx.bodyAsClass(AddReimbursementDTO.class);
		
		String reimbAmount = ctx.formParam("reimbAmount");
		String reimbType = ctx.formParam("reimbType");
		String reimbDescription = ctx.formParam("reimbDescription");
		
		UploadedFile file = ctx.uploadedFile("reimbCustomerReceipt");
		
		if (file == null) {
			ctx.status(400);
			ctx.json(new ExceptionMessageDTO("Must have an image to upload"));
			return;
		}
		
		InputStream content = file.getContent(); // This is the most important. It is the actual content of the file

		Tika tika = new Tika();
		
		String mimeType = tika.detect(content);

		Reimbursement newReimbursement = this.userService.newReimbursement(user, reimbAmount, reimbType, reimbDescription, mimeType, content);

		ctx.json(newReimbursement);

	};

	public Handler getAllReimbursement = (ctx) -> {

		Users user = (Users) ctx.req.getSession().getAttribute("validateduser");
		this.authService.authorizeFinanceManager(user);

		String reimbStatus = ctx.queryParam("reimbStatus");

		if (reimbStatus == null) {
			List<Reimbursement> listOfReimbursements = this.userService.getAllReimbursement();
			ctx.json(listOfReimbursements);
		} else {
			List<Reimbursement> listOfReimbursements = this.userService.getAllReimbursementByStatus(reimbStatus);
			ctx.json(listOfReimbursements);
		}

	};

	public Handler getAllReimbursementById = (ctx) -> {

		Users user = (Users) ctx.req.getSession().getAttribute("validateduser");
		this.authService.authorizeEmployee(user);
		

		List<Reimbursement> listOfReimbursements = this.userService.getAllReimbursementById(user);

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
	
	private Handler geCustomerReceiptFromReimbursementById = (ctx) -> {
		// protect endpoint
		Users user = (Users) ctx.req.getSession().getAttribute("validateduser");
		this.authService.authorizeEmployeeandFinanceManager(user);

		String reimbId = ctx.pathParam("id");

		InputStream receipt = this.userService.getCustomerReceiptFromReimbursementById(user, reimbId);

		Tika tika = new Tika();
		String mimeType = tika.detect(receipt);

		ctx.contentType(mimeType);
		ctx.result(receipt);
	};

	@Override
	public void mapEndpoints(Javalin app) {
		app.post("/newReimbursement", addReimbursement);
		app.get("/reimbursements", getAllReimbursement);
		app.get("/reimbursements/user", getAllReimbursementById);
		app.patch("/reimbursements/{reimb_id}/update", editReimbursement);
		app.get("/reimbursements/{id}/reimbursementReceipt", getReceiptFromReimbursementById);
		app.get("/reimbursements/{id}/customerReceipt", geCustomerReceiptFromReimbursementById);
	}

}
