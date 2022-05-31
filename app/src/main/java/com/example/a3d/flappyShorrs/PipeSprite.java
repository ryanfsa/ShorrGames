package com.example.a3d.flappyShorrs;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;

public class PipeSprite {

    private Bitmap image;
    private Bitmap image2;
    public int xX, yY, topY, botY;
    private int xVelocity = 50;
    private int screenHeight =
            Resources.getSystem().getDisplayMetrics().heightPixels;

    // gives me important information about the pipes
    public PipeSprite (Bitmap bmp, Bitmap bmp2, int x, int y) {
        image = bmp;
        image2 = bmp2;
        yY = y;
        xX = x;
    }

    //draws the pipes.
    public void draw(Canvas canvas) {
        canvas.drawBitmap(image, xX, -(GameView.gapHeight+700) + yY, null);
        canvas.drawBitmap(image2,xX, ((screenHeight / 2)
                + (GameView.gapHeight / 2)) + yY, null);
    }

    public void update() {
        // moves the pipes towards the left side of the screen
        xX -= xVelocity;
        // recycles the pipe to infront of the Shorr again after it passes off the screen
        if(xX < -500){
            xX = 5000;
            yY = (int)(Math.random()*800)-400;
        }
    }

}