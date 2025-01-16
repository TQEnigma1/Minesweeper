package org.example.objects;

import java.util.Arrays;
import java.util.Scanner;

import static java.lang.System.exit;

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

    public void setSquare(Square square){
        this.grid[square.getX()][square.getY()] = square;
    }

    public void setGrid(Square[][] grid) {
        this.grid = grid;
    }

    private int x;
    private int y;

    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    private int difficulty;

    public int getNumOfBombs() {
        return numOfBombs;
    }

    public void setNumOfBombs(int numOfBombs) {
        this.numOfBombs = numOfBombs;
    }

    private int numOfBombs;


    public int getTurnNumber() {
        return turnNumber;
    }

    public void setTurnNumber(int turnNumber) {
        this.turnNumber = turnNumber;
    }

    private int turnNumber = 0;

    public boolean isGameOver() {
        return gameOver;
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

    private boolean gameOver = false;

    public Grid(int x, int y, Square[][] grid) {
        this.x = x;
        this.y = y;
        this.grid = grid;
    }

    private Square[][] grid;

    public static void startGame(){


        Grid game = generateGrid();

        System.out.println("Welcome to Minesweeper, enter your grid size and start playing");
        System.out.println("To dig a square, just enter the format 'x,y', to flag a square enter in the format f'x,y'");
        Scanner scanner = new Scanner(System.in);

        boolean flag = false;
        do{
            System.out.println("Please enter a difficulty level: 1-5; this will affect the amount of bombs");
            if(scanner.next().matches("[12345]]")) {
                game.setDifficulty(Integer.parseInt(scanner.next()));
                flag = true;
            }
        }while(flag);

        game.populateGrid();
        game.bombDetection();
        game.printGrid();
        game.displayGrid();

        System.out.println("Begin game");

        while(!game.isGameOver()){
            game.haveTurn();
            game.displayGrid();
        }

        scanner.close();

    }

    private void haveTurn(){
        Scanner scanner = new Scanner(System.in);
        String guess = scanner.next();
        processGuess(guess);
        this.setTurnNumber(this.getTurnNumber()+1);
        scanner.close();
    }

    private void processGuess(String guess){
        String pattern = "[0-9]+,[0-9]+f*";
        if(guess.matches(pattern)){

            int guessX = Integer.parseInt(guess.split(",")[0]);
            int guessY = Integer.parseInt(guess.split(",")[1].split("f")[0]);

            System.out.println("guessX: " +guessX);
            System.out.println("guessY:" + guessY);

            if(guessX <= this.getX() && guessY <= this.getY()){

                if(this.getSquare(guessX, guessY).isRevealed()){
                    System.out.println("Square is already revealed, try again");
                    return;
                }

                if(guess.charAt(guess.length()-1) == 'f'){
                    this.getSquare(guessX, guessY).setFlagged(!this.getSquare(guessX, guessY).isFlagged());
                }else{

                    //catch for if the first guess is a bomb
                    if(this.getTurnNumber() == 0 && this.getSquare(guessX, guessY) instanceof Bomb){
                        Empty empty = new Empty(guessX, guessY);
                        this.setSquare(empty);
                    }



                    Square guessSquare = this.getSquare(guessX, guessY);
                    if(!guessSquare.isFlagged()){
                        if(guessSquare instanceof Bomb){
                            guessSquare.setRevealed(true);
                            System.out.println("You dug up a bomb, game over :(");
                            this.setGameOver(true);
                        }else if(guessSquare instanceof Empty e){
                            updateReveal(e);
                        }
                    }else{
                        System.out.println("Square has been flagged you cannot dig here");
                    }



                }

            }

        }else{
            System.out.println("invalid guess format");
        }


    }

    private void updateReveal(Empty guessSquare){

        guessSquare.setRevealed(true);

        if (guessSquare.getSurroundingBombs() == 0) {
            int[][] mask = {{1, 0}, {0, -1}, {-1, 0}, {0, 1}};
            for (int[] m : mask) {
                //System.out.println((x + m[0]) + " " + (y + m[1]));
                Square newSquare = this.getSquare(guessSquare.getX() + m[0], guessSquare.getY() + m[1]);
                if(!newSquare.isRevealed() && newSquare instanceof Empty e ){
                    updateReveal(e);
                }


            }
        }


    }

    private void bombDetection(){
        //similate moving a 3x3 window over each of the points and count the bombs, there is defo a quicker way doing funky maths


        int[][] mask = {{1, 0}, {1, -1}, {0, -1}, {-1, -1}, {-1, 0}, {-1, 1}, {0, 1}, {1,1}};
        int tot = 0;
        for(int x = 1; x < this.getX()+2; x++){
            for(int y = 1; y < this.getY()+2; y++){
                if(this.getSquare(x,y) instanceof Empty){

                    for (int[] m : mask) {
                        //System.out.println((x + m[0]) + " " + (y + m[1]));
                        if (this.getSquare(x + m[0], y + m[1]) instanceof Bomb) {
                            tot += 1;
                        }
                    }

                    ((Empty) this.getSquare(x, y)).setSurroundingBombs(tot);
                    System.out.println(x+ " " + y + " " + tot);
                    tot = 0;
                }

            }
        }

    }

    private static Grid generateGrid(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Provide grid height");
        int height = Integer.parseInt(scanner.next());
        System.out.println("Provide grid width");
        int width = Integer.parseInt(scanner.next());
        //Therefore the game grid has invisible padding around the edges to make my life easier
        Square[][] squares = new Square[height+2][width+2];
        scanner.close();
        return new Grid(height, width, squares);


    }

    private void populateGrid(){

        //TODO implement difficulty
        double bombRate = 0.1 * this.getDifficulty();
        Square[][] gameGrid = this.getGrid();
        int bombTot = 0;
        for(int x = 0; x <= this.getX()+1; x++){
            for(int y = 0; y <= this.getY()+1; y++){

                if(x == 0 || y == 0 || x == this.getX()+1 || y == this.getY()+1){
                    Blank blank = new Blank(x, y);

                    gameGrid[x][y] = blank;

                }else{
                    if(Math.random() < bombRate){
                        Bomb bomb = new Bomb(x, y);
                        bombTot += 1;
                        gameGrid[x][y] = bomb;
                    }else{
                        Empty empty = new Empty(x, y);

                        gameGrid[x][y] = empty;
                    }
                }



            }
        }
        this.setNumOfBombs(bombTot);
        this.setGrid(gameGrid);
    }

    private void displayGrid(){

        int tmp = this.getNumOfBombs();

        for(int x = 1; x <= this.getX(); x++){
            for(int y = 1; y<= this.getY(); y++){

                if(this.getSquare(x,y).isFlagged()){
                    System.out.print("F");
                }else if(this.getSquare(x,y).isRevealed()){
                    this.getSquare(x,y).printSymbol();
                    tmp -= 1;
                }else{
                    System.out.print("█");
                }

            }
            System.out.print("\n");
        }

        if(tmp <= 0){
            System.out.println("Congratulations you found all the bombs, you win!!!");
            this.setGameOver(true);
        }


    }
    //debug method
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
