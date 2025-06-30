package com.example.dairymanagement;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class viewallocatedwork extends AppCompatActivity implements AdapterView.OnItemClickListener,AdapterView.OnItemSelectedListener {
    ListView l1;
    SharedPreferences sh;
    String url;
    Spinner s1;
    String[] ar={"SHOP","USER"};
    ArrayList<String>oid,uname,date,amount,place;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewallocatedwork);
        l1=findViewById(R.id.listview6);
        s1=findViewById(R.id.spinner6);
        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());


         ArrayAdapter<String> ad=new ArrayAdapter<>(viewallocatedwork.this,android.R.layout.simple_list_item_1,ar);
        s1.setAdapter(ad);
        s1.setOnItemSelectedListener(viewallocatedwork.this);


    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        AlertDialog.Builder ald=new AlertDialog.Builder(viewallocatedwork.this);
        ald.setTitle("File")
                .setPositiveButton(" Track", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        try {

                            RequestQueue queue = Volley.newRequestQueue(viewallocatedwork.this);
                            String url = "http://" + sh.getString("ip", "") + ":5000/trackdelboy";

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
                                            String lati = json.getString("lati");
                                            String longi = json.getString("longi");


                                            Intent ik = new Intent(Intent.ACTION_VIEW);
                                            ik.setData(Uri.parse("http://maps.google.com?q=" + lati + "," + longi));
                                            startActivity(ik);


                                        } else {


                                            Toast.makeText(viewallocatedwork.this, "Invalid username or password", Toast.LENGTH_SHORT).show();
                                            Intent ik = new Intent(getApplicationContext(), login.class);
                                            startActivity(ik);

                                        }
                                    } catch (JSONException e) {
                                        Toast.makeText(viewallocatedwork.this, "aaaaaaa" + e, Toast.LENGTH_SHORT).show();

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
                                    params.put("place", place.get(position));


                                    return params;
                                }
                            };
                            queue.add(stringRequest);





                        }
                        catch(Exception e)
                        {
                            Toast.makeText(getApplicationContext(),e+"",Toast.LENGTH_LONG).show();
                        }

                    }
                })
                .setNegativeButton("status ", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {

                        Intent i=new Intent(getApplicationContext(),updatestatus.class);
                        i.putExtra("oid", oid.get(position));
                        startActivity(i);
                    }
                });

        AlertDialog al=ald.create();
        al.show();



    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        if(s1.getSelectedItem().toString().equalsIgnoreCase("SHOP")){

            url ="http://"+sh.getString("ip", "") + ":5000/viewallwrk1";
            RequestQueue queue = Volley.newRequestQueue(viewallocatedwork.this);

            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    // Display the response string.
                    Log.d("+++++++++++++++++",response);
                    try {

                        JSONArray ar=new JSONArray(response);
                        oid= new ArrayList<>();
                        uname= new ArrayList<>();
                        date= new ArrayList<>();
                        amount=new ArrayList<>();
                        place=new ArrayList<>();

                        for(int i=0;i<ar.length();i++)
                        {
                            JSONObject jo=ar.getJSONObject(i);
                            oid.add(jo.getString("order_id"));
                            uname.add(jo.getString("name"));
                            date.add(jo.getString("date"));
                            amount.add(jo.getString("total"));
                            place.add(jo.getString("place"));



                        }

                        // ArrayAdapter<String> ad=new ArrayAdapter<>(Home.this,android.R.layout.simple_list_item_1,name);
                        //lv.setAdapter(ad);
//
                        l1.setAdapter(new custom_assignwrk(viewallocatedwork.this,oid,amount,uname,date));
                        l1.setOnItemClickListener(viewallocatedwork.this);

                    } catch (Exception e) {
                        Log.d("=========", e.toString());
                    }


                }

            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    Toast.makeText(viewallocatedwork.this, "err"+error, Toast.LENGTH_SHORT).show();
                }
            }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();
                    params.put("lid",sh.getString("lid",""));

                    return params;
                }
            };
            queue.add(stringRequest);







        }
        else  {

            url = "http://" + sh.getString("ip", "") + ":5000/viewallwrk";
            RequestQueue queue = Volley.newRequestQueue(viewallocatedwork.this);

            StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    // Display the response string.
                    Log.d("+++++++++++++++++", response);
                    try {

                        JSONArray ar = new JSONArray(response);
                        oid = new ArrayList<>();
                        uname = new ArrayList<>();
                        date = new ArrayList<>();
                        amount = new ArrayList<>();
                        place = new ArrayList<>();

                        for (int i = 0; i < ar.length(); i++) {
                            JSONObject jo = ar.getJSONObject(i);
                            oid.add(jo.getString("order_id"));
                            uname.add(jo.getString("name"));
                            date.add(jo.getString("date"));
                            amount.add(jo.getString("total"));
                            place.add(jo.getString("place"));


                        }

                        // ArrayAdapter<String> ad=new ArrayAdapter<>(Home.this,android.R.layout.simple_list_item_1,name);
                        //lv.setAdapter(ad);
//
                        l1.setAdapter(new custom_assignwrk(viewallocatedwork.this, oid, amount, uname, date));
                        l1.setOnItemClickListener(viewallocatedwork.this);

                    } catch (Exception e) {
                        Log.d("=========", e.toString());
                    }


                }

            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    Toast.makeText(viewallocatedwork.this, "err" + error, Toast.LENGTH_SHORT).show();
                }
            }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();
                    params.put("lid", sh.getString("lid", ""));

                    return params;
                }
            };
            queue.add(stringRequest);
        }


    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}