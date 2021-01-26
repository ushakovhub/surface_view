package ru.ushakov.surfaceview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

public class DrawView extends SurfaceView implements SurfaceHolder.Callback {

    private DrawThread drawThread;

    public DrawView(Context context) {
        super(context);
        getHolder().addCallback(this);
    }

    public DrawView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    public void surfaceCreated(@NonNull SurfaceHolder holder) {
        drawThread = new DrawThread(holder);
        drawThread.start();
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {}

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder holder) {
        drawThread.stopRequest();

        boolean retry = true;
        while (retry) {
            try {
                drawThread.join();
                retry = false;
            } catch (InterruptedException e) {
            }
        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        drawThread.setXY(event.getX(), event.getY());
        return true;
    }
}
