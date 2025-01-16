package org.example.objects;

import java.util.Scanner;

public class Grid {

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

    public Square[][] getGrid() {
        return grid;
    }

    public void setGrid(Square[][] grid) {
        this.grid = grid;
    }

    private int x;
    private int y;
    private int difficulty;


    public Grid(int x, int y, Square[][] grid) {
        this.x = x;
        this.y = y;
        this.grid = grid;
    }

    private Square[][] grid;

    public static Grid generateGrid(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Provide grid width");
        int width = Integer.parseInt(scanner.next());
        System.out.println("Provide grid length");
        int length = Integer.parseInt(scanner.next());

        Square[][] squares = new Square[width][length];

        return new Grid(width, length, squares);


    }

    public Grid populateGrid(Grid game){

        int area = game.getX() * game.getY();


        return game

    }

}
