package com.raufur.malticalculator.loan;

public interface Loan {
	public void setPercentDeduction(String percent);
	
	public void setPercentDeduction(double percent);
	
	public void setAmountDeduction(String amount);
	
	public void setAmountDeduction(double amount);
	
	public double releaseAmount();
}
