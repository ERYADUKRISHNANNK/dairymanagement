package com.example.dairymanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
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

public class viewmore extends AppCompatActivity {
    Button b1,b2;
    ImageView i1;
    EditText e1;
    SharedPreferences sh;
    String qty;
    ArrayList<String> product,price,quantity;
    String url;
    TextView t1,t2;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewmore);
        i1 = (ImageView) findViewById(R.id.imageView);
        b1=findViewById(R.id.button15);
        b2=findViewById(R.id.button14);
        e1=findViewById(R.id.editTextTextPersonName27);
        t1=findViewById(R.id.textView62);
        t2=findViewById(R.id.textView63);
        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        t1.setText(getIntent().getStringExtra("name"));
        t2.setText(getIntent().getStringExtra("price"));
        java.net.URL thumb_u;
        try {

            //thumb_u = new java.net.URL("http://192.168.43.57:5000/static/photo/flyer.jpg");

            thumb_u = new java.net.URL("http://"+sh.getString("ip","")+":5000/static/product/"+getIntent().getStringExtra("image"));
            Drawable thumb_d = Drawable.createFromStream(thumb_u.openStream(), "src");
            i1.setImageDrawable(thumb_d);

        }
        catch (Exception e)
        {
            Log.d("errsssssssssssss",""+e);
        }



        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                qty=e1.getText().toString();
                RequestQueue queue = Volley.newRequestQueue(viewmore.this);
                String url = "http://" + sh.getString("ip","")+":5000/add_to_cart";

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

                                Toast.makeText(getApplicationContext(), "added to cart", Toast.LENGTH_LONG).show();
                                Intent i=new Intent(getApplicationContext(),viewbuyproduct.class);
                                startActivity(i);
                            } else {
                                Toast.makeText(getApplicationContext(), "out of stock", Toast.LENGTH_LONG).show();
                                Intent i=new Intent(getApplicationContext(),viewbuyproduct.class);
                                startActivity(i);
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
                        params.put("quantity", qty);
                        params.put("lid", sh.getString("lid",""));
                        params.put("pid",getIntent().getStringExtra("pid"));







                        return params;
                    }
                };
                queue.add(stringRequest);









            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                qty=e1.getText().toString();
                RequestQueue queue = Volley.newRequestQueue(viewmore.this);
                String url = "http://" + sh.getString("ip","")+":5000/order";

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
                                String oid=json.getString("oid");
                                String tot=json.getString("tot");

                                Toast.makeText(getApplicationContext(), "ordered", Toast.LENGTH_LONG).show();
                                Intent i=new Intent(getApplicationContext(),PaymentActivity.class);
                                i.putExtra("p",tot);
                                i.putExtra("oid",oid);
                                startActivity(i);
                            } else {
                                Toast.makeText(getApplicationContext(), "out of stock", Toast.LENGTH_LONG).show();
                                Intent i=new Intent(getApplicationContext(),viewbuyproduct.class);
                                startActivity(i);
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
                        params.put("qty", qty);
                        params.put("lid", sh.getString("lid",""));
                        params.put("pid",getIntent().getStringExtra("pid"));







                        return params;
                    }
                };
                queue.add(stringRequest);




            }


        });

    }
}