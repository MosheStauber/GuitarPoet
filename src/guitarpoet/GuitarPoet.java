/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package guitarpoet;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author moshe
 */
public class GuitarPoet {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        // Create a new Trie and add all the words to it
        WordTrie wordTrie = new WordTrie(null, null, false);
        try{
            BufferedReader br = new BufferedReader(new FileReader(new File("en.txt")));
 
            String line = null;
            while ((line = br.readLine()) != null) {
                int usefullness = calculateUsefullness(line);
                wordTrie.insertWord(line, usefullness);
            } 
            br.close();
        }catch(IOException e){
            
        }
        
        // User prompt
        while(true){
//                        System.out.println("Size of the program: " + (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));

            System.out.println("\n"
                    + "Press q to quit.\n"
                    + "1. Enter 'a,g' to get word in range.\n"
                    + "2. Enter 'all,3' to get all 3 letter words.\n"
                    + "3. Enter 'all' to get all words.\n"
                    + "4. Enter a list of letters separated by commas\n\t"
                    + "ie 'a,g,f,r,t' to get all words with those letters.");
            
            Scanner in = new Scanner(System.in);
            String input = in.nextLine();  
            String[] params = input.split(",");
            
            if(input.equals("q")){
                break;
            }else if(input.contains("all")){
                if(params.length == 1){
                    List<Word> words = wordTrie.getAllWords();
                    Collections.sort(words);
                    for(Word w : words){
                        System.out.printf("Word: %s Usefullness: %d\n", w, w.getUsefulness());                   
                    }
                }else{
                    List<Word> words = 
                            wordTrie.getAllWordsOfLength(Integer.parseInt(params[1]));
                    Collections.sort(words);
                    for(Word w : words){
                        System.out.printf("Word: %s Usefullness: %d\n", w, w.getUsefulness());                   
                    }
                }
            }else if (params.length == 2){
                List<Word> words = 
                        wordTrie.getAllWordsInRange(params[0].charAt(0), params[1].charAt(0));
                Collections.sort(words);
                for(Word w : words){
                    System.out.printf("Word: %s Usefullness: %d\n", w, w.getUsefulness());                   
                }
            } else if(params.length > 2){
                char [] chars = new char[params.length];
                for(int i = 0; i < params.length; i++){
                    chars[i] = params[i].charAt(0);
                }
                List<Word> words = 
                        wordTrie.getAllWordsWithLetters(chars);
                //Collections.sort(words);
                for(Word w : words){
                    System.out.printf("Word: %s Usefullness: %d\n", w, w.getUsefulness());                   
                }
            }
            
        }
    }

    /*
        Returns an integer from 0 - 1000 as a uselfullness value. The higher the 
        value, the more useful the word is musically or by english usage. The 
        proximity of the letters is the most important factor and will contribute 
        up to 50 percent of the usefullness value, with the other 2 categories 
        contributing up to 25 percent each to the total.
    */
    private static int calculateUsefullness(String line) {
        int proximity = 0;
        int repeating = 0;
        int popular = 0;
        
        int[] repeats = new int[26];
        repeats[line.charAt(0) - 'a']++;
        
        int wordLength = line.length();        
        for(int i = 1; i < wordLength; i++){
            char curr = line.charAt(i);
            char prev = line.charAt(i-1);
            
            proximity += (500/wordLength) - Math.abs(curr - prev) * 10;            
            repeats[curr - 'a']++;
        }
        
        for(int i = 0; i < repeats.length; i++){
            if(repeats[i] > 1){
                repeating += 250*(repeats[i]/wordLength); 
            }
        }
        
        return proximity + repeating + 250;
    }
    
}
