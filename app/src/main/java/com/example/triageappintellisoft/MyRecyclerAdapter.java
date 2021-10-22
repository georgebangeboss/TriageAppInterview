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

    private List<String> patientsNames;
    private List<String> patientsAges;
    private List<String> patientsBMIs;

    public MyRecyclerAdapter(List<String> patientsNames,
                             List<String> patientsAges,
                             List<String> patientsBMIs) {
        this.patientsNames = patientsNames;
        this.patientsAges = patientsAges;
        this.patientsBMIs = patientsBMIs;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.patient_item_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.getPatientNameTV().setText(patientsNames.get(position));
        holder.getBmiTV().setText(patientsBMIs.get(position));
        holder.getAgeTV().setText(patientsAges.get(position));

        //TODO set color
        if (position % 2 == 0) {

        } else {

        }
    }

    @Override
    public int getItemCount() {
        return patientsNames.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final MaterialTextView patientNameTV, ageTV, bmiTV;

        public ViewHolder(View view) {
            super(view);
            patientNameTV = (MaterialTextView) view.findViewById(R.id.full_name_tv);
            ageTV = (MaterialTextView) view.findViewById(R.id.age_tv);
            bmiTV = (MaterialTextView) view.findViewById(R.id.bmi_tv);
            //TODO add click listener here to Edit
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
