package org.example.objects;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.LinkedList;
import java.util.Scanner;

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
            System.out.println(s);
        }
    }

    public Leaderboard() {
        readLeaderboard();
    }

    private void readLeaderboard(){

        LinkedList<String> s = new LinkedList<>();

        try {

            BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/Leaderboard.txt"));
            String line = reader.readLine();
            while(line != null){
                s.add(line);
                line = reader.readLine();
            }


            reader.close();

            this.setLeaderboard(s);
        }
        catch (Exception e) {
            System.out.println("Error: " + e.toString());
        }

    }

    public void addToLeaderboard(int score, int time){

        Scanner consoleScanner = new Scanner(System.in);
        System.out.println("Please enter your name for the leaderboard");
        String name = consoleScanner.next();
        String newEntry = name + "," + score + "," + time;
        LinkedList<String> lBoard = this.getLeaderboard();
        int pos = 0;
        int theSize = lBoard.size();

        for(int x = 0; x <theSize; x++){

            int[] entry = processEntry(lBoard.get(x));
            if(score > entry[0]){
                System.out.println("cond1");
                lBoard.add(x, newEntry);
                pos = x;
                break;
            }else if(score == entry[0]) {
                if(time <= entry[1]){
                    System.out.println("cond2");
                    lBoard.add(x, newEntry);
                    pos = x;
                    break;
               }
            }else if(x <= theSize - 1){
                System.out.println("cond3");
                lBoard.addLast(newEntry);
                pos = theSize;
                break;
            }


        }

        System.out.println("You are in position: " + (pos+1));
        System.out.println(lBoard);
        this.setLeaderboard(lBoard);


    }

    private int[] processEntry(String entry){
        System.out.println(entry);
        int score = Integer.parseInt(entry.split(",")[1]);
        int time = Integer.parseInt(entry.split(",")[2]);
        int[] arr = new int[2];
        arr[0] = score;
        arr[1] = time;
        return arr;
    }

    public void writeLeaderboard(){


        try {
            FileWriter writer = new FileWriter("src/main/resources/Leaderboard.txt");
            for (String s : this.leaderboard) {
                writer.write(s+"\n");

                System.out.println(s);
            }
            writer.close();
        }
        catch (Exception e) {
            System.out.println("Error: " + e.toString());
        }
    }

}
