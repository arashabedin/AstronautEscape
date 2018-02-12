package net.arash.dk.astronautescape;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import net.arash.dk.astronautescape.R;

import java.util.Random;
/**
 * Created by Arash on 10/28/2017.
 */

public class Player {
    private Bitmap bitmap;
    private int x;
    private int y;
    private int speed = 50;
    private boolean boosting;
    public boolean goUp;
    public boolean goDown;

    private final int GRAVITY = -10;
    private int maxY;
    private int minY;

    private final int MIN_SPEED = 50;
    private final int MAX_SPEED = 2000;

    private Rect detectCollision;
    private boolean condition = false;

    private int fuel = 200;

    public int getFuel(){
        return fuel;
    }
    public void setFuel(int fuel){
        this.fuel = fuel;
    }


    public void setBitmap(Bitmap bitmap){
        this.bitmap = bitmap;
    }

    public Player(Context context, int screenX, int screenY) {
        x = 75;
        y = 50;
        speed = 1;
        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.player);
        maxY = screenY - bitmap.getHeight();
        minY = 0;
        boosting = false;
        goUp= false;
        goDown=false;

        //initializing rect object
        detectCollision =  new Rect(x, y, bitmap.getWidth(), bitmap.getHeight());
    }

    public void setBoosting() {
        boosting = true;
    }

    public void stopBoosting() {
        boosting = false;
        goDown =false;
        goUp =false;

    }

    public void goUp(){
        goUp =true;
    }
    public void goDown(){
        goDown =true;
    }

    public void update(Context context) {

        fuel--;

        //Player's animation effect
        if(fuel > 150){
            if(condition){
                if(new Random().nextInt(2)==1){
                    setBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.player_seq2_inv));
                }else{
                    setBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.player_inv));
                }
            }else {

                if(new Random().nextInt(2)==1){
           setBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.player_seq2));
        }else{
           setBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.player));
        }}
        }


        else if(fuel > 100){

            if(condition){
                if(new Random().nextInt(2)==1){
                    setBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.player_seq4_inv));
                }else{
                    setBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.player_seq3_inv));
                }
            }else{

            if(new Random().nextInt(2)==1){
                setBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.player_seq4));
            }else{
                setBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.player_seq3));
            }
            }




        }     else if(fuel > 50){
            if(condition){
                if(new Random().nextInt(2)==1){
                    setBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.player_seq5_inv));
                }else{
                    setBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.player_seq4_inv));
                }
            }else {


                if (new Random().nextInt(2) == 1) {
                    setBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.player_seq5));
                } else {
                    setBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.player_seq4));
                }
            }
        }else if(fuel > 10){
            if(condition){
                if(new Random().nextInt(2)==1){
                    setBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.player_seq6_inv));
                }else{
                    setBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.player_seq5_inv));
                }
            }else {


                if(new Random().nextInt(2)==1){
                setBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.player_seq6));
            }else{
                setBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.player_seq5));
            }}

        }   else {
            if(condition){
                if(new Random().nextInt(2)==1){
                    setBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.player_seq6_inv));
                }else{
                    setBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.player_seq6_inv));
                }
            }else {


                if(new Random().nextInt(2)==1){
            setBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.player_seq6));
        }else{
            setBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.player_seq6));
        }}

    }




       if (goUp){
           y += 70;

       }
       if (goDown){
           y -= 70;
       }

        if (speed > MAX_SPEED) {
            speed = MAX_SPEED;
        }

        if (speed < MIN_SPEED) {
            speed = MIN_SPEED;
        }


        if (y < minY) {
            y = minY;
        }
        if (y > maxY) {
            y = maxY;
        }

        //adding top, left, bottom and right to the rect object
        detectCollision.left = x+200;
        detectCollision.top = y+70;
        detectCollision.right = x + bitmap.getWidth()-70;
        detectCollision.bottom = y + bitmap.getHeight()-70;

    }

    //one more getter for getting the rect object
    public Rect getDetectCollision() {
        return detectCollision;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getSpeed() {
        return speed;
    }
    public void setSpeed(int speed){this.speed= speed;}
    public boolean getCondition(){
        return condition;
    }
    public void setCondition( boolean condition){
        this.condition = condition;

    }
}
