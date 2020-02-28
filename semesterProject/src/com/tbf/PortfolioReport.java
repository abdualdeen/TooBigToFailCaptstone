package com.tbf;

import java.util.List;

public class PortfolioReport {
	public static void main(String args[]) {
		List<Portfolio> portList = LoadNParse.parsePortfoliosFile();
		
		System.out.println("Portfolio Summary Report");
		System.out.println("===============================================================================================================================");
		StringBuilder report = new StringBuilder();
		report.append(String.format("%-10s %-20s %-18s %-15s %20s %10s %10s %10s\n", 
				"Portfolio", "Owner", "Manager", "Fees", "Commisions", "Weighted Risk", "Return", "Total"));
		
		
		
		for (Portfolio i : portList) {
			report.append(String.format("%-8s %-20s %-20s %-20.2f $%12.2f %12.4f $%12.2f $%12.2f\n", 
					i.getPortCode(), i.getOwnerName(), i.getManagerName(), i.getFee(), i.getCommission(), 
					i.getWeightedRisk(), i.getReturn(), i.getTotal())); 
			
		}
		System.out.println(report);
<<<<<<< HEAD
		System.out.println("\t\t-------------------------------------------------");
//		for(Portfolio j : portList) {
//			System.out.printf("\t\t\t %15.2f %12.2f %12.2f %12.2f", j.getFeeTotal(), j.getCommiTotal(), j.getReturnTotal(),j.getTotalTotal());
//		}
=======
		System.out.println(fee+"   "+commi+"   "+theReturn+"   "+finalTotal);
		//System.out.println("\t\t\t %15.2f %12.2f %12.2f %12.2f", getFeeTotal(), getCommiTotal(), getReturnTotal(), getTotalTotal());
		}
>>>>>>> bd910efea87c2d90e8a511c16e7ac471fbe3b60d
	}
}


