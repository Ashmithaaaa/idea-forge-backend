package com.example.demo.util;

public class IdeaSimilarityUtil {

    public static double calculateSimilarity(String text1, String text2){

        if(text1 == null || text2 == null){
            return 0;
        }

        String[] words1 = text1.toLowerCase().split(" ");
        String[] words2 = text2.toLowerCase().split(" ");

        int match = 0;

        for(String w1 : words1){

            for(String w2 : words2){

                if(w1.equals(w2)){
                    match++;
                }
            }
        }

        int max = Math.max(words1.length, words2.length);

        return (double) match / max;
    }
}