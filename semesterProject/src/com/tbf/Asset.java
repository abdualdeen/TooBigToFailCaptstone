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
	
	
	public Asset(String code, String accType, String label) {
		super();
		this.code = code;
		this.accType = accType;
		this.label = label;
	}
	
	public Asset() {
		// TODO Auto-generated constructor stub
	}

	public double getRisk() {
		double risk = 0;
				if (getAccType().contains("P")) {
					risk += getOmega() + Math.exp(-125500/getTotalValue());
				}else if (getAccType().contains("S")) {
					risk += getBeta();
				}else if (getAccType().contains("D")) {
					risk = 0;
				}
		return risk;
	}
	
	public double getReturn() {
		double theReturn = 0;
				if (getAccType().contains("D")) {
					theReturn = (Math.exp(getApr())-1)*getAmountVal();
					
				} else if (getAccType().contains("S")) {
					double value =getSharePrice();
					theReturn = ((getBaseROR()*value)+(4*(getQuartDivi()))*getNumberShares());
					
				} else if (getAccType().contains("P")) {
					theReturn = getBaseROR()*getTotalValue()*getPercentStake()+(4*(getQuartDivi()))*getPercentStake();
				}
		return theReturn;
	}
	
	public double getTotal() {
		double total = 0;
		if (getAccType().contains("D")) {
			total += getAmountVal();
		} else if (getAccType().contains("S")) {
			total += getSharePrice()*getNumberShares();
		} else if (getAccType().contains("P")) {
			total += getTotalValue()*getPercentStake();
		}
		return total;
	}
	public double getReturnRate() {
		double returnRate = 0;
			if(getAccType().contains("P")) {
				returnRate = (getReturn()/(getTotalValue()*getPercentStake()))*100;
				
			}else if(getAccType().contains("S")) {
				returnRate = (getReturn()/(getSharePrice()*getNumberShares()))*100;
				
			}else if(getAccType().contains("D")) {
				returnRate = 0;
			}
		return returnRate;
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