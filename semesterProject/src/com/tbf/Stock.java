package com.tbf;
/**
 * 
 * This deals with all the stock entries in the assets file.
 *
 */
public class Stock extends Asset {
	private double quartDivi;
	private double baseROR;
	private double beta;
	private String stockSymb;
	private double sharePrice;
	private int numberShares; //Added from the portfolio
	
	
	public double getApr() {
		return 0;
	}
	//Constructor after portfolio add.
	public Stock(String code, String accType, String label, double quartDivi, double baseROR, double beta, String stockSymb, double sharePrice, int numberShares) {
		super(code, accType, label);
		this.quartDivi = quartDivi;
		this.baseROR = baseROR;
		this.beta = beta;
		this.stockSymb = stockSymb;
		this.sharePrice = sharePrice;
		this.numberShares = numberShares;
	}
	
	public Stock(String code, String accType, String label, double quartDivi, double baseROR, double beta, String stockSymb, double sharePrice) {
		super(code, accType, label);
		this.quartDivi = quartDivi;
		this.baseROR = baseROR;
		this.beta = beta;
		this.stockSymb = stockSymb;
		this.sharePrice = sharePrice;
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
	public double getBeta() {
		return beta;
	}
	public void setBeta(double beta) {
		this.beta = beta;
	}
	public String getStockSymb() {
		return stockSymb;
	}
	public void setStockSymb(String stockSymb) {
		this.stockSymb = stockSymb;
	}
	public double getSharePrice() {
		return sharePrice;
	}
	public void setSharePrice(double sharePrice) {
		this.sharePrice = sharePrice;
	}
	
	//ZOMBIE METHODS
	@Override
	public double getOmega() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getTotalValue() {
		// TODO Auto-generated method stub
		return 0;
	}

	public int getNumberShares() {
		return numberShares;
	}

	public void setNumberShares(int numberShares) {
		this.numberShares = numberShares;
	}
	@Override
	public double getAmountVal() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public double getPercentStake() {
		// TODO Auto-generated method stub
		return 0;
	}

}
