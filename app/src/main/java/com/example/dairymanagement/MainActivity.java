package com.example.dairymanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class MainActivity extends AppCompatActivity {
    Button b1;
    EditText e1;
    SharedPreferences sh;
    String ip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        b1=findViewById(R.id.button18);
        e1=findViewById(R.id.editTextTextPersonName30);
        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ip=e1.getText().toString();
//                if (ip.equalsIgnoreCase(""))
//                {
//                    e1.setError("Enter ip address ");
//                }
                if (ip.equalsIgnoreCase(""))
                        {
                    e1.setError("Enter ip");
                } else if (!ip.matches("^[0-9,.]*$")) {
                    e1.setError("alphabet not allowed");
                }
                else {


                    SharedPreferences.Editor edp = sh.edit();
                    edp.putString("ip", ip);
                    edp.commit();

                    Intent ik = new Intent(getApplicationContext(), login.class);
                    startActivity(ik);


                }
            }
        });
    }
}