package com.raufur.malticalculator.loan;

public class InterestOnlyLoan extends AbstractLoan {

	public InterestOnlyLoan(double principal, double interest) {
		super(principal, interest);
	}
	
	public InterestOnlyLoan(String principal, String interest) {
		super(principal, interest);
	}

	public double monthlyPayment() {
		return Math.round(this.principal * (this.interest/100));
	}
}
