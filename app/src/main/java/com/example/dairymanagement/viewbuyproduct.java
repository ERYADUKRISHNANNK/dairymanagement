package com.example.dairymanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
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

public class viewbuyproduct extends AppCompatActivity implements AdapterView.OnItemClickListener {
    Button b1;
    ListView l1;
    Spinner s1;
    String arr[] = {"farmer", "admin"};
    ArrayList<String> product, price, image,details,pid;
    String url;


    SharedPreferences sh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewbuyproduct);
        b1 = findViewById(R.id.button13);
        l1 = findViewById(R.id.listview8);
        s1 = findViewById(R.id.spinner5);
        sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        ArrayAdapter<String> ad = new ArrayAdapter<>(viewbuyproduct.this, android.R.layout.simple_list_item_1, arr);
        s1.setAdapter(ad);


        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                url = "http://" + sh.getString("ip", "") + ":5000/viewandbuyprod";
                RequestQueue queue = Volley.newRequestQueue(viewbuyproduct.this);

                StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the response string.
                        Log.d("+++++++++++++++++", response);
                        try {

                            JSONArray ar = new JSONArray(response);
                            product = new ArrayList<>();
                            price = new ArrayList<>();
                            image = new ArrayList<>();
                            details = new ArrayList<>();
                            pid = new ArrayList<>();


                            for (int i = 0; i < ar.length(); i++) {
                                JSONObject jo = ar.getJSONObject(i);
                                product.add(jo.getString("product"));
                                price.add(jo.getString("price"));
                                image.add(jo.getString("image"));
                                details.add(jo.getString("details") );
                                pid.add(jo.getString("productid"));

                            }

                            // ArrayAdapter<String> ad=new ArrayAdapter<>(Home.this,android.R.layout.simple_list_item_1,name);
                            //lv.setAdapter(ad);
//
                            l1.setAdapter(new custom_buyprod(viewbuyproduct.this, product, image,price));
                            l1.setOnItemClickListener(viewbuyproduct.this);
//
                        } catch (Exception e) {
                            Log.d("=========", e.toString());
                        }

                    }

                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(viewbuyproduct.this, "err"+error, Toast.LENGTH_SHORT).show();
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
        });



    }



    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent ik = new Intent(getApplicationContext(), viewmore.class);
        ik.putExtra("name",product.get(position));
        ik.putExtra("pid",pid.get(position));
        ik.putExtra("image",image.get(position));
        ik.putExtra("details",details.get(position));
        ik.putExtra("price",price.get(position));

        startActivity(ik);

    }
}
