package net.arash.dk.astronautescape;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import net.arash.dk.astronautescape.R;
import android.preference.PreferenceManager;

import java.util.Locale;


public  class MainActivity extends Activity implements View.OnClickListener {

    //image button
    private ImageButton buttonPlay;
    // the high score button
    private ImageButton buttonScore;
    private ImageButton buttonCredit;
    private TextView closeProgram;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        AssetManager am = getApplicationContext().getAssets();
        final  Typeface typeface = Typeface.createFromAsset(am, String.format(Locale.US, "fonts/%s", "bit.ttf"));



        //setting the orientation to landscape
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);


        closeProgram = (TextView) findViewById(R.id.close);
        closeProgram.setTypeface(typeface);
        closeProgram.setOnClickListener(this);


        //getting the button
        buttonPlay = (ImageButton) findViewById(R.id.buttonPlay);

        //initializing the highscore button
        buttonScore = (ImageButton) findViewById(R.id.buttonScore);
        buttonCredit = (ImageButton) findViewById(R.id.buttonCredit);

        //setting the on click listener to play now button
        buttonPlay.setOnClickListener(this);
        //setting the on click listener to high score button
        buttonScore.setOnClickListener(this);
        //setting the on click listener to play now button
        buttonCredit.setOnClickListener(this);


        final TextView etUsername = (TextView) findViewById(R.id.etMyUsername);
        Intent intent = getIntent();

         String username = "";
        etUsername.setTypeface(typeface);
        if ((savedInstanceState != null)
                  && (savedInstanceState.getSerializable("username") != null)) {
            username = (String) savedInstanceState
                             .getSerializable("username");
            }

        if(username!= null) {
            etUsername.setText("^_^ " + username);
        }




        if ( username == "not given") {
            //show start activity

            startActivity(new Intent(MainActivity.this, LoginActivity.class));

        }



    }


    @Override
    public void onClick(View v) {

        if(v==buttonPlay){

            //the transition from MainActivity to GameActivity
            startActivity(new Intent(MainActivity.this, GameActivity.class));
        }
        if(v==buttonScore){
            //the transition from MainActivity to HighScore activity
            startActivity(new Intent(MainActivity.this, HighScore.class));
        }
        if(v==buttonCredit){
            //the transition from MainActivity to HighScore activity
            startActivity(new Intent(MainActivity.this, Credit.class));
        }

        if(v==closeProgram){
            onBackPressed();
        }


    }
    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        Intent intent = new Intent(Intent.ACTION_MAIN);
                        intent.addCategory(Intent.CATEGORY_HOME);
                        startActivity(intent);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();

    }

    @Override
    protected void onSaveInstanceState(Bundle state) {
        super.onSaveInstanceState(state);
        SharedPreferences sp = getSharedPreferences("my_pref", Activity.MODE_PRIVATE);
        String myUsername = sp.getString("username", "not given");
        state.putSerializable("username", myUsername);
    }






}