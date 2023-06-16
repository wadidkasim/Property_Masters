package com.example.propertymasters.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.propertymasters.R;
import com.example.propertymasters.SharedPreferenceManager;
import com.example.propertymasters.activities.MyInquiriesActivity;
import com.example.propertymasters.activities.MySubmissionsActivity;
import com.example.propertymasters.activities.WelcomeActivity;
import com.example.propertymasters.models.User;


public class ProfileFragment extends Fragment {

    Button logoutBtn;
    TextView usernameTV,emailTV,phoneTV;
    CardView myInquiries, mySubmissions;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        logoutBtn = view.findViewById(R.id.logout);

        usernameTV = view.findViewById(R.id.username);
        emailTV = view.findViewById(R.id.email);
        phoneTV = view.findViewById(R.id.phone);

        myInquiries = view.findViewById(R.id.my_inquiries);
        mySubmissions = view.findViewById(R.id.my_submissions);

        User user = SharedPreferenceManager.getInstance(getContext()).getUser();
        usernameTV.setText(user.getUserName());
        emailTV.setText(user.getEmail());
        phoneTV.setText(user.getPhone());

        myInquiries.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), MyInquiriesActivity.class);
                startActivity(intent);
            }
        });

        mySubmissions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), MySubmissionsActivity.class);
                startActivity(intent);
            }
        });

        logoutBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                SharedPreferenceManager.getInstance(getContext()).logout();
                Intent intent = new Intent(getContext(), WelcomeActivity.class);
            }
        });
        return view;
    }
}