package net.arash.dk.astronautescape;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.media.MediaPlayer;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import net.arash.dk.astronautescape.R;

import java.util.ArrayList;

public class GameView extends SurfaceView implements Runnable {

    volatile boolean playing;
    private Thread gameThread = null;
    private Player player;

    //a screenX holder
    int screenX;


    //context to be used in onTouchEvent to cause the activity transition from GameAvtivity to MainActivity.
    Context context;

    //the score holder
    int score;

    //the high Scores Holder
    int highScore[] = new int[4];

    //Shared Prefernces to store the High Scores
    SharedPreferences sharedPreferences;


    //to count the number of Misses
    int countMisses;

    //indicator that the enemy has just entered the game screen
    boolean flag ;

    //an indicator if the game is Over
    private boolean isGameOver ;

    private Paint paint;

    private Canvas canvas;
    private SurfaceHolder surfaceHolder;

    private Astroid.FuelDrop fuelDrop;



    //created a reference of the class Friend
    private Astroid astroid;
    private Planets planets;

    private ArrayList<Star> stars = new
            ArrayList<Star>();

    //defining  boom and Revive objects
    private Astroid.Boom boom;
    private Revive revive;



    //the mediaplayer objects to configure the background music
    static  MediaPlayer gameOnsound;

    final MediaPlayer killedEnemysound;

    final MediaPlayer gameOversound;




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




        fuelDrop = new Astroid.FuelDrop(context,screenX,screenY);

        //initializing boom and revive objects
        boom = new Astroid.Boom(context);
        revive = new Revive(context);

        //initializing the Friend class object



        astroid = new Astroid(context, screenX, screenY);



        planets = new Planets(context, screenX, screenY);
        //setting the score to 0 initially
        score = 0;

        //setting the countMisses to 0 initially
        countMisses = 0;


        this.screenX = screenX;


        isGameOver = false;


        //initializing shared Preferences
        sharedPreferences = context.getSharedPreferences("SHAR_PREF_NAME",Context.MODE_PRIVATE);


        //initializing the array high scores with the previous values
        highScore[0] = sharedPreferences.getInt("score1",0);
        highScore[1] = sharedPreferences.getInt("score2",0);
        highScore[2] = sharedPreferences.getInt("score3",0);
        highScore[3] = sharedPreferences.getInt("score4",0);


        //initializing the media players for the game sounds
        gameOnsound = MediaPlayer.create(context, R.raw.gameambient);
        killedEnemysound = MediaPlayer.create(context,R.raw.killedenemy);
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
        player.setSpeed(((int)score/100)*10+10);

        //setting boom and revive outside the screen
        boom.setX(-250);
        boom.setY(-250);

        revive.setX(-250);
        revive.setY(-250);

        for (Star s : stars) {

            s.update(player.getSpeed());
        }

        //setting the flag true when the enemy just enters the screen
        if(fuelDrop.getX()==screenX){

            flag = true;
        }


        fuelDrop.update(player.getSpeed());
                //if collision occurs with player
                if (Rect.intersects(player.getDetectCollision(), fuelDrop.getDetectCollision())) {

                    //displaying revive at that location
                    revive.setX(fuelDrop.getX());
                    revive.setY(fuelDrop.getY());


                    //playing a sound at the collision between player and the enemy
                    killedEnemysound.start();

                    fuelDrop.setX(-200);
                }

                else{// the condition where player misses the enemy

                    //if the enemy has just entered
                    if(flag){

                        //if player's x coordinate is equal to enemies's y coordinate
                        if(player.getDetectCollision().exactCenterX()>=fuelDrop.getDetectCollision().exactCenterX()){

                            //increment countMisses

                            player.setFuel(player.getFuel()+30);
                            if (player.getFuel()>200){
                                player.setFuel(200);
                            }

                            //setting the flag false so that the else part is executed only when new enemy enters the screen
                            flag = false;

                        }
                        }

                }



        //updating the friend ships coordinates
        astroid.update(context,player.getSpeed());
        planets.update(context,player.getSpeed());
                //checking for a collision between player and a friend
                if(Rect.intersects(player.getDetectCollision(),astroid.getDetectCollision())){

                    //displaying the boom at the collision
                    boom.setX(player.getX()+270);
                    boom.setY(player.getY());
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





        if(player.getFuel()<1){

            //displaying the boom at the collision
           // boom.setX(player.getX()+270);
            //boom.setY(player.getY());

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

            //drawing the score on the game screen
            paint.setTextSize(30);
            canvas.drawText("Score:"+score,100,50,paint);

            //drawing friends image
            canvas.drawBitmap(

                    astroid.getBitmap(),
                    astroid.getX(),
                    astroid.getY(),
                    paint
            );

            //drawing boom image
            canvas.drawBitmap(
                    boom.getBitmap(),
                    boom.getX(),
                    boom.getY(),
                    paint
            );

            //drawing revive image
            canvas.drawBitmap(
                    revive.getBitmap(),
                    revive.getX(),
                    revive.getY(),
                    paint
            );






            //draw game Over when the game is over
            if(isGameOver){
                paint.setTextSize(150);
                paint.setTextAlign(Paint.Align.CENTER);


                int yPos=(int) ((canvas.getHeight() / 2) - ((paint.descent() + paint.ascent()) / 2));



           //     Typeface myCustomFont = Typeface.createFromAsset(context.getAssets(), "fonts/visitor2.tff");
             //   paint.setTypeface(myCustomFont);

                canvas.drawText("Game Over",canvas.getWidth()/2,yPos,paint);

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

