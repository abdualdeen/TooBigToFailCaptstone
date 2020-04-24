package com.tbf;

import java.util.List;
/**
 * The PortfolioReport class brings in the Portfolio data (in this case from the SQL database) and prints out the relevant information.
 * In the process of printing, it calls several methods that calculate various important information for the Portfolio such as 
 * Commission, Weighted Risk, Fees, and Totals. 
 */
public class PortfolioReport {
	public static void main(String args[]) {
		// Setting a list variable equal to the method in DBReader that retrieves all portfolios
		List<Portfolio> portList = DBReader.retrieveAllPortfolios();
//		PortfolioPrint.printSummary(portList);
//		PortfolioPrint.printDetail(portList);
		NameSortList<Portfolio> nameList = new NameSortList<>();
		ValueSortList<Portfolio> valueList = new ValueSortList<>();
		ManagerSortList<Portfolio> managerList = new ManagerSortList<>();
		for(Portfolio p: portList) {
			nameList.add(p);
			valueList.add(p);
			managerList.add(p);
		}
		PortfolioPrint.printDetail(nameList);
		PortfolioPrint.printDetail(valueList);
		PortfolioPrint.printDetail(managerList);
	}
}
