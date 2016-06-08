package com.raufur.malticalculator.loan;

public class AmortizedLoan extends AbstractLoan {
	protected int term;
	
	public AmortizedLoan(double principal, double interest) {
		super(principal, interest);
	}
	
	public AmortizedLoan(String principal, String interest) {
		super(principal, interest);
	}

	public void setTerm(String term) {
		setTerm(Integer.parseInt(term));
	}
	
	public void setTerm(int term) {
		this.term = term;
	}
	
	public double monthlyPayment() {
		double totalInterest = 1 + ((this.term * this.interest) / 100);
		double amortization = (this.principal * totalInterest) / this.term;
		return (Math.ceil(amortization / 10)) * 10;
	}
}
