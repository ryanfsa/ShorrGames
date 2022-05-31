package com.example.a3d.flappyShorrs;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;

import java.util.ResourceBundle;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.widget.Toast;

import java.util.ResourceBundle;

public class CharacterSprite {
    private Bitmap image;
    public int x, y, w, h;
    public int yVelocity = 20;
    public int gravity = 5;
    public int MAX_Y_VELOCITY = 30;

    //gives me some important informtion
    public CharacterSprite(Bitmap bmp) {
        image = bmp;
        x = 100;
        y = 100;
        w = image.getWidth();
        h = image.getHeight();
    }

    //Draws the Shorr
    public void draw(Canvas canvas) {
        canvas.drawBitmap(image, x, y, null);
    }


    public void update() {
        // makes the shorr fall
        yVelocity += gravity;
        // stops the Shorr from faling too fast
        if (yVelocity > MAX_Y_VELOCITY) {
            yVelocity = MAX_Y_VELOCITY;
        }
        y += yVelocity;
    }

}