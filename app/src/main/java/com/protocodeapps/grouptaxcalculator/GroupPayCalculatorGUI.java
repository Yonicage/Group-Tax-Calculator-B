package com.protocodeapps.grouptaxcalculator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;

import com.protocodeapps.grouptaxcalculator.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class GroupPayCalculatorGUI extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav);

        bottomNavigationView = findViewById(R.id.bottom_nav);

        NavController navController = Navigation.findNavController(this, R.id.fragment_container);

        NavigationUI.setupWithNavController(bottomNavigationView, navController);


        // Initialize the BottomNavigationView

        /*
        bottomNavigationView.setSelectedItemId(R.id.menu_home); //Default selected buttonav button (Remember to change default fragment)

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) { //TODO: Add a money conversion fragment and an how to use tutorial fragment

                Fragment selected_fragment = null; // Initialize to null to get selected_fragment

                int id = item.getItemId(); // Get the ID of the selected item, it is an int

                if (id == R.id.menu_home) {
                    selected_fragment = new HomeFragment();
                }
                else if (id ==R.id.menu_percent) {
                    selected_fragment = new PercentageFragment();
                }
                else if (id ==R.id.menu_Settings) {
                    selected_fragment = new SettingsFragment();
                }

                if (selected_fragment != null) {
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container, selected_fragment).commit(); //Fragments replacement (powerfull!)
                }
                return true; //This is true to indicate the item selection
            }
        });
        //By default we show the home fragment
        if (savedInstanceState ==null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
        }
         */

    }
}
