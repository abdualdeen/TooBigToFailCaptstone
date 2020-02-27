package com.tbf;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class LoadNParse {
	
	public static List<Person> parsePersonsFile() {
		List<Person> personList = new ArrayList<Person>();
		Scanner inputFile = null;
		try {
			inputFile = new Scanner(new File("data/Persons.dat"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		Person persons = new Person();
		inputFile.nextLine();
		while (inputFile.hasNext()) {
			String line = inputFile.nextLine();
			String tokens[] = line.split(";");
			String name[] = tokens[2].split(",");
			String address[] = tokens[3].split(",");
			Name newName = new Name(name[1], name[0]);
			Address newAddress = new Address(address[0], address[1], address[2], address[3], address[4]);
			if (tokens.length == 4) {
				persons = new Person(tokens[0], tokens[1], newName, newAddress);

			} else {
				ArrayList<String> newEmail = new ArrayList<>();
				newEmail.add(tokens[4]);
				persons = new Person(tokens[0], tokens[1], newName, newAddress, newEmail);
			}
			personList.add(persons);
		}
		inputFile.close();
		return personList;
	}
	
	
	public static List<Asset> parseAssetsFile() {
		List<Asset> assList = new ArrayList<Asset>();
		
		Scanner inputFile = null;
		try {
			inputFile = new Scanner(new File("data/Assets.dat"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		
		inputFile.nextLine();
		while (inputFile.hasNextLine()) {
			String line = inputFile.nextLine();
			String tokens[] = line.split(";");
			Asset newAsset = null;
			if (tokens[1].contains("D")) {
				newAsset = new DepositAsset(tokens[0], tokens[1], tokens[2], Double.parseDouble(tokens[3]));
			
			} else if (tokens[1].contains("S")) {
				newAsset = new Stock(tokens[0], tokens[1], tokens[2], Double.parseDouble(tokens[3]),
						Double.parseDouble(tokens[4]), Double.parseDouble(tokens[5]), tokens[6],
						Double.parseDouble(tokens[7]));

			} else if (tokens[1].contains("P")) {
				newAsset = new PrivateInvest(tokens[0], tokens[1], tokens[2], Double.parseDouble(tokens[3]),
						Double.parseDouble(tokens[4]), Double.parseDouble(tokens[5]), Double.parseDouble(tokens[6]));

			}
			assList.add(newAsset);
		}
		
		inputFile.close();
		return assList;

	}
	
	public static List<Portfolio> parsePortfoliosFile() {
		List<Portfolio> portList = new ArrayList<Portfolio>();
		
		
		Scanner inputFile = null;
		try {
			inputFile = new Scanner(new File("data/Portfolios.dat"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		
		inputFile.nextLine();
		while (inputFile.hasNextLine()) {
			String line = inputFile.nextLine();
			String tokens[] = line.split(";");
			//saving the new information from the portfolio into the classes
//			String assTokens[] = tokens[4].split(",");
			List<Asset> assList = parseAssetsFile();
			List<Asset> newList = new ArrayList<Asset>();
			Asset newAss = null;
//			if (tokens.length > 4) {
//				String assTokens[] = tokens[4].split(",");
//				for (Asset a : assList) {
//					for (String s : assTokens) {
//						if(s.contains(a.getCode())) {
//							String oneAsset[] = s.split(":");
//							if(a.getAccType() == "D") {
//								newAss = new DepositAsset(a.getCode(), a.getAccType(), a.getLabel(), a.getApr(), Double.parseDouble(oneAsset[1]));
//							} else if (a.getAccType() == "S") {
//								newAss = new Stock(a.getCode(), a.getAccType(), a.getLabel(), a.getQuartDivi(), a.getBaseROR(), a.getBeta(), a.getStockSymb(), 
//										a.getSharePrice(), Integer.parseInt(oneAsset[1]));
//							} else if (a.getAccType() == "P"){
//								newAss = new PrivateInvest(a.getCode(), a.getAccType(), a.getLabel(), a.getQuartDivi(), a.getBaseROR(), a.getOmega(), 
//										a.getTotalValue(), Double.parseDouble(oneAsset[1]));
//							}
//							newList.add(newAss);
//						}
//					}
//				}
//			}
			//*****************
			if (tokens.length > 4) {
				String assTokens[] = tokens[4].split(",");
					for (String s : assTokens) {
						for (Asset a : assList) {
						if(a.getCode().contains(s)) {
							String oneAsset[] = s.split(":");
							if(a.getAccType() == "D") {
								newAss = new DepositAsset(a.getCode(), a.getAccType(), a.getLabel(), a.getApr(), Double.parseDouble(oneAsset[1]));
							} else if (a.getAccType() == "S") {
								newAss = new Stock(a.getCode(), a.getAccType(), a.getLabel(), a.getQuartDivi(), a.getBaseROR(), a.getBeta(), a.getStockSymb(), 
										a.getSharePrice(), Integer.parseInt(oneAsset[1]));
							} else if (a.getAccType() == "P"){
								newAss = new PrivateInvest(a.getCode(), a.getAccType(), a.getLabel(), a.getQuartDivi(), a.getBaseROR(), a.getOmega(), 
										a.getTotalValue(), Double.parseDouble(oneAsset[1]));
							}
							newList.add(newAss);
						}
					}
				}
			}
			
			
			
			//*****************
			
//			for (Asset a : assList) {
//				for (String s : assTokens) {
//					if(s.contains(a.getCode())) {
//						String oneAsset[] = s.split(":");
//						if(a.getAccType() == "D") {
//							newAss = new DepositAsset(a.getCode(), a.getAccType(), a.getLabel(), a.getApr(), Double.parseDouble(oneAsset[1]));
//						} else if (a.getAccType() == "S") {
//							newAss = new Stock(a.getCode(), a.getAccType(), a.getLabel(), a.getQuartDivi(), a.getBaseROR(), a.getBeta(), a.getStockSymb(), 
//									a.getSharePrice(), Integer.parseInt(oneAsset[1]));
//						} else if (a.getAccType() == "P"){
//							newAss = new PrivateInvest(a.getCode(), a.getAccType(), a.getLabel(), a.getQuartDivi(), a.getBaseROR(), a.getOmega(), 
//									a.getTotalValue(), Double.parseDouble(oneAsset[1]));
//						}
//						newList.add(newAss);
//					}
//				}
//			}
			Portfolio newPort = null;
			Person owner = getPerson(tokens[1]);
			Person manag = null;
			Person benef = null;
			manag = getPerson(tokens[2]);
			if (tokens.length > 3) {
				benef = getPerson(tokens[3]);
			}
			
			if (tokens.length == 3) {
				newPort = new Portfolio(tokens[0], owner, manag);

			} else if (tokens.length == 4) {
				newPort = new Portfolio(tokens[0], owner, manag, benef);
			} else {
				newPort = new Portfolio(tokens[0], owner, manag, benef, newList);
			}
			portList.add(newPort);
		}
		
		return portList;
	}
	
	public static Person getPerson(String token) {
		Person thisPerson = new Person();
		if (token.length() < 1){
			thisPerson = null;
		} else {
			List<Person> persList = parsePersonsFile();
			for (Person p : persList) {
				if (p.getPersonCode().contains(token)) {
					thisPerson = p;
				}
			}
		}
		
		return thisPerson;
	}
	
//	private List<Portfolio> getPortfolioAssets(String assetToken){
//		List<Portfolio> portAssets = parsePortfoliosFile();
//		String assTokens[] = portAssets.getAssetList();
//		Asset assets = new Asset();
//		assets = new DepositAsset(assTokens[0], Double.parseDouble(assTokens[1]));
//		//tokenized it stoke
//		//loop trough 
//		//token it again(:)
//		//num value and code
//		//find the code tokeninside asset list
//		//if it mathches
//		//set that specific asset's numerical value 
//		
//		return portAssets;
//		
//	}
	

}
