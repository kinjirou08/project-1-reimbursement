package com.revature.services;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidParameterException;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.security.auth.login.FailedLoginException;

import com.revature.dao.UsersDAO;
import com.revature.exceptions.ReimbursementNotFoundExcpetion;
import com.revature.exceptions.UnauthorizedException;
import com.revature.models.Reimbursement;
import com.revature.models.Users;

public class UsersService {

	UsersDAO userDao;

	public UsersService() {
		this.userDao = new UsersDAO();
	}

	public Users getUserByUsernameAndPassword(String ersUsername, String ersPassword)
			throws FailedLoginException, SQLException {

		Users user = this.userDao.selectUserByUsernameAndPassword(ersUsername, ersPassword);

		if (user == null) {
			throw new FailedLoginException("Incorrect username and/or password!");
		}
		
		return user;
	}

	public List<Reimbursement> getAllReimbursement() throws SQLException {

		return this.userDao.selectAllReimbursements();
	}

	public List<Reimbursement> getAllReimbursementById(Users user) throws SQLException {

		return this.userDao.selectAllReimbursementsById(user.getErsUserId());

	}

	public Reimbursement editReimbursement(Users user, String reimbId, String reimbStatus)
			throws SQLException, ReimbursementNotFoundExcpetion, UnauthorizedException, IOException {

		int rId = Integer.parseInt(reimbId);
		int reimbAuthor = user.getErsUserId();

		Reimbursement getReimbursementById = this.userDao.selectReimbursementById(rId);

		if (getReimbursementById == null) {
			throw new ReimbursementNotFoundExcpetion("There is no exisiting Reimbursement with that id of " + rId);
		}

		if (getReimbursementById.getReimbAuthor() == reimbAuthor) {
			throw new UnauthorizedException("Cannot approve/reject your own Reimbursement request!");
		}

		if (getReimbursementById.getReimbResolver() != 0) {
			throw new UnauthorizedException("This request has already been Approved/Rejected!");
		}

		return this.userDao.updateReimbursement(reimbAuthor, rId, getReimbursementById, reimbStatus);

	}

	public InputStream getReceiptFromReimbursementById(Users currentlyLoggedInUser, String reimbId)
			throws SQLException, UnauthorizedException, ReimbursementNotFoundExcpetion {

		int id = Integer.parseInt(reimbId);

		Reimbursement checkResolverId = this.userDao.selectReimbursementById(id);

		InputStream receipt = this.userDao.selectReceiptFromReimbursementById(id, currentlyLoggedInUser.getErsUserId());
		
		if(checkResolverId == null) {
			throw new ReimbursementNotFoundExcpetion("There is no exisiting Reimbursement with the id of " +id);
		}

		if (currentlyLoggedInUser.getErsUserId() != checkResolverId.getReimbResolver()) {
			throw new UnauthorizedException("This Receipt belongs to another Finance Manager!");
		}

		if (receipt == null) {
			throw new InvalidParameterException("Image was not found for reimbursement id " + id);
		}

		return receipt;
	}

	public InputStream getCustomerReceiptFromReimbursementById(Users user, String reimbId) throws SQLException, UnauthorizedException {

		try {
			
			int id = Integer.parseInt(reimbId);
			
			if (user.getErsRole().equals("Employee") || user.getErsRole().equals("Finance Manager")) {
				
				int userId = user.getErsUserId();
				
				List<Reimbursement> reimbursementsThatBelongsToEmployee = this.userDao.selectAllReimbursementsById(userId);
				Set<Integer> reimbursementIdsEncountered = new HashSet<>();
				for (Reimbursement r : reimbursementsThatBelongsToEmployee) {
					reimbursementIdsEncountered.add(r.getReimbId());
				}
				
				if (!reimbursementIdsEncountered.contains(id)) {
					throw new UnauthorizedException("You cannot access the images of reimbursements that do not belong to yourself");
				}
			}
		

		InputStream receipt = this.userDao.selectCustomerReceiptFromReimbursementById(id);

		if (receipt == null) {
			throw new InvalidParameterException("Image was not found for reimbursement id " + id);
		}

		return receipt;
		
		} catch (NumberFormatException e) {
			throw new InvalidParameterException("Reimbursement id supplied must be an int");
		}
	}

	public Users newUser(Users newUser) throws SQLException {

		if (newUser.getErsFirstName().trim().equals("")) {
			throw new InvalidParameterException("First Name field must not be empty!");
		}

		if (newUser.getErsLastName().trim().equals("")) {
			throw new InvalidParameterException("Last Name field must not be empty!");
		}

		if (newUser.getErsUsername().trim().equals("")) {
			throw new InvalidParameterException("Username field must not be empty!");
		}

		if (newUser.getErsPassword().trim().equals("")) {
			throw new InvalidParameterException("Password field must not be empty!");
		}

		if (newUser.getErsEmail().trim().equals("")) {
			throw new InvalidParameterException("Email field must not be empty!");
		}

		Set<String> validErsRole = new HashSet<>();
		validErsRole.add("Finance Manager");
		validErsRole.add("Employee");

		if (!validErsRole.contains(newUser.getErsRole())) {
			throw new InvalidParameterException("Role must be of Finance Manager or Employee!");
		}

		return this.userDao.insertNewUser(newUser);

	}

	public List<Reimbursement> getAllReimbursementByStatus(String reimbStatus) throws SQLException {

		Set<String> validStatus = new HashSet<>();
		validStatus.add("Pending");
		validStatus.add("Approved");
		validStatus.add("Rejected");

		if (!validStatus.contains(reimbStatus)) {
			throw new InvalidParameterException("Reimbursement Status can only be: Pending, Approved, or Rejected!");
		}

		return this.userDao.selectAllReimbursementsByStatus(reimbStatus);
	}

	public Reimbursement newReimbursement(Users user, String reimbAmount, String reimbType, String reimbDescription,
			String mimeType, InputStream content) throws SQLException {

		if (Double.parseDouble(reimbAmount) == 0) {
			throw new InvalidParameterException("Reimbursement Amount cannot be empty and/or Zero!");
		}

		Set<String> validReimbType = new HashSet<>();
		validReimbType.add("Food");
		validReimbType.add("Lodging");
		validReimbType.add("Travel");
		validReimbType.add("Other");

		Set<String> allowedFileTypes = new HashSet<>();
		allowedFileTypes.add("image/jpeg");
		allowedFileTypes.add("image/png");
		allowedFileTypes.add("image/gif");

		if (!allowedFileTypes.contains(mimeType)) {
			throw new InvalidParameterException("When adding an assignment image, only PNG, JPEG, or GIF are allowed");
		}

		if (!validReimbType.contains(reimbType)) {
			throw new InvalidParameterException("Reimbursement Type can only be: Food, Lodging, Travel, or Other");
		}
		if (reimbDescription.trim().equals("")) {
			throw new InvalidParameterException("Please put some description on why you want a Reimbursement...");
		}

		int reimbAuthor = user.getErsUserId();
		double amount = Double.parseDouble(reimbAmount);

		return this.userDao.insertNewReimbursement(reimbAuthor, amount, reimbType, reimbDescription, content);

	}

}
