package net.arash.dk.astronautescape;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import net.arash.dk.astronautescape.R;

import java.util.Random;

/**
 * Created by Arash on 10/28/2017.
 */

public class Asteroid {

    private Bitmap bitmap;
    private int x;
    private int y;
    private int speed = 1;
    private boolean condition;
    private int maxX;
    private int minX;

    private int maxY;
    private int minY;
    Random r = new Random();
    //creating a rect object for a friendly ship
    private Rect detectCollision;



    public Asteroid(Context context, int screenX, int screenY) {

        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.astroid);


        maxX = screenX;
        maxY = screenY;
        minX = 0;
        minY = 0;
        Random generator = new Random();
        speed = generator.nextInt(6) + 10;
        x = screenX;
        y = generator.nextInt(maxY) - bitmap.getHeight();

        //initializing rect object
        detectCollision = new Rect(x, y, bitmap.getWidth(), bitmap.getHeight());
    }

    public void update(Context context,int playerSpeed) {

        //Animations
        if(condition){
            if(new Random().nextInt(2)==1){
                setBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.astroid_seq2_inv));
            }else{
                setBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.astroid_inv));
            }

        }

        else{
        if(new Random().nextInt(2)==1){
            setBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.astroid_seq2));
        }else{
            setBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.astroid));
        }
        }

        x -= playerSpeed;
        x -= speed;
        if (x < minX - bitmap.getWidth()) {
            Random generator = new Random();
            speed = generator.nextInt(10) + 10;
            x = maxX;
            y = generator.nextInt(maxY) - bitmap.getHeight();
        }

        //Adding the top, left, bottom and right to the rect object
        detectCollision.left = x;
        detectCollision.top = y;
        detectCollision.right = x + bitmap.getWidth();
        detectCollision.bottom = y + bitmap.getHeight();
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
