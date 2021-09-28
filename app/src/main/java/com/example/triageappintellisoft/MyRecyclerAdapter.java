package com.example.triageappintellisoft;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;
import java.util.List;


public class MyRecyclerAdapter extends RecyclerView.Adapter<MyRecyclerAdapter.ViewHolder> {

    private List<String> patientsList;

    public MyRecyclerAdapter(List<String> patientsList) {
        this.patientsList = patientsList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.patient_item_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //TODO set Text
//        holder.getPatientNameTV().setText("");
//        holder.getPatientNameTV().setText("");
//        holder.getAgeTV().setText("");

        //TODO set color
        if (position % 2 == 0) {

        } else {

        }
    }

    @Override
    public int getItemCount() {
        return patientsList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final MaterialTextView patientNameTV, ageTV, bmiTV;

        public ViewHolder(View view) {
            super(view);
            patientNameTV = (MaterialTextView) view.findViewById(R.id.full_name_tv);
            ageTV = (MaterialTextView) view.findViewById(R.id.age_tv);
            bmiTV = (MaterialTextView) view.findViewById(R.id.bmi_tv);
            //TODO add click listener here
        }

        public MaterialTextView getPatientNameTV() {
            return patientNameTV;
        }

        public MaterialTextView getAgeTV() {
            return ageTV;
        }

        public MaterialTextView getBmiTV() {
            return bmiTV;
        }
    }
}
