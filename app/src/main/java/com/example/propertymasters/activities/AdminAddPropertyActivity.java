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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.example.propertymasters.AppHelper;
import com.example.propertymasters.R;
import com.example.propertymasters.URLs;
import com.example.propertymasters.VolleyMultipartRequest;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class AdminAddPropertyActivity extends AppCompatActivity {
    private ProgressBar progressBar;
    TextInputLayout nameTIL, descriptionTIL, priceTIL, locationTIL, isApproved;
    ImageView imageView;
    Button submit;
    ImageButton backBtn;
    AutoCompleteTextView autoCompleteTextView;
    private static final int PICK_IMAGE_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_property);

        progressBar = findViewById(R.id.progressBar);
        backBtn = findViewById(R.id.back);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AdminAddPropertyActivity.this.finish();
            }
        });


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
        progressBar.setVisibility(View.VISIBLE);
        //getting the tag from the edittext
        final String nameString = nameTIL.getEditText().getText().toString();
        final String descriptionString = descriptionTIL.getEditText().getText().toString();
        final String propertyTypeString = autoCompleteTextView.getText().toString();
        final String locationString = locationTIL.getEditText().getText().toString();
        final int price = Integer.parseInt(priceTIL.getEditText().getText().toString());
        final int isApproved = 1;

        //our custom volley request
        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, URLs.URL_CREATE_PROPERTY,
                new Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response) {


                        try {
                            Log.i("tagconvertstr", "["+new String(response.data)+"]");
                            JSONObject obj = new JSONObject(new String(response.data));

                            Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
                            Log.e("Message---",obj.getString("message"));
                            progressBar.setVisibility(View.GONE);
                            startActivity(new Intent(getApplicationContext(),AdminViewPropertyActivity.class));


                        } catch (JSONException e) {
                            e.printStackTrace();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);
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

    }
}