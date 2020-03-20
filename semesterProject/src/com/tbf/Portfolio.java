package com.tbf;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class Portfolio {
	private String portCode;
	private Person owner;
	private Person manag;
	private Person benef;
	private List<Asset> assetList;
	
	public static List<Portfolio> retrieveAllPortfolios() {
		Connection conn = DBTool.connectToDB();
		String ownerQuery  = "Select p.alphaCode, po.portCode from Person p"
				+ " join Portfolio po on p.personId = po.ownerId group by p.alphaCode;";
		String managerQuery  = "Select p.alphaCode, po.portCode from Person p"
				+ " join Portfolio po on p.personId = po.managerId group by p.alphaCode;";
		String beneficiaryQuery  = "Select p.alphaCode, po.portCode from Person p"
				+ " join Portfolio po on p.personId = po.benefId group by p.alphaCode;";
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		List<Portfolio> portfolios = new ArrayList<>();
		
			ps = conn.prepareStatement(ownerQuery, managerQuery, beneficiaryQuery);
			rs = ps.executeQuery();
			while (rs.next()) {
				String alphaCode = rs.getString("alphaCode");
				int portCode = rs.getString("portCode");
				ArrayList<String> = 
				
			}
				
	
	public double getTotal() {
		List<Asset> list = this.assetList;
		double total = 0;
		for (Asset a : list) {
			if (a.getAccType().contains("D")) {
				total += a.getBalance();
				
			} else if (a.getAccType().contains("S")) {
				total += a.getSharePrice()*a.getNumberShares();

			} else if (a.getAccType().contains("P")) {
				total += a.getTotalValue()*a.getPercentStake();

			}
		}
		return total;
	}
	
	public double getCommission() {
		double commi = 0;
		if (manag.getBrokerStatus().contains("E")) {
			commi = (.0375)*getReturn();
			
		} else if (manag.getBrokerStatus().contains("J")) {
			commi = (.0125)*getReturn();
		}

		return commi;
	}
	
	public HashMap<String, Integer> getBrokerAssNum() {
		Scanner inputFile = null;
		try {
			inputFile = new Scanner(new File("data/Portfolios.dat"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		HashMap<String, Integer> portAssNum = new HashMap<>();
		
		inputFile.nextLine();
		while (inputFile.hasNextLine()) {
			String line = inputFile.nextLine();
			String tokens[] = line.split(";",-1);
			//saving the new information from the portfolio into the classes
			String assTokens[] = tokens[4].split(",");
			portAssNum.put(tokens[0], assTokens.length);
		}
		return portAssNum;
	}
	
	public double getReturn() {
		List<Asset> list = this.assetList;
		double theReturn = 0;
		for (Asset a : list) {
				if (a.getAccType().contains("D")) {
					theReturn += (Math.exp(a.getApr())-1)*a.getBalance();
					
					
				} else if (a.getAccType().contains("S")) {
					double value =a.getSharePrice()*a.getNumberShares();
					theReturn += (a.getBaseROR()*value)+(4*(a.getQuartDivi())*a.getNumberShares());
					
					
				} else if (a.getAccType().contains("P")) {
					theReturn += (a.getBaseROR()*a.getTotalValue()+(4*(a.getQuartDivi())))*a.getPercentStake();
				}
		}
		return theReturn;
	}
	public double getFee() {
		double fee = 0;
		if (owner.getBrokerStatus().contains("E")) {
			fee = 0;

		} else if (owner.getBrokerStatus().contains("J")) {
			fee = (75*getBrokerAssNum().get(portCode));
		}
		return fee;
	}
	
	public String getOwnerName() {
		String name = owner.getName().getLastName() + ", " + owner.getName().getFirstName();
		return name;
	}

	
	public String getManagerName() {
		String name = manag.getName().getLastName() + ", " + manag.getName().getFirstName();
		return name;
	}
	
	public double getWeightedRisk() {
		double risk = 0;
		double value = 0;
		double totalRisk = 0;
		List<Asset> list = this.assetList;
		for (Asset a : list) {
			if (a.getAccType().contains("D")) {
				risk = 0;
				value += a.getBalance();
				totalRisk = risk*(a.getBalance()/value);
				
			} else if (a.getAccType().contains("S")) {
				risk = a.getBeta();
				value += a.getSharePrice()*a.getNumberShares();
				totalRisk = risk*((a.getNumberShares()*a.getSharePrice())/value);
				
			} else if (a.getAccType().contains("P")) {
				risk = a.getOmega() + Math.exp(-125500/a.getTotalValue());
				value += a.getPercentStake()*a.getTotalValue();
				totalRisk += risk*(a.getPercentStake()*a.getTotalValue())/value;
			}
		}
		return totalRisk;
	}
	
	
	
	
	//STORE OWNER CODE AND MANGCODE AS A "PERSON"
	public Portfolio(String portCode, Person owner, Person manag, Person benef, List<Asset> assetList) {
		super();
		this.portCode = portCode;
		this.owner = owner;
		this.manag = manag;
		this.benef = benef;
		this.assetList = assetList;
	}
	
	public Portfolio(String portCode, Person owner, Person manag, Person benef) {
		super();
		this.portCode = portCode;
		this.owner = owner;
		this.manag = manag;
		this.benef = benef;
		this.assetList = new ArrayList<Asset>();
	}
	
	
	public Portfolio(String portCode, Person owner, Person manag) {
		super();
		this.portCode = portCode;
		this.owner = owner;
		this.manag = manag;
		this.assetList = new ArrayList<Asset>();
	}
	

	public String getPortCode() {
		return portCode;
	}
	public void setPortCode(String portCode) {
		this.portCode = portCode;
	}
	public Person getOwnerCode() {
		return owner;
	}
	public void setOwnerCode(Person owner) {
		this.owner = owner;
	}
	public Person getManagCode() {
		return manag;
	}
	public void setManagCode(Person manag) {
		this.manag = manag;
	}
	public Person getBeneficiary() {
		return benef;
	}
	public void setBenefCode(Person benef) {
		this.benef = benef;
	}
	public List<Asset> getAssetList() {
		return assetList;
	}
	public void setAssetList(List<Asset> assetList) {
		this.assetList = assetList;
	}

}