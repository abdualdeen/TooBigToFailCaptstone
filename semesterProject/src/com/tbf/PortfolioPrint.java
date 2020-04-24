package com.tbf;

import java.util.List;

public class PortfolioPrint {
	
	public static void printSummary(SortList portList) {
		// Printing out the general summary report with the variables that are obtained
		// from the list of portfolios
		// The variables are gathered by the methods in the portfolio class
		System.out.println("Portfolio Summary Report");
		System.out.println(
				"===============================================================================================================================");
		StringBuilder report = new StringBuilder();
		report.append(String.format("%-10s %-20s %-18s %-15s %20s %10s %10s %10s\n", "Portfolio", "Owner", "Manager",
				"Fees", "Commisions", "Weighted Risk", "Return", "Total"));

		double fee = 0;
		double commi = 0;
		double theReturn = 0;
		double finalTotal = 0;
		for (Portfolio i : portList) {
			report.append(String.format("%-8s %-20s %-20s %-20.2f $%12.2f %12.4f $%12.2f $%12.2f\n", i.getPortCode(),
					i.getOwnerName(), i.getManagerName(), i.getFee(), i.getCommission(), i.getWeightedRisk(),
					i.getReturn(), i.getTotal()));

			fee += i.getFee();
			commi += i.getCommission();
			theReturn += i.getReturn();
			finalTotal += i.getTotal();
		}
		System.out.println(report);
		System.out.println(
				"++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
		System.out.printf("Total: $%50.2f $%28.2f $%24.2f $%10.2f", fee, commi, theReturn, finalTotal);
		System.out.println(
				"\n===================================================================================================================================");

	}

	public static void printDetail(SortList portList) {
		// For every specific portfolio, we are listing their assets, and the
		// specificities of them
		// For every asset in a specific portfolio we are then calling methods in the
		// asset class to obtain those values and print them out
		for (Portfolio j : portList) {
			String benefinfo = "";
			if (j.getBeneficiary() == null) {
				benefinfo = "None";
			} else {
				benefinfo = j.getBeneficiary().getName().getLastName() + ", "
						+ j.getBeneficiary().getName().getFirstName() + "\n" + j.getBeneficiary().getEmailAddress()
						+ "\n" + j.getBeneficiary().getAddress().getStreet() + "\n"
						+ j.getBeneficiary().getAddress().getCity() + ", " + j.getBeneficiary().getAddress().getState()
						+ " " + j.getBeneficiary().getAddress().getCountry() + " "
						+ j.getBeneficiary().getAddress().getZip();
			}
			System.out.printf(
					"\nPortfolio %s \n --------------------------------------\nOwner: \n%s \n%s\n%s\n%s, %s %s %s\nManager:\n%s\nBeneficiary:\n%s\n\nAssets",
					j.getPortCode(), j.getOwnerName(), j.getOwnerCode().getEmailAddress(),
					j.getOwnerCode().getAddress().getStreet(), j.getOwnerCode().getAddress().getCity(),
					j.getOwnerCode().getAddress().getState(), j.getOwnerCode().getAddress().getCountry(),
					j.getOwnerCode().getAddress().getZip(), j.getManagerName(), benefinfo);
			System.out.printf("\n%-15s %-30s %-14s %12s %30s %10s\n", "Code", "Asset", "Return Rate", "Risk",
					"Annual Return", "Value");
			for (Asset a : j.getAssetList()) {
				System.out.printf("%-15s %-30s %-20.2f %-25.2f %-10.2f %10.2f\n", a.getCode(), a.getLabel(),
						a.getReturnRate(), a.getRisk(), a.getReturn(), a.getTotal());
			}
			System.out.println("\n++++++++++++++++++++++++++++++++++++++");

			System.out.printf("%40s %33.4f $%23.2f $%14.2f\n", "Totals:", j.getWeightedRisk(), j.getReturn(),
					j.getTotal());
		}
	}

}
