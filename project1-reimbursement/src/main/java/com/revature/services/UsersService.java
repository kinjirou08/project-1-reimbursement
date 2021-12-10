package com.revature.services;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidParameterException;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.security.auth.login.FailedLoginException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.revature.dao.UsersDAO;
import com.revature.exceptions.ReceiptNotFoundException;
import com.revature.exceptions.ReimbursementNotFoundExcpetion;
import com.revature.exceptions.UnauthorizedException;
import com.revature.models.Reimbursement;
import com.revature.models.Users;

public class UsersService {

	Logger logger = LoggerFactory.getLogger(UsersService.class);

	UsersDAO userDao;

	public UsersService(UsersDAO userDao) {
		this.userDao = userDao;
	}

	public UsersService() {
		this.userDao = new UsersDAO();
	}

	public Users getUserByUsernameAndPassword(String ersUsername, String ersPassword)
			throws FailedLoginException, SQLException {

		logger.info("invoked getUserByUsernameAndPassword() method");

		Users user = this.userDao.selectUserByUsernameAndPassword(ersUsername, ersPassword);

		if (user == null) {
			logger.warn("FailedLoginException was thrown: " + "Incorrect username and/or password!");
			throw new FailedLoginException("Incorrect username and/or password!");
		}

		return user;
	}

	public List<Reimbursement> getAllReimbursement() throws SQLException {

		logger.info("invoked getAllReimbursement() method");
		return this.userDao.selectAllReimbursements();
	}

	public List<Reimbursement> getAllReimbursementById(Users user) throws SQLException {

		logger.info("invoked getAllReimbursementById() method");
		return this.userDao.selectAllReimbursementsById(user.getErsUserId());
	}

	public Reimbursement editReimbursement(Users user, String reimbId, String reimbStatus)
			throws SQLException, ReimbursementNotFoundExcpetion, UnauthorizedException, IOException {

		logger.info("invoked editReimbursement() method");
		int rId = Integer.parseInt(reimbId);
		int reimbAuthor = user.getErsUserId();

		Reimbursement getReimbursementById = this.userDao.selectReimbursementById(rId);

		if (getReimbursementById == null) {
			logger.warn("ReimbursementNotFoundExcpetion was thrown: "
					+ "There is no exisiting Reimbursement with that id of " + rId);
			throw new ReimbursementNotFoundExcpetion("There is no exisiting Reimbursement with that id of " + rId);
		}

		if (getReimbursementById.getReimbAuthor() == reimbAuthor) {
			logger.warn("UnauthorizedException was thrown: " + "Cannot approve/reject your own Reimbursement request!");
			throw new UnauthorizedException("Cannot approve/reject your own Reimbursement request!");
		}

		if (getReimbursementById.getReimbResolver() != 0) {
			logger.warn("UnauthorizedException was thrown: " + "This request has already been Approved/Rejected!");
			throw new UnauthorizedException("This request has already been Approved/Rejected!");
		}

		return this.userDao.updateReimbursement(reimbAuthor, rId, getReimbursementById, reimbStatus);

	}

	public InputStream getReceiptFromReimbursementById(Users currentlyLoggedInUser, String reimbId)
			throws SQLException, UnauthorizedException, ReimbursementNotFoundExcpetion, ReceiptNotFoundException {

		logger.info("invoked getReceiptFromReimbursementById() method");
		int id = Integer.parseInt(reimbId);
		Reimbursement checkResolverId = this.userDao.selectReimbursementById(id);

		if (checkResolverId == null) {
			logger.warn("ReimbursementNotFoundExcpetion was thrown: "
					+ "There is no exisiting Reimbursement with the id of " + id);
			throw new ReimbursementNotFoundExcpetion("There is no exisiting Reimbursement with the id of " + id);
		}

		if (currentlyLoggedInUser.getErsUserId() != checkResolverId.getReimbResolver()) {
			logger.warn("UnauthorizedException was thrown: " + "This Receipt belongs to another Finance Manager!");
			throw new UnauthorizedException("This Receipt belongs to another Finance Manager!");
		}

		InputStream receipt = this.userDao.selectReceiptFromReimbursementById(id, currentlyLoggedInUser.getErsUserId());

		if (receipt == null) {
			logger.warn("ReceiptNotFoundException was thrown: " + "Receipt was not found for reimbursement id " + id);
			throw new ReceiptNotFoundException("Receipt was not found for reimbursement id " + id);
		}

		return receipt;
	}

	public InputStream getCustomerReceiptFromReimbursementById(Users user, String reimbId)
			throws SQLException, UnauthorizedException, ReceiptNotFoundException {

		logger.info("invoked getCustomerReceiptFromReimbursementById() method");
		try {

			int id = Integer.parseInt(reimbId);
			int userId = user.getErsUserId();

			if (user.getErsRole().equals("Employee")) {

				List<Reimbursement> reimbursementsThatBelongsToEmployee = this.userDao
						.selectAllReimbursementsById(userId);
				Set<Integer> reimbursementIdsEncountered = new HashSet<>();
				for (Reimbursement r : reimbursementsThatBelongsToEmployee) {
					reimbursementIdsEncountered.add(r.getReimbId());
				}

				if (!reimbursementIdsEncountered.contains(id)) {
					logger.warn("UnauthorizedException was thrown: "
							+ "You cannot access the images of reimbursements that do not belong to yourself");
					throw new UnauthorizedException(
							"You cannot access the images of reimbursements that do not belong to yourself");
				}

			} else if (user.getErsRole().equals("Finance Manager")) {

				List<Reimbursement> reimbursementsThatBelongsToManager = this.userDao.selectAllReimbursements();
				Set<Integer> reimbursementIdsEncountered = new HashSet<>();
				for (Reimbursement r : reimbursementsThatBelongsToManager) {
					reimbursementIdsEncountered.add(r.getReimbId());
				}

				if (!reimbursementIdsEncountered.contains(id)) {
					logger.warn("UnauthorizedException was thrown: "
							+ "You cannot access the images of reimbursements that do not belong to yourself");
					throw new UnauthorizedException(
							"You cannot access the images of reimbursements that do not belong to yourself");
				}
			}
			InputStream receipt = this.userDao.selectCustomerReceiptFromReimbursementById(id);

			if (receipt == null) {
				logger.warn("ReceiptNotFoundException was thrown: "
						+ "Receipt was not found for reimbursement id " + id);
				throw new ReceiptNotFoundException("Receipt was not found for reimbursement id " + id);
			}

			return receipt;

		} catch (NumberFormatException e) {
			logger.warn("InvalidParameterException was thrown: "+ "Reimbursement id supplied must be an int");
			throw new InvalidParameterException("Reimbursement id supplied must be an int");
		}
	}

	public Users newUser(Users newUser) throws SQLException {
		
		logger.info("invoked newUser() method");
		if (newUser.getErsFirstName().trim().equals("")) {
			logger.warn("InvalidParameterException was thrown: "+ "First Name field must not be empty!");
			throw new InvalidParameterException("First Name field must not be empty!");
		}

		if (newUser.getErsLastName().trim().equals("")) {
			logger.warn("InvalidParameterException was thrown: "+ "Last Name field must not be empty!");
			throw new InvalidParameterException("Last Name field must not be empty!");
		}

		if (newUser.getErsUsername().trim().equals("")) {
			logger.warn("InvalidParameterException was thrown: "+ "Username field must not be empty!");
			throw new InvalidParameterException("Username field must not be empty!");
		}

		if (newUser.getErsPassword().trim().equals("")) {
			logger.warn("InvalidParameterException was thrown: "+ "Password field must not be empty!");
			throw new InvalidParameterException("Password field must not be empty!");
		}

		if (newUser.getErsEmail().trim().equals("")) {
			logger.warn("InvalidParameterException was thrown: "+ "Email field must not be empty!");
			throw new InvalidParameterException("Email field must not be empty!");
		}

		if (newUser.getErsRole().trim().equals("")) {
			logger.warn("InvalidParameterException was thrown: "+ "Role field must not be empty!");
			throw new InvalidParameterException("Role field must not be empty!");
		}

		Set<String> validErsRole = new HashSet<>();
		validErsRole.add("Finance Manager");
		validErsRole.add("Employee");

		if (!validErsRole.contains(newUser.getErsRole())) {
			logger.warn("InvalidParameterException was thrown: "+ "Role must be of Finance Manager or Employee!");
			throw new InvalidParameterException("Role must be of Finance Manager or Employee!");
		}

		return this.userDao.insertNewUser(newUser);

	}

	public List<Reimbursement> getAllReimbursementByStatus(String reimbStatus) throws SQLException {
		
		logger.info("invoked newUser() method");
		Set<String> validStatus = new HashSet<>();
		validStatus.add("Pending");
		validStatus.add("Approved");
		validStatus.add("Rejected");

		if (!validStatus.contains(reimbStatus)) {
			logger.warn("InvalidParameterException was thrown: "+ "Reimbursement Status can only be: Pending, Approved, or Rejected!");
			throw new InvalidParameterException("Reimbursement Status can only be: Pending, Approved, or Rejected!");
		}

		return this.userDao.selectAllReimbursementsByStatus(reimbStatus);
	}

	public Reimbursement newReimbursement(Users user, String reimbAmount, String reimbType, String reimbDescription,
			String mimeType, InputStream content) throws SQLException {
		
		logger.info("invoked newReimbursement() method");
		if (reimbAmount.trim().equals("")) {
			logger.warn("InvalidParameterException was thrown: "+ "Reimbursement Amount cannot be Empty!");
			throw new InvalidParameterException("Reimbursement Amount cannot be Empty!");
		}

		if (Double.parseDouble(reimbAmount) <= 0) {
			logger.warn("InvalidParameterException was thrown: "+ "Reimbursement Status can only be: Pending, Approved, or Rejected!");
			throw new InvalidParameterException("Reimbursement Amount cannot be Less than zero or Zero!");
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
			logger.warn("InvalidParameterException was thrown: "+ "When adding an assignment image, only PNG, JPEG, or GIF are allowed");
			throw new InvalidParameterException("When adding an assignment image, only PNG, JPEG, or GIF are allowed");
		}
		if (reimbType.trim().equals("")) {
			logger.warn("InvalidParameterException was thrown: "+ "Reimbursement Type cannot be empty!");
			throw new InvalidParameterException("Reimbursement Type cannot be empty!");
		}
		if (!validReimbType.contains(reimbType)) {
			logger.warn("InvalidParameterException was thrown: "+ "Reimbursement Type can only be: Food, Lodging, Travel, or Other");
			throw new InvalidParameterException("Reimbursement Type can only be: Food, Lodging, Travel, or Other");
		}
		if (reimbDescription.trim().equals("")) {
			logger.warn("InvalidParameterException was thrown: "+ "Please put some description on why you want a Reimbursement...");
			throw new InvalidParameterException("Please put some description on why you want a Reimbursement...");
		}

		int reimbAuthor = user.getErsUserId();
		double amount = Double.parseDouble(reimbAmount);

		return this.userDao.insertNewReimbursement(reimbAuthor, amount, reimbType, reimbDescription, content);

	}

	public Reimbursement getAReimbursementById(String reimbId) throws SQLException, ReimbursementNotFoundExcpetion {
		
		logger.info("invoked newReimbursement() method");
		try {
			int rId = Integer.parseInt(reimbId);

			if (userDao.selectReimbursementById(rId) == null) {
				logger.warn("ReimbursementNotFoundExcpetion was thrown: "+ "There is no exisiting Reimbursement with the id of " + rId);
				throw new ReimbursementNotFoundExcpetion("There is no exisiting Reimbursement with the id of " + rId);
			}
			return this.userDao.selectReimbursementById(rId);

		} catch (NumberFormatException e) {
			logger.warn("ReimbursementNotFoundExcpetion was thrown: "+ "Reimbursement id supplied must be an int");
			throw new InvalidParameterException("Reimbursement id supplied must be an int");
		}

	}

}
