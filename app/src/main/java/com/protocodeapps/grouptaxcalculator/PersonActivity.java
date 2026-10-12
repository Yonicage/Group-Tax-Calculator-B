package com.protocodeapps.grouptaxcalculator;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.protocodeapps.grouptaxcalculator.models.Person;
import com.protocodeapps.grouptaxcalculator.models.Product;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class PersonActivity extends AppCompatActivity {

    private EditText personNameInput;
    private EditText personTipInput;
    private LinearLayout productsContainer;
    private TextView emptyProductsText;

    private final List<Product> products = new ArrayList<>();

    private final ActivityResultLauncher<Intent> productLauncher =
            registerForActivityResult(
                    new ActivityResultContracts.StartActivityForResult(),
                    result -> {
                        if (result.getResultCode() == Activity.RESULT_OK
                                && result.getData() != null) {

                            Product product =
                                    (Product) result.getData()
                                            .getSerializableExtra("product");

                            if (product != null) {
                                products.add(product);
                                displayProducts();
                            }
                        }
                    }
            );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person);

        personNameInput = findViewById(R.id.personNameInput);
        personTipInput = findViewById(R.id.personTipInput);
        productsContainer = findViewById(R.id.productsContainer);
        emptyProductsText = findViewById(R.id.emptyProductsText);

        Button addProductButton = findViewById(R.id.addProductButton);
        Button savePersonButton = findViewById(R.id.savePersonButton);
        Button cancelPersonButton = findViewById(R.id.cancelPersonButton);

        setTitle("Add Person");

        addProductButton.setOnClickListener(v -> {
            Intent intent = new Intent(
                    PersonActivity.this,
                    ProductActivity.class
            );
            productLauncher.launch(intent);
        });

        savePersonButton.setOnClickListener(v -> savePerson());

        cancelPersonButton.setOnClickListener(v -> finish());
    }

    private void displayProducts() {
        productsContainer.removeAllViews();

        emptyProductsText.setVisibility(
                products.isEmpty() ? TextView.VISIBLE : TextView.GONE
        );

        for (Product product : products) {
            TextView item = new TextView(this);

            String preparedText = product.getPrepared()
                    ? "Prepared"
                    : "Not prepared";

            item.setText(
                    product.getName()
                            + " - $"
                            + product.getPrice().toPlainString()
                            + " (" + preparedText + ")"
            );

            item.setPadding(8, 12, 8, 12);
            productsContainer.addView(item);
        }
    }

    private void savePerson() {
        String name = personNameInput.getText()
                .toString().trim();

        String tipText = personTipInput.getText().toString().trim();


        if (name.isEmpty()) {
            personNameInput.setError("Enter a name");
            return;
        }

        if (products.isEmpty()) {
            Toast.makeText(this,
                    "Add at least one product",
                    Toast.LENGTH_SHORT).show();
            return;
        }
        try {
        BigDecimal tip; //Try catch needed here
        if (tipText.isEmpty()) {
            tip = new BigDecimal("0");
        } else {
            tip = new BigDecimal(tipText);

            tip = tip.setScale(
                    2,
                    java.math.RoundingMode.HALF_UP
            );
        }


        if (tip.signum() < 0) {
            Toast.makeText(this,
                    "Tip cannot be negative",
                    Toast.LENGTH_SHORT).show();
            return;
        }



        Person person = new Person(name,tip);

        for (Product product : products) {
            person.addProduct(product);
        }



        Intent result = new Intent();
        result.putExtra("person", person);

        setResult(Activity.RESULT_OK, result);
        finish();

        } catch (NumberFormatException e) {
            Toast.makeText(this,
                    "Enter a valid tip",
                    Toast.LENGTH_SHORT).show();
        }
    }
}
