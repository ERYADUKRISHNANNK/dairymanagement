package com.example.dairymanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
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
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class viewnotification extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    ListView l1;
    SharedPreferences sh;
    ArrayList<String> notification,date;
    String url;
    Spinner s1;
    String ar[]={"farmer","admin"};



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewnotification);
        l1=findViewById(R.id.listview5);
        s1=findViewById(R.id.spinner);
        s1.setOnItemSelectedListener(this);
        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

         ArrayAdapter<String> ad=new ArrayAdapter<>(viewnotification.this,android.R.layout.simple_list_item_1,ar);
        s1.setAdapter(ad);


    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        url ="http://"+sh.getString("ip", "") + ":5000/viewnotification";
        RequestQueue queue = Volley.newRequestQueue(viewnotification.this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Display the response string.
                Log.d("+++++++++++++++++",response);
                try {

                    JSONArray ar=new JSONArray(response);
                    notification= new ArrayList<>();
                    date=new ArrayList<>();

                    for(int i=0;i<ar.length();i++)
                    {
                        JSONObject jo=ar.getJSONObject(i);
                        notification.add(jo.getString("notifications"));
                        date.add(jo.getString("date"));



                    }

                    // ArrayAdapter<String> ad=new ArrayAdapter<>(Home.this,android.R.layout.simple_list_item_1,name);
                    //lv.setAdapter(ad);
//
                    l1.setAdapter(new customnoti(viewnotification.this,notification,date));
//                    l1.setOnItemClickListener(viewuser.this);

                } catch (Exception e) {
                    Log.d("=========", e.toString());
                }


            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(viewnotification.this, "err"+error, Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("type",s1.getSelectedItem().toString());

                return params;
            }
        };
        queue.add(stringRequest);


    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}

