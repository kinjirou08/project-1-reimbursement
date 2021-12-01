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

	private static UsersService userService;
	private static AuthorizationService authService;
	private final static String validatedUser = "validateduser";

	public UsersController() {
		UsersController.userService = new UsersService();
		UsersController.authService = new AuthorizationService();
	}

	private Handler addReimbursement = ctx -> {

		Users user = (Users) ctx.req.getSession().getAttribute(validatedUser);
		authService.authorizeEmployeeandFinanceManager(user);
		
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

		Reimbursement newReimbursement = userService.newReimbursement(user, reimbAmount, reimbType, reimbDescription, mimeType, content);

		ctx.json(newReimbursement);
		ctx.status(201);

	};

	private Handler getAllReimbursement = ctx -> {

		Users user = (Users) ctx.req.getSession().getAttribute(validatedUser);
		authService.authorizeFinanceManager(user);

		String reimbStatus = ctx.queryParam("reimbStatus");

		if (reimbStatus == null) {
			List<Reimbursement> listOfReimbursements = userService.getAllReimbursement();
			if(listOfReimbursements.isEmpty()) {
				ctx.result("There is nothing to show here!");
			} else {
				ctx.json(listOfReimbursements);
			}
		} else {
			List<Reimbursement> listOfReimbursements = userService.getAllReimbursementByStatus(reimbStatus);
			ctx.json(listOfReimbursements);
		}

	};

	private Handler getAllReimbursementById = ctx -> {

		Users user = (Users) ctx.req.getSession().getAttribute(validatedUser);
		authService.authorizeEmployee(user);
		

		List<Reimbursement> listOfReimbursements = userService.getAllReimbursementById(user);

		ctx.json(listOfReimbursements);
	};

	private Handler editReimbursement = ctx -> {

		Users user = (Users) ctx.req.getSession().getAttribute(validatedUser);
		authService.authorizeFinanceManager(user);

		String reimbId = ctx.pathParam("reimb_id");
		String reimbStatus = ctx.formParam("reimbStatus");

		Reimbursement reimbToBeUpdated = userService.editReimbursement(user, reimbId, reimbStatus);

		ctx.json(reimbToBeUpdated);

	};
	private Handler getReceiptFromReimbursementById = ctx -> {
		// protect endpoint
		Users user = (Users) ctx.req.getSession().getAttribute(validatedUser);
		authService.authorizeFinanceManager(user);

		String reimbId = ctx.pathParam("id");

		InputStream receipt = userService.getReceiptFromReimbursementById(user, reimbId);

		Tika tika = new Tika();
		String mimeType = tika.detect(receipt);

		ctx.contentType(mimeType);
		ctx.result(receipt);
	};
	
	private Handler geCustomerReceiptFromReimbursementById = ctx -> {
		// protect endpoint
		Users user = (Users) ctx.req.getSession().getAttribute(validatedUser);
		authService.authorizeEmployeeandFinanceManager(user);

		String reimbId = ctx.pathParam("id");

		InputStream receipt = userService.getCustomerReceiptFromReimbursementById(reimbId);

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
