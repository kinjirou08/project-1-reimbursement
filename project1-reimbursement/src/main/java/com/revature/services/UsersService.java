package com.revature.services;

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

	public Reimbursement newReimbursement(int ersUserId, AddReimbursementDTO addDto) throws SQLException {
						
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
		
		Reimbursement insertNewReimbursement = this.userDao.insertNewReimbursement(ersUserId, addDto);
		
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

	public Reimbursement editReimbursement(String userId, String reimbId, UpdateReimbursementDTO editDto) throws SQLException, ReimbursementNotFoundExcpetion {
		
		int uId = Integer.parseInt(userId);
		int rId = Integer.parseInt(reimbId);
				
		Reimbursement getReimbursementById = this.userDao.selectReimbursementById(rId);
		
		if (getReimbursementById == null) {
			throw new ReimbursementNotFoundExcpetion("There is no exisiting Reimbursement with that id of " +rId);
		}
		
		Reimbursement reimbToBeUpdated = this.userDao.updateReimbursement(uId, rId, getReimbursementById, editDto);
		
		return reimbToBeUpdated;
	}

	
}
