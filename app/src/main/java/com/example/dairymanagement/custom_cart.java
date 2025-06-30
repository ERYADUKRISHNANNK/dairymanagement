package com.example.dairymanagement;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
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

public class custom_cart extends BaseAdapter {
    private Context context;
    ArrayList<String> a;
    ArrayList<String> b;
    ArrayList<String> c;
    ArrayList<String> d;
    ArrayList<String> e;



    SharedPreferences sh;



    public custom_cart(Context applicationContext, ArrayList<String> a, ArrayList<String> b, ArrayList<String> c, ArrayList<String> d, ArrayList<String> e) {
        // TODO Auto-generated constructor stub
        this.context=applicationContext;
        this.a=a;
        this.b=b;
        this.c=c;
        this.d=d;
        this.e=e;



        sh=PreferenceManager.getDefaultSharedPreferences(applicationContext);
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return a.size();
    }

    @Override
    public Object getItem(int arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public long getItemId(int arg0) {
        // TODO Auto-generated method stub
        return 0;
    }
    @Override
    public int getItemViewType(int arg0) {
        // TODO Auto-generated method stub
        return 0;
    }


    @Override
    public View getView(int position, View convertview, ViewGroup parent) {
        // TODO Auto-generated method stub
        LayoutInflater inflator=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View gridView;
        if(convertview==null)
        {
            gridView=new View(context);
            gridView=inflator.inflate(R.layout.activity_custom_cart, null);

        }
        else
        {
            gridView=(View)convertview;

        }
        TextView tv1=(TextView)gridView.findViewById(R.id.tvroom);
        ImageView i1=(ImageView) gridView.findViewById(R.id.imgaprtmnt);

        TextView tv2=(TextView)gridView.findViewById(R.id.tvbalcony);

        TextView tv3=(TextView)gridView.findViewById(R.id.tvroom2);
        TextView tv4=(TextView)gridView.findViewById(R.id.textView86);
        Button b1=(Button) gridView.findViewById(R.id.button42);
        Button b2=(Button) gridView.findViewById(R.id.button43);

        tv4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RequestQueue queue = Volley.newRequestQueue(context);
                String url = "http://" + sh.getString("ip","") + ":5000/updateqty";

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
                                Intent ik = new Intent(context.getApplicationContext(),viewmycart.class);
                                context.startActivity(ik);



                            }
                            else  {



                            }
                        } catch (JSONException e) {
                            Toast.makeText(context, "aaaaaaa"+e, Toast.LENGTH_SHORT).show();

                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {


                        Toast.makeText(context.getApplicationContext(), "Error" + error, Toast.LENGTH_LONG).show();
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("itemid", e.get(position));
                        params.put("qty", "0");

                        return params;
                    }
                };
                queue.add(stringRequest);


            }
        });


        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View s= (View) v.getParent();
                TextView qnt=s.findViewById(R.id.tvbalcony);
                int rgfh=Integer.parseInt(qnt.getText().toString())-1;



                RequestQueue queue = Volley.newRequestQueue(context);
                String url = "http://" + sh.getString("ip","") + ":5000/updateqty";

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
                                Intent ik = new Intent(context.getApplicationContext(),viewmycart.class);
                                context.startActivity(ik);



                            }
                            else  {



                            }
                        } catch (JSONException e) {
                            Toast.makeText(context, "aaaaaaa"+e, Toast.LENGTH_SHORT).show();

                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {


                        Toast.makeText(context.getApplicationContext(), "Error" + error, Toast.LENGTH_LONG).show();
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("itemid", e.get(position));
                        params.put("qty", rgfh+"");

                        return params;
                    }
                };
                queue.add(stringRequest);


            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View s= (View) v.getParent();
                TextView qnt=s.findViewById(R.id.tvbalcony);
                int rgfh=Integer.parseInt(qnt.getText().toString())+1;

                qnt.setText(rgfh+"");

                RequestQueue queue = Volley.newRequestQueue(context);
                String url = "http://" + sh.getString("ip","") + ":5000/updateqty";

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
                                Intent ik = new Intent(context.getApplicationContext(),viewmycart.class);
                                context.startActivity(ik);



                            }
                            else  {



                            }
                        } catch (JSONException e) {
                            Toast.makeText(context, "aaaaaaa"+e, Toast.LENGTH_SHORT).show();

                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {


                        Toast.makeText(context.getApplicationContext(), "Error" + error, Toast.LENGTH_LONG).show();
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("itemid", e.get(position));
                        params.put("qty", rgfh+"");

                        return params;
                    }
                };
                queue.add(stringRequest);



            }
        });

        java.net.URL thumb_u;
        try {

            //thumb_u = new java.net.URL("http://192.168.43.57:5000/static/photo/flyer.jpg");

            thumb_u = new java.net.URL("http://"+sh.getString("ip","")+":5000/static/product/"+b.get(position));
            Drawable thumb_d = Drawable.createFromStream(thumb_u.openStream(), "src");
            i1.setImageDrawable(thumb_d);

        }
        catch (Exception e)
        {
            Log.d("errsssssssssssss",""+e);
        }


        tv1.setText(a.get(position));
        tv2.setText(c.get(position));
        tv3.setText(d.get(position));




        tv1.setTextColor(Color.BLACK);
        tv2.setTextColor(Color.BLACK);
        tv3.setTextColor(Color.BLACK);




        if(android.os.Build.VERSION.SDK_INT>9)
        {
            StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }







        return gridView;

    }

}
