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
import com.example.propertymasters.adapters.InquiryListRVAdapter;
import com.example.propertymasters.adapters.PropertyListRVAdapter;
import com.example.propertymasters.models.Inquiry;
import com.example.propertymasters.models.Property;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.util.ArrayList;

public class MyInquiriesActivity extends AppCompatActivity {

    ImageButton backBtn;
    private ArrayList<Inquiry> inquiryArrayList;
    private InquiryListRVAdapter inquiryListRVAdapter;
    private RecyclerView inquiryRV;
    JsonObjectRequest jsonObjectRequest;
    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_inquiries);

        backBtn = findViewById(R.id.back);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyInquiriesActivity.this.finish();
            }
        });

        inquiryArrayList= new ArrayList<>();
        inquiryRV=findViewById(R.id.rv_inquiries);


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(),RecyclerView.VERTICAL,false);
        inquiryRV.setLayoutManager(linearLayoutManager);
        inquiryJsonRequest(SharedPreferenceManager.getInstance(getApplicationContext()).getUser().getUserId());
    }

    public void inquiryJsonRequest(int userId) {

        // Create a JSONObject to hold the additional data
        JSONObject requestData = new JSONObject();
        try {
            requestData.put("userId", userId);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, URLs.URL_READ_MINE_INQUIRY, requestData, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {

                try {

                    JSONArray dataArray = response.getJSONArray("data");

                    for (int i = 0; i < dataArray.length(); i++) {
                        JSONObject jsonObject = dataArray.getJSONObject(i);
                        Inquiry inquiry = new Inquiry(
                                jsonObject.getInt("InquiryID"),
                                jsonObject.getString("Message"),
                                jsonObject.getInt("PropertyID"),
                                Timestamp.valueOf(jsonObject.getString("Timestamp")),
                                jsonObject.getString("Email"),
                                jsonObject.getString("Phone"),
                                jsonObject.getString("Status"),
                                jsonObject.getInt("UserID"),
                                jsonObject.getString("Name"),
                                jsonObject.getString("Description"),
                                jsonObject.getString("ImageUrl"),
                                jsonObject.getString("Location"),
                                jsonObject.getInt("Price"),
                                jsonObject.getString("PropertyType")
                        );
                        inquiryArrayList.add(inquiry);
                        Log.e("Response", inquiry.getMessage());

                    }


                    inquiryListRVAdapter = new InquiryListRVAdapter(inquiryArrayList, getApplicationContext());
                    inquiryRV.setAdapter(inquiryListRVAdapter);

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
            }
        });

        requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(jsonObjectRequest);


    }
}