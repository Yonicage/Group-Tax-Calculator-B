package com.protocodeapps.grouptaxcalculator.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.protocodeapps.grouptaxcalculator.PersonActivity;
import com.protocodeapps.grouptaxcalculator.R;

import com.protocodeapps.grouptaxcalculator.models.Person;
import com.protocodeapps.grouptaxcalculator.models.Product;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class HomeFragment extends Fragment {

    private TextView outputTextView;
    private final List<Person> people = new ArrayList<>();
    private LinearLayout peopleContainer;




    private final ActivityResultLauncher<Intent> personLauncher =
            registerForActivityResult(
                    new ActivityResultContracts.StartActivityForResult(),
                    result -> {
                        if (result.getResultCode() == Activity.RESULT_OK
                                && result.getData() != null) {

                            Person person = (Person) result.getData()
                                    .getSerializableExtra("person");

                            if (person != null) {
                                people.add(person);


                                Toast.makeText(requireContext(),
                                        "Added " + person.getName(),
                                        Toast.LENGTH_SHORT).show();

                                displayPeople();

                                // Refresh your home screen display here.
                            }
                        }
                    }
            );


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);


        outputTextView = view.findViewById(R.id.outputTextView);//TO BE DELETED

        peopleContainer = view.findViewById(R.id.peopleContainer);

        Button addPersonButton = view.findViewById(R.id.addPersonButton);

        Button calculateButton = view.findViewById(R.id.calculateButton);
        Button clearButton = view.findViewById(R.id.clearAllFields);

        // Button actions
        calculateButton.setOnClickListener(v -> calculate());
        clearButton.setOnClickListener(v -> clearAllFields());
        addPersonButton.setOnClickListener(v -> addPerson());

        requireActivity().setTitle("Welcome to Group Pay Calculator");

        return view;
    }

    private void notDisplayPeople(){
        peopleContainer.removeAllViews();
    }

    private void displayPeople() {
        peopleContainer.removeAllViews();

        for (Person person : people) {
            Button personButton = new Button(requireContext());

            String productList = person.getProducts().stream()
                    .map(Product::getName)
                    .collect(java.util.stream.Collectors.joining(", "));

            personButton.setText(person.getName() + "\n" + productList);


            personButton.setOnClickListener(v -> {
                PopupMenu popup = new PopupMenu(requireContext(), personButton);

                popup.getMenu().add("Edit Person");
                popup.getMenu().add("Delete Person");

                popup.setOnMenuItemClickListener(item -> {
                    if (item.getTitle().equals("Edit Person")) {
                        Intent intent = new Intent(
                                requireContext(),
                                PersonActivity.class
                        );

                        intent.putExtra("person", person);
                        startActivity(intent);
                        return true;
                    }

                    if (item.getTitle().equals("Delete Person")) {
                        people.remove(person);
                        displayPeople();

                        Toast.makeText(
                                requireContext(),
                                person.getName() + " deleted",
                                Toast.LENGTH_SHORT
                        ).show();

                        return true;
                    }

                    return false;
                });

                popup.show();
            });
            personButton.setAllCaps(false);

            LinearLayout.LayoutParams params =
                    new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT
                    );

            params.setMargins(0, 8, 0, 8);
            personButton.setLayoutParams(params);

            peopleContainer.addView(personButton);
        }
    }

    private void addPerson() {
        Intent intent = new Intent(
                requireActivity(),
                PersonActivity.class
        );

        personLauncher.launch(intent);
    }

    private void calculate() {
        BigDecimal groupSubTotal = BigDecimal.ZERO;
        BigDecimal groupTax = BigDecimal.ZERO;
        BigDecimal groupPayTotal = BigDecimal.ZERO;
        BigDecimal groupTip = BigDecimal.ZERO;

        try {

            outputTextView.setText("");

            if (people.isEmpty()){
                outputTextView.setText("No people added yet");
                return;
            }

            for (Person person : people) {
                person.calculateSubTotal();
                person.calculateTotalPay();

                groupSubTotal = groupSubTotal.add(person.getSubTotal());
                groupPayTotal = groupPayTotal.add(person.getPayTotal());

                String productList = person.getProducts().stream()
                        .map(Product::getName)
                        .collect(java.util.stream.Collectors.joining(", "));

                outputTextView.append(
                         person.getName() + "\n"
                                + "Products: " + productList + "\n"
                                + "----------------------------------\n"
                                + "Subtotal:  $" + person.getSubTotal().setScale(2, RoundingMode.HALF_UP).toPlainString() + "\n"
                                + "Total Pay: $" + person.getPayTotal().setScale(2, RoundingMode.HALF_UP).toPlainString() + "\n" +
                                 "----------------------------------\n\n"

                );
            }

            outputTextView.append(
                    "==================================\n"
                            + "GROUP SUMMARY\n"
                            + "==================================\n"
                            + "Group Subtotal: $" + groupSubTotal.setScale(2, RoundingMode.HALF_UP).toPlainString() + "\n"
                            + "Group Total Pay: $" + groupPayTotal.setScale(2, RoundingMode.HALF_UP).toPlainString() + "\n"
            );

            notDisplayPeople();

        } catch (NumberFormatException |ArrayIndexOutOfBoundsException e) {
            outputTextView.setText("Error in input. Please make sure all fields are filled correctly and be sure to press enter for each product line");
        }
    }

    private void clearAllFields() {
        notDisplayPeople();
        people.clear();
        outputTextView.setText("");
    } //View model delete all people
}