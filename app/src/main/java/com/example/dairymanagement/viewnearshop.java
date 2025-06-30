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
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class viewnearshop extends AppCompatActivity implements AdapterView.OnItemClickListener {
    ListView l1;
    SharedPreferences sh;
    String url;
    ArrayList<String>name,address,email,phone,lati,longi;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewnearshop);
        l1 = findViewById(R.id.listview10);
        sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        url = "http://" + sh.getString("ip", "") + ":5000/viewnearestshop";
        RequestQueue queue = Volley.newRequestQueue(viewnearshop.this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Display the response string.
                Log.d("+++++++++++++++++", response);
                try {

                    JSONArray ar = new JSONArray(response);
                    name = new ArrayList<>();
                    address = new ArrayList<>();
                    phone = new ArrayList<>();
                    email = new ArrayList<>();
                    lati = new ArrayList<>();
                    longi = new ArrayList<>();

                    for (int i = 0; i < ar.length(); i++) {
                        JSONObject jo = ar.getJSONObject(i);
                        name.add(jo.getString("name"));
                        address.add(jo.getString("place")+"\n"+jo.getString("post")+"\n"+jo.getString("pin"));
                        phone.add(jo.getString("phone"));
                        email.add(jo.getString("email"));
                        lati.add(jo.getString("latitude"));
                        longi.add(jo.getString("longitude"));


                    }

                    // ArrayAdapter<String> ad=new ArrayAdapter<>(Home.this,android.R.layout.simple_list_item_1,name);
                    //lv.setAdapter(ad);

                    l1.setAdapter(new custom_track(viewnearshop.this,name,address,email));
                    l1.setOnItemClickListener(viewnearshop.this);

                } catch (Exception e) {
                    Log.d("=========", e.toString());
                }


            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(viewnearshop.this, "err" + error, Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("lattitude",LocationService.lati);
                params.put("longitude",LocationService.logi);

                return params;
            }
        };
        queue.add(stringRequest);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        AlertDialog.Builder ald=new AlertDialog.Builder(viewnearshop.this);
        ald.setTitle("CALL OR TRACK ")
                .setPositiveButton("TRACK  ", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        try {

                            Intent ik = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.google.com/maps?q="+lati.get(position)+","+longi.get(position)));
                            startActivity(ik);

                        }
                        catch(Exception e)
                        {
                            Toast.makeText(getApplicationContext(),e+"",Toast.LENGTH_LONG).show();
                        }

                    }
                })
                .setNegativeButton("CALL NOW ", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getApplicationContext(),"calling",Toast.LENGTH_SHORT).show();
                        String phoneNumber = "tel:" + phone.get(position);
                        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse(phoneNumber));
//            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE)
//                    != PackageManager.PERMISSION_GRANTED) {
//                // Permission is not granted
//                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, 1);
//            }
                        startActivity(intent);

                    }
                });


        AlertDialog al = ald.create();
        al.show();
    }
}