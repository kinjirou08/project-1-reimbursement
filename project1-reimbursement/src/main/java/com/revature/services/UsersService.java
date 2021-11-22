package com.revature.services;

import java.sql.SQLException;
import java.util.List;

import javax.security.auth.login.FailedLoginException;

import com.revature.dao.UsersDAO;
import com.revature.dto.AddReimbursementDTO;
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

	
}
