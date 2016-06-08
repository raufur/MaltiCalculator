package com.raufur.malticalculator.loancalculator.business;

import com.raufur.malticalculator.loancalculator.model.Loan;
import com.raufur.malticalculator.loancalculator.util.BigDecimalHelper;

import java.math.BigDecimal;
import java.math.RoundingMode;



public class LoanPaymentCalculator {

	public BigDecimal calculatePayment(Loan loan) {
		
		BigDecimal ratePerPeriod = BigDecimalHelper.divide(BigDecimalHelper.divide(loan.getRate(), new BigDecimal("100")), new BigDecimal("12"));
		int numOfPeriods = (int) (12 * loan.getLength().doubleValue());
		BigDecimal onePlusR = BigDecimalHelper.add(BigDecimal.ONE, ratePerPeriod);
		BigDecimal top = BigDecimalHelper.multiply(ratePerPeriod, onePlusR.pow(numOfPeriods));
		BigDecimal bottom = BigDecimalHelper.subtract(onePlusR.pow(numOfPeriods),BigDecimal.ONE);
	
		BigDecimal result = BigDecimalHelper.multiply(BigDecimalHelper.divide(top, bottom), loan.getAmount());

		return result.setScale(2, RoundingMode.HALF_UP);
	}

	
}
