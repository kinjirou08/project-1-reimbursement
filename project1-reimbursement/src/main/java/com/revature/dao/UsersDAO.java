package com.revature.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.revature.dto.AddReimbursementDTO;
import com.revature.dto.UpdateReimbursementDTO;
import com.revature.models.Reimbursement;
import com.revature.models.Users;
import com.revature.util.JDBCUtility;

public class UsersDAO {

	public Users selectUserByUsernameAndPassword(String ers_username, String ers_password) throws SQLException {

		try (Connection con = JDBCUtility.getConnection()) {

			String sql = "SELECT * FROM ers_users WHERE ers_username = ? AND ers_password = ?";
			PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

			ps.setString(1, ers_username);
			ps.setString(2, ers_password);

			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				return new Users(rs.getInt("user_id"), rs.getString("ers_username"), rs.getString("ers_password"),
						rs.getString("user_first_name"), rs.getString("user_last_name"), rs.getString("user_email"),
						rs.getString("user_role"));
			} else {
				return null;
			}
		}

	}

	public Reimbursement insertNewReimbursement(int usersId, AddReimbursementDTO addDto) throws SQLException {

		Reimbursement reimb = new Reimbursement();

		try (Connection con = JDBCUtility.getConnection()) {

			String sql = "INSERT INTO ers_reimbursement (reimb_amount, reimb_submitted, reimb_type, reimb_description, fk_reimb_author)\r\n"
					+ "VALUES\r\n" + "(?, now(), ?, ?, ?);";
			PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

			ps.setDouble(1, addDto.getReimbAmount());
			ps.setString(2, addDto.getReimbType());
			ps.setString(3, addDto.getReimbDescription());
			ps.setInt(4, usersId);

			ps.execute();

			ResultSet rs = ps.getGeneratedKeys();

			rs.next();
			int autoGenKeys = rs.getInt(1);

			return new Reimbursement(autoGenKeys, addDto.getReimbAmount(), rs.getString("reimb_submitted"),
					rs.getString("reimb_resolved"), rs.getString("reimb_status"), addDto.getReimbType(),
					addDto.getReimbDescription(), reimb.getReimbReceipt(), usersId, 0);
		}

	}

	public List<Reimbursement> selectAllReimbursements() throws SQLException {

		List<Reimbursement> listOfReimbursements = new ArrayList<>();

		try (Connection con = JDBCUtility.getConnection()) {

			String sql = "SELECT * FROM ers_reimbursement;";
			PreparedStatement ps = con.prepareStatement(sql);

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				listOfReimbursements.add(new Reimbursement(rs.getInt("reimb_id"), rs.getDouble("reimb_amount"),
						rs.getString("reimb_submitted"), rs.getString("reimb_resolved"), rs.getString("reimb_status"),
						rs.getString("reimb_type"), rs.getString("reimb_description"), rs.getBytes("reimb_receipt"),
						rs.getInt("fk_reimb_author"), rs.getInt("fk_reimb_resolver")));
			}
		}
		return listOfReimbursements;
	}

	public Reimbursement selectReimbursementById(int rId) throws SQLException {

		try (Connection con = JDBCUtility.getConnection()) {
			String sql = "SELECT *\r\n" + "FROM ers_reimbursement\r\n" + "WHERE reimb_id = ?;";
			PreparedStatement ps = con.prepareStatement(sql);

			ps.setInt(1, rId);

			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				return new Reimbursement(rId, rs.getDouble("reimb_amount"), rs.getString("reimb_submitted"),
						rs.getString("reimb_resolved"), rs.getString("reimb_status"), rs.getString("reimb_type"),
						rs.getString("reimb_description"), rs.getBytes("reimb_receipt"), rs.getInt("fk_reimb_author"),
						rs.getInt("fk_reimb_resolver"));
			} else {
				return null;
			}
		}
	}

	public Reimbursement updateReimbursement(int uId, int rId, Reimbursement getReimbursementById,
			UpdateReimbursementDTO editDto) throws SQLException {

		try (Connection con = JDBCUtility.getConnection()) {

			String sql = "UPDATE ers_reimbursement\r\n"
					+ "SET reimb_resolved = now(), fk_reimb_resolver = ?, reimb_status = ?\r\n" + "WHERE reimb_id = ?;";
			PreparedStatement ps = con.prepareStatement(sql);

			ps.setInt(1, uId);
			ps.setString(2, editDto.getReimbStatus());
			ps.setInt(3, rId);

			ps.executeUpdate();

			getReimbursementById = selectReimbursementById(rId);
			
			return new Reimbursement(rId, getReimbursementById.getReimbAmount(),
					getReimbursementById.getReimbSubmitted(), getReimbursementById.getReimbResolved(),
					editDto.getReimbStatus(), getReimbursementById.getReimbType(),
					getReimbursementById.getReimbDescription(), getReimbursementById.getReimbReceipt(),
					getReimbursementById.getReimbAuthor(), uId);

		}

	}

}
