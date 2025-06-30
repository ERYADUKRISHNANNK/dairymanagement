package com.example.dairymanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class addmilk extends AppCompatActivity {
    Button b1;
    EditText e1,e2;
    SharedPreferences sh;
    String addmilk,datee;
    final Calendar myCalendar= Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addmilk);
        b1=findViewById(R.id.button3);
        e1=findViewById(R.id.editTextTextPersonName5);
        e2=findViewById(R.id.editTextTextPersonName15);
        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());


        DatePickerDialog.OnDateSetListener date =new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH,month);
                myCalendar.set(Calendar.DAY_OF_MONTH,day);
                updateLabel();
            }
        };
        e2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(addmilk.this,date,myCalendar.get(Calendar.YEAR),myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addmilk=e1.getText().toString();
                datee=e2.getText().toString();
                 if(addmilk.equalsIgnoreCase(""))
                {
                    e1.setError("Enter Your milk measurement");
                }
                else if(datee.equalsIgnoreCase(""))
                {
                    e2.setError("choose date");

                }
                else {

                     RequestQueue queue = Volley.newRequestQueue(addmilk.this);
                     String url = "http://" + sh.getString("ip", "") + ":5000/addmilkmeasu";

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

                                     Intent ik = new Intent(getApplicationContext(), fhome.class);
                                     startActivity(ik);

                                 } else {

                                     Toast.makeText(addmilk.this, "Invalid username or password", Toast.LENGTH_SHORT).show();

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
                             params.put("quantity", addmilk);
                             params.put("date", datee);
                             params.put("lid", sh.getString("lid",""));

                             return params;
                         }
                     };
                     queue.add(stringRequest);
                 }


            }
        });

            }



    private void updateLabel(){
        String myFormat="yyyy-MM-dd";
        SimpleDateFormat dateFormat=new SimpleDateFormat(myFormat, Locale.US);
        e2.setText(dateFormat.format(myCalendar.getTime()));
    }
        }



