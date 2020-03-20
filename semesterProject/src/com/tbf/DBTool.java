package com.tbf;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class DBTool {

	public static Connection connectToDB() {
		String DRIVER = "com.mysql.jdbc.Driver";
		String url = "jdbc:mysql://cse.unl.edu/ahamad";
		String username = "ahamad";
		String password = "83J:Pg";
		try {
			Class.forName(DRIVER).getDeclaredConstructor().newInstance();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		Connection conn = null;
		try {
			conn = DriverManager.getConnection(url, username, password);
		} catch (SQLException sqle) {
			throw new RuntimeException(sqle);
		}
		return conn;
	}


	public static void disconnectFromDB(Connection conn, PreparedStatement ps, ResultSet rs) {
		try {
			if (rs != null && !rs.isClosed()) {
				rs.close();
			}
			if (ps != null && !ps.isClosed()) {
				ps.close();
			}
			if (conn != null && !conn.isClosed()) {
				conn.close();
			}
		} catch (SQLException sqle) {
			throw new RuntimeException(sqle);
		}
	}
}
