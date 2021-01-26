package ru.ushakov.surfaceview;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.SurfaceHolder;

public class DrawThread extends Thread {
    private boolean working = true;
    private float x = 150;
    private float y = 150;
    private float r = 150;

    private Integer count = 0;
    private String text = "";
    private SurfaceHolder holder;

    public DrawThread(SurfaceHolder holder) {
        this.holder = holder;
    }

    @Override
    public void run() {
        Paint paint = new Paint();
        paint.setColor(Color.RED);

        Paint backgr = new Paint();
        backgr.setColor(Color.WHITE);

        Paint pText = new Paint();
        pText.setColor(Color.BLACK);
        pText.setTextSize(100.0f);
        pText.setStyle(Paint.Style.FILL);
        pText.setAntiAlias(true);

        Rect textBoundRect = new Rect();
        float textWidth, textHeight;
        float width, height, centerX, centerY;

        int stepX = 15;
        int stepY = 15;

        while (working) {
            Canvas canvas = holder.lockCanvas();

            if (canvas != null){
                width = canvas.getWidth();
                height = canvas.getHeight();

                centerX = width / 2;
                centerY = height / 2;

                text = count.toString();

                pText.getTextBounds(text, 0, text.length(), textBoundRect);

                textWidth = pText.measureText(text);
                textHeight = textBoundRect.height();

                try {
                    canvas.drawPaint(backgr);

                    canvas.drawCircle(x, y, r, paint);
                    x += stepX;
                    y += stepY;

                    canvas.drawText(
                            text,
                            centerX - (textWidth / 2f),
                            centerY + (textHeight /2f),
                            pText);

                    if(x >= width-150){
                        stepX =-stepX;
                        count++;
                    }
                    if(x < 150){
                        stepX =-stepX;
                        count++;
                    }

                    if(y >= height-150){
                        stepY =-stepY;
                        count++;
                    }
                    if(y < 150){
                        stepY =-stepY;
                        count++;
                    }

                    sleep(16); // для настройки частоты кадров

                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    holder.unlockCanvasAndPost(canvas);
                }
            }
        }
    }

    public void setXY(float x, float y){
        this.x = x;
        this.y = y;
    }
    public void stopRequest(){
        working = false;
    }
}
