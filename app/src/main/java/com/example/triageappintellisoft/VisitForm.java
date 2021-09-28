package com.example.triageappintellisoft;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioGroup;

import com.example.triageappintellisoft.databinding.ActivityVisitFormBinding;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class VisitForm extends AppCompatActivity {
    private ActivityVisitFormBinding binding;
    private RadioGroup drugRadioGroup;
    private RadioGroup healthRadioGroup;
    private MaterialButton saveBtn;
    private TextInputEditText textInputEditText;

    private HealthStatus healthStatus=HealthStatus.GOOD;
    private DrugHistory drugHistory=DrugHistory.NO;
    private String comments="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityVisitFormBinding.inflate(getLayoutInflater());
        View view= binding.getRoot();
        setContentView(view);
        Intent intent=getIntent();
        String formTitle=intent.getStringExtra("FORM_TITLE");

        drugRadioGroup=binding.drugsRadios;
        healthRadioGroup=binding.heathStatusRadios;
        saveBtn=binding.saveBtn;
        textInputEditText=binding.commentsEditText;

        drugRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch(i){
                    case R.id.radio_yes:
                        drugHistory=DrugHistory.YES;
                        break;
                    case R.id.radio_no:
                        drugHistory=DrugHistory.NO;
                        break;
//                    default:
//                        break;
                }
            }
        });
        healthRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch(i){
                    case R.id.radio_good:
                        healthStatus=HealthStatus.GOOD;
                        break;
                    case R.id.radio_bad:
                        healthStatus=HealthStatus.BAD;
                        break;
//                    default:
//                        break;
                }
            }
        });

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                comments=textInputEditText.getText().toString();
                //TODO send POST request
                //TODO show activity main
            }
        });

    }
    private enum HealthStatus{
        GOOD("G"),
        BAD("B");
        private String statusInitials;
        private HealthStatus(String statusInitials){
            this.statusInitials=statusInitials;
        }
        public String getHealthStatusInitials() {
            return statusInitials;
        }
    }
    private enum DrugHistory{
        YES("Y"),
        NO("N");
        private String drugHistoryInitials;
        private DrugHistory(String drugHistoryInitials){
            this.drugHistoryInitials=drugHistoryInitials;
        }
        public String getDrugHistoryInitials() {
            return drugHistoryInitials;
        }
    }

}
