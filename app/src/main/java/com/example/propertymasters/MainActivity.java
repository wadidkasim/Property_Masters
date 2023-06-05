package com.example.propertymasters;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.propertymasters.activities.LoginActivity;

public class MainActivity extends AppCompatActivity {

    Button logoutBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        logoutBtn = findViewById(R.id.button);
        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //storing the user in shared preferences
                SharedPreferenceManager.getInstance(getApplicationContext()).logout();
                finish();
            }
        });
    }
}