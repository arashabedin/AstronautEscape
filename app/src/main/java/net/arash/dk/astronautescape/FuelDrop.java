package net.arash.dk.astronautescape;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import java.util.Random;

/**
 * Created by Arash on 11/19/2017.
 */

public class FuelDrop {
    private Bitmap bitmap;
    private int x;
    private int y;
    private int speed = 1;

    private int maxX;
    private int minX;

    private int maxY;
    private int minY;
    private boolean condition;

    Context context;
    Activity activity;

    //creating a rect object
    private Rect detectCollision;

    public FuelDrop(Context context, int screenX, int screenY) {
        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.fueldrop);
        maxX = screenX;
        maxY = screenY;
        minX = 0;
        minY = 0;

        this.context = context;

        activity = (Activity) context;
        Random generator = new Random();
        speed = generator.nextInt(6) + 10;
        x = screenX;
        y = generator.nextInt(maxY) - bitmap.getHeight();

        //initializing rect object
        detectCollision = new Rect(x, y, bitmap.getWidth(), bitmap.getHeight());
    }

    public void update(int playerSpeed) {
        x -= playerSpeed;
        x -= speed;
        if (x < minX - bitmap.getWidth()) {
            Random generator = new Random();
            speed = generator.nextInt(10) + 10;
            x = maxX;
            y = generator.nextInt(maxY) - bitmap.getHeight();
        }

        if(condition){
            setBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.fueldrop_inv));
        }else{

            setBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.fueldrop));

        }

        //Adding the top, left, bottom and right to the rect object
        detectCollision.left = x;
        detectCollision.top = y;
        detectCollision.right = x + bitmap.getWidth();
        detectCollision.bottom = y + bitmap.getHeight();


    }

    //adding a setter to x coordinate so that we can change it after collision
    public void setX(int x){

        this.x = x;

    }

    //one more getter for getting the rect object
    public Rect getDetectCollision() {
        return detectCollision;
    }

    //getters
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
    public boolean getCondition(){
        return condition;
    }
    public void setCondition( boolean condition){
        this.condition = condition;
    }
    public void setBitmap(Bitmap bitmap){
        this.bitmap = bitmap;
    }
}
