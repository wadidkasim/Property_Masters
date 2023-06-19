package com.example.propertymasters.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
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

public class LoginActivity extends AppCompatActivity {
    TextInputLayout emailTIL, passwordTIL;
    LinearLayout dontHaveAnAccount;
    Button loginBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailTIL = findViewById(R.id.emailTIL);
        passwordTIL = findViewById(R.id.passwordTIL);
        loginBtn = findViewById(R.id.loginBtn);
        dontHaveAnAccount = findViewById(R.id.dontHaveAnAccount);


        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emailString = emailTIL.getEditText().getText().toString();
                String password = passwordTIL.getEditText().getText().toString();
                Toast.makeText(getApplicationContext(),emailString, Toast.LENGTH_SHORT).show();
                Log.e("tag",emailString);
                userLogin();
            }
        });

        dontHaveAnAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this,SignUpActivity.class);
                startActivity(intent);
            }
        });
    }



    private void userLogin() {
        //first getting the values
        final String email = emailTIL.getEditText().getText().toString();
        final String password = passwordTIL.getEditText().getText().toString();
        //validating inputs
        if (TextUtils.isEmpty(email)) {
            emailTIL.setError("Please enter your email");
            emailTIL.requestFocus();
            return;
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailTIL.setError("Please enter a valid email");
            emailTIL.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            passwordTIL.setError("Please enter your password");
            passwordTIL.requestFocus();
            return;
        }

        Toast.makeText(LoginActivity.this,"data collected",Toast.LENGTH_SHORT).show();


        //if everything is fine

        JSONObject jsonBody = new JSONObject();
        try {
            // Add your JSON data to the request body
            jsonBody.put("email", email);
            jsonBody.put("password", password);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.URL_LOGIN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Handle the response from the server
                        try {

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
                                if(SharedPreferenceManager.getInstance(getApplicationContext()).isAdmin()){
                                    startActivity(new Intent(LoginActivity.this, AdminDashboardActivity.class));
                                } else{
                                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                }
                            } else {
                                Toast.makeText(getApplicationContext(), obj.getString("msg"), Toast.LENGTH_SHORT).show();
                                Log.e("error message", obj.getString("msg"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
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