package org.example.objects;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.LinkedList;

public class LeaderboardLoader {

    public static void writeLeaderboard(Leaderboard l){
        try {
            FileWriter writer = new FileWriter("src/main/resources/Leaderboard.txt");
            for (String s : l.leaderboard) {
                writer.write(s+"\n");


            }
            writer.close();
        }
        catch (Exception e) {
            System.out.println("Error: " + e.toString());
        }
    }

    public static Leaderboard readLeaderboard(){
        LinkedList<String> s = new LinkedList<>();
        Leaderboard l = new Leaderboard();
        try {

            BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/Leaderboard.txt"));
            String line = reader.readLine();
            while(line != null){
                s.add(line);
                line = reader.readLine();
            }


            reader.close();

            l.setLeaderboard(s);
            return l;
        }
        catch (Exception e) {
            System.out.println("Error: " + e.toString());
            return null;
        }


    }

}
