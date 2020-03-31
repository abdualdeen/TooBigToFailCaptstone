package com.tbf;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DBReader {
	// This is used so we can later retrieve the address for a specific person from our address database based on their corresponding addressId
	// We are obtaining the street, city, state, zip, and country
	// State and country are also retrieved from different methods since they have their own database
	public static Address retrieveAddress(int addressId) {
		Address address = new Address();
		Connection conn = DBTool.connectToDB();
		String query = "select * from Address where addressId = ?;";
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = conn.prepareStatement(query);
			ps.setInt(1, addressId);
			rs = ps.executeQuery();
			rs.next();
			address = new Address(rs.getString("street"), rs.getString("city"), retrieveState(rs.getInt("stateId")), rs.getString("zip"), retrieveCountry(rs.getInt("countryId")));
			
		} catch (SQLException sqle) {
			throw new RuntimeException(sqle);
		}
		DBTool.disconnectFromDB(conn, ps, rs);
		return address;
	}
	// This is used so we can later retrieve the state for a specific person from our state database based on their corresponding stateId
	// We are obtaining the state abbreviation
	public static String retrieveState(int stateId) {
		String state = "";
		Connection conn = DBTool.connectToDB();
		String query = "select * from State where stateId = ?;";
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = conn.prepareStatement(query);
			ps.setInt(1, stateId);
			rs = ps.executeQuery();
			rs.next();
			state = rs.getString("abbreviation");
		} catch(SQLException sqle) {
			throw new RuntimeException(sqle);
		}
		DBTool.disconnectFromDB(conn, ps, rs);
		return state;
	}
	// This is used so we can later retrieve the country for a specific person from our country database based on their corresponding countryId
	// We are obtaining the country abbreviation
	public static String retrieveCountry(int countryId) {
		String country = "";
		Connection conn = DBTool.connectToDB();
		String query = "select * from Country where countryId = ?;";
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = conn.prepareStatement(query);
			ps.setInt(1, countryId);
			rs = ps.executeQuery();
			rs.next();
			country = rs.getString("abbreviation");
		} catch(SQLException sqle) {
			throw new RuntimeException(sqle);
		}
		DBTool.disconnectFromDB(conn, ps, rs);
		return country;
	}
	// This is used so we can later retrieve the email(s) for a specific person from our email database based on their corresponding personId
	// This allows for us to account for multiple email addresses 
	// We are obtaining ALL email addresses listed for a person
	public static ArrayList<String> retrieveEmailAddress(int personId){
		ArrayList<String> emails = new ArrayList<>();
		Connection conn = DBTool.connectToDB();
		String query = "select emailAddress from EmailAddress where personId = ?;";
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			ps = conn.prepareStatement(query);
			ps.setInt(1, personId);
			rs = ps.executeQuery();
			
			while (rs.next()) {
				emails.add(rs.getString("emailAddress"));
			}
			
		} catch (SQLException sqle) {
			throw new RuntimeException(sqle);
		}
		DBTool.disconnectFromDB(conn, ps, rs);
		return emails;
	}
	// In this method we are retrieving a certain person based on the personId
	// Then we are assigning the corresponding variables from previous methods above, or calling our variables straight from our result statement
	// For broker status we accounted for if there was no broker status for a person
	public static Person retrievePerson(int personId){
		if (personId == 0) {
			return null;
		}
		Person person = new Person();
		Connection conn = DBTool.connectToDB();
		String query = "select * from Person where personId = ?;";
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = conn.prepareStatement(query);
			ps.setInt(1, personId);
			rs = ps.executeQuery();
			rs.next();
			Name n = new Name(rs.getString("firstName"), rs.getString("lastName"));
			String brokerStat = rs.getString("brokerStat");
			if (brokerStat != null) {
				person = new Person(rs.getString("alphaCode"), brokerStat, n, retrieveAddress(rs.getInt("addressId")), 
						retrieveEmailAddress(rs.getInt("personId")));
			} else {
				person = new Person(rs.getString("alphaCode"), "", n, retrieveAddress(rs.getInt("addressId")), 
						retrieveEmailAddress(rs.getInt("personId")));
			
			}
		}catch (SQLException sqle) {
			throw new RuntimeException(sqle);
		}
		DBTool.disconnectFromDB(conn, ps, rs);
		return person;
	}
	
	// We are obtaining all persons and adding them to a list
	// We are calling the retrievePerson() and retrieving the information to create a whole list of all persons that includes
	// first and last name, alphaCode, brokerStat(if applicable), address(from retrieveAddress), and email(from retrieveEmail)
	public static List<Person> retrieveAllPerson() {
		Connection conn = DBTool.connectToDB();
		String query = "select * from Person;";
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		List<Person> persons = new ArrayList<>();
		
			try {
				ps = conn.prepareStatement(query);
				rs = ps.executeQuery();
				while (rs.next()) {
					Name n = new Name(rs.getString("firstName"), rs.getString("lastName"));
					Person p = new Person(rs.getString("alphaCode"), rs.getString("brokerStat"), n, retrieveAddress(rs.getInt("addressId")), 
							retrieveEmailAddress(rs.getInt("personId")));
					persons.add(p);
				}
			} catch (SQLException sqle) {
				throw new RuntimeException(sqle);
			}
			
			DBTool.disconnectFromDB(conn, ps, rs);
			return persons;
	}
	
	public static List<Asset> retrieveAllAssets() {
		List<Asset> assets = new ArrayList<>();
		Connection conn = DBTool.connectToDB();
		String query = "select * from Asset;";
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			ps = conn.prepareStatement(query);
			rs = ps.executeQuery();
			
			while (rs.next()) {
				if (rs.getString("assetType").equals("D")) {
					Asset newDepo  = new DepositAsset(rs.getString("assetCode"), rs.getString("assetType"), rs.getString("label"), rs.getDouble("apr"));
					assets.add(newDepo);

				} else if (rs.getString("assetType").equals("S")) {
					Asset newStock = new Stock(rs.getString("assetCode"), rs.getString("assetType"), rs.getString("label"), rs.getDouble("quartDivi"), rs.getDouble("baseROR"),
							rs.getDouble("beta"), rs.getString("stockSymb"), rs.getDouble("SharePrice"));
					assets.add(newStock);

					
				} else if (rs.getString("assetType").equals("P")) {
					Asset newPI = new PrivateInvest(rs.getString("assetCode"), rs.getString("assetType"), rs.getString("label"), rs.getDouble("quartDivi"), 
							rs.getDouble("baseROR"), rs.getDouble("omega"), rs.getDouble("investmentValue"));
					assets.add(newPI);

				}
 			}
		} catch (SQLException sqle) {
			throw new RuntimeException(sqle);
		}
		
		DBTool.disconnectFromDB(conn, ps, rs);
		return assets;
	}

	public static List<Asset> retrieveAssets(int portId) {
		List<Asset> assets = new ArrayList<>();
		Connection conn = DBTool.connectToDB();
		String query = "select * from Asset a join PortfolioAsset pa on a.assetId = pa.assetId where pa.portId = ?;";
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			ps = conn.prepareStatement(query);
			ps.setInt(1, portId);
			rs = ps.executeQuery();
			
			while (rs.next()) {
				if (rs.getString("assetType").equals("D")) {
					Asset newDepo  = new DepositAsset(rs.getString("assetCode"), rs.getString("assetType"), rs.getString("label"), rs.getDouble("apr"), 
							rs.getDouble("assetInfo"));
					assets.add(newDepo);

				} else if (rs.getString("assetType").equals("S")) {
					Asset newStock = new Stock(rs.getString("assetCode"), rs.getString("assetType"), rs.getString("label"), rs.getDouble("quartDivi"), rs.getDouble("baseROR"),
							rs.getDouble("beta"), rs.getString("stockSymb"), rs.getDouble("SharePrice"), rs.getDouble("assetInfo"));
					assets.add(newStock);

					
				} else if (rs.getString("assetType").equals("P")) {
					Asset newPI = new PrivateInvest(rs.getString("assetCode"), rs.getString("assetType"), rs.getString("label"), rs.getDouble("quartDivi"), 
							rs.getDouble("baseROR"), rs.getDouble("omega"), rs.getDouble("investmentValue"), rs.getDouble("assetInfo"));
					assets.add(newPI);

				}
 			}
		} catch (SQLException sqle) {
			throw new RuntimeException(sqle);
		}
		
		DBTool.disconnectFromDB(conn, ps, rs);
		return assets;
	}
	
	public static List<Portfolio> retrieveAllPortfolios() {
		Connection conn = DBTool.connectToDB();
		String query  = "select * from Portfolio;";
				
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		List<Portfolio> portfolios = new ArrayList<>();
		
		try {
			ps = conn.prepareStatement(query);
			rs = ps.executeQuery();
			Portfolio p = new Portfolio();
			while (rs.next()) {
				if (rs.getInt("benefId") >= 1) {
					p = new Portfolio(rs.getString("portCode"), 
							retrievePerson(rs.getInt("ownerId")), retrievePerson(rs.getInt("managerId")), retrievePerson(rs.getInt("benefId")), 
							retrieveAssets(rs.getInt("portId"))); 
				} else {
					p = new Portfolio(rs.getString("portCode"), 
							retrievePerson(rs.getInt("ownerId")), retrievePerson(rs.getInt("managerId")), retrieveAssets(rs.getInt("portId"))); 
				}
				
				portfolios.add(p);
			}
		}
			catch (SQLException sqle) {
				throw new RuntimeException(sqle);
			}
			
			DBTool.disconnectFromDB(conn, ps, rs);
			return portfolios;
				
		}
}
