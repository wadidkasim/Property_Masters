package com.example.propertymasters.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.propertymasters.R;

public class SubmissionDetailsActivity extends AppCompatActivity {


    ImageView propertyImage;
    ImageButton backBtn;
//    Button approve,reply;
    TextView name,price,location,description,email,phone,replyTV;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submission_details);

        propertyImage = findViewById(R.id.propertyImg);
        name = findViewById(R.id.name);
        price = findViewById(R.id.price);
        location = findViewById(R.id.location);
        description = findViewById(R.id.description);
        email = findViewById(R.id.email);
        phone = findViewById(R.id.phone);
        backBtn = findViewById(R.id.back);


        replyTV = findViewById(R.id.replyTV);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SubmissionDetailsActivity.this.finish();
            }
        });

        name.setText(getIntent().getStringExtra("name"));
        price.setText(getIntent().getStringExtra("price"));
        location.setText(getIntent().getStringExtra("location"));
        description.setText(getIntent().getStringExtra("description"));
        email.setText(getIntent().getStringExtra("email"));
        phone.setText(getIntent().getStringExtra("phone"));
        replyTV.setText(getIntent().getStringExtra("reply"));

        Glide.with(getApplicationContext())
                .load(getIntent().getStringExtra("image"))
                .centerCrop()
                .placeholder(R.drawable.placeholder)
                .into(propertyImage);

    }
}