package com.tbf;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * This is a collection of utility methods that define a general API for
 * interacting with the database supporting this application.
 *
 */
public class PortfolioData {
	
	
	
	/**
	 * Method that removes every person record from the database
	 */
	public static void removeAllPersons() {
		String q1 = "delete from EmailAddress;";
		String q2 = "delete from PortfolioAsset;";
		String q3 = "delete from Portfolio;";
		String q4 = "delete from Person;";
		
		PreparedStatement ps;
		ResultSet rs;
		Connection conn = DBTool.connectToDB();
		
		try {
			ps = conn.prepareStatement(q1);
			rs = ps.executeQuery();
			ps = conn.prepareStatement(q2);
			rs = ps.executeQuery();
			ps = conn.prepareStatement(q3);
			rs = ps.executeQuery();
			ps = conn.prepareStatement(q4);
			
		} catch (SQLException sqle) {
			throw new RuntimeException(sqle);
		}
		DBTool.disconnectFromDB(conn, ps, rs);
	}
	
	/**
	 * Removes the person record from the database corresponding to the
	 * provided <code>personCode</code>
	 * @param personCode
	 */
	public static void removePerson(String personCode) {
		String q1 = "delete from EmailAddress where personId = ?;";
		String q2 = "update Portfolio set benefId = null where portId = ?;";
		String q3 = "delete from Person where personId = ?;";
		
		PreparedStatement ps;
		ResultSet rs;
		Connection conn = DBTool.connectToDB();
		
		try {
			ps = conn.prepareStatement(q1);
			ps.setString(1, personCode);
			rs = ps.executeQuery();
			ps = conn.prepareStatement(q2);
			ps.setString(1, personCode);
			rs = ps.executeQuery();
			ps = conn.prepareStatement(q3);
			ps.setString(1, personCode);
			rs = ps.executeQuery();
			
		} catch (SQLException sqle) {
			throw new RuntimeException(sqle);
		}
		DBTool.disconnectFromDB(conn, ps, rs);
	}
	public static int addCountry(String country) {
		String q1 = "insert into Country (name) values ('?');";
		String q2 = "select countryId from Country where name = (\"?\");";
		PreparedStatement ps = null;
		ResultSet rs;
		Connection conn = DBTool.connectToDB();
		int countryId;
		
		try {
			ps = conn.prepareStatement(q1);
			ps.setString(1, country);
			rs = ps.executeQuery();
			ps = conn.prepareStatement(q2);
			ps.setString(1, country);
			rs = ps.executeQuery();
			rs.next();
			countryId = rs.getInt("name");
		} catch (SQLException sqle) {
			throw new RuntimeException(sqle);
		}
		DBTool.disconnectFromDB(conn, ps, rs);
		return countryId;
	}
	
	public static int addState(String state) {
		String q1 = "insert into State (name) values (\"?\");";
		String q2 = "select stateId from State where name = (\"?\");";
		PreparedStatement ps = null;
		ResultSet rs;
		Connection conn = DBTool.connectToDB();
		int stateId;
		
		try {
			ps = conn.prepareStatement(q1);
			ps.setString(1, state);
			rs = ps.executeQuery();
			ps = conn.prepareStatement(q2);
			ps.setString(1, state);
			rs = ps.executeQuery();
			rs.next();
			stateId = rs.getInt("name");
		} catch (SQLException sqle) {
			throw new RuntimeException(sqle);
		}
		DBTool.disconnectFromDB(conn, ps, rs);
		return stateId;
	}
	public static int addAddress(String street, String city, String state, String zip, int stateId, int countryId) {
		String q1 = "insert into Address (street, city, zip, stateId, countryId) values (\"?\", \"?\", \"?\", ?, ?);";
		String q2 = "select AddressId from Address where street = \"?\" and city = \"?\" and zip = \"?\";";
		PreparedStatement ps = null;
		ResultSet rs;
		Connection conn = DBTool.connectToDB();
		int addressId;
		
		try {
			ps = conn.prepareStatement(q1);
			ps.setString(1, street);
			ps.setString(2, city);
			ps.setString(3, zip);
			ps.setInt(4, stateId);
			ps.setInt(5, countryId);
			rs = ps.executeQuery();
			
			ps = conn.prepareStatement(q2);
			ps.setString(1, street);
			ps.setString(2, city);
			ps.setString(3, zip);
			rs.next();
			addressId = rs.getInt("addressId");
		} catch (SQLException sqle) {
			throw new RuntimeException(sqle);
		}
		DBTool.disconnectFromDB(conn, ps, rs);
		return addressId;
	}
	
	/**
	 * Method to add a person record to the database with the provided data. The
	 * <code>brokerType</code> will either be "E" or "J" (Expert or Junior) or 
	 * <code>null</code> if the person is not a broker.
	 * @param personCode
	 * @param firstName
	 * @param lastName
	 * @param street
	 * @param city
	 * @param state
	 * @param zip
	 * @param country
	 * @param brokerType
	 */
	public static void addPerson(String personCode, String firstName, String lastName, String street, String city, String state, String zip, 
			String country, String brokerType, String secBrokerId) {
		int countryId = addCountry(country);
		int stateId = addState(state);
		int addressId = addAddress(street, city, state, zip, stateId, countryId);
//		String q1 = "insert into State (name, abbreviation) values (?, ?);";
//		String q2 = "insert into Country (name, abbreviation) values (?, ?);";
//		String q1 = "insert into Address (street, city, zip, stateId, countryId) values (\"?\", \"?\", \"?\", ?, ?);";
		String q1;
		String brokerStat = null;
		if (brokerType != null || !brokerType.isEmpty()) {
			q1 = "insert into Person(alphaCode, lastName, firstName, addressId) values (\"?\", \"?\", \"?\", ?);";
		} else {
			brokerStat = brokerType+", "+secBrokerId;
			q1 = "insert into Person(alphaCode, brokerStat, lastName, firstName, addressId) values ('?', '?', '?', '?', ?);";
		}
		
		PreparedStatement ps;
		ResultSet rs;
		Connection conn = DBTool.connectToDB();
		
		try {
			ps  = conn.prepareStatement(q1);
			ps.setString(1, personCode);
			if (brokerType == null || brokerType.isEmpty()) {
				ps.setString(2, lastName);
				ps.setString(3, firstName);
				ps.setInt(4, addressId);
				
			} else {
				ps.setString(2, brokerStat);
				ps.setString(3, lastName);
				ps.setString(4, firstName);
				ps.setInt(5, addressId);
			}
			
//			ps.setString(2, city);
//			ps.setString(3, zip);
//			ps.setInt(4, stateId);
//			ps.setInt(5, countryId);
//			ps.setString(2, email);
			rs = ps.executeQuery();
		} catch (SQLException sqle) {
			throw new RuntimeException(sqle);
		}
		DBTool.disconnectFromDB(conn, ps, rs);
	}
	
	/**
	 * Adds an email record corresponding person record corresponding to the
	 * provided <code>personCode</code>
	 * @param personCode
	 * @param email
	 */
	public static void addEmail(String personCode, String email) {
		String query = "insert into EmailAddress (personId, emailAddress) values(?, '?');";
		PreparedStatement ps;
		ResultSet rs;
		Connection conn = DBTool.connectToDB();
		try {
			ps  = conn.prepareStatement(query);
			ps.setString(1, personCode);
			ps.setString(2, email);
			rs = ps.executeQuery();
		} catch (SQLException sqle) {
			throw new RuntimeException(sqle);
		}
		DBTool.disconnectFromDB(conn, ps, rs);
	}

	/**
	 * Removes all asset records from the database
	 */
	public static void removeAllAssets() {}

	/**
	 * Removes the asset record from the database corresponding to the
	 * provided <code>assetCode</code>
	 * @param assetCode
	 */
	public static void removeAsset(String assetCode) {}
	
	/**
	 * Adds a deposit account asset record to the database with the
	 * provided data. 
	 * @param assetCode
	 * @param label
	 * @param apr
	 */
	public static void addDepositAccount(String assetCode, String label, double apr) {}
	
	/**
	 * Adds a private investment asset record to the database with the
	 * provided data. 
	 * @param assetCode
	 * @param label
	 * @param quarterlyDividend
	 * @param baseRateOfReturn
	 * @param baseOmega
	 * @param totalValue
	 */
	public static void addPrivateInvestment(String assetCode, String label, Double quarterlyDividend, 
			Double baseRateOfReturn, Double baseOmega, Double totalValue) {}
	
	/**
	 * Adds a stock asset record to the database with the
	 * provided data. 
	 * @param assetCode
	 * @param label
	 * @param quarterlyDividend
	 * @param baseRateOfReturn
	 * @param beta
	 * @param stockSymbol
	 * @param sharePrice
	 */
	public static void addStock(String assetCode, String label, Double quarterlyDividend, 
			Double baseRateOfReturn, Double beta, String stockSymbol, Double sharePrice) {}

	/**
	 * Removes all portfolio records from the database
	 */
	public static void removeAllPortfolios() {
	String q1 = "truncate table Portfolio;";
	PreparedStatement ps;
	ResultSet rs;
	Connection conn = DBTool.connectToDB();
	try {
		ps = conn.prepareStatement(q1);
		rs = ps.executeQuery();
	} catch (SQLException sqle) {
		throw new RuntimeException(sqle);
	}
	}
	/**
	 * Removes the portfolio record from the database corresponding to the
	 * provided <code>portfolioCode</code>
	 * @param portfolioCode
	 */
	public static void removePortfolio(String portCode) {
	String q1 = "delete from Portfolio where portCode = ?;";
	PreparedStatement ps;
	ResultSet rs;
	Connection conn = DBTool.connectToDB();
	try {
		ps = conn.prepareStatement(q1);
		ps.setString(1, portCode);
		rs = ps.executeQuery();
	} catch (SQLException sqle) {
		throw new RuntimeException(sqle);
	}
	DBTool.disconnectFromDB(conn, ps, rs);
	}
	/**
	 * Adds a portfolio records to the database with the given data.  If the portfolio has no
	 * beneficiary, the <code>beneficiaryCode</code> will be <code>null</code>
	 * @param portfolioCode
	 * @param ownerCode
	 * @param managerCode
	 * @param beneficiaryCode
	 */
	public static void addPortfolio(String portCode, String ownerId, String managerId, String benefId) {
		String q1 = "insert into Portfolio (ownerId, managerId, benefId, portCode) values (?, ?, ?, ?);";
		PreparedStatement ps;
		ResultSet rs;
		Connection conn = DBTool.connectToDB();
		try {
			ps = conn.prepareStatement(q1);
			ps.setString(1, ownerId);
			ps.setString(2, managerId);
			ps.setString(3, benefId);
			ps.setString(4, portCode);
			rs = ps.executeQuery();
		} catch (SQLException sqle) {
			throw new RuntimeException(sqle);
		}
		DBTool.disconnectFromDB(conn, ps, rs);
	}
	
		/**
	 * Associates the asset record corresponding to <code>assetCode</code> with the 
	 * portfolio corresponding to the provided <code>portfolioCode</code>.  The third 
	 * parameter, <code>value</code> is interpreted as a <i>balance</i>, <i>number of shares</i>
	 * or <i>stake percentage</i> depending on the type of asset the <code>assetCode</code> is
	 * associated with.
	 * @param portfolioCode
	 * @param assetCode
	 * @param value
	 */
	public static void addAsset(String portfolioCode, String assetCode, double value) {}
	
	
}

