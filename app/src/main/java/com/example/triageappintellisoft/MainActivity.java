package com.example.triageappintellisoft;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.example.triageappintellisoft.databinding.ActivityMainBinding;
import com.example.triageappintellisoft.databinding.ActivityRegistrationPageBinding;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityMainBinding.inflate(getLayoutInflater());
        View view= binding.getRoot();
        setContentView(view);

        //TODO get patient details from api and remove the list below
        List<String> patientsList = new ArrayList<>();
        MyRecyclerAdapter myAdapter=new MyRecyclerAdapter(patientsList);
        RecyclerView rv=binding.patientsRecyclerView;
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(myAdapter);
    }
}
