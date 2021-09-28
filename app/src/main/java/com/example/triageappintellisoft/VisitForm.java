package com.example.triageappintellisoft;

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
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textview.MaterialTextView;

public class VisitForm extends AppCompatActivity {
    private ActivityVisitFormBinding binding;
    private RadioGroup dietHistoryRadioGroup;
    private RadioGroup healthRadioGroup;
    private TextInputEditText textInputEditText;
    private MaterialTextView currentDateTV;

    private HealthStatus healthStatus = HealthStatus.GOOD;
    private DietHistory dietHistory = DietHistory.NO;
    private String comments = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityVisitFormBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        Intent intent = getIntent();
        String formTitle = intent.getStringExtra("FORM_TITLE");

        currentDateTV = binding.currentDateTv;

        dietHistoryRadioGroup = binding.dietHistoryRadios;
        healthRadioGroup = binding.heathStatusRadios;
        textInputEditText = binding.commentsEditText;

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
                //TODO
                // save to server then go to next page
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
