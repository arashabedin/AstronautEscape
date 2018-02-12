package net.arash.dk.astronautescape;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.widget.Toast;

import java.util.Random;

import static android.R.attr.data;

/**
 * Created by Arash on 2/9/2018.
 */

public class BlackHole {
    private Bitmap bitmap;
    private int x;
    private int y;
    private int speed = 1;

    private int maxX;
    private int minX;

    private int maxY;
    private int minY;
    private int numOfLuck;
    Context context;
    Activity activity;
    private boolean readyToInvert = false;
    private Boolean condition = false;

    //creating a rect object
    private Rect detectCollision;

    public BlackHole(Context context, int screenX, int screenY) {
        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.blackhole_seq1);
        maxX = screenX;
        maxY = screenY;
        minX = 0;
        minY = 0;

        this.context = context;

        activity = (Activity) context;
        Random generator = new Random();
        speed = generator.nextInt(6) + 10;
        x = minX - bitmap.getWidth();
        y = (maxY - bitmap.getHeight()) / 2;
        //initializing rect object
        detectCollision = new Rect(x, y, bitmap.getWidth(), bitmap.getHeight());
    }

    public void update(int playerSpeed) {

        x -= playerSpeed;
        x -= speed;

    if(condition){
        if(new Random().nextInt(2)==1){
            setBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.blackhole_inv_seq1));
        }else{
            setBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.blackhole_inv_seq2));
        }
    }else{
        if(new Random().nextInt(2)==1){
            setBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.blackhole_seq1));
        }else{
            setBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.blackhole_seq2));
        }
    }




            if (x < minX - bitmap.getWidth()) {
                Random showIt = new Random();
                numOfLuck = showIt.nextInt(1000);
                if(numOfLuck > 990 && readyToInvert) {
                Random generator = new Random();
                speed = generator.nextInt(10) + 10;
                x = maxX;
                y = (maxY - bitmap.getHeight()) / 2;

            }

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


    public void setBitmap(Bitmap bitmap){
        this.bitmap = bitmap;
    }
    public boolean getCondition(){
        return condition;
    }
    public void setCondition( boolean condition){
        this.condition = condition;
    }
    public void setReadyToInvert( boolean readyToInvert){
        this.readyToInvert = readyToInvert;
    }

    public int getSpeed() {
        return speed;
    }
}
