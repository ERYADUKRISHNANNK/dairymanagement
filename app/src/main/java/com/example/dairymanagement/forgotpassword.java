package com.example.dairymanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class forgotpassword extends AppCompatActivity {
    Button b1;
    EditText e1,e2;
    SharedPreferences sh;
    String username,email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgotpassword);
        b1=findViewById(R.id.button2);
        e1=findViewById(R.id.editTextTextPersonName88);
        e2=findViewById(R.id.editTextTextPersonName89);

        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username=e1.getText().toString();
                email=e2.getText().toString();

        RequestQueue queue = Volley.newRequestQueue(forgotpassword.this);
        String url = "http://" + sh.getString("ip", "") + ":5000/fp";

        // Request a string response from the provided URL.


        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Display the response string.
                Log.d("+++++++++++++++++", response);
                try {
                    JSONObject json = new JSONObject(response);
                    String res = json.getString("task");

                    if (res.equalsIgnoreCase("success")) {
                        Toast.makeText(forgotpassword.this, "mail send successfully", Toast.LENGTH_SHORT).show();

                        Intent ik = new Intent(getApplicationContext(),login.class);
                        startActivity(ik);

                    }
                    else if(res.equalsIgnoreCase("invalid")){
                        Toast.makeText(forgotpassword.this, "not a valid check again", Toast.LENGTH_SHORT).show();
                    }
                    else {

                        Toast.makeText(forgotpassword.this, "Invalid ", Toast.LENGTH_SHORT).show();

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


                Toast.makeText(getApplicationContext(), "error"+error, Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("username", username);
                params.put("email", email);



                return params;
            }
        };
        queue.add(stringRequest);
            }
        });
    }




}

