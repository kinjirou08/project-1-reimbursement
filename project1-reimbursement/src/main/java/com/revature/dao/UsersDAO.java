package com.revature.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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

}
