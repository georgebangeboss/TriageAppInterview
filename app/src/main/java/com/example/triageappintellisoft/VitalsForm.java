package com.example.triageappintellisoft;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.example.triageappintellisoft.databinding.ActivityRegistrationPageBinding;
import com.example.triageappintellisoft.databinding.ActivityVitalsFormBinding;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textview.MaterialTextView;


public class VitalsForm extends AppCompatActivity {
    private ActivityVitalsFormBinding binding;
    private TextInputEditText heightEditText, weightEditText;
    private MaterialTextView currentDateTV, bmiTV;

    public static final String FORM_TITLE = "com.example.triageappintellisoft.FORM_TITLE";
    private double bmi, height, weight;
    private String dob;
    public static final double OVERWEIGHT = 25.0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityVitalsFormBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);


        currentDateTV = binding.currentDateTv;
        bmiTV = binding.bmiTv;
        heightEditText = binding.textInputEditTextHeight;
        weightEditText = binding.textInputEditTextWeight;
    }

    private void goToVisitForms() {
        Intent intent = new Intent(VitalsForm.this, VisitForm.class);
        String formTitle = (bmi >= OVERWEIGHT) ? "FORM B - OVERWEIGHT" : "FORM A";
        intent.putExtra(FORM_TITLE, "formTitle");
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.appbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_save:
                //TODO
                // save to server then go to next page
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}