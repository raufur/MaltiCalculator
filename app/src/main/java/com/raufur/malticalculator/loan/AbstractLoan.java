package com.raufur.malticalculator.loan;

public abstract class AbstractLoan implements Loan {
	protected double interest;
	protected double principal;
	protected double percentDeduction;
	protected double amountDeduction;
	
	public AbstractLoan(String principal, String interest) {
		this.principal = Double.parseDouble(principal);
		this.interest = Double.parseDouble(interest);
		
	}
	
	public AbstractLoan(double principal, double interest) {
		this.principal = principal;
		this.interest = interest;
	}
	
	public void setPercentDeduction(String percent) {
		setPercentDeduction(Double.parseDouble(percent));
	}
	
	public void setPercentDeduction(double percent) {
		this.percentDeduction = percent;
	}
	
	public void setAmountDeduction(String amount) {
		setAmountDeduction(Double.parseDouble(amount));
	}
	
	public void setAmountDeduction(double amount) {
		this.amountDeduction = amount;
	}
	
	public double releaseAmount() {
		double percentDeductionAmount = Math.round(this.principal * (this.percentDeduction/100));
		return principal - (percentDeductionAmount + this.amountDeduction);
	}
}
