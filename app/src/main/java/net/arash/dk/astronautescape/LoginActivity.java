package net.arash.dk.astronautescape;
//added
import android.app.Activity;
import android.content.Intent;


import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.content.Context;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Locale;
public class LoginActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);





        AssetManager am = getApplicationContext().getAssets();
        Typeface typeface = Typeface.createFromAsset(am, String.format(Locale.US, "fonts/%s", "bit.ttf"));

        //added
        final EditText etUsername = (EditText) findViewById(R.id.etUsername);
        final EditText etPassword = (EditText) findViewById(R.id.etPassword);
        final Button bLogin = (Button) findViewById(R.id.bLogin);
        final Button registerLink = (Button) findViewById(R.id.bRegister);
        registerLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerIntent = new Intent(LoginActivity.this ,RegisterActivity.class );
                LoginActivity.this.startActivity(registerIntent);

            }
        });
        SharedPreferences sp = getSharedPreferences("my_pref", Activity.MODE_PRIVATE);
        String myUsername = sp.getString("username", "");
        String myPassword = sp.getString("password", "");

        etUsername.setText(myUsername);
        etPassword.setText(myPassword);

        etUsername.setTypeface(typeface);
        etPassword.setTypeface(typeface);
        bLogin.setTypeface(typeface);
        registerLink.setTypeface(typeface);


        bLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String username = etUsername.getText().toString();
                final String password = etPassword.getText().toString();

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if(success){
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                intent.putExtra("username", username);


                                SharedPreferences sp = getSharedPreferences("my_pref", Activity.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sp.edit();
                                editor.clear();
                                editor.putString("username", username);
                                editor.putString("password", password);
                                editor.commit();

                                LoginActivity.this.startActivity(intent);
                            }else{

                                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                                builder.setMessage("Username or Password is wrong").setNegativeButton("Retry",null).create().show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                };



                if(username.isEmpty() ||  password.isEmpty() ){
                    AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                    builder.setMessage("Please fill all the fields").setNegativeButton("Try again", null).create().show();

                }else{

                LoginRequest loginRequest = new LoginRequest(username,password,responseListener);
                RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
                queue.add(loginRequest);
                }
                if(!isNetworkConnected()){

                    AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                    builder.setMessage("No internet connection:(").setNegativeButton("Try again", null).create().show();

                }
            }


        });
    }


    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;
    }
}
