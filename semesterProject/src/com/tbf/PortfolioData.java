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
	
	
	/**
	 * @addCountry
	 * @param country
	 * The method is passed the country name and runs a query to add that country into the Country table.
	 * A second query is then run to find the countryId of the country just entered. 
	 */
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
	
	
	/**
	 * @addState
	 * @param state
	 * Given the state name, the method will add the wanted state.
	 * A second query then is used to find the stateId and returns it.
	 */
	public static int addState(String state) {
		String q1 = "insert into State (name) values ('?');";
		String q2 = "select stateId from State where name = ('?');";
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
	
	
	/**
	 * @addAddress
	 * @param street
	 * @param city
	 * @param state
	 * @param zip
	 * @param stateId
	 * @param countryId
	 * Given the street, city, zip, state, stateId and countryId, The method runs a query that that firsts inserts 
	 * information into the Address table. Then runs a new query that finds the addressId of the address just entered 
	 * and returns it. 
	 */
	public static int addAddress(String street, String city, String state, String zip, int stateId, int countryId) {
		String q1 = "insert into Address (street, city, zip, stateId, countryId) values ('?', '?', '?', ?, ?);";
		String q2 = "select AddressId from Address where street = '?' and city = '?' and zip = '?';";
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
		int personId = findPersonId(personCode);
		
		PreparedStatement ps;
		ResultSet rs;
		Connection conn = DBTool.connectToDB();
		
		try {
			ps  = conn.prepareStatement(query);
			ps.setInt(1, personId);
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
	public static void removeAllAssets() {
		String q1 = "delete from PortfolioAsset;";
		String q2 = "delete from Asset;";
		
		PreparedStatement ps;
		ResultSet rs;
		Connection conn = DBTool.connectToDB();
		
		try {
			ps  = conn.prepareStatement(q1);
			rs = ps.executeQuery();
			
			ps  = conn.prepareStatement(q2);
			rs = ps.executeQuery();
		} catch (SQLException sqle) {
			throw new RuntimeException(sqle);
		}
		DBTool.disconnectFromDB(conn, ps, rs);
		
	}

	
	/**
	 * Removes the asset record from the database corresponding to the
	 * provided <code>assetCode</code>
	 * @param assetCode
	 */
	public static void removeAsset(String assetCode) {
		String q1 = "select assetId from Asset where assetCode = '?';";
		String q2 = "delete from PortfolioAsset where assetId = ?;";
		String q3 = "delete from Asset where assetId = ?;";
		
		PreparedStatement ps;
		ResultSet rs;
		Connection conn = DBTool.connectToDB();
		
		try {
			ps  = conn.prepareStatement(q1);
			ps.setString(1, assetCode);
			rs = ps.executeQuery();
			rs.next();
			int assetId = rs.getInt("assetId");
			
			ps  = conn.prepareStatement(q2);
			ps.setInt(1, assetId);
			rs = ps.executeQuery();
			
			ps  = conn.prepareStatement(q3);
			ps.setInt(1, assetId);
			rs = ps.executeQuery();
		} catch (SQLException sqle) {
			throw new RuntimeException(sqle);
		}
		DBTool.disconnectFromDB(conn, ps, rs);
	}
	
	
	/**
	 * Adds a deposit account asset record to the database with the
	 * provided data. 
	 * @param assetCode
	 * @param label
	 * @param apr
	 */
	public static void addDepositAccount(String assetCode, String label, double apr) {
	String q1 = "insert into Asset(assetType, assetId, label, apr) values ('D', '?', '?', ?);";
	PreparedStatement ps;
	ResultSet rs;
	Connection conn = DBTool.connectToDB();
	try {
		ps = conn.prepareStatement(q1);
		ps.setString(1, assetCode);
		ps.setString(2, label);
		ps.setDouble(3, apr);
		rs = ps.executeQuery();
	} catch (SQLException sqle) {
		throw new RuntimeException(sqle);
	}
	DBTool.disconnectFromDB(conn, ps, rs);
}
	
	
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
	public static void addPrivateInvestment(String assetCode, String label, Double quartDivi, 
			Double baseROR, Double omega, Double investmentValue) {
	String q1 = "insert into Asset(assetType, assetCode, label, quartDivi, baseROR, omega, investmentValue) "
			+ "values ('P', '?', '?', ?, ?, ?, ?)";
	PreparedStatement ps;
	ResultSet rs;
	Connection conn = DBTool.connectToDB();
	try {
		ps = conn.prepareStatement(q1);
		ps.setString(1, assetCode);
		ps.setString(2, label);
		ps.setDouble(3, quartDivi);
		ps.setDouble(4, baseROR);
		ps.setDouble(5, omega);
		ps.setDouble(6, investmentValue);
		rs = ps.executeQuery();
	} catch(SQLException sqle) {
		throw new RuntimeException(sqle);
	}
	DBTool.disconnectFromDB(conn, ps, rs);
}
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
	public static void addStock(String assetCode, String label, Double quartDivi, 
			Double baseROR, Double beta, String stockSymb, Double sharePrice) {
	String q1 = "insert into Asset(assetType, assetCode, label, quartDivi, baseROR, beta, stockSymb, sharePrice) "
			+ "values ('S', '?', '?', ?, ?, ?, '?', ?);";
	PreparedStatement ps;
	ResultSet rs;
	Connection conn = DBTool.connectToDB();
	try {
		ps = conn.prepareStatement(q1);
		ps.setString(1, assetCode);
		ps.setString(2, label);
		ps.setDouble(3, quartDivi);
		ps.setDouble(4, baseROR);
		ps.setDouble(5, beta);
		ps.setString(6, stockSymb);
		ps.setDouble(7, sharePrice);
		rs = ps.executeQuery();	
	} catch (SQLException sqle) {
		throw new RuntimeException(sqle);
	}
	DBTool.disconnectFromDB(conn, ps, rs);
}
	
	/**
	 * Removes all portfolio records from the database
	 */
	public static void removeAllPortfolios() {
	String q1 = "delete from PortfolioAsset;";
	String q2 = "delete from Portfolio;";
	
	PreparedStatement ps;
	ResultSet rs;
	Connection conn = DBTool.connectToDB();
	
	try {
		ps = conn.prepareStatement(q1);
		rs = ps.executeQuery();
		
		ps = conn.prepareStatement(q2);
		rs = ps.executeQuery();
	} catch (SQLException sqle) {
		throw new RuntimeException(sqle);
	}
	DBTool.disconnectFromDB(conn, ps, rs);
	}
	
	
	/**
	 * Removes the portfolio record from the database corresponding to the
	 * provided <code>portfolioCode</code>
	 * @param portfolioCode
	 */
	public static void removePortfolio(String portCode) {
	String q1 = "select portId from Portfolio where portCode = '?';";
	String q2 = "delete from PortfolioAsset where portId = ?;";
	String q3 = "delete from Portfolio where portId = '?';";
	
	PreparedStatement ps;
	ResultSet rs;
	Connection conn = DBTool.connectToDB();
	
	try {
		ps = conn.prepareStatement(q1);
		ps.setString(1, portCode);
		rs = ps.executeQuery();
		rs.next();
		int portId = rs.getInt("portId");
		
		ps = conn.prepareStatement(q2);
		ps.setInt(1, portId);
		rs = ps.executeQuery();
		
		ps = conn.prepareStatement(q3);
		ps.setInt(1, portId);
		rs = ps.executeQuery();
	} catch (SQLException sqle) {
		throw new RuntimeException(sqle);
	}
	DBTool.disconnectFromDB(conn, ps, rs);
	}
	
	
	/**
	 * @findPersonId
	 * @param alphaCode
	 * Given the old alpha numeric code, the method runs a query to find the SQL table personId and returns it as an integer.
	 */
	public static int findPersonId(String alphaCode) {
		String query = "select personId from Person where alphaCode = '?'";
		
		PreparedStatement ps;
		ResultSet rs;
		Connection conn = DBTool.connectToDB();
		int personId; 
		
		try {
			ps = conn.prepareStatement(query);
			ps.setString(1, alphaCode);
			rs = ps.executeQuery();
			rs.next();
			personId = rs.getInt("personId");
		} catch (SQLException sqle) {
			throw new RuntimeException(sqle);
		}
		DBTool.disconnectFromDB(conn, ps, rs);
		return personId;
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
		String q1 = "insert into Portfolio (ownerId, managerId, benefId, portCode) values (?, ?, ?, '?');";
		int ownId = findPersonId(ownerId);
		int managId = findPersonId(managerId);
		int benId = findPersonId(benefId);
		PreparedStatement ps;
		ResultSet rs;
		Connection conn = DBTool.connectToDB();
		
		try {
			ps = conn.prepareStatement(q1);
			ps.setInt(1, ownId);
			ps.setInt(2, managId);
			ps.setInt(3, benId);
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
	public static void addAsset(String portfolioCode, String assetCode, double value) {
		String q1 = "select assetId from Asset where assetCode = '?';";
		String q2 = "select portId from Portfolio where portCode = '?';";
		String q3 = "insert into PortfolioAsset (portId, assetId, assetInfo) values (?, ?, ?);";
		
		PreparedStatement ps = null;
		ResultSet rs;
		Connection conn = DBTool.connectToDB();
		
		try {
			ps = conn.prepareStatement(q1);
			ps.setString(1, assetCode);
			rs = ps.executeQuery();
			rs.next();
			int assetId = rs.getInt("assetId");
			
			ps = conn.prepareStatement(q2);
			ps.setString(1, portfolioCode);
			rs = ps.executeQuery();
			rs.next();
			int portId = rs.getInt("portId");
			
			ps = conn.prepareStatement(q3);
			ps.setInt(1, portId);
			ps.setInt(2, assetId);
			ps.setDouble(3, value);
			rs = ps.executeQuery();
		} catch (SQLException sqle) {
			throw new RuntimeException(sqle);
		}
		DBTool.disconnectFromDB(conn, ps, rs);
	}
	
	
}

