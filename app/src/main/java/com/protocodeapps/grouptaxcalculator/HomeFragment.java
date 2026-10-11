package com.protocodeapps.grouptaxcalculator;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;



public class HomeFragment extends Fragment {

    private EditText tipInput, taxInput, productInput;
    private TextView outputTextView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // Initialize views using "view.findViewById"
        tipInput = view.findViewById(R.id.tipInput);
        taxInput = view.findViewById(R.id.taxInput);
        productInput = view.findViewById(R.id.productInput);
        outputTextView = view.findViewById(R.id.outputTextView);

        Button calculateButton = view.findViewById(R.id.calculateButton);
        Button clearButton = view.findViewById(R.id.clearAllFields);

        // Button actions
        calculateButton.setOnClickListener(v -> calculate());
        clearButton.setOnClickListener(v -> clearAllFields());

        requireActivity().setTitle("Group Pay Calculator");



        return view;
    }

    private void calculate() {

        try { //Get input values
            double taxPercentage;
            double  tipPercentage;

            //Total and output results initialize
            StringBuilder output = new StringBuilder(); //this will contain all of the output
            double totalGroupPay = 0; // Accumulate total pay here

            //initialise taxInput and tipInput: tax input=11.5 and tipInput=0
            if (taxInput.getText().toString().isEmpty()){
                taxPercentage=11.5/100;
            } else {
                taxPercentage=Double.parseDouble(taxInput.getText().toString())/100;
            }
            if (tipInput.getText().toString().isEmpty()){
                tipPercentage=0;
            } else {
                tipPercentage=Double.parseDouble(tipInput.getText().toString())/100;
            }

            String personName="Person: ";
            String[] productLines=productInput.getText().toString().split("\n"); //this divides the products per line (can be done better for users)
            //Get text is basically getting the text from the GUI side rather than a scanner.


            //Initialize person_pay array called persons
            person_pay[] persons = new person_pay[productLines.length];
            for (int i=0;i<productLines.length;i++){
                persons[i]=new person_pay();
            }



            //Process each person's product prices
            for (int i=0;i<productLines.length;i++){
                String[] productPrices=productLines[i].split("#");//examine individual prices PER LINE
                personName=productPrices[0];
                productPrices[0]="0"; //put 0 to cero to not have the String of the name in that position

                //add the product prices for total
                for (String price:productPrices){
                    double productPrice=Double.parseDouble(price.trim()); //trim is for spaces

                    persons[i].addPay(productPrice);

                }
                double personTotal = persons[i].getPay();
                double totalWithTax = personTotal + (personTotal*taxPercentage);
                double totalWithTip = personTotal + (personTotal*tipPercentage);
                double taxTot =totalWithTax-personTotal;
                double tipTot =totalWithTip-personTotal;
                double tip_taxTot =taxTot+totalWithTip;//////
                if (personTotal==0){
                    output.append("Please enter values: "+personName+"\n");
                    break;
                }

                totalGroupPay += personTotal; //calculates the total while calculating individual totals
                output.append(personName).append(": \n");
                output.append("Total: ").append(String.format("%.2f", personTotal)).append("\n");
                output.append("Tax: ").append(String.format("%.2f",taxTot)).append("\n");
                output.append("Total Taxed: ").append(String.format("%.2f", totalWithTax)).append("\n");

                //Skip print of total with tax and tip if tip is 0
                if (tipPercentage!=0){
                    output.append("Tip : ").append(String.format("%.2f",tipTot)).append("\n");
                    output.append("Total with tax and tip: ").append(String.format("%.2f",tip_taxTot)).append("\n\n");

                } else{
                    output.append("\n");
                }


            }
            //If there is only one person there is no need to print
            if(productLines.length>1){
                double groupPayWithTax =totalGroupPay + (totalGroupPay*taxPercentage);
                double groupPayWithTip =totalGroupPay + (totalGroupPay*tipPercentage);
                double taxTotG =groupPayWithTax-totalGroupPay;
                double tipTotG =groupPayWithTip-totalGroupPay;
                double tip_taxTotG =taxTotG+groupPayWithTip;//////
                output.append("___________________________________________________\n");
                output.append("Total for group: ").append(String.format("%.2f", totalGroupPay)).append("\n");
                output.append("The total group tax is: ").append(String.format("%.2f",taxTotG)).append("\n");
                output.append("Total for group with tax: ").append(String.format("%.2f", groupPayWithTax)).append("\n");
                output.append("");

                //If there is no tip there is no need to print
                if (tipPercentage!=0){
                    output.append("The total added tip is: ").append(String.format("%.2f",tipTotG)).append("\n");
                    output.append("Total for group with tax and tip: ").append(String.format("%.2f", tip_taxTotG)).append("\n");

                }
            }

            outputTextView.setText(output.toString());

        } catch (NumberFormatException |ArrayIndexOutOfBoundsException e) {
            outputTextView.setText("Error in input. Please make sure all fields are filled correctly and be sure to press enter for each product line");
        }
    }

    private void clearAllFields() {
        tipInput.setText("");
        taxInput.setText("");
        productInput.setText("");
        outputTextView.setText("");
    }
}