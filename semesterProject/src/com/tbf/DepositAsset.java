package com.tbf;

import java.util.List;

/**
 * This class deals specifically with all the deposit accounts in the Assets file.
 * 
 *
 */
public class DepositAsset extends Asset {
    private double apr;
    private double amountVal; //Added from portfolio
    
	//Constructor after portfolio add.
    public DepositAsset(String code, String accType, String label, double apr, double amountVal) {
		super(code, accType, label);
		this.apr = apr;
		this.amountVal = amountVal;
	}
    
    
	public DepositAsset(String code, String accType, String label, double apr) {
		super(code, accType, label);
		this.apr = apr;
	}
	
	public double getApr() {
		return apr;
	}
	public void setApr(double apr) {
		this.apr = apr;
	}


	public double getAmountVal() {
		return amountVal;
	}


	public void setAmountVal(double assetVal) {
		this.amountVal = assetVal;
	}


	//ZOMBIE METHODS
	@Override
	public double getQuartDivi() {
		// TODO Auto-generated method stub
		return 0;
	}


	@Override
	public double getBaseROR() {
		// TODO Auto-generated method stub
		return 0;
	}


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


	@Override
	public double getNumberShares() {
		// TODO Auto-generated method stub
		return 0;
	}


	@Override
	public double getPercentStake() {
		// TODO Auto-generated method stub
		return 0;
	}
}
