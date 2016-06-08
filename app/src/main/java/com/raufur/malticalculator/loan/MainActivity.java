package com.raufur.malticalculator.loan;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.raufur.malticalculator.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loan_main);
        init();
    }

    protected void init() {
    	
    	RadioGroup radioGroup = (RadioGroup) findViewById(R.id.paymentType);
    	
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() 
        {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
            	if (checkedId == R.id.amortizedPayment) {
            		showAmortizedLoanView(true);
            	} else {
            		showAmortizedLoanView(false);
            	}
            }
        });
        showAmortizedLoanView(false);
    }
    
    private void showAmortizedLoanView(boolean enabled) {
    	TextView monthText = (TextView) findViewById(R.id.monthsText);
    	EditText term = (EditText) findViewById(R.id.term);
    	
    	if (enabled) {
    		term.setVisibility(EditText.VISIBLE);
    		monthText.setVisibility(TextView.VISIBLE);
    	} else {
    		term.setVisibility(EditText.INVISIBLE);
    		monthText.setVisibility(TextView.INVISIBLE);
    	}
    	
    }
    
    private boolean validateInputs() {
    	RadioGroup radioGroup = (RadioGroup) findViewById(R.id.paymentType);
    	int paymentTypeSelected = radioGroup.getCheckedRadioButtonId();
    	
    	if (R.id.amortizedPayment == paymentTypeSelected) {
    		EditText termInput = (EditText) findViewById(R.id.term);
    		String term = termInput.getText().toString();
    		if (term.trim().length() == 0) {
    			termInput.requestFocus();
    			return false;
    		}
    	}
    	
    	EditText principalInput = (EditText) findViewById(R.id.principal);
    	if (principalInput.getText().toString().trim().length() == 0) {
    		principalInput.requestFocus();
    		return false;
    	}
    	
    	EditText interestInput = (EditText) findViewById(R.id.interest);
    	if (interestInput.getText().toString().trim().length() == 0) {
    		interestInput.requestFocus();
    		return false;
    	}
    	
    	return true;
    }
    
    private Loan getLoan() {
    	RadioGroup radioGroup = (RadioGroup) findViewById(R.id.paymentType);
    	int paymentTypeSelected = radioGroup.getCheckedRadioButtonId();
    	
    	String principal = ((EditText) findViewById(R.id.principal)).getText().toString();
 	   	String interest = ((EditText) findViewById(R.id.interest)).getText().toString();
 	   	String percentDeduction = ((EditText) findViewById(R.id.percentDeduction)).getText().toString();
 	   	String amountDeduction = ((EditText) findViewById(R.id.amountDeduction)).getText().toString();
    	
    	Loan loan;
    	switch (paymentTypeSelected) {
    	case R.id.amortizedPayment: 
    		loan = new AmortizedLoan(principal, interest);
    		String term = ((EditText) findViewById(R.id.term)).getText().toString();
    		((AmortizedLoan) loan).setTerm(term);
    		break;
    	case R.id.singlePayment: 
    		loan = new SinglePaymentLoan(principal, interest);
    		break;
    	case R.id.interestPayment: 
    		loan = new InterestOnlyLoan(principal, interest);
    		break;
    	default:
    		loan = new SinglePaymentLoan(principal, interest);
    		break;
    	}
    	
    	CheckBox percentDeductionToggle = (CheckBox) findViewById(R.id.percentDeductionToggle);
    	CheckBox amountDeductionToggle = (CheckBox) findViewById(R.id.amountDeductionToggle);
    	
    	if (percentDeductionToggle.isChecked() && 
    		percentDeduction.length() > 0) {
    		loan.setPercentDeduction(percentDeduction);
    	}
    	if (amountDeductionToggle.isChecked() &&
    		amountDeduction.length() > 0) {
    		loan.setAmountDeduction(amountDeduction);
    	}

    	return loan;
    }
    
    private void setReleaseAmount(String amount) {
    	TextView release = (TextView) findViewById(R.id.releaseAmount);
  	   	release.setText(String.valueOf(amount));
    }
    
    private void setPaymentTerm(String label, String amount) {
    	TextView paymentTermLabel = (TextView) findViewById(R.id.paymentTermLabel);
    	paymentTermLabel.setText(label);
    	
    	TextView paymentTermText = (TextView) findViewById(R.id.paymentTermText);
    	paymentTermText.setText(amount);
    }
    
    private void displayLoanComputation(SinglePaymentLoan loan) {
 	   setPaymentTerm("Final Payment: ", loan.finalPayment()+"");
    }
    
    private void displayLoanComputation(AmortizedLoan loan) {
  	   	setPaymentTerm("Monthly Due: ", loan.monthlyPayment()+"");
    }
    
    private void displayLoanComputation(InterestOnlyLoan loan) {
    	setPaymentTerm("Monthly Interest: ", loan.monthlyPayment()+"");
    }
    
    public void displayComputation(View view) {
    	
    	boolean validationResult = validateInputs();
    	
    	if (validationResult) {
	    	Loan loan = getLoan();
	    	
	    	setReleaseAmount(loan.releaseAmount()+"");
	    	if (loan instanceof SinglePaymentLoan) {
	    		displayLoanComputation((SinglePaymentLoan) loan);
	    	} else if (loan instanceof AmortizedLoan) {
	    		displayLoanComputation((AmortizedLoan) loan);
	    	} else if (loan instanceof InterestOnlyLoan) {
	    		displayLoanComputation((InterestOnlyLoan) loan);
	    	}
    	}
   }
    
}
