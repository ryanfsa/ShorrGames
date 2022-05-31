package com.example.a3d.flappyShorrs;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Toast;

import java.nio.channels.Pipe;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {

    private MainThread thread;
    private CharacterSprite characterSprite;
    private PipeSprite pipeSprite;
    public static int gapHeight = 400;
    public static int velocity = 5;
    private PipeSprite pipe1;
    private PipeSprite pipe2;
    private PipeSprite pipe3;
    private int screenHeight = Resources.getSystem().getDisplayMetrics().heightPixels;
    private Context context;


    //constructor
    public GameView(Context context) {
        super(context);
        this.context = context;
        getHolder().addCallback(this);
        thread = new MainThread(getHolder(), this);
        setFocusable(true);
    }

    //override some methods
    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    // starts thread
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        characterSprite = new CharacterSprite(BitmapFactory.decodeResource(getResources(), com.example.a3d.R.drawable.shorr));
        Bitmap bmp;
        Bitmap bmp2;
        int y;
        int x;
        bmp = getResizedBitmap(BitmapFactory.decodeResource
                (getResources(), com.example.a3d.R.drawable.pipe_up), 300, screenHeight);
        bmp2 = getResizedBitmap(BitmapFactory.decodeResource
                (getResources(), com.example.a3d.R.drawable.pipe_down), 500, screenHeight);

        pipe1 = new PipeSprite(bmp, bmp2, 2666, -300);
        pipe2 = new PipeSprite(bmp, bmp2, 4333, 0);
        pipe3 = new PipeSprite(bmp, bmp2, 5998, 300);
        thread.setRunning(true);
        thread.start();
    }

    //resizes the shorr and pipes
    public Bitmap getResizedBitmap(Bitmap bm, int newWidth, int newHeight) {
        int width = bm.getWidth();
        int height = bm.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // CREATE A MATRIX FOR THE MANIPULATION
        Matrix matrix = new Matrix();
        // RESIZE THE BIT MAP
        matrix.postScale(scaleWidth, scaleHeight);

        // "RECREATE" THE NEW BITMAP
        Bitmap resizedBitmap =
                Bitmap.createBitmap(bm, 0, 0, width, height, matrix, false);
        bm.recycle();
        return resizedBitmap;
    }

    //stops thread
    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        while (retry) {
            try {
                thread.setRunning(false);
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            retry = false;
        }
    }

    // updates everything so that they countinue to move
    public void update() {
        characterSprite.update();
        pipe1.update();
        pipe2.update();
        pipe3.update();
        // Checks if it is game over and resets level if it is
        if (isGameOver()) {
            resetLevel();
        }
    }

    //draws the pipes and blue back drop
    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        if (canvas != null) {
            canvas.drawRGB(0, 100, 205);
            characterSprite.draw(canvas);
            pipe1.draw(canvas);
            pipe2.draw(canvas);
            pipe3.draw(canvas);
        }
    }

    // calls the is illed method on every pipe
    public boolean isGameOver() {
        if(isKilled(pipe1)){
            return true;
        }
        if(isKilled(pipe2)){
            return true;
        }
        if(isKilled(pipe3)){
            return true;
        }
        return false;
    }

    //checks to see if the Shorr is touching a pipe
    public boolean isKilled(PipeSprite curPipe){
        int spriteW = characterSprite.w;
        int spriteH = characterSprite.h;
        int spriteX = 100;
        int pipeW = 100;
        if (characterSprite.y > Resources.getSystem().getDisplayMetrics().heightPixels + 100) {
            return true;
        }
        if (spriteX + spriteW >= curPipe.xX &&
                spriteX < curPipe.xX + pipeW &&
                characterSprite.y < curPipe.yY+300) {
            return true;
        }
        if (spriteX + spriteW >= curPipe.xX &&
                spriteX < curPipe.xX + pipeW &&
                characterSprite.y+spriteH > curPipe.yY+1000) {
            return true;
        }
        return false;
    }

    //jumps the Shorr up
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        characterSprite.yVelocity = -60;
        return super.onTouchEvent(event);
    }

    //resets everything to the starting position
    public void resetLevel() {
        pipe1.xX = 2666;
        pipe1.yY = -300;
        pipe2.xX = 4333;
        pipe2.yY = 0;
        pipe3.xX = 5998;
        pipe3.yY = 300;
        characterSprite.y = 100;
        characterSprite.x = 100;
    }
}