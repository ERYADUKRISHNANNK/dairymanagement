package com.example.dairymanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
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

public class registerfarmer extends AppCompatActivity {
    Button b1;
    EditText e1,e2,e3,e4,e5,e6,e7;
    RadioButton r1,r2,r3;
    Spinner s1;

    SharedPreferences sh;
    String name,age,place,email,gender,phone,uname,pswrd,spinner;
    String arr[] = {"farmer","user"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registerfarmer);
        b1=findViewById(R.id.button2);
        r1=findViewById(R.id.radioButton);
        r2=findViewById(R.id.radioButton2);
        r3=findViewById(R.id.radioButton3);
        s1=findViewById(R.id.spinner3);


        e1=findViewById(R.id.editTextTextPersonName89);
        e2=findViewById(R.id.editTextTextPersonName2);
        e3=findViewById(R.id.editTextTextPersonName3);
        e4=findViewById(R.id.editTextTextEmailAddress);
        e5=findViewById(R.id.editTextPhone);
        e6=findViewById(R.id.editTextTextPersonName8);
        e7=findViewById(R.id.editTextTextPassword);
        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        ArrayAdapter<String> ad=new ArrayAdapter<>(registerfarmer.this, android.R.layout.simple_list_item_1,arr);
        s1.setAdapter(ad);


        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name=e1.getText().toString();
                age=e2.getText().toString();
                place=e3.getText().toString();
                email=e4.getText().toString();
                phone=e5.getText().toString();
                uname=e6.getText().toString();
                pswrd=e7.getText().toString();
                gender="";
                if (r1.isChecked()) {
                    gender = r1.getText().toString();
                }
                 else if (r2.isChecked()) {
                    gender = r2.getText().toString();
                }
                 else if(r3.isChecked())
                {
                    gender=r3.getText().toString();
                }
                if (name.equalsIgnoreCase(""))
                {
                    e1.setError("Enter first name");
                }
                else if (!name.matches("^[a-z A-Z ]*$")) {
                    e1.setError("only characters allowed");
                }

                else if(age.equalsIgnoreCase(""))
                {
                    e2.setError("Enter age");
                }
                else if(age.length()>2)
                {
                    e2.setError("age must less than 99 ");
                    e2.requestFocus();
                }
                else if(place.equalsIgnoreCase(""))
                {
                    e3.setError("Enter Your Place");
                }

                else if(email.equalsIgnoreCase(""))
                {
                    e4.setError("Enter Your Email");
                }
                else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches())
                {
                    e4.setError("Enter Valid Email");
                    e4.requestFocus();
                }
                else if(phone.equalsIgnoreCase(""))
                {
                    e5.setError("Enter Your Phone No");
                }
                else if(phone.length()!=10)
                {
                    e5.setError(" 10 nos required");
                    e5.requestFocus();
                }


                else if(uname.equalsIgnoreCase(""))
                {
                    e6.setError("Enter Your username");
                }
                else if(uname.length()<4)
                {
                    e6.setError("Minimum 4 Characters required");
                    e6.requestFocus();
                }
                else if(pswrd.equalsIgnoreCase(""))
                {
                    e7.setError("Enter Your password");
                }
                else if(pswrd.length()<6)
                {
                    e7.setError("minimum 6 nos required");
                    e7.requestFocus();
                }else if(gender.equalsIgnoreCase(""))
                {
                    Toast.makeText(getApplicationContext(),"Select gender",Toast.LENGTH_LONG).show();
                }
                else {


                    RequestQueue queue = Volley.newRequestQueue(registerfarmer.this);
                    String url = "http://" + sh.getString("ip", "") + ":5000/regfarmer";

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

                                    Toast.makeText(getApplicationContext(), "sucessfully inserted", Toast.LENGTH_LONG).show();
                                    Intent i = new Intent(getApplicationContext(), login.class);
                                    startActivity(i);
                                } else {
                                    Toast.makeText(getApplicationContext(), "failed", Toast.LENGTH_LONG).show();
                                    Intent i = new Intent(getApplicationContext(), registerfarmer.class);
                                    startActivity(i);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {


                            Toast.makeText(getApplicationContext(), "duplicate entry please choose another one", Toast.LENGTH_LONG).show();
                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams() {
                            Map<String, String> params = new HashMap<String, String>();
                            params.put("name", name);
                            params.put("age", age);
                            params.put("place", place);
                            params.put("email", email);
                            params.put("gender", gender);
                            params.put("phone", phone);
                            params.put("uname", uname);
                            params.put("pass", pswrd);
                            params.put("type", s1.getSelectedItem().toString());


                            return params;
                        }
                    };
                    queue.add(stringRequest);
                }








            }
        });




    }
}