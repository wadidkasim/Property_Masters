package com.example.propertymasters.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

import org.json.JSONException;
import org.json.JSONObject;

public class PropertyDetailsAdminActivity extends AppCompatActivity {

    ImageView propertyImage;
    ImageButton backBtn;
    Button deleteBtn, updateBtn;
    TextView name,price,location,description;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_property_details_admin);

        propertyImage = findViewById(R.id.propertyImg);
        name = findViewById(R.id.name);
        price = findViewById(R.id.price);
        location = findViewById(R.id.location);
        description = findViewById(R.id.description);
        backBtn = findViewById(R.id.back);
        deleteBtn = findViewById(R.id.delete);
        updateBtn = findViewById(R.id.update);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PropertyDetailsAdminActivity.this.finish();
            }
        });

        name.setText(getIntent().getStringExtra("name"));
        price.setText("UGx."+getIntent().getStringExtra("price")+"/=");
        location.setText(getIntent().getStringExtra("location"));
        description.setText(getIntent().getStringExtra("description"));

        Glide.with(getApplicationContext())
                .load(getIntent().getStringExtra("image"))
                .centerCrop()
                .placeholder(R.drawable.placeholder)
                .into(propertyImage);

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteProperty();
            }
        });

        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), UpdatePropertyAdminActivity.class);

                intent.putExtra("id",getIntent().getStringExtra("id"));
                intent.putExtra("name",getIntent().getStringExtra("name"));
                intent.putExtra("price",getIntent().getStringExtra("price"));
                intent.putExtra("description",getIntent().getStringExtra("description"));
                intent.putExtra("location",getIntent().getStringExtra("location"));
                intent.putExtra("image",getIntent().getStringExtra("image"));
                intent.putExtra("propertyType",getIntent().getStringExtra("propertyType"));


                startActivity(intent);
            }
        });
    }

    private void deleteProperty() {

        //if everything is fine

        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("propertyId", Integer.parseInt(getIntent().getStringExtra("id")));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.URL_DELETE_PROPERTY,
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

                                startActivity(new Intent(PropertyDetailsAdminActivity.this, AdminViewPropertyActivity.class));
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