package com.example.triageappintellisoft;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.triageappintellisoft.databinding.ActivityRegistrationPageBinding;

public class RegistrationPage extends AppCompatActivity {
    private ActivityRegistrationPageBinding binding;
    private String firstName,lastName,dob;
    private Gender gender;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegistrationPageBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

    }

    private void goToVitalsPage() {
        Intent intent = new Intent(RegistrationPage.this, VitalsForm.class);
        startActivity(intent);
    }
    private enum Gender{
        MALE("M"),
        FEMALE("F"),
        NO_SAY("N");
        private String genderInitials;
        private Gender(String genderInitials){
            this.genderInitials=genderInitials;
        }
        public String getGenderInitials() {
            return genderInitials;
        }
    }

}