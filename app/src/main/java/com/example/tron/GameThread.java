package com.example.tron;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceHolder;

import java.util.List;

public class GameThread extends Thread {

    private SurfaceHolder surfaceHolder;
    private boolean isRunning;

    private PlayerCycle playerCycle;
    private GameActivity gameActivity;

    public int surfaceHeight;
    public int surfaceWidth;

    public GameThread(SurfaceHolder surfaceHolder, PlayerCycle playerCycle, GameActivity gameActivity) {

        this.surfaceHolder = surfaceHolder;
        this.playerCycle = playerCycle;
        this.gameActivity = gameActivity;
    }

    public void setRunning(boolean isRunning) {

        this.isRunning = isRunning;
    }

    public void setSurfaceDimensions(int width, int height) {
        this.surfaceHeight = height;
        this.surfaceWidth = width;
    }

    @Override
    public void run() {
        while (isRunning) {
            Canvas canvas = null;
            try {
                canvas = surfaceHolder.lockCanvas();
                if (canvas != null) {
                    synchronized (surfaceHolder) {
                        updateGame();
                        drawGame(canvas);
                    }
                }
            }
            catch (Exception e){
                e.printStackTrace();
            } finally {
                if (canvas != null) {
                    surfaceHolder.unlockCanvasAndPost(canvas);
                }
            }
        }
    }

    private void updateGame() {
        playerCycle.move();

        if (playerCycle.getX() < 0 || playerCycle.getX() >= surfaceWidth ||
                playerCycle.getY() < 0 || playerCycle.getY() >= surfaceHeight) {
            isRunning = false;
        }

        // TODO: add more logic for collisions and npcs
    }

    private void triggerGameOver() {
        isRunning = false;
        gameActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                gameActivity.showGameOverScreen();
            }
        });
    }

    private void drawGame(Canvas canvas) {
        canvas.drawColor(0xFF000000);
        drawGrid(canvas);
        drawPlayerCycle(canvas);
        drawTrail(canvas, playerCycle.getTrail());
    }


    private void drawGrid(Canvas canvas){
        int cellSize = 50;
        Paint gridPaint = new Paint();
        gridPaint.setColor(0xFF444444);

        for (int x = 0; x < surfaceWidth; x += cellSize){
            canvas.drawLine(x, 0, x, surfaceHeight, gridPaint); //vertical lines
        }
        for (int y = 0; y < surfaceHeight; y += cellSize){
            canvas.drawLine(0, y, surfaceHeight, y, gridPaint); //horizontal lines
        }
    }

    private void drawPlayerCycle(Canvas canvas) {
        Paint cyclePaint = new Paint();
        cyclePaint.setColor(0xFF00FF00);

        float cycleSize = 30;
        canvas.drawRect(playerCycle.getX() - cycleSize / 2, playerCycle.getY() - cycleSize /2,
                playerCycle.getX() + cycleSize / 2, playerCycle.getY() + cycleSize / 2, cyclePaint);
    }

    private void drawTrail(Canvas canvas, List<Segment> trail){
        Paint trialPaint = new Paint();
        trialPaint.setColor(0xFF00FFFF);
        trialPaint.setStrokeWidth(5);

        for (Segment segment : trail) {
            canvas.drawLine(segment.startX, segment.startY, segment.endX, segment.endY,trialPaint);
        }
    }

}
