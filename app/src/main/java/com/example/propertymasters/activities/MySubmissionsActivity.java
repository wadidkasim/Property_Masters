package com.example.propertymasters.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.propertymasters.R;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

public class MySubmissionsActivity extends AppCompatActivity {

    ExtendedFloatingActionButton addSubmissionBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_submissions);

        addSubmissionBtn = findViewById(R.id.add_submission_btn);

        addSubmissionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MySubmissionsActivity.this, AddEditSubmissionActivity.class);
                startActivity(intent);
            }
        });
    }
}