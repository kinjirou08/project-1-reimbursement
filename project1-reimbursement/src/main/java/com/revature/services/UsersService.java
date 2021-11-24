package com.revature.services;

import java.io.InputStream;
import java.security.InvalidParameterException;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.security.auth.login.FailedLoginException;

import com.revature.dao.UsersDAO;
import com.revature.dto.AddReimbursementDTO;
import com.revature.dto.UpdateReimbursementDTO;
import com.revature.exceptions.ReimbursementNotFoundExcpetion;
import com.revature.models.Reimbursement;
import com.revature.models.Users;

public class UsersService {
	
	UsersDAO userDao;
	
	public UsersService () {
		this.userDao = new UsersDAO();
	}

	public Users getUserByUsernameAndPassword(String ersUsername, String ersPassword) throws FailedLoginException, SQLException {
		
		Users user = this.userDao.selectUserByUsernameAndPassword(ersUsername, ersPassword);
		
		if (user == null) {
			throw new FailedLoginException("Incorrect username and/or password!");
		}
		
		return user;
	}

	public Reimbursement newReimbursement(Users user, AddReimbursementDTO addDto) throws SQLException {
						
		if (addDto.getReimbAmount() == 0) {
			throw new InvalidParameterException("Reimbursement Amount cannot be empty and/or Zero!");
		}	
		Set<String> validReimbType = new HashSet<>();
		validReimbType.add("Food");
		validReimbType.add("Lodging");
		validReimbType.add("Travel");
		validReimbType.add("Other");
		
		if (!validReimbType.contains(addDto.getReimbType())) {
			throw new InvalidParameterException("Reimbursement Type can only be: Food, Lodging, Travel, or Other");
		}			
		if (addDto.getReimbDescription().trim().equals("")) {
			throw new InvalidParameterException("Please put some description on why you want a Reimbursement...");
		}
		int reimbAuthor = user.getErsUserId();
		Reimbursement insertNewReimbursement = this.userDao.insertNewReimbursement(reimbAuthor, addDto);
		
		return insertNewReimbursement;
	}

	public List<Reimbursement> getAllReimbursement() throws SQLException {
		
		return this.userDao.selectAllReimbursements();
	}
	
	public List<Reimbursement> getAllReimbursementById(String userId) throws SQLException {
		
		int id = Integer.parseInt(userId);
		
		List<Reimbursement> listOfReimbursementById = this.userDao.selectAllReimbursementsById(id);
		
		return listOfReimbursementById;
	}

	public Reimbursement editReimbursement(Users user, String reimbId, String reimbStatus, InputStream receipt) throws SQLException, ReimbursementNotFoundExcpetion, UnauthorizedException {
		
		int rId = Integer.parseInt(reimbId);
		int reimbAuthor = user.getErsUserId();
				
		Reimbursement getReimbursementById = this.userDao.selectReimbursementById(rId);
		
		if (getReimbursementById == null) {
			throw new ReimbursementNotFoundExcpetion("There is no exisiting Reimbursement with that id of " +rId);
		}
		
		if (getReimbursementById.getReimbAuthor() == reimbAuthor) {
			throw new UnauthorizedException("Cannot approve/reject your own Reimbursement request!");
		}
		//Reimbursement reimbToBeUpdated = this.userDao.updateReimbursement(reimbAuthor, rId, getReimbursementById, editDto);
		Reimbursement reimbToBeUpdated = this.userDao.updateReimbursement(reimbAuthor, rId, getReimbursementById, reimbStatus, receipt);
		return reimbToBeUpdated;
		
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
