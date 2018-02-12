package net.arash.dk.astronautescape;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import net.arash.dk.astronautescape.R;

import java.util.Random;
/**
 * Created by Arash on 10/28/2017.
 */

public class Planets {

    private Bitmap bitmap;
    private int x;
    private int y;
    private int speed = 1;

    private int maxX;
    private int minX;

    private int maxY;
    private int minY;
    private boolean condition = false;
    private boolean firstFrame = false;
    private int currentPlanet = 1;

    public Planets(Context context, int screenX, int screenY) {
        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.earth);
        maxX = screenX;
        maxY = screenY;
        minX = 0;
        minY = 0;
        Random generator = new Random();
        speed = 0;
        x = -1060;
        y = -200 - screenY;

    }

    public void update(Context context, int playerSpeed) {
        x -= playerSpeed;
        x -= speed;

        if(condition){
            if (x < minX - bitmap.getWidth()) {
                int rand = new Random().nextInt(4);
                if (rand==1){
                    bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.planet2_inv);
                    currentPlanet=2;

                }else if(rand==2){
                    //    bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.earth);
                    bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.planet3_inv);
                    currentPlanet=3;

                } else if(rand ==3){
                    bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.planet3_inv);
                    currentPlanet=3;


                }else{

                    bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.planet4_inv);
                    currentPlanet=4;


                }


                Random generator = new Random();
                speed = generator.nextInt(6);
                x = maxX;
                y = generator.nextInt(maxY) - bitmap.getHeight();
            } else if(firstFrame){
                    switch (currentPlanet){
                        case 1 : bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.earth_inv);
                            break;
                        case 2 : bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.planet2_inv);
                            break;
                        case 3 : bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.planet3_inv);
                            break;
                        case 4 : bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.planet4_inv);



                    }
                    firstFrame = false;

            }

        }
        else{
        if (x < minX - bitmap.getWidth()) {
            int rand = new Random().nextInt(4);
            if (rand==1){
                bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.planet2);
                currentPlanet=2;
            }else if(rand==2){
            //    bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.earth);
                bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.planet3);
                currentPlanet=3;

            } else if(rand ==3){
                bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.planet2);
                currentPlanet=2;

            }else{

                bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.planet4);
                currentPlanet=4;

            }


            Random generator = new Random();
            speed = generator.nextInt(6);
            x = maxX;
            y = generator.nextInt(maxY) - bitmap.getHeight();
        }else if(firstFrame){
            switch (currentPlanet){
                case 1 : bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.earth);
                    break;
                case 2 : bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.planet2);
                    break;
                case 3 : bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.planet3);
                    break;
                case 4 : bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.planet4);



            }
            firstFrame = false;

        }
        }
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
    public void setFirstFrame(boolean firstFrame){
        this.firstFrame = firstFrame;

    }
}
