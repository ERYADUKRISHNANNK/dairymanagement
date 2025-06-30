package com.example.dairymanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
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

public class viewmycart extends AppCompatActivity {
    ListView l1;
    Button b1;
    ArrayList<String>product,image,quantity,price,itemid;
    SharedPreferences sh;
    String url,tot,oid;
    TextView t1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewmycart);
        l1=findViewById(R.id.listview35);
        b1=findViewById(R.id.button39);
        t1=findViewById(R.id.textView80);
        sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ik = new Intent(getApplicationContext(), PaymentActivity.class);
                ik.putExtra("p",tot);
                ik.putExtra("oid",oid);
                startActivity(ik);

            }
        });
        url = "http://" + sh.getString("ip", "") + ":5000/viewcart";
        RequestQueue queue = Volley.newRequestQueue(viewmycart.this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Display the response string.
                Log.d("+++++++++++++++++", response);
                try {
                    JSONObject js=new JSONObject(response);
                    tot=js.getString("d");
                    oid=js.getString("oid");
                    t1.setText(tot);


                    JSONArray ar = js.getJSONArray("data");
                    product = new ArrayList<>();
                    image = new ArrayList<>();
                    quantity = new ArrayList<>();
                    price = new ArrayList<>();
                    itemid = new ArrayList<>();


                    for (int i = 0; i < ar.length(); i++) {
                        JSONObject jo = ar.getJSONObject(i);
                        product.add(jo.getString("product"));
                        image.add(jo.getString("image"));
                        quantity.add(jo.getString("quantity"));
                        price.add(jo.getString("price"));
                        itemid.add(jo.getString("item_id"));

                    }

                    // ArrayAdapter<String> ad=new ArrayAdapter<>(Home.this,android.R.layout.simple_list_item_1,name);
                    //lv.setAdapter(ad);
//
                    l1.setAdapter(new custom_cart(viewmycart.this, product, image,quantity,price,itemid));
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

                Toast.makeText(viewmycart.this, "err"+error, Toast.LENGTH_SHORT).show();
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent ik = new Intent(getApplicationContext(), userhome2.class);

        startActivity(ik);

    }
}