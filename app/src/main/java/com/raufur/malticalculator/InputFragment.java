package com.raufur.malticalculator;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.common.base.Strings;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.Calendar;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link InputFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link InputFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class InputFragment extends android.app.Fragment {
    public static final String TAG_INPUT = "INPUT_FRAGMENT";
    Button btnCalculate;
    EditText txtHomeValue;
    EditText txtDownPayment;
    EditText txtAPR;
    Spinner txtTerms;
    EditText txtTaxRale;
    Double valHomeValue;
    Double valDownPayment;
    Double valAPR;
    Double valTerms;
    Double valTaxRate;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment InputFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static InputFragment newInstance(String param1, String param2) {
        InputFragment fragment = new InputFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public InputFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
           /* if (savedInstanceState != null) {
                valHomeValue = savedInstanceState.getDouble("HomeLoan");
                Log.d(TAG_INPUT, valHomeValue + "" + "Ankit");
                valDownPayment = savedInstanceState.getDouble("DownPaymet");
                valAPR = savedInstanceState.getDouble("APR");
                valTerms = savedInstanceState.getDouble("Term");
                valTaxRate = savedInstanceState.getDouble("TaxRate");
                generateViews();
            }*/
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_input, container, false);
        txtHomeValue = (EditText) view.findViewById(R.id.val_HomeValue);
        txtDownPayment = (EditText) view.findViewById(R.id.val_DownPayment);
        txtAPR = (EditText) view.findViewById(R.id.val_InterestRate);
        txtTerms = (Spinner) view.findViewById(R.id.val_Terms);
        txtTaxRale = (EditText) view.findViewById(R.id.val_PropertyTax);
        btnCalculate = (Button) view.findViewById(R.id.button);

        btnCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager inputManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);

                inputManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
                buttonCalculateOnClick(v);
            }
        });
        return view;
    }

    private void buttonCalculateOnClick(View v) {
        if (validateInputValues()) {
            Log.i(TAG_INPUT, "got all value " + valHomeValue + " - " + valDownPayment + " - " + valAPR +
                    " - " + valTerms + " - " + valTaxRate);
            calculateMortgageValues();
        }
    }

    public boolean validateInputValues() {
        if (!Strings.isNullOrEmpty(txtHomeValue.getText().toString())) {
            valHomeValue = Double.parseDouble(txtHomeValue.getText().toString());
        } else {
            txtHomeValue.setError("Home Value cannot be blank");
            return false;
        }

        if (!Strings.isNullOrEmpty(txtDownPayment.getText().toString())) {
            valDownPayment = Double.parseDouble(txtDownPayment.getText().toString());
        } else {
            txtDownPayment.setError("Down Payment cannot be blank");
            return false;
        }

        if (!Strings.isNullOrEmpty(txtAPR.getText().toString())) {
            valAPR = Double.parseDouble(txtAPR.getText().toString());
        } else {
            txtAPR.setError("APR cannot be blank");
            return false;
        }

        if (!Strings.isNullOrEmpty(txtTaxRale.getText().toString())) {
            valTaxRate = Double.parseDouble(txtTaxRale.getText().toString());
        } else {
            txtTaxRale.setError("Tax Rate cannot be blank");
            return false;
        }

        valTerms = Double.parseDouble(txtTerms.getSelectedItem().toString());
        return true;

    }

    public void calculateMortgageValues() {
        double N = valTerms * 12;
        double P = valHomeValue - valDownPayment;
        double totalPropertyTax = valHomeValue * valTaxRate * valTerms / 100;

        double monthlyPropertyTax = totalPropertyTax / N;

        double i = valAPR / 1200;
        double tt = Math.pow(1 + i, N);
        double num = P * i * tt;
        double den = tt - 1;
        double monthlyPayment = num / den;
        double totalInterestPaid = monthlyPayment + monthlyPropertyTax;
        double totalMonthlyPayment = (totalInterestPaid * N) - P - totalPropertyTax;

        Calendar cal = Calendar.getInstance();

        cal.add(Calendar.MONTH, (int) N);

        String date = new SimpleDateFormat("MMM").format(cal.getTime()) + ", " +
                new SimpleDateFormat("yyyy").format(cal.getTime());


        android.app.Fragment fragment2 = new OutputFragment();
        FragmentManager fm = getFragmentManager();
        android.app.FragmentTransaction ft = fm.beginTransaction();
        Bundle args = new Bundle();
        args.putString("TPT", round(totalPropertyTax, 2) + "");
        args.putString("TMP", round(totalMonthlyPayment, 2) + "");
        args.putString("TIP", round(totalInterestPaid, 2) + "");
        args.putString("DATE", date);
        fragment2.setArguments(args);
        ft.replace(R.id.Output_Fragment, fragment2);
        ft.commit();
    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }


    public void onRestoreInstanceState() {

    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        // Save UI state changes to the savedInstanceState.
        // This bundle will be passed to onCreate if the process is
        // killed and restarted.

       /* if (!Strings.isNullOrEmpty(txtHomeValue.getText().toString())) {
            savedInstanceState.putDouble("HomeLoan", Double.parseDouble(txtHomeValue.getText().toString()));
        }
        if (!Strings.isNullOrEmpty(txtDownPayment.getText().toString())) {
            savedInstanceState.putDouble("DownPaymet", Double.parseDouble(txtDownPayment.getText().toString()));
        }
        if (!Strings.isNullOrEmpty(txtAPR.getText().toString())) {
            savedInstanceState.putDouble("APR", Double.parseDouble(txtAPR.getText().toString()));
        }
        if (txtTerms.getSelectedItem() !=  null) {
            savedInstanceState.putDouble("Term", Double.parseDouble(txtTerms.getSelectedItem().toString()));
        }
        if (!Strings.isNullOrEmpty(txtTaxRale.getText().toString())) {
            savedInstanceState.putDouble("TaxRate", Double.parseDouble(txtTerms.getSelectedItem().toString()));
        }
*/
        // etc.
    }

}


