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
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textview.MaterialTextView;

public class RegistrationPage extends AppCompatActivity {
    private ActivityRegistrationPageBinding binding;
    private MaterialTextView currentDateTV;
    private TextInputEditText firstNameEditText, lastNameEditText, dayEditText, monthEditText, yearEditText, genderEditText;
    private String firstName, lastName, dob;
    private Gender gender;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegistrationPageBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        currentDateTV = binding.currentDateTv;
        firstNameEditText = binding.textInputEditTextFirstName;
        lastNameEditText = binding.textInputEditTextLastName;
        dayEditText = binding.dayEditText;
        monthEditText = binding.monthEditText;
        yearEditText = binding.yearEditText;
        genderEditText = binding.genderEditText;

    }

    private void goToVitalsPage() {
        Intent intent = new Intent(RegistrationPage.this, VitalsForm.class);
        startActivity(intent);
    }

    private enum Gender {
        MALE("M"),
        FEMALE("F"),
        NO_SAY("N");
        private String genderInitials;

        private Gender(String genderInitials) {
            this.genderInitials = genderInitials;
        }

        public String getGenderInitials() {
            return genderInitials;
        }
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