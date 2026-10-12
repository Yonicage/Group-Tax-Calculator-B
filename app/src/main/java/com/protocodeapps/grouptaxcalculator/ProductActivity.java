package com.protocodeapps.grouptaxcalculator;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.protocodeapps.grouptaxcalculator.models.Product;

import java.math.BigDecimal;

public class ProductActivity extends AppCompatActivity {

    public EditText nameInput;
    public EditText taxInput;
    public EditText priceInput;



    public CheckBox preparedCheckBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        nameInput = findViewById(R.id.productNameInput);
        taxInput = findViewById(R.id.productTaxInput);
        priceInput = findViewById(R.id.productPriceInput);
        preparedCheckBox = findViewById(R.id.preparedCheckBox);

        Button saveButton = findViewById(R.id.saveProductButton);
        saveButton.setOnClickListener(v -> saveProduct());

        Button cancelButton = findViewById(R.id.cancelPersonButton);
        cancelButton.setOnClickListener(v -> finish());


        setTitle("Add Product");

    }

    public void saveProduct(){
        String name = nameInput.getText().toString().trim();
        String priceText = priceInput.getText().toString().trim();
        String taxText = taxInput.getText().toString().trim();

        if (name.isEmpty() || priceText.isEmpty()) {
            Toast.makeText(this,
                    "Enter a product name and price",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            BigDecimal price = new BigDecimal(priceText);

            if (price.signum() < 0) {
                Toast.makeText(this,
                        "Price cannot be negative",
                        Toast.LENGTH_SHORT).show();
                return;
            }

            price = price.setScale(
                    2,
                    java.math.RoundingMode.HALF_UP
            );

            BigDecimal tax;

            if (taxText.isEmpty()) {
                tax = new BigDecimal("0.115");
            } else {
                tax = new BigDecimal(taxText);
            }

            if (tax.signum() < 0) {
                Toast.makeText(this,
                        "Tax cannot be negative",
                        Toast.LENGTH_SHORT).show();
                return;
            }


            Product product = new Product(
                    name,
                    tax,
                    price,
                    preparedCheckBox.isChecked()
            );

            Intent result = new Intent();
            result.putExtra("product", product);

            setResult(Activity.RESULT_OK, result);
            finish();

        } catch (NumberFormatException e) {
            Toast.makeText(this,
                    "Enter a valid price",
                    Toast.LENGTH_SHORT).show();
        }

    }
}