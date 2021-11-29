package com.revature.services;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.spec.InvalidKeySpecException;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.security.auth.login.FailedLoginException;

import com.revature.dao.UsersDAO;
import com.revature.dto.AddReimbursementDTO;
import com.revature.dto.NewUsersDTO;
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

//	public Reimbursement newReimbursement(Users user, AddReimbursementDTO addDto) throws SQLException {
//
//		if (addDto.getReimbAmount() == 0) {
//			throw new InvalidParameterException("Reimbursement Amount cannot be empty and/or Zero!");
//		}
//		Set<String> validReimbType = new HashSet<>();
//		validReimbType.add("Food");
//		validReimbType.add("Lodging");
//		validReimbType.add("Travel");
//		validReimbType.add("Other");
//
//		if (!validReimbType.contains(addDto.getReimbType())) {
//			throw new InvalidParameterException("Reimbursement Type can only be: Food, Lodging, Travel, or Other");
//		}
//		if (addDto.getReimbDescription().trim().equals("")) {
//			throw new InvalidParameterException("Please put some description on why you want a Reimbursement...");
//		}
//
//		int reimbAuthor = user.getErsUserId();
//		Reimbursement insertNewReimbursement = this.userDao.insertNewReimbursement(reimbAuthor, addDto);
//
//		return insertNewReimbursement;
//	}

	public List<Reimbursement> getAllReimbursement() throws SQLException {

		return this.userDao.selectAllReimbursements();
	}

	public List<Reimbursement> getAllReimbursementById(Users user) throws SQLException {

		List<Reimbursement> listOfReimbursementById = this.userDao.selectAllReimbursementsById(user.getErsUserId());

		return listOfReimbursementById;
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

		Reimbursement reimbToBeUpdated = this.userDao.updateReimbursement(reimbAuthor, rId, getReimbursementById,
				reimbStatus);
		return reimbToBeUpdated;

	}

	public InputStream getReceiptFromReimbursementById(Users currentlyLoggedInUser, String reimbId)
			throws SQLException {

		int id = Integer.parseInt(reimbId);

		InputStream receipt = this.userDao.selectReceiptFromReimbursementById(id);

		if (receipt == null) {
			throw new InvalidParameterException("Image was not found for reimbursement id " + id);
		}

		return receipt;
	}
	
	public InputStream getCustomerReceiptFromReimbursementById(Users currentlyLoggedInUser, String reimbId)
			throws SQLException {

		int id = Integer.parseInt(reimbId);

		InputStream receipt = this.userDao.selectCustomerReceiptFromReimbursementById(id);

		if (receipt == null) {
			throw new InvalidParameterException("Image was not found for reimbursement id " + id);
		}

		return receipt;
	}


	public Users newUser(NewUsersDTO newUser) throws NoSuchAlgorithmException, NoSuchProviderException, SQLException, InvalidKeySpecException {

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

		Users insertNewUser = this.userDao.insertNewUser(newUser);

		return insertNewUser;
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
		
		Reimbursement insertNewReimbursement = this.userDao.insertNewReimbursement(reimbAuthor, amount, reimbType, reimbDescription, content);

		return insertNewReimbursement;
	}

//	public Reimbursement editReimbursement(Users user, String reimbId, UpdateReimbursementDTO editDto) throws SQLException, ReimbursementNotFoundExcpetion, UnauthorizedException {
//		
//		int rId = Integer.parseInt(reimbId);
//		int reimbAuthor = user.getErsUserId();
//				
//		Reimbursement getReimbursementById = this.userDao.selectReimbursementById(rId);
//		
//		if (getReimbursementById == null) {
//			throw new ReimbursementNotFoundExcpetion("There is no exisiting Reimbursement with that id of " +rId);
//		}
//		
//		if (getReimbursementById.getReimbAuthor() == reimbAuthor) {
//			throw new UnauthorizedException("Cannot approve/reject your own Reimbursement request!");
//		}
//		Reimbursement reimbToBeUpdated = this.userDao.updateReimbursement(reimbAuthor, rId, getReimbursementById, editDto);
//		
//		return reimbToBeUpdated;
//	}

}
