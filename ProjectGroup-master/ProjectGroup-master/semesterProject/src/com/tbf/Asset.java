package com.tbf;

public abstract class Asset {
	private String code;
	private String accType;
	private String label;
	
	public abstract double getApr();
	public abstract double getQuartDivi();
	public abstract double getBaseROR();
	public abstract double getBeta();
	public abstract String getStockSymb();
	public abstract double getSharePrice();
	public abstract double getOmega();
	public abstract double getTotalValue();
	public abstract double getAmountVal();
	public abstract double getNumberShares();
	public abstract double getPercentStake();
	
	public double getReturn(double apy, double totalValue) {
		double monReturn = apy/totalValue;
		return monReturn;
	}
	
	
	public Asset(String code, String accType, String label) {
		super();
		this.code = code;
		this.accType = accType;
		this.label = label;
	}
	
	public Asset() {
		// TODO Auto-generated constructor stub
	}


	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getAccType() {
		return accType;
	}
	public void setAccType(String accType) {
		this.accType = accType;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	
	

}
