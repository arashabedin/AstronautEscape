package net.arash.dk.astronautescape;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import net.arash.dk.astronautescape.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Locale;

/**
 * Created by Arash on 10/28/2017.
 */
public class HighScore extends AppCompatActivity {

    ListView lv;
    ArrayAdapter<String> adapter;
    String address="http://arash.dk/astronaut/Scores.php";
    InputStream is = null;
    String line = null;
    String result = null;
    String[] data = null;
    TextView scoreTitle;
    TextView userScore;
    String myUsername;
    int myScoreLocation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_score);
        AssetManager am = getApplicationContext().getAssets();
        Typeface typeface = Typeface.createFromAsset(am, String.format(Locale.US, "fonts/%s", "bit.ttf"));
        lv = (ListView) findViewById(R.id.ListView1);
        SharedPreferences sp = getSharedPreferences("my_pref", Activity.MODE_PRIVATE);
        myUsername = sp.getString("username", "");
        myScoreLocation=-1;
      //  lv.text(typeface);

        //ALLOW NETWORK IN MAIN THREAD
        StrictMode.setThreadPolicy((new StrictMode.ThreadPolicy.Builder().permitNetwork().build()));

        //RETRIEVE
        getData();

        //ADAPTER

        adapter = new ArrayAdapter<String>(this, R.layout.custome_list_layout,data);
        lv.setAdapter(adapter);
        scoreTitle = (TextView) findViewById(R.id.scoreTitle);
        userScore = (TextView) findViewById(R.id.userScore);
        scoreTitle.setTypeface(typeface);
        userScore.setTypeface(typeface);
        scoreTitle.setText("Your Rank");
        if(myScoreLocation<0){
            userScore.setText("You haven't played yet");
        }else{
        userScore.setText(data[myScoreLocation]);
        }
    }


    private void getData(){
        try {
            URL url = new URL(address);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            is = new BufferedInputStream(con.getInputStream());
        }catch(Exception e){
            e.printStackTrace();
        }

        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            StringBuilder sb = new StringBuilder();

            while((line = br.readLine())!= null){
                sb.append(line+ "\n");
            }
            is.close();
            result = sb.toString();

        }catch (Exception e){
            e.printStackTrace();
        }


        //Parse JSON DATA

        try{
            JSONArray ja = new JSONArray(result);
            JSONObject jo = null;

            data = new String[ja.length()];

            for(int i = 0; i< ja.length(); i++){

                jo= ja.getJSONObject(i);
                if(jo.getString("username").equals(myUsername)){
                    myScoreLocation = i;
                }
                data[i] =(i + 1) + "  -  " + jo.getString("username");
                data[i] += "    " +  jo.getString("score");

            }

        }catch (Exception e){
            e.printStackTrace();
        }


    }


}
