package com.tbf;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class Portfolio {
	private String portCode;
	private Person ownerCode;
	private Person managCode;
	private Person benefCode;
	private List<Asset> assetList;
	
	
	public double getTotal() {
		List<Asset> list = this.assetList;
		double total = 0;
		for (Asset a : list) {
			if (a.getAccType().contains("D")) {
				total += a.getAmountVal();
				
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
		if (managCode.getBrokerStatus().contains("E")) {
			commi = (.0375)*getReturn();
			
		} else if (managCode.getBrokerStatus().contains("J")) {
			commi = (.0125)*getReturn();
		}
//		List<Person> persList = LoadNParse.parsePersonsFile();
//		double commi = 0;
//		for (Person i : persList) {
//			if (i.getPersonCode() == ownerCode && i.getBrokerStatus().contains("E")) {
//				commi = (.0375)*getReturn();
//				
//			} else if (i.getPersonCode() == ownerCode && i.getBrokerStatus().contains("J")) {
//				commi = (.0125)*getReturn();
//			}
//		}
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
					theReturn += (Math.exp(a.getApr())-1)*a.getAmountVal();
					
					
				} else if (a.getAccType().contains("S")) {
					double value =a.getSharePrice();
					theReturn += (a.getBaseROR()*value+(4*(a.getQuartDivi())));//*a.getNumberShares();
					
					
				} else if (a.getAccType().contains("P")) {
					theReturn += (a.getBaseROR()*a.getTotalValue()+(4*(a.getQuartDivi())));//*a.getPercentStake();
				}
		}
		return theReturn;
	}
	public double getFee() {
		double fee = 0;
		if (ownerCode.getBrokerStatus().contains("E")) {
			fee = 0;

		} else if (ownerCode.getBrokerStatus().contains("J")) {
			fee = (75*getBrokerAssNum().get(portCode));
		}
//		for (Person i : persList) {
//			if (i.getPersonCode() == ownerCode && i.getBrokerStatus().contains("E")) {
//				fee = getCommission();
//				
//			} else if (i.getPersonCode() == ownerCode && i.getBrokerStatus().contains("J")) {
//				fee = 75*getBrokerAssNum().get(portCode)+getCommission();
//			}
//		}
		return fee;
	}
	
	public String getOwnerName() {
		String name = null;
		name = ownerCode.getName().getLastName() + ", " + ownerCode.getName().getFirstName();

		return name;
	}
	public double getFeeTotal() {
		double feeTotal = 0;
		feeTotal += getFee();
		return feeTotal;
		
	}
	public double getCommiTotal() {
		double commiTotal = 0;
		commiTotal += getCommission();
		return commiTotal;
	}
	public double getReturnTotal() {
		double returnTotal = 0;
		returnTotal += getReturn();
		return returnTotal;
	}
	public double getTotalTotal() {
		double totalTotal = 0;
		totalTotal += getTotal();
		return totalTotal;
	}
	public String getManagerName() {
		String name = null; 
		name = managCode.getName().getLastName() + ", " + managCode.getName().getFirstName();
//		List<Person> persList = LoadNParse.parsePersonsFile();
//		String name = null;
//		for (Person i : persList) {
//			if (i.getPersonCode() == managCode) {
//				name = i.getName().getLastName() + ", " + i.getName().getFirstName();
//			}
//		}
		return name;
	}
	
	public double getWeightedRisk() {
		double risk1 = 0;
		double risk2 = 0;
		double risk3 = 0;
		double weightedRisk = 0;
		List<Asset> list = this.assetList;
			for(Asset a : list) {
				if (a.getAccType().contains("P")) {
					risk1 += a.getOmega() + Math.exp(-125500/a.getTotalValue());
				}else if (a.getAccType().contains("S")) {
					risk2 += a.getBeta();
				}else if (a.getAccType().contains("D")) {
					risk3 = 0;
				}
			}
		
		weightedRisk = (risk1 + risk2 + risk3)/3;
		return weightedRisk;
	}
	
	
	
	//STORE OWNER CODE AND MANGCODE AS A "PERSON"
	public Portfolio(String portCode, Person ownerCode, Person managCode, Person benefCode, List<Asset> assetList) {
		super();
		this.portCode = portCode;
		this.ownerCode = ownerCode;
		this.managCode = managCode;
		this.benefCode = benefCode;
		this.assetList = assetList;
	}
	
	public Portfolio(String portCode, Person ownerCode, Person managCode, Person benefCode) {
		super();
		this.portCode = portCode;
		this.ownerCode = ownerCode;
		this.managCode = managCode;
		this.benefCode = benefCode;
		this.assetList = new ArrayList<Asset>();
	}
	
	
	public Portfolio(String portCode, Person ownerCode, Person managCode) {
		super();
		this.portCode = portCode;
		this.ownerCode = ownerCode;
		this.managCode = managCode;
		this.assetList = new ArrayList<Asset>();
	}
	

	public String getPortCode() {
		return portCode;
	}
	public void setPortCode(String portCode) {
		this.portCode = portCode;
	}
	public Person getOwnerCode() {
		return ownerCode;
	}
	public void setOwnerCode(Person ownerCode) {
		this.ownerCode = ownerCode;
	}
	public Person getManagCode() {
		return managCode;
	}
	public void setManagCode(Person managCode) {
		this.managCode = managCode;
	}
	public Person getBenefCode() {
		return benefCode;
	}
	public void setBenefCode(Person benefCode) {
		this.benefCode = benefCode;
	}
	public List<Asset> getAssetList() {
		return assetList;
	}
	public void setAssetList(List<Asset> assetList) {
		this.assetList = assetList;
	}

}
