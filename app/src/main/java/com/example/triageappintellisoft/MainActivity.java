package com.example.triageappintellisoft;

import static com.example.triageappintellisoft.VitalsForm.PATIENT_PK;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.triageappintellisoft.databinding.ActivityMainBinding;
import com.example.triageappintellisoft.databinding.ActivityRegistrationPageBinding;
import com.google.android.material.datepicker.MaterialTextInputPicker;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private MaterialTextView currentDateTV;
    private FloatingActionButton addFab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityMainBinding.inflate(getLayoutInflater());
        View view= binding.getRoot();
        setContentView(view);

        currentDateTV=binding.currentDateTv;
        addFab=binding.addPatientButton;
        addFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, RegistrationPage.class);
                startActivity(intent);
                finish();
            }
        });

        //TODO get patient details from api and write the list below

        List<String> patientsList = new ArrayList<>();
        MyRecyclerAdapter myAdapter=new MyRecyclerAdapter(patientsList);
        RecyclerView rv=binding.patientsRecyclerView;
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(myAdapter);
    }
}
