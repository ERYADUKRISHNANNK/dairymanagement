package com.example.dairymanagement;


import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class regdelboy extends AppCompatActivity {
    Button b1, b2;
    EditText e1, e2, e3, e4, e5, e6, e7,e8;
    SharedPreferences sh;
    String Name, Age, email,lisence, contact, address, uname, pass;



    String res;
    String fileName = "", path = "";
    private static final int FILE_SELECT_CODE = 0;
    String dob, obective, house, place, post, pin, father, mother, quardn, relatn;
    String gender = "";
    String url, ip, lid,title,url1;
    String PathHolder="";
    byte[] filedt=null;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regdelboy);
        b1 = findViewById(R.id.button41);
        b2 = findViewById(R.id.button44);
        e1 = findViewById(R.id.editTextTextPersonName6);
        e2 = findViewById(R.id.editTextTextPersonName16);
        e3 = findViewById(R.id.editTextTextPersonName18);
        e4 = findViewById(R.id.editTextTextPersonName29);
        e5 = findViewById(R.id.editTextTextPersonName19);
        e6 = findViewById(R.id.editTextTextPostalAddress);
        e7 = findViewById(R.id.editTextTextPersonName31);
        e8 = findViewById(R.id.editTextTextPassword3);
        sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        url = "http://" + sh.getString("ip","") + ":5000/regdelboy";

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("*/*");
//            intent.setType("application/pdf");
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                startActivityForResult(intent, 7);




            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Name = e1.getText().toString();
                Age = e2.getText().toString();
                email = e3.getText().toString();
                lisence=e4.getText().toString();
                contact = e5.getText().toString();
                address = e6.getText().toString();
                uname = e7.getText().toString();
                pass = e8.getText().toString();


                if (Name.equalsIgnoreCase(""))
                {
                    e1.setError("Enter first name");
                }
                else if (!Name.matches("^[a-z A-Z ]*$")) {
                    e1.setError("characters allowed");
                }
                else if (Age.equalsIgnoreCase("")) {
                    e2.setError("Enter age");
                } else if (Age.length() != 2) {
                    e2.setError("Invalid age");
                }
                else if (email.equalsIgnoreCase(""))
                {
                    e3.setError("Enter Email");
                }

                else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches())
                {
                    e3.setError("Enter Valid Email");
                    e3.requestFocus();
                }
                else if (lisence.equalsIgnoreCase(""))
                {
                    e4.setError("Enter path of your lisence");
                }
                else if(contact.equalsIgnoreCase(""))
                {
                    e5.setError("Enter Your Phone No");
                }
                else if(contact.length()!=10)
                {
                    e5.setError(" 10 nos required");

                    e5.requestFocus();
                }
                else if(address.equalsIgnoreCase(""))
                {
                    e6.setError("Enter Your address");
                }
                else if(uname.equalsIgnoreCase(""))
                {
                    e7.setError("Enter Your username");
                }
                else if(pass.equalsIgnoreCase(""))
                {
                    e8.setError("Enter Your password");
                }
                else if (!Name.matches("^[a-z A-Z 0-9]*$")) {
                    e8.setError("characters allowed");
                }
                else{
                    uploadBitmap(title);
                }









            }

        });
    }
    ProgressDialog pd;
    private void uploadBitmap(final String title) {
        pd=new ProgressDialog(regdelboy.this);
        pd.setMessage("Uploading....");
        pd.show();
        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, url,
                new Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response1) {
                        pd.dismiss();
                        String x=new String(response1.data);
                        try {
                            JSONObject obj = new JSONObject(new String(response1.data));
//                        Toast.makeText(Upload_agreement.this, "Report Sent Successfully", Toast.LENGTH_LONG).show();
                            if (obj.getString("task").equalsIgnoreCase("success")) {

                                Toast.makeText(regdelboy.this, "Successfully uploaded", Toast.LENGTH_LONG).show();
                                Intent i=new Intent(getApplicationContext(),login.class);
                                startActivity(i);
                            } else {
                                Toast.makeText(getApplicationContext(), "failed", Toast.LENGTH_LONG).show();
                            }

                        } catch (Exception e) {
                            Toast.makeText(getApplicationContext(), "Error" + e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();


                params.put("name", Name);
                params.put("age", Age);
                params.put("email", email);
                params.put("contact", contact);
                params.put("adrs", address);
                params.put("uname", uname);
                params.put("pass", pass);

                if (Name.equalsIgnoreCase("enter your name"))
                {
                    e1.setError("Enter first name");
                }

                return params;
            }

            @Override
            protected Map<String, DataPart> getByteData() {
                Map<String, DataPart> params = new HashMap<>();
                long imagename = System.currentTimeMillis();
                params.put("file", new DataPart(PathHolder , filedt ));









                return params;
            }
        };

        Volley.newRequestQueue(this).add(volleyMultipartRequest);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 7:
                if (resultCode == RESULT_OK) {
                    Uri uri = data.getData();
                    Log.d("File Uri", "File Uri: " + uri.toString());
                    // Get the path
                    try {
                        PathHolder = FileUtils.getPathFromURI(this, uri);
//                        PathHolder = data.getData().getPath();
//                        Toast.makeText(this, PathHolder, Toast.LENGTH_SHORT).show();

                        filedt = getbyteData(PathHolder);
                        Log.d("filedataaa", filedt + "");
//                        Toast.makeText(this, filedt+"", Toast.LENGTH_SHORT).show();
                        e4.setText(PathHolder);
                    }
                    catch (Exception e){
                        Toast.makeText(this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
                break;
        }
    }

    private byte[] getbyteData(String pathHolder) {
        Log.d("path", pathHolder);
        File fil = new File(pathHolder);
        int fln = (int) fil.length();
        byte[] byteArray = null;
        try {
            InputStream inputStream = new FileInputStream(fil);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            byte[] b = new byte[fln];
            int bytesRead = 0;

            while ((bytesRead = inputStream.read(b)) != -1) {
                bos.write(b, 0, bytesRead);
            }
            byteArray = bos.toByteArray();
            inputStream.close();
        } catch (Exception e) {
        }
        return byteArray;


    }






}