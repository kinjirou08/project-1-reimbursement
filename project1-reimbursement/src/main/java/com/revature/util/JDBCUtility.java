package com.revature.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.postgresql.Driver;

public class JDBCUtility {

	private static final String server = "localhost";
	private static final String url = "jdbc:postgresql://" + server + "/Reimbursement";
	private static final String username = "postgres";
	private static final String password = "p4ssw0rd";

	public static Connection getConnection() throws SQLException {

//		String url = System.getenv("db_url");
//		String username = System.getenv("db_username");
//		String password = System.getenv("db_password");

		Driver postgresDriver = new Driver();
		DriverManager.registerDriver(postgresDriver);

		Connection con = DriverManager.getConnection(url, username, password);

		return con;
	}
	
}
