package com.company;

import java.awt.*;
import java.lang.reflect.Field;

public class Tron {
    //The array is used to store all the traversed locations of the tron
    private final int[] x = new int[100001];   // size of the array is the size of the tron
    private final int[] y = new int[100001];

    private String name;
    private Color color;
    private int size = 0;
    private boolean up = false;
    private boolean down = false;
    private boolean left = false;
    private boolean right = false;

    public Color getTronColor(){
        return color;
    }

    public int getTronX(int index) {
        return x[index];
    }

    public int getTronY(int index) {
        return y[index];
    }

    public String getTronName(){
        return name;
    }

    public void setTronColor(String c){         // Typecasting string to a color object
        try {
            Field field = Class.forName("java.awt.Color").getField(c);
            color = (Color)field.get(null);
        } catch (Exception e) {
            color = null; // Not defined
        }
    }

    public void setTronName(String name){
        this.name = name;
    }

    public void setSize(int j) {
        size = j;
    }

    public void setTronX(int index, int i) {
        x[index] = i;
    }

    public void setTronY(int index, int i) {
        y[index] = i;
    }

    public boolean isMovingLeft() {
        return left;
    }

    public void setMovingLeft(boolean movingLeft) {
        this.left = movingLeft;
    }

    public boolean isMovingRight() {
        return right;
    }

    public void setMovingRight(boolean movingRight) {
        this.right = movingRight;
    }

    public boolean isMovingUp() {
        return up;
    }

    public void setMovingUp(boolean movingUp) {
        this.up = movingUp;
    }

    public boolean isMovingDown() {
        return down;
    }

    public void setMovingDown(boolean movingDown) {
        this.down = movingDown;
    }

    public int getSize() {
        return size;
    }

}