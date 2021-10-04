package com.example.triageappintellisoft;

import static com.example.triageappintellisoft.VitalsForm.FORM_TITLE;
import static com.example.triageappintellisoft.VitalsForm.PATIENT_PK;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioGroup;

import com.example.triageappintellisoft.databinding.ActivityVisitFormBinding;
import com.example.triageappintellisoft.models.Visit;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textview.MaterialTextView;
import com.google.gson.Gson;

public class VisitForm extends AppCompatActivity {
    private ActivityVisitFormBinding binding;
    private RadioGroup dietHistoryRadioGroup;
    private RadioGroup healthRadioGroup;
    private TextInputEditText commentsEditText;
    private MaterialTextView currentDateTV;

    private HealthStatus healthStatus = HealthStatus.GOOD;
    private DietHistory dietHistory = DietHistory.NO;
    private String comments = "";
    private String formTitle;
    private int patientPK;
    private String dietHistoryString, healthStatusString;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityVisitFormBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        Intent intent = getIntent();
        formTitle = intent.getStringExtra(FORM_TITLE);
        //TODO reset the activity label here

        patientPK = intent.getIntExtra(PATIENT_PK, 1000000);
        currentDateTV = binding.currentDateTv;

        dietHistoryRadioGroup = binding.dietHistoryRadios;
        healthRadioGroup = binding.heathStatusRadios;
        commentsEditText = binding.commentsEditText;

        dietHistoryRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.radio_yes:
                        dietHistory = DietHistory.YES;
                        break;
                    case R.id.radio_no:
                        dietHistory = DietHistory.NO;
                        break;
//                    default:
//                        break;
                }
            }
        });
        healthRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.radio_good:
                        healthStatus = HealthStatus.GOOD;
                        break;
                    case R.id.radio_bad:
                        healthStatus = HealthStatus.BAD;
                        break;
//                    default:
//                        break;
                }
            }
        });


    }

    private enum HealthStatus {
        GOOD("G"),
        BAD("B");
        private String statusInitials;

        private HealthStatus(String statusInitials) {
            this.statusInitials = statusInitials;
        }

        public String getHealthStatusInitials() {
            return statusInitials;
        }
    }

    private enum DietHistory {
        YES("Y"),
        NO("N");
        private String dietHistoryInitials;

        private DietHistory(String dietHistoryInitials) {
            this.dietHistoryInitials = dietHistoryInitials;
        }

        public String getDietHistoryInitials() {
            return dietHistoryInitials;
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
                saveToDB();
                Intent intent = new Intent(VisitForm.this, MainActivity.class);
                startActivity(intent);
                finish();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void saveToDB() {
        comments = commentsEditText.getText().toString();
        dietHistoryString = dietHistory.getDietHistoryInitials();
        healthStatusString = healthStatus.getHealthStatusInitials();
        Visit visit = new Visit(healthStatusString, dietHistoryString, patientPK, comments);

        Gson gson = new Gson();
        String json = gson.toJson(visit);

        //TODO serialize and post
    }

}
