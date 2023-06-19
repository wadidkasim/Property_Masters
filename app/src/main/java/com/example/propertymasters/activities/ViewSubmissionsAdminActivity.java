package com.example.propertymasters.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.propertymasters.R;
import com.example.propertymasters.SharedPreferenceManager;
import com.example.propertymasters.URLs;
import com.example.propertymasters.adapters.PropertySubmissionsListAdminRVAdapter;
import com.example.propertymasters.adapters.PropertySubmissionsListRVAdapter;
import com.example.propertymasters.models.PropertySubmission;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ViewSubmissionsAdminActivity extends AppCompatActivity {


    ImageButton backBtn;
    private ArrayList<PropertySubmission> propertySubmissionArrayList;
    private PropertySubmissionsListAdminRVAdapter propertySubmissionsListRVAdapter;
    private RecyclerView submissionRV;
    JsonObjectRequest jsonObjectRequest;
    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_submissions_admin);

        backBtn = findViewById(R.id.back);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ViewSubmissionsAdminActivity.this.finish();
            }
        });

        propertySubmissionArrayList = new ArrayList<>();
        submissionRV=findViewById(R.id.rv_submissions);


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(),RecyclerView.VERTICAL,false);
        submissionRV.setLayoutManager(linearLayoutManager);
        submissionJsonRequest(SharedPreferenceManager.getInstance(getApplicationContext()).getUser().getUserId());


    }

    public void submissionJsonRequest(int userId) {

        jsonObjectRequest = new JsonObjectRequest(URLs.URL_READ_SUBMISSION, new Response.Listener<JSONObject>() {

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


                    propertySubmissionsListRVAdapter = new PropertySubmissionsListAdminRVAdapter(propertySubmissionArrayList, getApplicationContext());
                    submissionRV.setAdapter(propertySubmissionsListRVAdapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e("Response", e.toString());
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Log.e("error","" +error.getMessage());
                Log.e("error","" +error);
            }
        });

        requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(jsonObjectRequest);


    }
}