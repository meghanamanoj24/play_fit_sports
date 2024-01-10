package com.example.play_fit_sports;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Registration extends AppCompatActivity {


    EditText regname,regadde, regmail, regphn,regpass;

    Button rb1,regcal;
    String name, address,email, phonenumber, password,gender,dob,status,message;

    String url = config.baseurl+"register.php";


    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        //binding ids
//        rt1 = findViewById(R.id.t1);

        regname= findViewById(R.id.re);

        regmail = findViewById(R.id.re1);
        regphn= findViewById(R.id.re2);
        regpass = findViewById(R.id.re4);
        rb1 = findViewById(R.id.re5);


        //Intent passing

        rb1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(Registration.this, LoginActivity.class);
                startActivity(in);
            }
        });
//        rt2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i = new Intent(Registration.this, login.class);
//                startActivity(i);
//            }
//        });




        rb1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registration1();
            }
        });


    }

    private void registration1() {
        name = regname.getText().toString();
        email = regmail.getText().toString();
        phonenumber = regphn.getText().toString();
        password = regpass.getText().toString();






        if (TextUtils.isEmpty(name)) {
            regname.requestFocus();
            regname.setError("Enter name");
            return;
        }



        if (TextUtils.isEmpty(email)) {
            regmail.requestFocus();
            regmail.setError("Enter email");
            return;
        }
        if (TextUtils.isEmpty(phonenumber)) {
            regphn.requestFocus();
            regphn.setError("Enter phone number");
            return;
        }
        if (TextUtils.isEmpty(password)) {
            regpass.requestFocus();
            regpass.setError("Enter password");
            return;
        }
        if (password.length() < 6) {
            regpass.setError( "Password Must be 6 Characters");
            return;
        }
        if (phonenumber.length() < 10) {
            regphn.setError( "Enter Valid Phone Number");
            return;
        }
        if (phonenumber.length() > 10) {
            regphn.setError( "Enter Valid Phone Number");
            return;
        }
        StringRequest StringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                           Toast.makeText(Registration.this, response, Toast.LENGTH_SHORT).show();
                        try {
                            JSONObject c = new JSONObject(response);
                            status = c.getString("status");
                            message = c.getString("message");
                            checklogin();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //run cheyikkumbo error indo ennu nokkan
                        Toast.makeText(Registration.this, String.valueOf(error), Toast.LENGTH_SHORT).show();
                    }

                }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("name", name);

                params.put("email", email);
                params.put("phonenumber", phonenumber);
                params.put("password", password);

                return params;
            }


        };

        //string reqt ne execute cheyan aanu requestqueue
        Volley volley =  null;
        RequestQueue requestQueue = volley.newRequestQueue(this);
        requestQueue.add(StringRequest);
    }


    private void checklogin() {
        if (status.equals("2")){
            Toast.makeText(this, "Already Registered", Toast.LENGTH_SHORT).show();
            finish();
        }
        if (status.equals("0")){
            Toast.makeText(this, "Invalied", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(this, "Registered successfully", Toast.LENGTH_SHORT).show();
            Intent i =new Intent(Registration.this,LoginActivity.class);
            startActivity(i);
            finish();
        }

    }
}



