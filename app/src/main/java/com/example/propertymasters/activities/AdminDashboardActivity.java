package com.example.propertymasters.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.propertymasters.R;
import com.example.propertymasters.SharedPreferenceManager;

public class AdminDashboardActivity extends AppCompatActivity {

    TextView logoutTV;
    CardView addPropertyCard, viewPropertyCard,viewInquiriesCard, viewSubmissionsCard;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);

        logoutTV = findViewById(R.id.logout);
        addPropertyCard = findViewById(R.id.add_property);
        viewPropertyCard = findViewById(R.id.view_properties);
        viewInquiriesCard = findViewById(R.id.view_inquiries);
        viewSubmissionsCard = findViewById(R.id.view_submissions);

        logoutTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferenceManager.getInstance(getApplicationContext()).logout();
                startActivity(new Intent(AdminDashboardActivity.this,WelcomeActivity.class));
            }
        });

        addPropertyCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminDashboardActivity.this,AdminAddPropertyActivity.class));
            }
        });

        viewPropertyCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminDashboardActivity.this,AdminViewPropertyActivity.class));
            }
        });

        viewInquiriesCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminDashboardActivity.this,ViewInquiriesActivity.class));
            }
        });

        viewSubmissionsCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminDashboardActivity.this,ViewSubmissionsAdminActivity.class));
            }
        });
    }
}