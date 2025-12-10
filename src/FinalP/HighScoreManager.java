package FinalP;

import java.io.*;
import java.util.*;

public class HighScoreManager {

    private static final String FILE_NAME = "highscores.txt";


    public static class Score implements Comparable<Score> {
        String name;
        int time;
        String difficulty;

        public Score(String name, int time, String difficulty) {
            this.name = name;
            this.time = time;
            this.difficulty = difficulty;
        }

        @Override
        public int compareTo(Score other) {
            return this.time - other.time;
        }

        @Override
        public String toString() {
            return name + "," + time + "," + difficulty;
        }
    }


    public static void saveScore(String name, int time, String difficulty) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME, true))) {
            writer.write(name + "," + time + "," + difficulty);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static ArrayList<Score> getScoresByDifficulty(String difficultyLevel) {
        ArrayList<Score> scores = new ArrayList<>();
        File file = new File(FILE_NAME);
        if (!file.exists()) return scores;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    String name = parts[0];
                    int time = Integer.parseInt(parts[1]);
                    String diff = parts[2];

                    if (diff.equalsIgnoreCase(difficultyLevel)) {
                        scores.add(new Score(name, time, diff));
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        Collections.sort(scores); // الترتيب
        return scores;
    }
}