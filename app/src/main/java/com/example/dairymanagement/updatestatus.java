package com.example.dairymanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
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

public class updatestatus extends AppCompatActivity {
    Button b1;
    Spinner s1;
    SharedPreferences sh;
    String status;
    String stts[]={"delivered","not delivered","on the way","on your door"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updatestatus);
        b1=findViewById(R.id.button17);
        s1=findViewById(R.id.spinner4);
        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        ArrayAdapter<String> ad=new ArrayAdapter<>(updatestatus.this,android.R.layout.simple_list_item_1,stts);
        s1.setAdapter(ad);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                RequestQueue queue = Volley.newRequestQueue(updatestatus.this);
                String url = "http://" + sh.getString("ip","") + ":5000/updatestts";

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

                                Toast.makeText(updatestatus.this, "updated", Toast.LENGTH_SHORT).show();
                                Intent ik = new Intent(getApplicationContext(),viewallocatedwork.class);
                                startActivity(ik);




                            }
                            else  {


                                Toast.makeText(updatestatus.this, "Invalid username or password", Toast.LENGTH_SHORT).show();
                                Intent ik = new Intent(getApplicationContext(),login.class);
                                startActivity(ik);

                            }
                        } catch (JSONException e) {
                            Toast.makeText(updatestatus.this, "aaaaaaa"+e, Toast.LENGTH_SHORT).show();

                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {


                        Toast.makeText(getApplicationContext(), "Error" + error, Toast.LENGTH_LONG).show();
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("stts", s1.getSelectedItem().toString());
                        params.put("oid", getIntent().getStringExtra("oid"));


                        return params;
                    }
                };
                queue.add(stringRequest);



            }
        });
    }
}