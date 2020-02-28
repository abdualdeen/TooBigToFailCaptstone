package com.tbf;

import java.util.List;

public class PortfolioReport {
	public static void main(String args[]) {
		List<Portfolio> portList = LoadNParse.parsePortfoliosFile();

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
			report.append(String.format("%-8s %-20s %-20s %-20.2f $%12.2f %12.6f $%12.2f $%12.2f\n",
					i.getPortCode(), i.getOwnerName(), i.getManagerName(), i.getFee(), i.getCommission(),
					i.getWeightedRisk(), i.getReturn(), i.getTotal()));
			fee += i.getFee();
			commi += i.getCommission();
			theReturn += i.getReturn();
			finalTotal += i.getTotal();
		}
		System.out.println(report);
		System.out.printf("Total: $%50.2f $%30.2f $%25.2f $%10.2f",fee, commi, theReturn, finalTotal);

		
		// Portfolio j = new Portfolio();
		// System.out.printf("\t\t\t %15.2f %12.2f %12.2f %12.2f", j.getFeeTotal(),
		// j.getCommiTotal(), j.getReturnTotal(),j.getTotalTotal());

		for (Portfolio j : portList) {
			String benefinfo = "";
			if(j.getBeneficiary() == null) {
				benefinfo = "None";
			}else {
				benefinfo = j.getBeneficiary().getName().getLastName() + ", " + j.getBeneficiary().getName().getFirstName() +j.getBeneficiary().getEmailAddress()
						+ j.getBeneficiary().getAddress().getStreet()+j.getBeneficiary().getAddress().getCity()+
						j.getBeneficiary().getAddress().getState()+j.getBeneficiary().getAddress().getCountry()+j.getBeneficiary().getAddress().getZip();
			}
			System.out.printf("\nPortfolio %s \n --------------------------------------\nOwner: \n%s \n%s\n%s\n%s, %s %s %s\nManager:\n%s\nBeneficiary:\n%s\nAssets",
					j.getPortCode(), j.getOwnerName(), j.getOwnerCode().getEmailAddress(),
					j.getOwnerCode().getAddress().getStreet(),j.getOwnerCode().getAddress().getCity(),j.getOwnerCode().getAddress().getState(),
					j.getOwnerCode().getAddress().getCountry(),j.getOwnerCode().getAddress().getZip(),j.getManagerName(), benefinfo);
			System.out.printf("\n%-15s %-30s %-14s %12s %30s %10s\n","Code","Asset","Return Rate","Risk","Annual Return", "Value");
			for (Asset a : j.getAssetList()) {
				System.out.printf("%-15s %-30s %-20.2f %-25.2f %-10.2f %10.2f\n", a.getCode(), a.getLabel(),
						a.getReturnRate(), a.getRisk(), a.getReturn(), a.getTotal());
			}
			System.out.printf("%40s %33.4f $%23.2f $%14.2f\n","Totals:",j.getWeightedRisk(),j.getReturn(),j.getTotal());
		}
	}
}