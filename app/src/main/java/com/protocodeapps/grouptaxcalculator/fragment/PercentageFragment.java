package com.protocodeapps.grouptaxcalculator.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.protocodeapps.grouptaxcalculator.R;


public class PercentageFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private EditText percentInputof, numberInputof, numberInputiswhatof1, numberInputiswhatof2 , numberInputisofwhat1, numberInputisofwhat2;
    private TextView percentofANS, iswhatpercentofANS,ispercentofwhatANS;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_percentage, container, false);

        percentInputof = view.findViewById(R.id.percentInputof);
        numberInputof = view.findViewById(R.id.numberInputof);
        numberInputiswhatof1 = view.findViewById(R.id.numberInputiswhatof1);
        numberInputiswhatof2 = view.findViewById(R.id.numberInputiswhatof2);
        numberInputisofwhat1 = view.findViewById(R.id.numberInputisofwhat1);
        numberInputisofwhat2 = view.findViewById(R.id.numberInputisofwhat2);


        percentofANS = view.findViewById(R.id.percentofANS);
        iswhatpercentofANS = view.findViewById(R.id.iswhatpercentofANS);
        ispercentofwhatANS = view.findViewById(R.id.ispercentofwhatANS);

        Button percentofCalc = view.findViewById(R.id.percentofButton);
        Button iswhatpercentofCalc = view.findViewById(R.id.iswhatpercentofButton);
        Button ispercentofwhatCalc = view.findViewById(R.id.ispercentofwhatButton);

        //Button actions
        percentofCalc.setOnClickListener(v -> percentofCalc());
        iswhatpercentofCalc.setOnClickListener(v -> iswhatpercentofCalc());
        ispercentofwhatCalc.setOnClickListener(v -> ispercentofwhatCalc());

        requireActivity().setTitle("Percentage Calculator");



        return view;
    }
    private double getDouble(EditText et) {
        String text = et.getText().toString().trim();

        if (text.isEmpty()) {
            et.setError("Required");
            return 0; // or handle differently if needed
        }

        return Double.parseDouble(text);
    }

    @SuppressLint("DefaultLocale")
    private void percentofCalc() {
        double number1 =  getDouble(percentInputof);
        double number2 =  getDouble(numberInputof);
        double result =  number2*number1/100;
        percentofANS.setText(String.format("%.2f", result));
    }

    @SuppressLint("DefaultLocale")
    private void iswhatpercentofCalc() {
        double number1 =  getDouble(numberInputiswhatof1);
        double number2 =  getDouble(numberInputiswhatof2);
        double result = number1/number2*100;
        iswhatpercentofANS.setText(String.format("%.2f", result));
    }

    @SuppressLint("DefaultLocale")
    private void ispercentofwhatCalc() {
        double number1 =  getDouble(numberInputisofwhat1);
        double number2 =  getDouble(numberInputisofwhat2);
        double result = number1*number2;
        ispercentofwhatANS.setText(String.format("%.2f", result));
    }
}