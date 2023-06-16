package com.example.propertymasters.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.propertymasters.AppHelper;
import com.example.propertymasters.R;
import com.example.propertymasters.SharedPreferenceManager;
import com.example.propertymasters.URLs;
import com.example.propertymasters.VolleyMultipartRequest;
import com.example.propertymasters.VolleySingleton;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class AddEditSubmissionActivity extends AppCompatActivity {

    TextInputLayout propertyTypeTIL,nameTIL, descriptionTIL, priceTIL, locationTIL, isApproved;
    ImageView imageView;
    Button submit;
    AutoCompleteTextView autoCompleteTextView;
    private static final int PICK_IMAGE_REQUEST = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_submission);


        autoCompleteTextView = findViewById(R.id.propertyTypeAutoComplete);
        nameTIL= findViewById(R.id.nameTIL);
        descriptionTIL =findViewById(R.id.descriptionTIL);
        priceTIL = findViewById(R.id.priceTIL);
        locationTIL = findViewById(R.id.locationTIL);
        submit = findViewById(R.id.submit);
        // Create an array of values
        String[] propertyTypes = {"Land", "Housing"};

        // Create an ArrayAdapter and set it as the adapter for the AutoCompleteTextView
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, propertyTypes);
        autoCompleteTextView.setAdapter(adapter);

        imageView = findViewById(R.id.imageView);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, PICK_IMAGE_REQUEST);

            }
        });

        submit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                createProperty();
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri imageUri = data.getData();
            // Perform further actions with the selected image
            // For example, you can display the image in an ImageView or upload it to a server
            imageView.setImageURI(imageUri);
        }
    }

    private void createProperty() {

        //getting the tag from the edittext
        final String nameString = nameTIL.getEditText().getText().toString();
        final String descriptionString = descriptionTIL.getEditText().getText().toString();
        final String propertyTypeString = autoCompleteTextView.getText().toString();
        final String locationString = locationTIL.getEditText().getText().toString();
        final int price = Integer.parseInt(priceTIL.getEditText().getText().toString());
        final int isApproved = 0;

        //our custom volley request
        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, URLs.URL_CREATE_PROPERTY_SUB,
                new Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response) {


                        try {
                            Log.i("tagconvertstr", "["+new String(response.data)+"]");
                            JSONObject obj = new JSONObject(new String(response.data));

                            Toast.makeText(getApplicationContext(), obj.getString("propertyID"), Toast.LENGTH_SHORT).show();
                            Log.e("Property ID---",obj.getString("propertyID"));
                            sendSubmission(Integer.parseInt(obj.getString("propertyID")));


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {

            /*
             * If you want to add more parameters with the image
             * you can do it here
             * here we have only one parameter with the image
             * which is tags
             * */
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("propertyType", propertyTypeString);
                params.put("name", nameString);
                params.put("description",descriptionString);
                params.put("price",String.valueOf(price));
                params.put("location",locationString);
                params.put("isApproved",String.valueOf(isApproved));
                return params;
            }


            /*
             * Here we are passing image by renaming it with a unique name
             * */
            @Override
            protected Map<String, DataPart> getByteData() {
                Map<String, DataPart> params = new HashMap<>();
                long imagename = System.currentTimeMillis();
                params.put("image", new DataPart(imagename + ".png", AppHelper.getFileDataFromDrawable(getApplicationContext(), imageView.getDrawable()), "image/jpg"));
                return params;
            }
        };

        //adding the request to volley
        Volley.newRequestQueue(this).add(volleyMultipartRequest);
        Intent intent = new Intent(AddEditSubmissionActivity.this,MySubmissionsActivity.class);
        startActivity(intent);
    }

    private void sendSubmission(int PropertyID) {

        //if everything is fine

        JSONObject jsonBody = new JSONObject();
        try {
            int userId = SharedPreferenceManager.getInstance(getApplicationContext()).getUser().getUserId();
            // Add your JSON data to the request body
            jsonBody.put("status", "Under Consideration");
            jsonBody.put("userId", SharedPreferenceManager.getInstance(getApplicationContext()).getUser().getUserId());
            jsonBody.put("propertyId", PropertyID);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.URL_CREATE_SUBMISSION,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Handle the response from the server
                        Log.e("respnose",response);
                        try {

                            //converting response to json object
                            JSONObject obj = new JSONObject(response);
                            //if no error in response
                            if (!obj.getBoolean("error")) {
                                Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
                                Log.i("success message", obj.getString("message"));
                                startActivity(new Intent(AddEditSubmissionActivity.this, MySubmissionsActivity.class));
                            } else {
                                Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
                                Log.e("error message", obj.getString("message"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.e("stack trace error message", e.getMessage());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle any errors that occur during the request
                        Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_SHORT).show();
                    }

                }) {
            @Override
            public String getBodyContentType() {
                return "application/json"; // Set the content type as application/json
            }

            @Override
            public byte[] getBody() throws AuthFailureError {
                Log.i("JSON BODY", jsonBody.toString());
                return jsonBody.toString().getBytes(); // Convert the JSON body to bytes
            }
        };

        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }



}