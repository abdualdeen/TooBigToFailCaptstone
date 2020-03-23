package com.tbf;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DBReader {
	
	public static Address retrieveAddress(int addressId) {
		Address address = new Address();
		Connection conn = DBTool.connectToDB();
		String query = "select * from Address where addressId = " +addressId+";";
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = conn.prepareStatement(query);
			rs = ps.executeQuery();
			rs.next();
			address = new Address(rs.getString("street"), rs.getString("city"), rs.getString("abbreviation"), rs.getString("zip"), rs.getString("name"));
			
		} catch (SQLException sqle) {
			throw new RuntimeException(sqle);
		}
		return address;
	}
	
	public static ArrayList<String> retrieveEmailAddress(int personId){
		ArrayList<String> emails = new ArrayList<>();
		Connection conn = DBTool.connectToDB();
		String query = "select emailAddress from EmailAddress where personId = " +personId+ ";";
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			ps = conn.prepareStatement(query);
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
	
	public static Person retrievePerson(int personId){
		Person person = new Person();
		Connection conn = DBTool.connectToDB();
		String query = "select * from Person where personId = " + personId + ";";
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = conn.prepareStatement(query);
			rs = ps.executeQuery();
//			String fName = rs.getString("firstName"); //commented out for debugging (remove once done.)
			rs.next();
			Name n = new Name(rs.getString("firstName"), rs.getString("lastName"));
			person = new Person(rs.getString("alphaCode"), rs.getString("brokerStat"), n, retrieveAddress(rs.getInt("addressId")), 
					retrieveEmailAddress(rs.getInt("personId")));
		}catch (SQLException sqle) {
			throw new RuntimeException(sqle);
		}
		DBTool.disconnectFromDB(conn, ps, rs);
		return person;
	}
	
	
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
		String query = "select * from Asset a join PortfolioAsset pa on a.assetId = pa.assetId where pa.portId = " + portId + ";";
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			ps = conn.prepareStatement(query);
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
			while (rs.next()) {
				Portfolio p = new Portfolio(rs.getString("portCode"), retrievePerson(rs.getInt("ownerId")), retrievePerson(rs.getInt("managerId")), 
						retrievePerson(rs.getInt("beneficiaryId")), retrieveAssets(rs.getInt("portId"))); 
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
