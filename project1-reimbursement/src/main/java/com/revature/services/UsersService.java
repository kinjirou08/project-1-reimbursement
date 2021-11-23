package com.revature.services;

import java.sql.SQLException;
import java.util.List;

import javax.security.auth.login.FailedLoginException;

import com.revature.dao.UsersDAO;
import com.revature.dto.AddReimbursementDTO;
import com.revature.dto.UpdateReimbursementDTO;
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

	public Reimbursement newReimbursement(String id, AddReimbursementDTO addDto) throws SQLException {

		int usersId = Integer.parseInt(id);
		
		Reimbursement insertNewReimbursement = this.userDao.insertNewReimbursement(usersId, addDto);
		
		return insertNewReimbursement;
	}

	public List<Reimbursement> getAllReimbursement() throws SQLException {
		
		return this.userDao.selectAllReimbursements();
	}

	public Reimbursement editReimbursement(String userId, String reimbId, UpdateReimbursementDTO editDto) throws SQLException {
		
		int uId = Integer.parseInt(userId);
		int rId = Integer.parseInt(reimbId);
		
		Reimbursement getReimbursementById = this.userDao.selectReimbursementById(rId);
		
		Reimbursement reimbToBeUpdated = this.userDao.updateReimbursement(uId, rId, getReimbursementById, editDto);
		
		return reimbToBeUpdated;
	}

	
}
