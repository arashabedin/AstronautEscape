package net.arash.dk.astronautescape;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.support.v7.app.AlertDialog;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Random;

import android.app.Fragment;
import android.widget.TextView;
import android.widget.Toast;

import static android.R.attr.fragment;
import static android.R.attr.textViewStyle;

/**
 * Created by Arash on 10/28/2017.
 */

public class GameView extends SurfaceView implements Runnable {
    //Using volatile keyword, so that the variable's value will be modified by different threads
    volatile boolean playing;
    private Thread gameThread = null;
    private Player player;

    //a screenX holder
    int screenX;
    TextView textView;
    String gameOverMsg="";


    //context to be used in onTouchEvent to cause the activity transition from GameAvtivity to MainActivity.
    Context context;

    //the score holder
    int score;

    //the high Scores Holder
    int highScore[] = new int[4];

    //Shared Prefernces to store the High Scores
    SharedPreferences sharedPreferences;


    //indicator that the fuel drop has just entered the game screen
    boolean flag ;

    //an indicator if the game is Over
    private boolean isGameOver ;

    private Paint paint;

    private Canvas canvas;
    private SurfaceHolder surfaceHolder;

    private FuelDrop fuelDrop;


    //created a reference of the class Friend
    private Asteroid asteroid;
    private Planets planets;

    private ArrayList<Star> stars = new
            ArrayList<Star>();

    //defining  boom object
    private Boom boom;




    //the mediaplayer objects to configure the background music
    static  MediaPlayer gameOnsound;


    final MediaPlayer gotFuelSound;

    final MediaPlayer gameOversound;

    Typeface typeface;


    public GameView(Context context, int screenX, int screenY) {
        super(context);
        player = new Player(context, screenX, screenY);


        surfaceHolder = getHolder();
        paint = new Paint();

        //initializing context
        this.context = context;

        int starNums = 100;
        for (int i = 0; i < starNums; i++) {
            Star s = new Star(screenX, screenY);
            stars.add(s);
        }



        AssetManager am = context.getApplicationContext().getAssets();
        typeface = Typeface.createFromAsset(am, String.format(Locale.US, "fonts/%s", "bit.ttf"));

        fuelDrop = new FuelDrop(context,screenX,screenY);

        //initializing boom object
        boom = new Boom(context);



        asteroid = new Asteroid(context, screenX, screenY);



        planets = new Planets(context, screenX, screenY);
        //setting the score to 0 initially
        score = 0;



        this.screenX = screenX;


        isGameOver = false;


        //initializing shared Preferences
        sharedPreferences = context.getSharedPreferences("SHAR_PREF_NAME",Context.MODE_PRIVATE);





        //initializing the media players for the game sounds

            gameOnsound = MediaPlayer.create(context, R.raw.gameambient);


        gotFuelSound = MediaPlayer.create(context,R.raw.gotfuel);
        gameOversound = MediaPlayer.create(context,R.raw.gameover);

        //starting the music to be played across the game
        gameOnsound.start();

    }





    @Override
    public void run() {

        while (playing) {

            update();
            draw();
            control();

        }


    }


    private void update() {


        //incrementing score as time passes
        score++;

        player.update(context);
          if(score<1200) {
        player.setSpeed(((int) score / 100) * 10 + 10);
          }
        //setting boom outside the screen
        boom.setX(-250);
        boom.setY(-250);


        for (Star s : stars) {

            s.update(player.getSpeed());
        }

        //setting the flag true when the fuel drop just enters the screen



        fuelDrop.update(player.getSpeed());
                //if collision occurs with player
                if (Rect.intersects(player.getDetectCollision(), fuelDrop.getDetectCollision())) {


                    //playing a sound at the collision between player and the fuel drop
                    gotFuelSound.start();

                    //increment fuel
                    player.setFuel(player.getFuel()+50);
                    if (player.getFuel()>200){
                        player.setFuel(200);
                    }
                    fuelDrop.setX(-300);
                }




        //updating the friend ships coordinates
        asteroid.update(context,player.getSpeed());
        planets.update(context,player.getSpeed());
                //checking for a collision between player and a friend
                if(Rect.intersects(player.getDetectCollision(), asteroid.getDetectCollision())){

                    //displaying the boom at the collision
                    boom.setX(player.getX()+270);
                    boom.setY(player.getY());
                    //setting playing false to stop the game
                    playing = false;
                    //setting the isGameOver true as the game is over
                    gameOverMsg="Game Over";
                    isGameOver = true;



                    //stopping the gameon music
                    gameOnsound.stop();
                    //play the game over sound
                    gameOversound.start();


                    //Updating Score to database




                    sharedPreferences  = context.getSharedPreferences("SHAR_PREF_NAME", Context.MODE_PRIVATE);
                    SharedPreferences sp = context.getSharedPreferences("my_pref", Activity.MODE_PRIVATE);

                    final String username =  sp.getString("username", "not given");
                    final int score = this.score;
                    final Activity activity = (Activity) context;

                    Response.Listener<String> responseListener = new Response.Listener<String>(){
                        @Override
                        public void onResponse(String response){
                            try {
                                JSONObject jsonResponse = new JSONObject(response);
                                boolean success = jsonResponse.getBoolean("success");
                                if(success){


                                    Toast.makeText(activity, "Your score " + score , Toast.LENGTH_LONG)
                                            .show();
                                }else{

                                    Toast.makeText(activity, "Not Succeed:(", Toast.LENGTH_LONG)
                                            .show();
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    };
                    UpdateScoreRequest updateScoreRequest = new UpdateScoreRequest( username, score,responseListener);

                    RequestQueue queue = Volley.newRequestQueue(activity);
                    queue.add(updateScoreRequest);






                }





        if(player.getFuel()<1){
            gameOverMsg = "No Fuel";
            //setting playing false to stop the game
            playing = false;
            //setting the isGameOver true as the game is over
            isGameOver = true;



            //stopping the gameon music
            gameOnsound.stop();
            //play the game over sound
            gameOversound.start();

            //Assigning the scores to the highscore integer array
            for(int i=0;i<4;i++){

                if(highScore[i]<score){

                    final int finalI = i;
                    highScore[i] = score;
                    break;
                }


            }

            //storing the scores through shared Preferences
            SharedPreferences.Editor e = sharedPreferences.edit();

            for(int i=0;i<4;i++){

                int j = i+1;
                e.putInt("score"+j,highScore[i]);
            }
            e.apply();

        }

    }


    private void draw() {

        if (surfaceHolder.getSurface().isValid()) {
            canvas = surfaceHolder.lockCanvas();
            canvas.drawColor(Color.BLACK);

            paint.setTypeface(typeface);

            paint.setColor(Color.WHITE);
            paint.setTextSize(20);

            for (Star s : stars) {
                paint.setStrokeWidth(s.getStarWidth());
                canvas.drawPoint(s.getX(), s.getY(), paint);
            }
            canvas.drawBitmap(

                    planets.getBitmap(),
                    planets.getX(),
                    planets.getY(),
                    paint
            );

            canvas.drawBitmap(
                    player.getBitmap(),
                    player.getX(),
                    player.getY(),
                    paint);


                canvas.drawBitmap(
                        fuelDrop.getBitmap(),
                        fuelDrop.getX(),
                        fuelDrop.getY(),
                        paint

                );


            //drawing astroids image
            canvas.drawBitmap(

                    asteroid.getBitmap(),
                    asteroid.getX(),
                    asteroid.getY(),
                    paint
            );

            //drawing boom image
            canvas.drawBitmap(
                    boom.getBitmap(),
                    boom.getX(),
                    boom.getY(),
                    paint
            );


            paint.setTextSize(45);
            canvas.drawText("Score:"+score,getWidth()-500,50,paint);
            paint.setTextSize(60);
            paint.setColor(Color.argb(255,251,251,251));

            canvas.drawText("↑",230,250,paint);
            canvas.drawText("↓",230,getHeight()-250,paint);

            //draw game Over when the game is over
            if(isGameOver){
                paint.setTextSize(150);
                paint.setTextAlign(Paint.Align.CENTER);


                int yPos=(int) ((canvas.getHeight() / 2) - ((paint.descent() + paint.ascent()) / 2));



                paint.setTypeface(typeface);
                paint.setColor(Color.argb(255,255,255,255));
                canvas.drawText(gameOverMsg,canvas.getWidth()/2,yPos,paint);

            }

            surfaceHolder.unlockCanvasAndPost(canvas);

        }
    }

    private void control() {
        try {
            gameThread.sleep(17);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void pause() {
        playing = false;
        try {
            gameThread.join();
        } catch (InterruptedException e) {
        }
    }

    public void resume() {
        playing = true;
        gameThread = new Thread(this);
        gameThread.start();
    }

    //stop the music on exit
    public static void stopMusic(){

        gameOnsound.stop();
    }


    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {


        switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_UP:
                player.stopBoosting();
                break;
            case MotionEvent.ACTION_DOWN:
                if(motionEvent.getY() > getHeight()/2){
                player.goUp();
                }else {
                    player.goDown();

                }
                break;

        }


//if the game's over, tappin on game Over screen sends you to MainActivity
        if(isGameOver){

            if(motionEvent.getAction()==MotionEvent.ACTION_DOWN){

                context.startActivity(new Intent(context, MainActivity.class));

            }

        }

        return true;

    }




}

