package com.company;

public class Move {
    
    public void player(Tron t) {
        for (int i = t.getSize(); i > 0; i--) {
            //moves back the locations of the tron in the array
            t.setTronX(i, t.getTronX(i - 1));
            t.setTronY(i, t.getTronY(i - 1));
        }
        if (t.isMovingLeft()) {
            t.setTronX(0, t.getTronX(0) - 5);
        }
        if (t.isMovingRight()) {
            t.setTronX(0, t.getTronX(0) + 5);
        }
        if (t.isMovingDown()) {
            t.setTronY(0, t.getTronY(0) + 5);
        }
        if (t.isMovingUp()) {
            t.setTronY(0, t.getTronY(0) - 5);
        }
    }
}