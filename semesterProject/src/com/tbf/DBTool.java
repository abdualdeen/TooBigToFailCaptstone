package com.tbf;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
/**
 * DBTool has two methods that take care of connection and disconnection from the Database.
 * This is done because the code below is is repeated many times.
 */

public class DBTool {
	/**
	 * @connectToDB
	 * @param conn
	 * When called, establishes a connection to the database and returns a Connection variable that holds the connection to the database.
	 */
	public static Connection connectToDB() {
		String url = "jdbc:mysql://cse.unl.edu/ahamad?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
		String username = "ahamad";
		String password = "83J:Pg";
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(url, username, password);
		} catch (SQLException sqle) {
			throw new RuntimeException(sqle);
		}
		return conn;
	}
	
	/**
	 * @disconnectFromDB
	 * @param conn
	 * @param ps
	 * @param rs
	 * A method that is fed a Connection, PreparedStatement, and ResultSet variables that are closed. 
	 * Before closing any, We check the variables for if they are closed or null to avoid errors.
	 */
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
