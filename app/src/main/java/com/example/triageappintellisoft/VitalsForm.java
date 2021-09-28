package com.example.triageappintellisoft;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.triageappintellisoft.databinding.ActivityRegistrationPageBinding;
import com.example.triageappintellisoft.databinding.ActivityVitalsFormBinding;
import com.google.android.material.textfield.TextInputEditText;


public class VitalsForm extends AppCompatActivity {
    private ActivityVitalsFormBinding binding;
    private TextInputEditText dayEditText,monthEditText,yearEditText,heightEditText,weightEditText,bmiTextView;

    public static final String FORM_TITLE = "com.example.triageappintellisoft.FORM_TITLE";
    private double bmi,height,weight;
    private String dob;
    public static final double OVERWEIGHT = 25.0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityVitalsFormBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
    }

    private void goTOVisitForms() {
        Intent intent = new Intent(VitalsForm.this, VisitForm.class);
        String formTitle=(bmi >= OVERWEIGHT)?"FORM B - OVERWEIGHT":"FORM A";
        intent.putExtra(FORM_TITLE, "formTitle");
        startActivity(intent);
    }


}