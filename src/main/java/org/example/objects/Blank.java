package org.example.objects;

public class Blank extends Square{
    public Blank(int x, int y) {
        super(x, y);
    }

    public void printType(){
        System.out.print("|");
    }

    @Override
    public void printSymbol() {
        //Do nothing
    }
}
