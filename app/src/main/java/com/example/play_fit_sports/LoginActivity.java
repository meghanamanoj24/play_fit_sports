package com.example.play_fit_sports;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

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

import javax.security.auth.login.LoginException;

public class LoginActivity extends AppCompatActivity {
    EditText name,password;
    Button b1,b2;
String n,p,status,message;
String url=config.baseurl+"login.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        name=findViewById(R.id.ln);
        password=findViewById(R.id.ln1);
        b1=findViewById(R.id.ln2);
        b2=findViewById(R.id.ln3);


        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in=new Intent(LoginActivity.this, Registration.class);
                startActivity(in);
            }

        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in=new Intent(LoginActivity.this, Registration.class);
                startActivity(in);
            }
        });
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login1();
            }
        });
    }

    private void login1() {
        n=name.getText().toString();
        p=password.getText().toString();

        if(TextUtils.isEmpty(n)){
            b1.requestFocus();
            b1.setError("Invalid Name");
            return;

        }
        if(TextUtils.isEmpty(p)){
            b1.requestFocus();
            b1.setError("Invalid Password");
            return;

        }
      ;
        StringRequest string=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(LoginActivity.this,response, Toast.LENGTH_SHORT).show();
                try {
                    JSONObject json = new JSONObject(response);
                    status = json.getString("status");
                    message = json.getString("message");
                } catch (JSONException ex) {
                    ex.printStackTrace();
                }

                if ("1".equals(status)) {
                    Toast.makeText(LoginActivity.this, "Successful", Toast.LENGTH_SHORT).show();
                    Intent j= new Intent(LoginActivity.this,Registration.class);
                    startActivity(j);
                } else if("2".equals(status)) {
                    Toast.makeText(LoginActivity.this, "Already Registered.Please Try again", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(LoginActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(LoginActivity.this, String.valueOf(error), Toast.LENGTH_SHORT).show();
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map=new HashMap<>();
                map.put("name",n);
                map.put("password",p);

                return map;
            }
        };
        RequestQueue req= Volley.newRequestQueue(this);
        req.add(string);

    }

}


