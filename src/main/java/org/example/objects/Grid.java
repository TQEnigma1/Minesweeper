package org.example.objects;

import java.util.Arrays;
import java.util.Scanner;

import static java.lang.System.exit;

public class Grid {

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getTimeTaken() {
        return timeTaken;
    }

    public void setTimeTaken(long timeTaken) {
        this.timeTaken = timeTaken;
    }

    public long timeTaken;

    long startTime;


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

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    private int score;
    private int x;
    private int y;

    public int getNotRevealed() {
        return notRevealed;
    }

    public void setNotRevealed(int notRevealed) {
        this.notRevealed = notRevealed;
    }

    private int notRevealed;

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


        Display.displayConsole("Welcome to Minesweeper, enter your grid size and start playing\n");
        Display.displayConsole("To dig a square, just enter the format 'x,y', to flag a square enter in the format 'x,yf'\n");
        Display.displayConsole("Guesses are column then row\n");
        Scanner scanner = new Scanner(System.in);
        int diffLvl = 1;
        boolean flag = false;
        do{
            Display.displayConsole("Please enter a difficulty level: 1-5; this will affect the amount of bombs\n");
            String answer = scanner.next();
            if(answer.matches("[12345]")) {
                diffLvl = Integer.parseInt(answer);
                flag = true;
            }
        }while(!flag);
        Grid game = generateGrid();
        game.setDifficulty(diffLvl);
        game.populateGrid();
        game.bombDetection();
        //game.printGrid();
        Display.displayConsole(game);

        Display.displayConsole("Begin game\n");
        game.setStartTime(System.currentTimeMillis() / 1000);
        while(!game.isGameOver()){
            game.haveTurn();
            Display.displayConsole(game);
        }



        scanner.close();

    }

    private void haveTurn(){
        Scanner scanner = new Scanner(System.in);
        String guess = scanner.next();
        processGuess(guess);
        this.setTurnNumber(this.getTurnNumber()+1);
        if(this.getNumOfBombs() == this.getNotRevealed()){
            processWin();
        }
    }

    private void processGuess(String guess){
        String pattern = "[0-9]+,[0-9]+f*";
        if(guess.matches(pattern)){

            int guessX = Integer.parseInt(guess.split(",")[0]);
            int guessY = Integer.parseInt(guess.split(",")[1].split("f")[0]);


            if(guessX <= this.getX() && guessY <= this.getY()){

                if(this.getSquare(guessX, guessY).isRevealed()){
                    Display.displayConsole("Square is already revealed, try again\n");
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
                            Display.displayConsole("You dug up a bomb, game over :(\n");
                            this.setGameOver(true);
                        }else if(guessSquare instanceof Empty e){
                            updateReveal(e);
                        }
                    }else{
                        Display.displayConsole("Square has been flagged you cannot dig here\n");
                    }



                }

            }

        }else{
            Display.displayConsole("invalid guess format\n");
        }


    }

    final int[][] mask = {{1, 0}, {1, -1}, {0, -1}, {-1, -1}, {-1, 0}, {-1, 1}, {0, 1}, {1,1}};

    private void updateReveal(Empty guessSquare){

        guessSquare.setRevealed(true);
        this.setNotRevealed(this.getNotRevealed() - 1);
        if (guessSquare.getSurroundingBombs() == 0) {

            for (int[] m : mask) {
                //Display.displayConsole((x + m[0]) + " " + (y + m[1]));
                Square newSquare = this.getSquare(guessSquare.getX() + m[0], guessSquare.getY() + m[1]);
                if(!newSquare.isRevealed() && newSquare instanceof Empty e && !e.isFlagged() ){
                    updateReveal(e);
                }


            }
        }


    }

    private void bombDetection(){

        int tot = 0;
        for(int x = 1; x < this.getX()+2; x++){
            for(int y = 1; y < this.getY()+2; y++){
                if(this.getSquare(x,y) instanceof Empty){

                    for (int[] m : mask) {
                        //Display.displayConsole((x + m[0]) + " " + (y + m[1]));
                        if (this.getSquare(x + m[0], y + m[1]) instanceof Bomb) {
                            tot += 1;
                        }
                    }

                    ((Empty) this.getSquare(x, y)).setSurroundingBombs(tot);
                    //Display.displayConsole(x+ " " + y + " " + tot);
                    tot = 0;
                }

            }
        }

    }

    private static Grid generateGrid(){
        Scanner scanner = new Scanner(System.in);
        Display.displayConsole("Provide grid height\n");
        int height = Integer.parseInt(scanner.next());
        Display.displayConsole("Provide grid width\n");
        int width = Integer.parseInt(scanner.next());
        //Therefore the game grid has invisible padding around the edges to make my life easier
        Square[][] squares = new Square[height+2][width+2];

        return new Grid(height, width, squares);


    }

    private void populateGrid(){


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
        this.setNotRevealed(this.getX() * this.getY());
        this.setGrid(gameGrid);
    }



    public void processWin(){
        Display.displayConsole("Congratulations you found all the bombs, you win!!!\n");
        long timeDelta = (System.currentTimeMillis()/1000) - this.getStartTime();
        Display.displayConsole("This run took you: " + timeDelta + " seconds\n");
        this.setTimeTaken(timeDelta);
        this.setScore(this.getX() * this.getY() * this.getDifficulty());
        Display.displayConsole("You scored: " + this.getScore() + "\n");

        Leaderboard leaderboard = new Leaderboard();
        leaderboard.addToLeaderboard(this.getScore(), (int) this.getTimeTaken());
        leaderboard.displayLeaderboard();
        leaderboard.writeLeaderboard();

        this.setGameOver(true);
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
