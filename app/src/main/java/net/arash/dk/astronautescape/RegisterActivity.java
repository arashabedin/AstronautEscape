package net.arash.dk.astronautescape;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

//added
import android.view.View;
import com.android.volley.toolbox.Volley;


import android.widget.Button;

import android.widget.EditText;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Locale;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register2);
        AssetManager am = getApplicationContext().getAssets();
        final  Typeface typeface = Typeface.createFromAsset(am, String.format(Locale.US, "fonts/%s", "bit.ttf"));
        //added
        final EditText etUsername = (EditText) findViewById(R.id.etUsername);
        final EditText etPassword = (EditText) findViewById(R.id.etPassword);
        final EditText etPassword2 = (EditText) findViewById(R.id.etPassword2);

        final Button bRegister = (Button) findViewById(R.id.bRegister);
        etUsername.setTypeface(typeface);
        etPassword.setTypeface(typeface);
        etPassword2.setTypeface(typeface);
        bRegister.setTypeface(typeface);

        bRegister.setOnClickListener( new View.OnClickListener(){
       @Override
            public void onClick(View v){

           final String username = etUsername.getText().toString();
           final String password = etPassword.getText().toString();
           final String password2 = etPassword2.getText().toString();

if(username.isEmpty() ||  password.isEmpty()|| password2.isEmpty() ){
    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
    builder.setMessage("Please fill all the fields").setNegativeButton("Try again", null).create().show();

}
else if(!password.equals(password2) ){

    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
    builder.setMessage("Password and confirm password don't match").setNegativeButton("Try again", null).create().show();


}else {


    Response.Listener<String> responseListener = new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {

            try {
                JSONObject jsonResponse = new JSONObject(response);
                boolean success = jsonResponse.getBoolean("success");
                if (success) {
                    Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                    SharedPreferences sp = getSharedPreferences("my_pref", Activity.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sp.edit();
                    editor.clear();
                    editor.putString("username", username);
                    editor.putString("password", password);
                    editor.commit();
                    RegisterActivity.this.startActivity(intent);
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                    builder.setMessage("The nickname has already been taken").setNegativeButton("Try another", null).create().show();

                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };
    RegisterRequest registerRequest = new RegisterRequest(username, password, responseListener);
    RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
    queue.add(registerRequest);
}}

        });


    }
}
