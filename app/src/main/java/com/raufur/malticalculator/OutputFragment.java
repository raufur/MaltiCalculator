package com.raufur.malticalculator;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

public class OutputFragment extends android.app.Fragment {

    public static final String TAG = "OUTPUT_TAG";
    EditText txtTaxPaid;
    EditText txtInterestPaid;
    EditText txtMonthlyPayment;
    EditText txtPayOffDate;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public static OutputFragment newInstance(String param1, String param2) {
        OutputFragment fragment = new OutputFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public OutputFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_output, container, false);
        String strtext = getArguments().getString("TPT");
        String strtext2 = getArguments().getString("TMP");
        String strtext3 = getArguments().getString("TIP");
        String date = getArguments().getString("DATE");

        txtTaxPaid = (EditText) view.findViewById(R.id.val_TextPaid);
        txtInterestPaid = (EditText) view.findViewById(R.id.val_InterestPaid);
        txtMonthlyPayment = (EditText) view.findViewById(R.id.val_Monthly_Payment);
        txtPayOffDate = (EditText) view.findViewById(R.id.val_Pay_Off_Date);
        txtTaxPaid.setEnabled(false);
        txtInterestPaid.setEnabled(false);
        txtMonthlyPayment.setEnabled(false);
        txtPayOffDate.setEnabled(false);

        txtTaxPaid.setText("");
        txtInterestPaid.setText("");
        txtMonthlyPayment.setText("");
        txtPayOffDate.setText("");

        txtTaxPaid.setText(strtext);
        txtInterestPaid.setText(strtext2);
        txtMonthlyPayment.setText(strtext3);
        txtPayOffDate.setText(date);
        return view;
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

}