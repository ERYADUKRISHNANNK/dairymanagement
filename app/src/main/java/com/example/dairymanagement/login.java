package com.example.dairymanagement;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Service;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ServiceConfigurationError;

public class login extends AppCompatActivity {
    Button b1,b2,b3;
    EditText e1;
    EditText e2;
    SharedPreferences sh;
    String uname,pwrd;
    TextView t1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        b1=findViewById(R.id.button5);
        b2=findViewById(R.id.button6);
        t1=findViewById(R.id.textView2);

        e1=findViewById(R.id.uname);
        e2=findViewById(R.id.editTextTextPassword);
        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        t1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ii=new Intent(getApplicationContext(),forgotpassword.class);
                startActivity(ii);
            }
        });



        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uname = e1.getText().toString();
                pwrd = e2.getText().toString();


                if (uname.equalsIgnoreCase("")) {
                    e1.setError("Enter Your username");
                } else if (pwrd.equalsIgnoreCase("")) {
                    e2.setError("Enter Your password");
                } else {


                    RequestQueue queue = Volley.newRequestQueue(login.this);
                    String url = "http://" + sh.getString("ip", "") + ":5000/login";

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

                                    String lid = json.getString("id");
                                    String type = json.getString("type");
                                    Toast.makeText(login.this, type, Toast.LENGTH_SHORT).show();
                                    SharedPreferences.Editor edp = sh.edit();
                                    edp.putString("lid", lid);
                                    edp.putString("type", type);
                                    edp.commit();
                                    if (type.equalsIgnoreCase("farmer")) {
                                        Intent ik = new Intent(getApplicationContext(), fhome.class);
                                        startActivity(ik);
                                        Intent in1 = new Intent(getApplicationContext(), LocationServiceno.class);
                                        startService(in1);
                                    } else if (type.equalsIgnoreCase("user")) {
                                        Intent ik = new Intent(getApplicationContext(), userhome2.class);
                                        startActivity(ik);
                                        Intent in1 = new Intent(getApplicationContext(), LocationServiceno.class);
                                        startService(in1);
                                        Intent in = new Intent(getApplicationContext(), LocationService.class);
                                        startService(in);
                                    } else if (type.equalsIgnoreCase("delivery boy")) {
                                        Intent in1 = new Intent(getApplicationContext(), LocationServiceno.class);
                                        startService(in1);
                                        Intent ik = new Intent(getApplicationContext(), dhome.class);
                                        startActivity(ik);

                                    } else {
                                        Intent ik = new Intent(getApplicationContext(), login.class);
                                        startActivity(ik);

                                    }
                                } else {


                                    Toast.makeText(login.this, "Invalid username or password", Toast.LENGTH_SHORT).show();
                                    Intent ik = new Intent(getApplicationContext(), login.class);
                                    startActivity(ik);

                                }
                            } catch (JSONException e) {
                                Toast.makeText(login.this, "aaaaaaa" + e, Toast.LENGTH_SHORT).show();

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
                            params.put("uname", uname);
                            params.put("pass", pwrd);

                            return params;
                        }
                    };
                    queue.add(stringRequest);


                }
            }
        });


        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                AlertDialog.Builder ald = new AlertDialog.Builder(login.this);
                ald.setTitle("SIGNUP HERE")
                        .setPositiveButton("FARMER OR USER", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent ii=new Intent(getApplicationContext(),registerfarmer.class);
                                startActivity(ii);
                            }
                        })
                        .setNegativeButton("DELIVERY BOY", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                Intent ii=new Intent(getApplicationContext(),regdelboy.class);
                                startActivity(ii);

                            }
                        });

                AlertDialog al = ald.create();
                al.show();
            }
        });

    }

    @Override
    public void onBackPressed() {


        AlertDialog.Builder ald = new AlertDialog.Builder(login.this);
        ald.setTitle("Do you want to exit ?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent in = new Intent(Intent.ACTION_MAIN);
                        in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        in.addCategory(Intent.CATEGORY_HOME);
                        startActivity(in);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {



                    }
                });

        AlertDialog al = ald.create();
        al.show();
    }
}