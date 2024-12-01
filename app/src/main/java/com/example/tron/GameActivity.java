package com.example.tron;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;


public class GameActivity extends Activity implements SurfaceHolder.Callback {

    private SurfaceView gameSurfaceView;
    private GameThread gameThread;
    private PlayerCycle playerCycle;
    private SurfaceHolder surfaceHolder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        gameSurfaceView = findViewById(R.id.gameSurfaceView);
        SurfaceHolder holder = gameSurfaceView.getHolder();
        holder.addCallback(this);

        playerCycle = new PlayerCycle(100, 100);
    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder holder) {
        gameThread = new GameThread(holder, playerCycle, this);
        gameThread.setSurfaceDimensions(gameSurfaceView.getWidth(), gameSurfaceView.getHeight());
        gameThread.setRunning(true);
        gameThread.start();
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {
        if (gameThread != null){
            gameThread.setSurfaceDimensions(width, height);
        }
    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder holder) {
        boolean retry = true;
        gameThread.setRunning(false);
        while(retry) {
            try {
                gameThread.join();
                retry = false;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                float touchX = event.getX();

                if(touchX > gameSurfaceView.getWidth() / 2) {
                    playerCycle.turnRight();
                } else {
                    playerCycle.turnLeft();
                }
                return true;
            default:
                return super.onTouchEvent(event);
        }
    }

    public void showGameOverScreen() {
        setContentView(R.layout.activity_game_over);
        findViewById(R.id.newGameButton).setOnClickListener(v -> {
            restartGame();
        });
    }

    private void restartGame() {
        setContentView(R.layout.activity_game);
        surfaceHolder = gameSurfaceView.getHolder();
        surfaceHolder.addCallback(this);

        playerCycle = new PlayerCycle(100, 100);
        gameThread = new GameThread(surfaceHolder, playerCycle, this);
        gameThread.setSurfaceDimensions(gameSurfaceView.getWidth(), gameSurfaceView.getHeight());
        gameThread.setRunning(true);
        gameThread.start();
    }
}
