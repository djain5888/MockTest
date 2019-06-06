package com.kanishque.mocktest.UI.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.kanishque.mocktest.Helpers.Helper;
import com.kanishque.mocktest.Helpers.JsonSingleton;
import com.kanishque.mocktest.Helpers.SharedPref;
import com.kanishque.mocktest.Helpers.Url;
import com.kanishque.mocktest.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {


    private final String TAG = getClass().getSimpleName();
    private TextView textView2;
    private EditText Email, Password;
    private CardView login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        SharedPref.init(getApplicationContext());
        Email = findViewById(R.id.email);
        Password = findViewById(R.id.password);
        login = findViewById(R.id.login);
        textView2 = findViewById(R.id.textViewLogin);

        textView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), RegisterActivity.class));

            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mEmail = Email.getText().toString().trim();
                String mPassword = Password.getText().toString().trim();
                Email.getText().clear();
                Password.getText().clear();


                if (checkAuth(mEmail, mPassword)) {
                    if (Helper.isnetworkavailable(LoginActivity.this)) {
                        Login(mEmail, mPassword);

                    } else {
                        //network not available
                        Toast.makeText(LoginActivity.this, "No Active Internet Connection", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    //fields does not match
                    Toast.makeText(LoginActivity.this, "Fields does not Match", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private boolean checkAuth(String email, String pass) {
        if (!email.isEmpty() || !pass.isEmpty()) {
            return true;
        } else {
            Email.setError("Please Enter Email");
            Password.setError("Please Enter Password");
            return false;
        }
    }

    private void Login(final String email, final String password) {
        final ProgressDialog progress;
        progress = new ProgressDialog(this);
        progress.setTitle("Please Wait!!");
        progress.setMessage("Loging In");
        progress.setCancelable(true);
        progress.show();

        //  login.setVisibility(View.GONE);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Url.URL_LOGIN, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progress.dismiss();
                try {
                    Log.d(TAG, "onResponse: Login" + response
                    );
                    JSONObject jsonObject = new JSONObject(response);
                    JSONObject jsonObject1 = jsonObject.getJSONObject("login");
                    JSONArray jsonArray = jsonObject1.getJSONArray("payload");
                    if (jsonObject1.getString("status").equals("True")) {
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject object = jsonArray.getJSONObject(i);
                            //store data
                            SharedPreferences sharedPreferences = getSharedPreferences("user", MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("email", object.getString("email"));
                            editor.putString("name", object.getString("username"));
                            editor.apply();
                            /*SharedPref.write("id",object.getString("id"));
                            SharedPref.write("username",object.getString("username").trim());
                            SharedPref.write("email",object.getString("email").trim());*/
                            //start intent


                            Log.i(TAG, "onResponse: Login" + "LoggedIN");
                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        }
                    } else {
                        Log.d(TAG, "onResponse: Login" + jsonObject.getString("message"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "onErrorResponse: Login ", error);

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("email", email);
                params.put("password", password);
                return params;
            }
        };
        JsonSingleton.getInstance(LoginActivity.this).addToQueue(stringRequest);


    }
}
