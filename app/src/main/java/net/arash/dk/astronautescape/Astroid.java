package net.arash.dk.astronautescape;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import net.arash.dk.astronautescape.R;

import java.util.Random;

/**
 * Created by Manish on 10/24/2016.
 */

public class Astroid {

    private Bitmap bitmap;
    private int x;
    private int y;
    private int speed = 1;

    private int maxX;
    private int minX;

    private int maxY;
    private int minY;
    Random r = new Random();
    //creating a rect object for a friendly ship
    private Rect detectCollision;

    public void setBitmap(Bitmap bitmap){
        this.bitmap = bitmap;
    }

    public Astroid(Context context, int screenX, int screenY) {

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

        if(new Random().nextInt(2)==1){
            setBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.astroid_seq2));
        }else{
            setBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.astroid));
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

    /**
     * Created by Belal on 6/15/2016.
     */
    public static class Boom {

        //bitmap object
        private Bitmap bitmap;

        //coordinate variables
        private int x;
        private int y;

        //constructor
        public Boom(Context context) {
            //getting boom image from drawable resource
            bitmap = BitmapFactory.decodeResource
                    (context.getResources(), R.drawable.boom);

            //setting the coordinate outside the screen
            //so that it won't shown up in the screen
            //it will be only visible for a fraction of second
            //after collission
            x = -150;
            y = -150;
        }

        //setters for x and y to make it visible at the place of collision
        public void setX(int x) {
            this.x = x;
        }

        public void setY(int y) {
            this.y = y;
        }

        //getters
        public Bitmap getBitmap() {
            return bitmap;
        }

        public void setBitmap(Bitmap bitmap) {
            this.bitmap = bitmap;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

    }

    /**
     * Created by Belal on 6/15/2016.
     */
    public static class FuelDrop {
        private Bitmap bitmap;
        private int x;
        private int y;
        private int speed = 1;

        private int maxX;
        private int minX;

        private int maxY;
        private int minY;

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

    }
}
