package com.example.propertymasters.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.example.propertymasters.R;
import com.example.propertymasters.SharedPreferenceManager;
import com.example.propertymasters.URLs;
import com.example.propertymasters.VolleySingleton;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

public class SubmissionDetailsAdminActivity extends AppCompatActivity {

    ImageView propertyImage;
    ImageButton backBtn;
    Button approve,reply;
    TextView name,price,location,description,email,phone,replyTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submission_details_admin);

        propertyImage = findViewById(R.id.propertyImg);
        name = findViewById(R.id.name);
        price = findViewById(R.id.price);
        location = findViewById(R.id.location);
        description = findViewById(R.id.description);
        email = findViewById(R.id.email);
        phone = findViewById(R.id.phone);
        backBtn = findViewById(R.id.back);
        approve = findViewById(R.id.approve);
        reply = findViewById(R.id.reply);

                replyTV = findViewById(R.id.replyTV);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SubmissionDetailsAdminActivity.this.finish();
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


        approve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                approvedProperty();
            }
        });

        reply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogWithInput();
            }
        });


    }


    private void showDialogWithInput() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this); // Use 'this' instead of 'requireContext()' to refer to the activity
        LayoutInflater inflater = getLayoutInflater(); // Use 'getLayoutInflater()' instead of 'requireActivity().getLayoutInflater()'
        View dialogView = inflater.inflate(R.layout.sub_reply_input_dialog, null);
        builder.setView(dialogView);

        AutoCompleteTextView messageACT = dialogView.findViewById(R.id.messageACT);
        AutoCompleteTextView interestACT = dialogView.findViewById(R.id.interest);

        String[] interests = {"Interested","Not Interested"};
        // Create an ArrayAdapter and set it as the adapter for the AutoCompleteTextView
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, interests);
        interestACT.setAdapter(adapter);

        String message1 = "Hi, thank you for your submission, we are interested in this property. One of our agents will get in touch with you through the contact details you provided";
        String message2 = "Hi, thank you for your submission. But unfortunately, we are not interested in this property.";

        String[] messages = {message1,message2};
        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, messages);
        messageACT.setAdapter(adapter2);


        Button submit = dialogView.findViewById(R.id.submitReply);


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String messageText = messageACT.getText().toString().trim();
                String interestText = interestACT.getText().toString().trim();

                if (TextUtils.isEmpty(messageText)) {
                    messageACT.setError("Message cannot be empty");
                    messageACT.requestFocus();
                    return;
                }

                if (TextUtils.isEmpty(interestText)) {
                    interestACT.setError("Email cannot be empty");
                    interestACT.requestFocus();
                    return;
                }

                Toast.makeText(getApplicationContext(), "Entered value: " + messageText, Toast.LENGTH_SHORT).show();
                sendReply(messageText,interestText);
            }
        });

        builder.setNegativeButton("Cancel", null);

        AlertDialog dialog = builder.create();
        dialog.show();
    }


    private void sendReply(String reply,String status) {

        //if everything is fine

        JSONObject jsonBody = new JSONObject();
        try {

            // Add your JSON data to the request body
            jsonBody.put("reply", reply);
            jsonBody.put("id", Integer.parseInt(getIntent().getStringExtra("submissionId")));
            jsonBody.put("status", status);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.URL_UPDATE_SUBMISSION_REPLY,
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

                                startActivity(new Intent(SubmissionDetailsAdminActivity.this, ViewSubmissionsAdminActivity.class));
                            } else {
                                Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
                                Log.e("error message", obj.getString("message"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.e("error message", e.getMessage());
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

    private void approvedProperty() {

        //if everything is fine

        JSONObject jsonBody = new JSONObject();
        try {

            // Add your JSON data to the request body
            jsonBody.put("isApproved", 1);
            jsonBody.put("id", Integer.parseInt(getIntent().getStringExtra("id")));

        } catch (JSONException e) {
            e.printStackTrace();
        }

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.URL_APPROVE_PROPERTY,
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

                                startActivity(new Intent(SubmissionDetailsAdminActivity.this, AdminViewPropertyActivity.class));
                            } else {
                                Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
                                Log.e("error message", obj.getString("message"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.e("error message", e.getMessage());
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