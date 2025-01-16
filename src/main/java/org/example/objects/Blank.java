package org.example.objects;

public class Blank extends Square{
    public Blank(int x, int y) {
        super(x, y);
    }

    public void printType(){
        System.out.print("|");
    }
}
