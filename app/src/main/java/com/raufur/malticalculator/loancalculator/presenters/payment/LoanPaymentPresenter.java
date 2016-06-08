package com.raufur.malticalculator.loancalculator.presenters.payment;

import com.raufur.malticalculator.loancalculator.business.LoanPaymentCalculator;
import com.raufur.malticalculator.loancalculator.model.Loan;
import com.raufur.malticalculator.loancalculator.views.payment.ILoanPaymentView;

import java.math.BigDecimal;



public class LoanPaymentPresenter {
	private ILoanPaymentView _view;

	public LoanPaymentPresenter(ILoanPaymentView view)
	{
		_view = view;
	}

	public void calculatePayment() {
		//Clear currently displayed payment
		clearPaymentAndHideKeyboard();
		
		if(validateInput())
		{			
			LoanPaymentCalculator calc = new LoanPaymentCalculator();
			Loan loan = new Loan(new BigDecimal(_view.getAmount()),
					new BigDecimal(_view.getLength()),
					new BigDecimal(_view.getRate()));

			BigDecimal payment = calc.calculatePayment(loan);

			_view.setDisplayedPayment(payment.toString());
		}
		else
		{			
			_view.setToastMessage("All fields must be entered!");
		}
	}

	private void clearPaymentAndHideKeyboard() {
		_view.setDisplayedPayment("");
		_view.clearSoftKeyboard();
	}

	private boolean validateInput()
	{
		return _view.getAmount() != null && !_view.getAmount().equals("") &&
		       _view.getLength() != null && !_view.getLength().equals("") &&
		       _view.getRate() != null && !_view.getRate().equals("");
	}
}
