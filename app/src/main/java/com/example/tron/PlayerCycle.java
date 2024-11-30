package com.example.tron;

import java.util.ArrayList;
import java.util.List;

public class PlayerCycle {
    private float x, y;
    private float speed = 5;
    private int direction = 0;
    private List<Segment> trail = new ArrayList<>();
    private float lastX, lastY;

    public PlayerCycle (float startX, float startY){
        this.x = startX;
        this.y = startY;
        this.lastX = startX;
        this.lastY = startY;
    }

    public void turnLeft() {
        direction = (direction + 3) % 4;
        addTrialSegment();
    }

    public void turnRight() {
        direction = (direction +1) % 4;
        addTrialSegment();
    }

    public void move() {
        switch (direction) {
            case 0: // up
                y -= speed;
                break;
            case 1: // right
                x+= speed;
                break;
            case 2: // down
                y+= speed;
                break;
            case 3: // left
                x-= speed;
                break;
        }
    }

    public void addTrialSegment() {
        trail.add(new Segment(lastX, lastY, x, y));
        lastX = x;
        lastY = y;
    }

    public List<Segment> getTrail() {
        return trail;
    }

    public float getX(){return x;}
    public float getY(){return y;}
}
