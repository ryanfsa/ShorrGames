package com.example.a3d.flappyShorrs;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

public class MainThread extends Thread{
    //A thread is essentially like a parallel fork of code that can run simultaneously alongside the main part of your code.
    // You can have lots of threads running all at once, thereby allowing things to occur simultaneously rather than adhering to a strict sequence.
    private final SurfaceHolder surfaceHolder;
    private GameView gameView;
    private boolean running;
    public static Canvas canvas;

    public MainThread(SurfaceHolder surfaceHolder, GameView gameView){
        super();
        this.surfaceHolder = surfaceHolder;
        this.gameView = gameView;
    }

    // Game Loop: a loop of code that goes round and round and checks inputs and variables before drawing the screen
    @Override
    public void run() {
        while (running) {
            canvas = null;
            try {
                canvas = this.surfaceHolder.lockCanvas();
                synchronized(surfaceHolder) {
                    this.gameView.update();
                    this.gameView.draw(canvas);
                }
            } catch (Exception e) {} finally {
                if (canvas != null) {
                    try {
                        surfaceHolder.unlockCanvasAndPost(canvas);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
    // starts the thread
    public void setRunning(boolean isRunning) {
        running = isRunning;
    }


}