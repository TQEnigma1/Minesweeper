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

    public void populateGrid(){

        //TODO implement difficulty
        double bombRate = 0.3;
        Square[][] gameGrid = this.getGrid();

        for(int x = 0; x < this.getX(); x++){
            for(int y = 0; y < this.getY(); y++){
                if(Math.random() < bombRate){
                    Bomb bomb = new Bomb();
                    gameGrid[x][y] = bomb;
                }else{
                    Empty blank = new Empty();
                    gameGrid[x][y] = blank;
                }

            }
        }


        this.setGrid(gameGrid);

    }

    public void printGrid(){
        Square[][] squares = this.getGrid();
        for(int x = 0; x < this.getX(); x++){
            for(int y = 0; y < this.getY(); y++){
                squares[x][y].printType();
            }
            System.out.print("\n");
        }
    }

}
