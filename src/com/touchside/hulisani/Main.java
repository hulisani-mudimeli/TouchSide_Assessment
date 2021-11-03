package com.touchside.hulisani;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {

    private static final Map<String, Integer> wordFrequency = new HashMap<>();
    private static String mostFreqWord = null;
    private static int mostFreqWordVal = 0;

    private static final Map<String, Integer> ch7wordFrequency = new HashMap<>();
    private static String mostFreq7chWord = null;
    private static int mostFreq7chWordVal = 0;

    private static String highScoreWord = null;
    private static int highScore = 0;

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter book file name & extension: ");
        String fileName = scanner.next();
        System.out.println();

        try {
            readFile(fileName);
            calculateValues();
            System.out.println("Most frequent word: '" + mostFreqWord + "' occurred " + mostFreqWordVal + " times");
            System.out.println("Most frequent 7-character word: '" + mostFreq7chWord + "' occurred " + mostFreq7chWordVal + " times");
            System.out.println("Highest scoring word(s) (according to the score table): '" + highScoreWord + "' with a score of " + highScore);

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void readFile(String fileName) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {//automatic resource management
            String line = br.readLine();

            while (line != null) {
                String[] words = line.split("\\W+");

                for (String word : words) {
                    //Most frequent word
                    Integer freq = wordFrequency.get(word.toLowerCase());
                    if(freq == null)
                        freq = 0;
                    wordFrequency.put(word.toLowerCase(), ++freq);

                    //Most frequent 7 character word
                    if(word.length() == 7) {
                        ch7wordFrequency.put(word.toLowerCase(), freq);
                    }

                    //Highest scoring word
                    int highScoreCal = calculateScore(word.toLowerCase());
                    if(highScoreCal > highScore) {
                        highScore = highScoreCal;
                        highScoreWord = word.toLowerCase();
                    }
                }

                line = br.readLine();
            }
        }
    }

    private static void calculateValues(){
        //Most frequent word
        for(Map.Entry<String, Integer> entry : wordFrequency.entrySet()){
            if(entry.getValue() > mostFreqWordVal){
                mostFreqWord = entry.getKey();
                mostFreqWordVal = entry.getValue();
            }
        }

        //Most frequent 7 character word
        for(Map.Entry<String, Integer> entry : ch7wordFrequency.entrySet()){
            if(entry.getValue() > mostFreq7chWordVal){
                mostFreq7chWord = entry.getKey();
                mostFreq7chWordVal = entry.getValue();
            }
        }
    }

    private static int calculateScore(String word){
        int score = 0;
        for (int i = 0; i < word.length(); i++) {
            Character character = word.toUpperCase().charAt(i);
            if(SCORE_VALUES.containsKey(character)) {
                score += SCORE_VALUES.get(word.toUpperCase().charAt(i));
            }
        }

        return score;
    }

    public final static Map<Character, Integer> SCORE_VALUES = new HashMap<>() {{
        put('A', 1);
        put('B', 3);
        put('C', 3);
        put('D', 2);
        put('E', 1);
        put('F', 4);
        put('G', 2);
        put('H', 4);
        put('I', 1);
        put('J', 8);
        put('K', 5);
        put('L', 1);
        put('M', 3);
        put('N', 1);
        put('O', 1);
        put('P', 3);
        put('Q', 10);
        put('R', 1);
        put('S', 1);
        put('T', 1);
        put('U', 1);
        put('V', 4);
        put('W', 4);
        put('X', 8);
        put('Y', 4);
        put('Z', 10);
    }};
}
