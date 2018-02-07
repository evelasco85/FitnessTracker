package com.codeflowcrafter.FitnessTracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.codeflowcrafter.FitnessTracker.Profile.Activity_Profile_Main;

public class DashboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Button btnProfiles = (Button)findViewById(R.id.btnProfiles);
        Button btnExercises = (Button)findViewById(R.id.btnExercises);

        btnProfiles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenProfiles();
            }
        });

        btnExercises.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenExercises();
            }
        });
    }

    void OpenProfiles(){
        Intent amountListIntent = new Intent(this, Activity_Profile_Main.class);

        this.startActivity(amountListIntent);
    }

    void OpenExercises(){
        Intent amountListIntent = new Intent(this, com.codeflowcrafter.FitnessTracker.Exercise.Activity_Main.class);

        this.startActivity(amountListIntent);
    }
}
