package com.example.propertymasters.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
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

public class PropertyDetailsActivity extends AppCompatActivity {
    ImageView propertyImage;
    ImageButton backBtn;
    Button sendInquiry;
    TextView name,price,location,description;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_property_details);

        propertyImage = findViewById(R.id.propertyImg);
        name = findViewById(R.id.name);
        price = findViewById(R.id.price);
        location = findViewById(R.id.location);
        description = findViewById(R.id.description);
        backBtn = findViewById(R.id.back);
        sendInquiry = findViewById(R.id.sendInquiry);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PropertyDetailsActivity.this.finish();
            }
        });

        name.setText(getIntent().getStringExtra("name"));
        price.setText(getIntent().getStringExtra("price"));
        location.setText(getIntent().getStringExtra("location"));
        description.setText(getIntent().getStringExtra("description"));

        Glide.with(getApplicationContext())
                .load(getIntent().getStringExtra("image"))
                .centerCrop()
                .placeholder(R.drawable.placeholder)
                .into(propertyImage);

        sendInquiry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogWithInput();
            }
        });


    }

    private void showDialogWithInput() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this); // Use 'this' instead of 'requireContext()' to refer to the activity
        LayoutInflater inflater = getLayoutInflater(); // Use 'getLayoutInflater()' instead of 'requireActivity().getLayoutInflater()'
        View dialogView = inflater.inflate(R.layout.inquiry_message_input_dialog, null);
        builder.setView(dialogView);

        TextInputLayout messageInputLayout = dialogView.findViewById(R.id.InquiryInput);
        TextInputLayout emailInputLayout = dialogView.findViewById(R.id.EmailInput);
        TextInputLayout phoneInputLayout = dialogView.findViewById(R.id.PhoneInput);

        String userEmail = SharedPreferenceManager.getInstance(getApplicationContext()).getUser().getEmail();
        String userPhone = SharedPreferenceManager.getInstance(getApplicationContext()).getUser().getPhone();

        emailInputLayout.getEditText().setText(userEmail);
        phoneInputLayout.getEditText().setText(userPhone);
        messageInputLayout.getEditText().setText("Hi, I am interested in the property. Please contact me so we can make a deal and come to an agreement");

        Button submit = dialogView.findViewById(R.id.submitInquiry);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String messageText = messageInputLayout.getEditText().getText().toString();
                String email = emailInputLayout.getEditText().getText().toString().trim();
                String phone = phoneInputLayout.getEditText().getText().toString().trim();
                if (TextUtils.isEmpty(messageText)) {
                    messageInputLayout.setError("Message cannot be empty");
                    messageInputLayout.requestFocus();
                    return;
                }

                if (TextUtils.isEmpty(email)) {
                    emailInputLayout.setError("Email cannot be empty");
                    emailInputLayout.requestFocus();
                    return;
                }

                if (TextUtils.isEmpty(phone)) {
                    phoneInputLayout.setError("Phone cannot be empty");
                    phoneInputLayout.requestFocus();
                    return;
                }

                Toast.makeText(getApplicationContext(), "Entered value: " + messageText, Toast.LENGTH_SHORT).show();
                sendInquiry(messageText,email,phone);
            }
        });

        builder.setNegativeButton("Cancel", null);

        AlertDialog dialog = builder.create();
        dialog.show();
    }


    private void sendInquiry(String message,String email,String phone) {

        //if everything is fine

        JSONObject jsonBody = new JSONObject();
        try {
            int userId = SharedPreferenceManager.getInstance(getApplicationContext()).getUser().getUserId();
            // Add your JSON data to the request body
            jsonBody.put("message", message);
            jsonBody.put("userId", userId);
            jsonBody.put("propertyId", Integer.parseInt(getIntent().getStringExtra("id")));
            jsonBody.put("email", email);
            jsonBody.put("phone", phone);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.URL_CREATE_INQUIRY,
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

                                startActivity(new Intent(PropertyDetailsActivity.this, MyInquiriesActivity.class));
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