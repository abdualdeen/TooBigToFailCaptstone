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
		String q2 = "delete from Portfolio;";
		String q3 = ""
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
		
	}
	
	/**
	 * Adds an email record corresponding person record corresponding to the
	 * provided <code>personCode</code>
	 * @param personCode
	 * @param email
	 */
	public static void addEmail(String personCode, String email) {
		String query = "insert into EmailAddress (personId, emailAddress) values(?, ?);";
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
		String q1 = "insert into Portfolio (ownerId, managerId, benefId, portCode) values (?,?,?,?);";
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

