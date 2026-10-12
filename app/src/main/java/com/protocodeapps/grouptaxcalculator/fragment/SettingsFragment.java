package com.protocodeapps.grouptaxcalculator.fragment;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;

import androidx.fragment.app.Fragment;


public class SettingsFragment extends Fragment {

    private Switch switchDarkMode;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(com.protocodeapps.grouptaxcalculator.R.layout.fragment_settings, container, false);

        switchDarkMode = view.findViewById(com.protocodeapps.grouptaxcalculator.R.id.switchDarkMode);

        SharedPreferences prefs =
                PreferenceManager.getDefaultSharedPreferences(getContext());

        switchDarkMode.setChecked(prefs.getBoolean("dark_mode", false));

        switchDarkMode.setOnCheckedChangeListener((buttonView, isChecked) -> {
            prefs.edit().putBoolean("dark_mode", isChecked).apply();
        });

        requireActivity().setTitle("Settings");

        return view;
    }
}