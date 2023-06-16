package com.example.propertymasters.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.propertymasters.R;
import com.example.propertymasters.URLs;
import com.example.propertymasters.adapters.PropertyListRVAdapter;
import com.example.propertymasters.models.Property;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class HomeFragment extends Fragment {

    ChipGroup chipGroup;
    Chip chipHousing;
    Chip chipLand;

    private ArrayList<Property> propertyArrayList;
    private PropertyListRVAdapter propertyListRVAdapter;
    private RecyclerView propertyRV;
    JsonObjectRequest jsonObjectRequest;
    RequestQueue requestQueue;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        chipGroup = view.findViewById(R.id.chipGroup);
        chipHousing = view.findViewById(R.id.chipHousing);
        chipLand = view.findViewById(R.id.chipLand);
        chipHousing.setChecked(true);

        // Set click listener for the chips
        chipLand.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // Perform actions for the selected chip (fetch data, update UI, etc.)
                    //fetchDataForLandPropertyType();

                    propertyArrayList= new ArrayList<>();
                    propertyListRVAdapter.setData(propertyArrayList); // Update the adapter dataset

                    propertyRV=view.findViewById(R.id.rv_properties);

                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false);
                    propertyRV.setLayoutManager(linearLayoutManager);
                    propertyJsonRequest("Land");
                    // Deselect the other chip
                    chipHousing.setChecked(false);
                }
            }
        });

        chipHousing.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // Perform actions for the selected chip (fetch data, update UI, etc.)
                    //fetchDataForHousingPropertyType();

                    propertyArrayList= new ArrayList<>();
                    propertyListRVAdapter.setData(propertyArrayList); // Update the adapter dataset

                    propertyRV=view.findViewById(R.id.rv_properties);

                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false);
                    propertyRV.setLayoutManager(linearLayoutManager);
                    propertyJsonRequest("Housing");
                    // Deselect the other chip
                    chipLand.setChecked(false);
                }
            }
        });

        propertyArrayList= new ArrayList<>();



        propertyRV=view.findViewById(R.id.rv_properties);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false);
        propertyRV.setLayoutManager(linearLayoutManager);
        propertyJsonRequest("Housing");

        return view;
    }


    public void propertyJsonRequest(String propertyType) {

//        // Create a JSONObject to hold the additional data
//        JSONObject requestData = new JSONObject();
//        try {
//            requestData.put("propertyType", propertyType);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }

        jsonObjectRequest = new JsonObjectRequest(URLs.URL_READ_PROPERTY+"?propertyType="+propertyType, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {

                try {

                    JSONArray dataArray = response.getJSONArray("data");

                    for (int i = 0; i < dataArray.length(); i++) {
                        JSONObject jsonObject = dataArray.getJSONObject(i);
                        Property property = new Property(
                                jsonObject.getInt("PropertyID"),
                                jsonObject.getString("PropertyType"),
                                jsonObject.getString("Location"),
                                jsonObject.getInt("Price"),
                                jsonObject.getString("Name"),
                                jsonObject.getString("Description"),
                                (jsonObject.getInt("isApproved")==1)?true:false,
                                jsonObject.getString("ImageUrl"),
                                (jsonObject.getInt("isAvailable")==1)?true:false
                        );
                        propertyArrayList.add(property);
                        Log.e("Response", property.getName());

                    }


                    propertyListRVAdapter = new PropertyListRVAdapter(propertyArrayList, getContext());
                    propertyRV.setAdapter(propertyListRVAdapter);

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

        requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(jsonObjectRequest);


    }
}