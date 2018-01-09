package net.arash.dk.astronautescape;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Locale;

/**
 * Created by Arash on 10/28/2017.
 */
public class Credit extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credit);
        AssetManager am = getApplicationContext().getAssets();
        Typeface typeface = Typeface.createFromAsset(am, String.format(Locale.US, "fonts/%s", "bit.ttf"));

        //added
        final TextView illustrator = (TextView) findViewById(R.id.illustrator);
        final TextView developer = (TextView) findViewById(R.id.developer);
        final TextView music = (TextView) findViewById(R.id.music);
        final TextView website = (TextView) findViewById(R.id.website);

        illustrator.setTypeface(typeface);
        developer.setTypeface(typeface);
        music.setTypeface(typeface);
        website.setTypeface(typeface);

        illustrator.setText("Designer: Arash Abedin");
        developer.setText("Developer: Arash Abedin");
        music.setText("Music: Pcorf , Jean-Michel Jarre");
        website.setText("www.arash.dk");


website.setOnClickListener(new View.OnClickListener(){

    @Override
     public void onClick( View v){
        String url = "http://www.arash.dk";

        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }
});



    }
}
