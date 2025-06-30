package com.example.dairymanagement;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;


import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class userhome2 extends AppCompatActivity  implements NavigationView.OnNavigationItemSelectedListener{



    ImageButton B1,B2,B3,B4,B5;
    ImageView I1;
    TextView T1,t2;
    SharedPreferences sh;
    String url,name,photo;
    ListView L1;

    ArrayList<String>heading,content;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userhome2);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
//        View linearLayout=navigationView.inflateHeaderView(R.layout.nav_header_home);
        if(android.os.Build.VERSION.SDK_INT>9)
        {
            StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }


//        T1.setText("xcxcvv");

//T1.setText("ffffffff");

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

//        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);


        navigationView.setNavigationItemSelectedListener(this);



    }














    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.userhome2, menu);
        return true;
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id= item.getItemId();
        if(id==R.id.nav_home)
        {
            Intent i=new Intent(getApplicationContext(),viewnearshop.class);
            startActivity(i);

        }
        if(id==R.id.nav_gallery)
        {
            Intent i=new Intent(getApplicationContext(),viewbuyproduct.class);
            startActivity(i);

        }
if(id==R.id.booking)
        {
            Intent i=new Intent(getApplicationContext(),viewmybooking.class);
            startActivity(i);

        }
if(id==R.id.cart)
        {
            Intent i=new Intent(getApplicationContext(),viewmycart.class);
            startActivity(i);

        }

        if(id==R.id.o_status)
        {
            Intent i=new Intent(getApplicationContext(),view_o_status.class);
            startActivity(i);

        }


        if(id==R.id.feed)
        {
            Intent i=new Intent(getApplicationContext(),feedback.class);
            startActivity(i);

        }if(id==R.id.comp)
        {
            Intent i=new Intent(getApplicationContext(),complaint.class);
            startActivity(i);

        }if(id==R.id.noti)
        {
            Intent i=new Intent(getApplicationContext(),viewnotification.class);
            startActivity(i);

        }if(id==R.id.chat)
        {
            Intent i=new Intent(getApplicationContext(),chatfarmer.class);
            startActivity(i);

        }if(id==R.id.logout)
        {
            Intent i=new Intent(getApplicationContext(),login.class);
            startActivity(i);
            Intent ii = new Intent(getApplicationContext(),LocationServiceno.class);
            stopService(ii);

        }

        return false;
    }
}