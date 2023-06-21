package com.example.propertymasters.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.propertymasters.R;
import com.example.propertymasters.SharedPreferenceManager;
import com.example.propertymasters.URLs;
import com.example.propertymasters.adapters.InquiryListRVAdapter;
import com.example.propertymasters.adapters.PropertySubmissionsListRVAdapter;
import com.example.propertymasters.models.Inquiry;
import com.example.propertymasters.models.PropertySubmission;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Timestamp;
import java.util.ArrayList;

public class MySubmissionsActivity extends AppCompatActivity {

    ExtendedFloatingActionButton addSubmissionBtn;

    private ProgressBar progressBar;
    ImageButton backBtn;
    private ArrayList<PropertySubmission> propertySubmissionArrayList;
    private PropertySubmissionsListRVAdapter propertySubmissionsListRVAdapter;
    private RecyclerView submissionRV;
    JsonObjectRequest jsonObjectRequest;
    RequestQueue requestQueue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_submissions);

        progressBar = findViewById(R.id.progressBar);
        backBtn = findViewById(R.id.back);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MySubmissionsActivity.this.finish();
            }
        });

        addSubmissionBtn = findViewById(R.id.add_submission_btn);

        addSubmissionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MySubmissionsActivity.this, AddEditSubmissionActivity.class);
                startActivity(intent);
            }
        });


        propertySubmissionArrayList = new ArrayList<>();
        submissionRV=findViewById(R.id.rv_submissions);


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(),RecyclerView.VERTICAL,false);
        submissionRV.setLayoutManager(linearLayoutManager);
        submissionJsonRequest(SharedPreferenceManager.getInstance(getApplicationContext()).getUser().getUserId());
    }

    public void submissionJsonRequest(int userId) {

        progressBar.setVisibility(View.VISIBLE);
        // Create a JSONObject to hold the additional data
        JSONObject requestData = new JSONObject();
        try {
            requestData.put("userID", userId);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, URLs.URL_READ_MINE_SUBMISSION, requestData, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.e("Response", response.toString());
                try {

                    JSONArray dataArray = response.getJSONArray("data");

                    for (int i = 0; i < dataArray.length(); i++) {
                        JSONObject jsonObject = dataArray.getJSONObject(i);
                        PropertySubmission propertySubmission = new PropertySubmission(
                                jsonObject.getInt("SubmissionID"),
                                jsonObject.getString("Status"),
                                jsonObject.getString("Reply"),
                                jsonObject.getInt("PropertyID"),
                                jsonObject.getString("PropertyType"),
                                jsonObject.getString("Location"),
                                jsonObject.getString("ImageUrl"),
                                jsonObject.getString("Name"),
                                jsonObject.getString("Description"),
                                jsonObject.getInt("Price"),
                                (jsonObject.getInt("isApproved") == 1 ? true : false ),
                                (jsonObject.getInt("isAvailable")== 1 ? true : false ),
                                jsonObject.getInt("UserID"),
                                jsonObject.getString("Phone"),
                                jsonObject.getString("Email")
                        );
                        propertySubmissionArrayList.add(propertySubmission);
                        Log.e("submission name", propertySubmission.getName());

                    }


                    propertySubmissionsListRVAdapter = new PropertySubmissionsListRVAdapter(propertySubmissionArrayList, getApplicationContext());
                    submissionRV.setAdapter(propertySubmissionsListRVAdapter);
                    progressBar.setVisibility(View.GONE);

                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e("Response", e.toString());
                    progressBar.setVisibility(View.GONE);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Log.e("error","" +error.getMessage());
                Log.e("error","" +error);
                progressBar.setVisibility(View.GONE);
            }
        });

        requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(jsonObjectRequest);


    }
}