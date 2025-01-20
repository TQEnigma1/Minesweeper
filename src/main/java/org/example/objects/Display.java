package org.example.objects;

import java.util.Scanner;

public class Display {

      public static void display(Grid grid){


        for(int x = 0; x <= grid.getX(); x++){
            for(int y = 0; y<= grid.getY(); y++){

                if(x == 0){
                    display(" _" +Integer.toString(y).substring(Integer.toString(y).length() - 1)+"_ ");
                }else if(y == 0){
                    display(" _" +Integer.toString(x).substring(Integer.toString(x).length() - 1)+"_ ");
                }else{
                    if(grid.getSquare(x,y).isFlagged()){
                        display(" [F] ");
                    }else if(grid.getSquare(x,y).isRevealed()){
                        display(grid.getSquare(x,y));

                    }else{
                        display(" [█] ");
                    }
                }

            }
            display("\n");
        }
    }

    public static void display(String s){
        System.out.print(s);
    }



    public static void display(Square square){
        if(square instanceof Bomb){
            display(" [B] ");
        }else if(square instanceof Empty e){
            if (e.surroundingBombs != 0){
                display(" [" + e.surroundingBombs + "] ");
            }else{
                display(" [ ] ");
            }
        }
    }

    public static String getInput(){
        Scanner scan = new Scanner(System.in);
        return scan.next();

    }

}
