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

    public Square getSquare(int x, int y){
        return this.grid[x][y];
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
        System.out.println("Provide grid height");
        int height = Integer.parseInt(scanner.next());
        System.out.println("Provide grid width");
        int width = Integer.parseInt(scanner.next());
        //Therefore the game grid has invisible padding around the edges to make my life easier
        Square[][] squares = new Square[height+2][width+2];

        return new Grid(height, width, squares);


    }

    public void populateGrid(){

        //TODO implement difficulty
        double bombRate = 0.3;
        Square[][] gameGrid = this.getGrid();

        for(int x = 0; x <= this.getX()+1; x++){
            for(int y = 0; y <= this.getY()+1; y++){

                if(x == 0 || y == 0 || x == this.getX()+1 || y == this.getY()+1){
                    Blank blank = new Blank(x, y);

                    gameGrid[x][y] = blank;

                }else{
                    if(Math.random() < bombRate){
                        Bomb bomb = new Bomb(x, y);

                        gameGrid[x][y] = bomb;
                    }else{
                        Empty empty = new Empty(x, y);

                        gameGrid[x][y] = empty;
                    }
                }



            }
        }
        this.setGrid(gameGrid);
    }

    public void displayGrid(){

        for(int x = 1; x <= this.getX(); x++){
            for(int y = 1; y<= this.getY(); y++){

                if(this.getSquare(x,y).isRevealed()){
                    //TODO do stuff
                }else{
                    System.out.print("█");
                }

            }
            System.out.print("\n");
        }


    }

    public void printGrid(){
        Square[][] squares = this.getGrid();

        for(int x = 0; x <= this.getX()+1; x++){
            for(int y = 0; y <= this.getY()+1; y++){
               // System.out.print(x + " " + y);
                squares[x][y].printType();
            }
            System.out.print("\n");
        }
    }

}
