package org.example.objects;

public class Display {



    public static void displayConsole(Grid grid){

        for(int x = 1; x <= grid.getX(); x++){
            for(int y = 1; y<= grid.getY(); y++){

                if(grid.getSquare(x,y).isFlagged()){
                    displayConsole(" [F] ");
                }else if(grid.getSquare(x,y).isRevealed()){
                    displayConsole(grid.getSquare(x,y));

                }else{
                    displayConsole(" [█] ");
                }

            }
            System.out.print("\n");
        }
    }

    public static void displayConsole(String s){
        System.out.print(s);
    }



    public static void displayConsole(Square square){
        if(square instanceof Bomb){
            displayConsole(" [B] ");
        }else if(square instanceof Empty e){
            if (e.surroundingBombs != 0){
                displayConsole(" [" + e.surroundingBombs + "] ");
            }else{
                displayConsole(" [ ] ");
            }
        }
    }

}
