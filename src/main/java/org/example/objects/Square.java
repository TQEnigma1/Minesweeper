package org.example.objects;

public abstract class Square {

    protected int x;
    protected int y;
    protected boolean revealed = false;

    public boolean isFlagged() {
        return isFlagged;
    }

    public void setFlagged(boolean flagged) {
        isFlagged = flagged;
    }

    protected boolean isFlagged = false;

    public boolean isRevealed() {
        return revealed;
    }

    public void setRevealed(boolean revealed) {
        this.revealed = revealed;
    }

    public abstract void printType();

    public abstract void printSymbol();


    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }



    public Square(int x, int y) {
        this.x = x;
        this.y = y;
    }

}
