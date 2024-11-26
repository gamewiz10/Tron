package com.example.tron;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceHolder;

public class GameThread extends Thread {

    private SurfaceHolder surfaceHolder;
    private boolean isRunning;

    public GameThread(SurfaceHolder surfaceHolder) {
        this.surfaceHolder = surfaceHolder;
    }

    public void setRunning(boolean isRunning) {
        this.isRunning = isRunning;
    }

    @Override
    public void run() {
        while (isRunning) {
            Canvas canvas = null;
            try {
                canvas = surfaceHolder.lockCanvas();
                if (canvas != null) {
                    synchronized (surfaceHolder) {
                        drawGame(canvas);
                    }
                }
            } finally {
                if (canvas != null) {
                    surfaceHolder.unlockCanvasAndPost(canvas);
                }
            }
        }
    }

    private void drawGame(Canvas canvas) {
        canvas.drawColor(Color.BLACK);
        Paint gridPaint = new Paint();
        gridPaint.setColor(Color.GRAY);
        gridPaint.setStrokeWidth(2);

        int cellSize = 50;
        for (int x = 0; x < canvas.getWidth(); x += cellSize) {
            canvas.drawLine(x, 0 , x, canvas.getHeight(), gridPaint);
        }
        for (int y = 0; y <canvas.getHeight(); y += cellSize){
            canvas.drawLine(0, y, canvas.getWidth(), y, gridPaint);
        }

        Paint cyclePaint = new Paint();
        cyclePaint.setColor(Color.BLUE);
        canvas.drawRect(100,100,150,150,cyclePaint);

        Paint trailPaint = new Paint();
        trailPaint.setColor(Color.CYAN);
        trailPaint.setStrokeWidth(5);
        canvas.drawLine(100,125,200,125,trailPaint);
    }
}
