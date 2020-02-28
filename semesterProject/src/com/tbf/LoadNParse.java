package com.tbf;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
/**
 * This class loads in and parses the Persons, Assets, and Portfolios files.
 */
public class LoadNParse {
	
	/**
	 * The @parsePersonsFile method brings in the Persons.dat file from the data folder.
	 */
	
	public static List<Person> parsePersonsFile() {
		List<Person> personList = new ArrayList<Person>();
		Scanner inputFile = null;
		try {
			inputFile = new Scanner(new File("data/Persons.dat"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		/**
		 * [Iterating through each line in the Persons file.]
		 * The person object in initialized and the first line in the file that holds the number of records is skipped.
		 * The lines after the first are tokenized and fed to their new person objects. 
		 * Some of the tokens are tokenized and appropriately fed to name or address objects.
		 * Emails are stored in an ArrayList.
		 */
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
	
	/**
	 * The @parseAssetsFile method parses through the Assets.dat file.
	 */
	public static List<Asset> parseAssetsFile() {
		List<Asset> assList = new ArrayList<Asset>();
		
		Scanner inputFile = null;
		try {
			inputFile = new Scanner(new File("data/Assets.dat"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		/**
		 * Skipping over the number of records line, each line in the file is tokenized.
		 * Checking @tokens[1] gives us the asset type. 
		 * After knowing the asset type we can pass the appropriate constructor for the correct asset.
		 */
		inputFile.nextLine();
		while (inputFile.hasNextLine()) {
			String line = inputFile.nextLine();
			String tokens[] = line.split(";");
			Asset newAsset = null;
			double ror = 0;
			if (tokens[1].contains("D")) {
				newAsset = new DepositAsset(tokens[0], tokens[1], tokens[2], Double.parseDouble(tokens[3])/100.0);
			
			} else if (tokens[1].contains("S")) {
				if (Double.parseDouble(tokens[4]) > 1) {
					ror = Double.parseDouble(tokens[4])/100;
					newAsset = new Stock(tokens[0], tokens[1], tokens[2], Double.parseDouble(tokens[3]),
							ror, Double.parseDouble(tokens[5]), tokens[6], Double.parseDouble(tokens[7]));
					
				} else {
					newAsset = new Stock(tokens[0], tokens[1], tokens[2], Double.parseDouble(tokens[3]),
							Double.parseDouble(tokens[4]), Double.parseDouble(tokens[5]), tokens[6],
							Double.parseDouble(tokens[7]));
				}

			} else if (tokens[1].contains("P")) {
				if (Double.parseDouble(tokens[4]) > 1) {
					ror = Double.parseDouble(tokens[4])/100;
							newAsset = new PrivateInvest(tokens[0], tokens[1], tokens[2], Double.parseDouble(tokens[3]),
									ror, Double.parseDouble(tokens[5]), Double.parseDouble(tokens[6]));
				} else {
					newAsset = new PrivateInvest(tokens[0], tokens[1], tokens[2], Double.parseDouble(tokens[3]),
							Double.parseDouble(tokens[4]), Double.parseDouble(tokens[5]), Double.parseDouble(tokens[6]));
				}

			}
			assList.add(newAsset);
		}
		
		inputFile.close();
		return assList;

	}
	/**
	 * @parsePortfoliosFile parses the Portfolios.dat file.
	 */
	public static List<Portfolio> parsePortfoliosFile() {
		List<Portfolio> portList = new ArrayList<Portfolio>();
		
		
		Scanner inputFile = null;
		try {
			inputFile = new Scanner(new File("data/Portfolios.dat"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		/**
		 * Parsing the portfolios is more involved as it requires joining information from other classes.
		 * Therefore, the list of assets and list of persons is imported.
		 */
		inputFile.nextLine();
		/**
		 * @assList the current asset list is brought in.
		 *The line is tokenized. 
		 */
		List<Asset> assList = parseAssetsFile();
		while (inputFile.hasNextLine()) {
			String line = inputFile.nextLine();
			String tokens[] = line.split(";",-1);
			List<Asset> newList = new ArrayList<Asset>();
			Asset newAss = null;
			/**
			 * The length of the tokens is checked as not all portfolios have assets.
			 */
			if (tokens.length > 4) { 
				/**
				 * For portfolios that do have a list of assets, they are split into tokens by ",".
				 * As well as interating through the lines in the file we have to also iterate through each asset in the asset list of the portfolio.
				 */
				String assTokens[] = tokens[4].split(",");
					for (String s : assTokens) {
						String oneAsset[] = s.split(":");
						for (Asset a : assList) {
						if(a.getCode().equals(oneAsset[0])) {
							if(a.getAccType().contentEquals("D")) {
								newAss = new DepositAsset(a.getCode(), a.getAccType(), a.getLabel(), a.getApr(), Double.parseDouble(oneAsset[1]));
							} else if (a.getAccType().equals("S")) {
								newAss = new Stock(a.getCode(), a.getAccType(), a.getLabel(), a.getQuartDivi(), a.getBaseROR()/100.0, a.getBeta(), a.getStockSymb(), 
										a.getSharePrice(), Double.parseDouble(oneAsset[1]));
							} else if (a.getAccType().contentEquals("P")){
								newAss = new PrivateInvest(a.getCode(), a.getAccType(), a.getLabel(), a.getQuartDivi(), a.getBaseROR(), a.getOmega(), 
										a.getTotalValue(), Double.parseDouble(oneAsset[1])/100.0);
							}
							newList.add(newAss);
							break;
						}
					}
				}
			}

			Portfolio newPort = null;
			Person owner = getPerson(tokens[1]);
			Person manag = null;
			Person benef = null;
			manag = getPerson(tokens[2]);
			if (tokens.length > 3) {
				benef = getPerson(tokens[3]);
			}

			newPort = new Portfolio(tokens[0], owner, manag, benef, newList);
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
	
	

}
