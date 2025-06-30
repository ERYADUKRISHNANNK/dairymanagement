package com.example.dairymanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

public class fhome extends AppCompatActivity {
    ImageView b1,b3,b4,b5,b6,b7;

    SharedPreferences sh;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fhome);
        b1=findViewById(R.id.imageView2);

        b3=findViewById(R.id.imageView3);
        b4=findViewById(R.id.imageView22);
        b5=findViewById(R.id.imageView23);
        b6=findViewById(R.id.imageView5);
        b7=findViewById(R.id.imageView9);
        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ii=new Intent(getApplicationContext(),viewpolicy.class);
                startActivity(ii);

            }
        });

        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ii=new Intent(getApplicationContext(),vieworderverify.class);
                startActivity(ii);


            }
        });
        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ii=new Intent(getApplicationContext(),manageproducct.class);
                startActivity(ii);


            }
        });
        b5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ii=new Intent(getApplicationContext(),addmilk.class);
                startActivity(ii);


            }
        });
        b6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ik = new Intent(getApplicationContext(),login.class);
                startActivity(ik);

                Intent ii = new Intent(getApplicationContext(),LocationServiceno.class);
                stopService(ii);

            }
        });
        b7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ii=new Intent(getApplicationContext(),chatuser.class);
                startActivity(ii);


            }
        });




    }
}