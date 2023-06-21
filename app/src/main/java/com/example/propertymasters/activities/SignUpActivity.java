package com.example.propertymasters.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.propertymasters.R;
import com.example.propertymasters.SharedPreferenceManager;
import com.example.propertymasters.URLs;
import com.example.propertymasters.VolleySingleton;
import com.example.propertymasters.models.User;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

public class SignUpActivity extends AppCompatActivity {

    private ProgressBar progressBar;
    TextInputLayout nameTIL, emailTIL,phoneTIL, passwordTIL, confirmPasswordTIL;
    LinearLayout alreadyHaveAnAccount;
    Button signUpBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        progressBar = findViewById(R.id.progressBar);
        nameTIL = findViewById(R.id.nameTIL);
        emailTIL = findViewById(R.id.emailTIL);
        phoneTIL = findViewById(R.id.phoneTIL);
        passwordTIL = findViewById(R.id.passwordTIL);
        confirmPasswordTIL = findViewById(R.id.confirmPasswordTIL);
        alreadyHaveAnAccount=findViewById(R.id.alreadyHaveAnAccount);

        signUpBtn = findViewById(R.id.signupBTN);

        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userSignUp();
            }
        });

        alreadyHaveAnAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignUpActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });




    }

    private void userSignUp() {
        progressBar.setVisibility(View.VISIBLE);
        //first getting the values
        String nameString = nameTIL.getEditText().getText().toString();
        String emailString = emailTIL.getEditText().getText().toString();
        String phoneString = phoneTIL.getEditText().getText().toString();
        String passwordString = passwordTIL.getEditText().getText().toString();
        String confirmPasswordString = confirmPasswordTIL.getEditText().getText().toString();

        //validating inputs
        if (TextUtils.isEmpty(nameString)) {
            nameTIL.setError("Please enter your fullname");
            nameTIL.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(emailString)) {
            emailTIL.setError("Please enter your email");
            emailTIL.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(phoneString)) {
            phoneTIL.setError("Please enter your phone");
            phoneTIL.requestFocus();
            return;
        }

        if (!Patterns.PHONE.matcher(phoneString).matches()) {
            emailTIL.setError("Please enter a valid phone number");
            emailTIL.requestFocus();
            return;
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(emailString).matches()) {
            emailTIL.setError("Please enter your email");
            emailTIL.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(passwordString)) {
            passwordTIL.setError("Please enter your password");
            passwordTIL.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(confirmPasswordString)) {
            confirmPasswordTIL.setError("Please confirm your password");
            confirmPasswordTIL.requestFocus();
            return;
        }

        Toast.makeText(SignUpActivity.this,"data collected",Toast.LENGTH_SHORT).show();


        //if everything is fine

        JSONObject jsonBody = new JSONObject();
        try {
            // Add your JSON data to the request body
            jsonBody.put("userName", nameString);
            jsonBody.put("email", emailString);
            jsonBody.put("phone", phoneString);
            jsonBody.put("password", passwordString);
            jsonBody.put("password", confirmPasswordString);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.URL_SIGNUP,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Handle the response from the server
                        try {
                            Log.e("response123",response);
                            //converting response to json object
                            JSONObject obj = new JSONObject(response);
                            //if no error in response
                            if (!obj.getBoolean("error")) {
                                Toast.makeText(getApplicationContext(), obj.getString("msg"), Toast.LENGTH_SHORT).show();

                                //getting the user from the response
                                JSONObject userJson = obj.getJSONObject("user");

                                //creating a new student object
                                User user = new User(
                                        userJson.getInt("userId"),
                                        userJson.getString("userName"),
                                        userJson.getString("email"),
                                        userJson.getString("phone"),
                                        userJson.getString("profilePicture"),
                                        userJson.getInt("roleId"),
                                        userJson.getString("roleName")
                                );

//                              storing the user in shared preferences
                                SharedPreferenceManager.getInstance(getApplicationContext()).userLogin(user);

                                //starting the profile activity
                                finish();
                                startActivity(new Intent(SignUpActivity.this, MainActivity.class));
                                progressBar.setVisibility(View.GONE);
                            } else {
                                Toast.makeText(getApplicationContext(), obj.getString("msg"), Toast.LENGTH_SHORT).show();
                                Log.e("error message", obj.getString("msg"));
                                progressBar.setVisibility(View.GONE);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle any errors that occur during the request
                        Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_SHORT).show();
                        Log.i("JSON BODY", jsonBody.toString());
                        progressBar.setVisibility(View.GONE);
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