package org.example.objects;

public class Empty extends Square{

    public Empty(int x, int y) {
        super(x, y);
    }

    public int getSurroundingBombs() {
        return surroundingBombs;
    }

    public void setSurroundingBombs(int surroundingBombs) {
        this.surroundingBombs = surroundingBombs;
    }

    int surroundingBombs;



    public void printType(){
        System.out.print("E");
    }


}
