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

public class complaint extends AppCompatActivity {
    Button b1,b2;
    EditText e1;
    SharedPreferences sh;
    String complaint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaint);
        b1=findViewById(R.id.button38);
        b2=findViewById(R.id.button29);
        e1=findViewById(R.id.editTextTextPersonName17);
        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ii=new Intent(getApplicationContext(),reply.class);
                startActivity(ii);

            }
        });
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                complaint=e1.getText().toString();
                if (complaint.equalsIgnoreCase(""))
                {
                    e1.setError("Enter complaint");
                }
                else {
                    Toast.makeText(getApplicationContext(), " error", Toast.LENGTH_LONG).show();
                }
                RequestQueue queue = Volley.newRequestQueue(complaint.this);
                String url = "http://" + sh.getString("ip","") + ":5000/sendcomplaint";

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

                                Intent ik = new Intent(getApplicationContext(),userhome.class);
                                startActivity(ik);

                            } else {

                                Toast.makeText(complaint.this, "Invalid ", Toast.LENGTH_SHORT).show();

                            }
                        } catch (JSONException e) {
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
                        params.put("complaint", complaint);
                        params.put("lid",sh.getString("lid",""));

                        return params;
                    }
                };
                queue.add(stringRequest);


            }
        });

    }
}