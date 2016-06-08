package com.raufur.malticalculator.loan;

public class SinglePaymentLoan extends AbstractLoan {

	public SinglePaymentLoan(double principal, double interest) {
		super(principal, interest);
	}
	
	public SinglePaymentLoan(String principal, String interest) {
		super(principal, interest);
	}

	public double finalPayment() {
		double interestAmount = Math.round(this.principal * (this.interest/100));
		return this.principal + interestAmount;
	}
}
