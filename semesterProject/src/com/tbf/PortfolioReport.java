package com.tbf;

import java.util.List;

public class PortfolioReport {
	public static void main(String args[]) {
		List<Portfolio> portList = LoadNParse.parsePortfoliosFile();
		
		System.out.println("Portfolio Summary Report");
		System.out.println("===============================================================================================================================");
		StringBuilder report = new StringBuilder();
		report.append(String.format("%-10s %-10s %-20s %-20s %20s %20s %20s %20s\n", 
				"Portfolio", "Owner", "Manager", "Fees", "Commisions", "Weighted Risk", "Return", "Total"));
		System.out.println(report);
		
		
		for (Portfolio i : portList) {
			report.append(String.format("%-8s %-20s %-10s %-30s $%8.2d $%8.2d $%8.2d $%8.2d\n", 
					i.getPortCode(), i.getOwnerName(), i.getManagerName(), i.getFee(), i.getCommission(), 
					i.getWeightedRisk(), i.getReturn(), i.getTotal())); 
		}
		}
	}


