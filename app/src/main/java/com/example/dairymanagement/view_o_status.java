package com.example.dairymanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
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

public class view_o_status extends AppCompatActivity {
    ListView l1;
    SharedPreferences sh;
    ArrayList<String> status,pname,qnty,total;
    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_ostatus);
        l1=findViewById(R.id.list234);
        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());


        url = "http://" + sh.getString("ip", "") + ":5000/view_o_status_user";
        RequestQueue queue = Volley.newRequestQueue(view_o_status.this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Display the response string.
                Log.d("+++++++++++++++++", response);
                try {
//                    Toast.makeText(view_o_status.this, response+"", Toast.LENGTH_SHORT).show();


                    JSONArray ar = new JSONArray(response);
                    pname = new ArrayList<>();
                    qnty = new ArrayList<>();
                    total = new ArrayList<>();
                    status = new ArrayList<>();
//                    itemid = new ArrayList<>();


                    for (int i = 0; i < ar.length(); i++) {
                        JSONObject jo = ar.getJSONObject(i);
                        pname.add(jo.getString("product"));
                        qnty.add(jo.getString("quantity"));
                        total.add(jo.getString("total"));
                        status.add(jo.getString("status"));

//                        itemid.add(jo.getString("item_id"));

                    }

                    // ArrayAdapter<String> ad=new ArrayAdapter<>(Home.this,android.R.layout.simple_list_item_1,name);
                    //lv.setAdapter(ad);
//
                    l1.setAdapter(new custom_o_status(view_o_status.this,pname,qnty,total,status));
//                            l1.setOnItemClickListener(viewbuyproduct.this);
//
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(),e+"====",Toast.LENGTH_LONG).show();
                    Log.d("=========", e.toString());
                }

            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(view_o_status.this, "err"+error, Toast.LENGTH_SHORT).show();
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
}