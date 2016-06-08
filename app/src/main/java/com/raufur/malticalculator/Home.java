package com.raufur.malticalculator;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Spinner;

public class Home extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        android.app.Fragment fragment = new InputFragment();
        android.app.FragmentManager fm = getFragmentManager();
        android.app.FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.Input_Fragment, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.menu_Reset) {
            EditText txtHomeValue = (EditText) findViewById(R.id.val_HomeValue);
            EditText txtDownPayment = (EditText) findViewById(R.id.val_DownPayment);
            EditText txtAPR = (EditText) findViewById(R.id.val_InterestRate);
            EditText txtTaxRate = (EditText) findViewById(R.id.val_PropertyTax);
            Spinner txtTerms = (Spinner) findViewById(R.id.val_Terms);
            txtHomeValue.setText("");
            txtDownPayment.setText("");
            txtAPR.setText("");
            txtTaxRate.setText("");
            txtTerms.setSelection(0);
        }

        return super.onOptionsItemSelected(item);
    }
}
