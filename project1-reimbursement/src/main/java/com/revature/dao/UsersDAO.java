package com.revature.dao;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.revature.models.Reimbursement;
import com.revature.models.Users;
import com.revature.util.JDBCUtility;

public class UsersDAO {
	
	PreparedStatement ps = null;

	public Users selectUserByUsernameAndPassword(String erUsername, String ersPassword) throws SQLException {

		try (Connection con = JDBCUtility.getConnection()) {

			String sql = "SELECT * FROM ers_users WHERE ers_username = ? AND ers_password = crypt(?,ers_password)";
			ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

			ps.setString(1, erUsername);
			ps.setString(2, ersPassword);

			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				return new Users(rs.getInt("user_id"), rs.getString("ers_username"), "----------------",
						rs.getString("user_first_name"), rs.getString("user_last_name"), rs.getString("user_email"),
						rs.getString("user_role"));
			} else {
				return null;
			}
			
		} finally {
			ps.close();
		}

	}

	public Reimbursement insertNewReimbursement(int usersId, double reimbAmount, String reimbType, String reimbDescription, InputStream content) throws SQLException {

		try (Connection con = JDBCUtility.getConnection()) {

			String sql = "INSERT INTO ers_reimbursement (reimb_amount, reimb_submitted, reimb_type, "
					+ "reimb_description, reimb_customer_receipt, fk_reimb_author)\r\n"
					+ "VALUES\r\n"
					+ "(?, now(), ?, ?, ?, ?);";
			ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

			ps.setDouble(1, reimbAmount);
			ps.setString(2, reimbType);
			ps.setString(3, reimbDescription);
			ps.setBinaryStream(4, content);
			ps.setInt(5, usersId);

			ps.execute();

			ResultSet rs = ps.getGeneratedKeys();

			rs.next();
			int autoGenKeys = rs.getInt(1);

			return new Reimbursement(autoGenKeys, rs.getDouble("reimb_amount"), rs.getString("reimb_submitted"),
					rs.getString("reimb_resolved"), rs.getString("reimb_status"), rs.getString("reimb_type"),
					rs.getString("reimb_description"), usersId, 0);
		} finally {
			ps.close();
		}

	}

	public List<Reimbursement> selectAllReimbursements() throws SQLException {

		List<Reimbursement> listOfReimbursements = new ArrayList<>();

		try (Connection con = JDBCUtility.getConnection()) {

			String sql = "SELECT * FROM ers_reimbursement ORDER BY reimb_id;";
			ps = con.prepareStatement(sql);

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				listOfReimbursements.add(new Reimbursement(rs.getInt("reimb_id"), rs.getDouble("reimb_amount"),
						rs.getString("reimb_submitted"), rs.getString("reimb_resolved"), rs.getString("reimb_status"),
						rs.getString("reimb_type"), rs.getString("reimb_description"), rs.getInt("fk_reimb_author"),
						rs.getInt("fk_reimb_resolver")));
			}
		} finally {
			ps.close();
		}
		return listOfReimbursements;
	}
	
	public List<Reimbursement> selectAllReimbursementsByStatus(String reimbStatus) throws SQLException {
		
		List<Reimbursement> listOfReimbursements = new ArrayList<>();
	
		try (Connection con = JDBCUtility.getConnection()) {

			String sql = "SELECT * FROM ers_reimbursement WHERE reimb_status = ? ORDER BY reimb_id;";
			ps = con.prepareStatement(sql);
			
			ps.setString(1, reimbStatus);

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				listOfReimbursements.add(new Reimbursement(rs.getInt("reimb_id"), rs.getDouble("reimb_amount"),
						rs.getString("reimb_submitted"), rs.getString("reimb_resolved"), rs.getString("reimb_status"),
						rs.getString("reimb_type"), rs.getString("reimb_description"), rs.getInt("fk_reimb_author"),
						rs.getInt("fk_reimb_resolver")));
			}
		} finally {
			ps.close();
		}
		return listOfReimbursements;
	}

	public List<Reimbursement> selectAllReimbursementsById(int id) throws SQLException {

		List<Reimbursement> listOfReimbursements = new ArrayList<>();

		try (Connection con = JDBCUtility.getConnection()) {

			String sql = "SELECT * FROM ers_reimbursement WHERE fk_reimb_author = ? ORDER BY reimb_id;";
			ps = con.prepareStatement(sql);

			ps.setInt(1, id);

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				listOfReimbursements.add(new Reimbursement(rs.getInt("reimb_id"), rs.getDouble("reimb_amount"),
						rs.getString("reimb_submitted"), rs.getString("reimb_resolved"), rs.getString("reimb_status"),
						rs.getString("reimb_type"), rs.getString("reimb_description"), rs.getInt("fk_reimb_author"),
						rs.getInt("fk_reimb_resolver")));
			}
		} finally {
			ps.close();
		}
		return listOfReimbursements;
	}
	
	public Reimbursement selectReimbursementById(int rId) throws SQLException {

		try (Connection con = JDBCUtility.getConnection()) {
			String sql = "SELECT * FROM ers_reimbursement WHERE reimb_id = ? ORDER BY reimb_id;";
			ps = con.prepareStatement(sql);

			ps.setInt(1, rId);

			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				return new Reimbursement(rId, rs.getDouble("reimb_amount"), rs.getString("reimb_submitted"),
						rs.getString("reimb_resolved"), rs.getString("reimb_status"), rs.getString("reimb_type"),
						rs.getString("reimb_description"), rs.getInt("fk_reimb_author"),
						rs.getInt("fk_reimb_resolver"));
			} else {
				return null;
			}
		} finally {
			ps.close();
		}
	}

	public Reimbursement updateReimbursement(int reimbAuthor, int reimbId, Reimbursement getReimbursementById,
			String reimbStatus) throws SQLException, IOException {

		try (Connection con = JDBCUtility.getConnection()) {

			String sql = "UPDATE ers_reimbursement\r\n"
					+ "SET reimb_resolved = now(), fk_reimb_resolver = ?, reimb_status = ?\r\n" + "WHERE reimb_id = ?;";
			ps = con.prepareStatement(sql);

			ps.setInt(1, reimbAuthor);
			ps.setString(2, reimbStatus);
			ps.setInt(3, reimbId);

			ps.executeUpdate();

			getReimbursementById = selectReimbursementById(reimbId);

			InputStream receipt = ReceiptMaker.makeReceipt(getReimbursementById);

			updateReceipt(reimbId, receipt);

			return new Reimbursement(reimbId, getReimbursementById.getReimbAmount(),
					getReimbursementById.getReimbSubmitted(), getReimbursementById.getReimbResolved(), reimbStatus,
					getReimbursementById.getReimbType(), getReimbursementById.getReimbDescription(),
					getReimbursementById.getReimbAuthor(), reimbAuthor);
			
		} finally {
			ps.close();
		}
	}

	private void updateReceipt(int rId, InputStream receipt)
			throws SQLException {
		try (Connection con = JDBCUtility.getConnection()) {
			String sql = "UPDATE ers_reimbursement\r\n" + "SET reimb_receipt = ?\r\n" + "WHERE reimb_id = ?;";
			ps = con.prepareStatement(sql);

			ps.setBinaryStream(1, receipt);
			ps.setInt(2, rId);

			ps.executeUpdate();
		} finally {
			ps.close();
		}

	}

	public InputStream selectReceiptFromReimbursementById(int reimbId, int resolverId) throws SQLException {
		try (Connection con = JDBCUtility.getConnection()) {
			String sql = "SELECT reimb_receipt FROM ers_reimbursement WHERE reimb_id = ? AND fk_reimb_resolver = ?";

			ps = con.prepareStatement(sql);

			ps.setInt(1, reimbId);
			ps.setInt(2, resolverId);

			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				return rs.getBinaryStream("reimb_receipt");
			}
			return null;
			
		} finally {
			ps.close();
		}
	}
	
	public InputStream selectCustomerReceiptFromReimbursementById(int id) throws SQLException {
		try (Connection con = JDBCUtility.getConnection()) {
			String sql = "SELECT reimb_customer_receipt FROM ers_reimbursement WHERE reimb_id = ?";

			ps = con.prepareStatement(sql);

			ps.setInt(1, id);

			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				return rs.getBinaryStream("reimb_customer_receipt");

			}
			return null;
			
		} finally {
			ps.close();
		}
	}

	public Users insertNewUser(Users newUser) throws SQLException {

		try (Connection con = JDBCUtility.getConnection()) {
			String sql = "INSERT INTO ers_users (ers_username, ers_password, user_first_name, user_last_name, user_email, user_role) "
					+ "VALUES (?,crypt(?, gen_salt('bf')),?,?,?,?);";
			ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

			ps.setString(1, newUser.getErsUsername());
			ps.setString(2, newUser.getErsPassword());
			ps.setString(3, newUser.getErsFirstName());
			ps.setString(4, newUser.getErsLastName());
			ps.setString(5, newUser.getErsEmail());
			ps.setString(6, newUser.getErsRole());

			ps.execute();

			ResultSet rs = ps.getGeneratedKeys();

			rs.next();
			int generatedId = rs.getInt(1);

			int passwordLength = newUser.getErsPassword().length();
			StringBuilder convertToAsterisk = new StringBuilder();

			for (int i = 0; i < passwordLength; i++) {
				convertToAsterisk.append("*");
			}

			return new Users(generatedId, rs.getString("ers_username"), convertToAsterisk.toString(),
					rs.getString("user_first_name"), rs.getString("user_last_name"), rs.getString("user_email"),
					rs.getString("user_role"));
		} catch (SQLException e) {
			throw new SQLException("Username already exists!");
		} finally {
			ps.close();
		}

	}

}
