package org.example.objects;

import java.util.LinkedList;
import java.util.Scanner;

import static org.example.objects.Display.display;

public class Leaderboard {

    public LinkedList<String> getLeaderboard() {
        return leaderboard;
    }

    public void setLeaderboard(LinkedList<String> leaderboard) {
        this.leaderboard = leaderboard;
    }

    LinkedList<String> leaderboard;

    //extract out later
    public void displayLeaderboard(){
        for(String s : this.getLeaderboard()){
            Display.display(s +"\n");
        }
    }

    public void addToLeaderboard(int score, int time){
        Display.display("Please enter your name for the leaderboard\n");
        String name = Display.getInput();
        String newEntry = name + "," + score + "," + time;
        LinkedList<String> lBoard = this.getLeaderboard();
        int pos = 0;
        int theSize = lBoard.size();
        int[] entry;
        for(int x = 0; x <theSize; x++){
            entry = processEntry(lBoard.get(x));
            if(score > entry[0]){
                lBoard.add(x, newEntry);
                pos = x;
                break;
            }else if(score == entry[0]) {
                if(time <= entry[1]){
                    lBoard.add(x, newEntry);
                    pos = x;
                    break;
               }
            }else if(x == theSize - 1){
                lBoard.addLast(newEntry);
                pos = theSize;
                break;
            }
        }
        Display.display("You are in position: " + (pos+1) + "\n");
        this.setLeaderboard(lBoard);
    }

    private int[] processEntry(String entry){
        int score = Integer.parseInt(entry.split(",")[1]);
        int time = Integer.parseInt(entry.split(",")[2]);
        int[] arr = new int[2];
        arr[0] = score;
        arr[1] = time;
        return arr;
    }

    public void writeLeaderboard(){
        LeaderboardLoader.writeLeaderboard(this);
    }

    public static Leaderboard readLeaderboard(){
        return LeaderboardLoader.readLeaderboard();
    }

}
