package com.tbf;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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

	public static List<Person> retrieveAllPerson() {
		Connection conn = connectToDB();
		String query = "Select p.personId, p.alphaCode, p.brokerStat, p.lastName, p.firstName, a.street, a.city, a.zip, s.abbreviation, c.name from Person p"
				+ " join Address a on p.addressId = a.addressId join State s on a.stateId = s.stateId join Country c on a.countryId = c.countryId;";

		PreparedStatement ps = null;
		ResultSet rs = null;
		
		List<Person> persons = new ArrayList<>();
		
			ps = (PreparedStatement) conn.prepareStatement(query);
			rs = ps.executeQuery();
			while (rs.next()) {
				int personId = rs.getInt("personId");
				int alphaCode = rs.getInt("alphaCode");
				String brokerStat = rs.getString("brokerStat");
				String lastName = rs.getString("lastName");
				String firstName = rs.getString("firstName");
				String street = rs.getString("street");
				String city = rs.getString("city");
				String zip = rs.getString("zip");
				String abbreviation = rs.getString("abbreviation");
				String name = rs.getString("name");
				Name n = new Name(firstName, lastName);
				Address a = new Address(street, city, abbreviation, zip, name);
				Person p = new Person(personId, alphaCode, brokerStat, n, a);
				persons.add(p);
				return persons;
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
