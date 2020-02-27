package com.tbf;
/**
 * 
 * This deals with the private investment entries in the assets file.
 *
 */
public class PrivateInvest extends Asset {
	private double quartDivi;
	private double baseROR;
	private double omega;
	private double totalValue;
	private double percentStake; //Added from portfolio
	
	public double getApr() {
		return 0;
	}
	
	//Constructor after portfolio add.
	public PrivateInvest(String code, String accType, String label, double quartDivi, double baseROR, double omega, double totalValue, double percentStake) {
		super(code, accType, label);
		this.quartDivi = quartDivi;
		this.baseROR = baseROR;
		this.omega = omega;
		this.totalValue = totalValue;
		this.percentStake = percentStake;
	}
	
	
	public PrivateInvest(String code, String accType, String label, double quartDivi, double baseROR, double omega, double totalValue) {
		super(code, accType, label);
		this.quartDivi = quartDivi;
		this.baseROR = baseROR;
		this.omega = omega;
		this.totalValue = totalValue;
	}
	
	
	public double getQuartDivi() {
		return quartDivi;
	}
	public void setQuartDivi(double quartDivi) {
		this.quartDivi = quartDivi;
	}
	public double getBaseROR() {
		return baseROR;
	}
	public void setBaseROR(double baseROR) {
		this.baseROR = baseROR;
	}
	public double getOmega() {
		return omega;
	}
	public void setOmega(double omega) {
		this.omega = omega;
	}
	public double getTotalValue() {
		return totalValue;
	}
	public void setTotalValue(double totalValue) {
		this.totalValue = totalValue;
	}
	
	//ZOMBIE METHODS
	
	@Override
	public double getBeta() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getStockSymb() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public double getSharePrice() {
		// TODO Auto-generated method stub
		return 0;
	}

	public double getPercentStake() {
		return percentStake;
	}

	public void setPercentStake(double percentStake) {
		this.percentStake = percentStake;
	}

	@Override
	public double getAmountVal() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getNumberShares() {
		// TODO Auto-generated method stub
		return 0;
	}

}
