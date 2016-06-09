/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package guitarpoet;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * This class contains the entire English dictionary. 
 * Each node in the Trie has a corresponding letter and a list of children nodes.
 * Each child node is positioned in the list of size 26 (for the lowercase letters of the alphabet)
 * by its ASCII value index minus the value of 'a'. 
 * A character node at the end of a word is marked as the end of the node.
 * 
 * @author moshe
 */
public class WordTrie {
    private Character nodeLetter;
    private WordTrie parentNode;
    private boolean isWordEnding;
    private int usefullness;
    private ArrayList<WordTrie> children;
    
    public WordTrie(Character nodeLetter, WordTrie parent, boolean isWordEnding){
        this.nodeLetter = nodeLetter;
        this.parentNode = parent;
        this.isWordEnding = isWordEnding;
        this.usefullness = 0;
        this.children = new ArrayList<WordTrie>();
        while(children.size() < 26)
            children.add(null);
    }

    public boolean isWordEnding() {
        return isWordEnding;
    }
    
    /*
        Marks this node as the end of a word
    */
    public void markWordEnding(){
        this.isWordEnding = true;
    }
    
    public void setUsefullness(int usefullness){
        this.usefullness = usefullness;
    }
    
    public int getUsefullness(){
        return usefullness;
    }
    
    /*
        Builds the word by adding its own character to its parents character.
    */
    public String getWord(){
        if(this.parentNode == null){
            return "";
        }else{
            return parentNode.getWord() + nodeLetter;
        }
    }
    
    /*
        This inserts words into the trie one character at a time. It takes the 
        first character of the word and checks if a child node exists in this 
        nodes children list for that character. If it is null it creates a new 
        node and adds it to the list. In either case it inserts the remainder 
        of the word from the second character to the child node. If it is the 
        last character of a word it marks the node as the end of the word.
    */
    public void insertWord(String word, int usefullness){
        if(word.length() == 0){
            return;
        }
        
        char firstLetter = word.charAt(0);
        int idx = firstLetter - 'a';
        
        if(word.length() > 1){
            if(children.get(idx) == null){
                WordTrie node = new WordTrie(firstLetter, this, false);
                children.add(idx, node);
                children.get(idx).insertWord(word.substring(1), usefullness);
            }else{
                children.get(idx).insertWord(word.substring(1), usefullness);
            }
        }else{            
            if(children.get(idx) == null){
                WordTrie node = new WordTrie(firstLetter, this, true);
                node.setUsefullness(usefullness);
                children.add(idx, node);
            }else{
                children.get(idx).markWordEnding();
                children.get(idx).setUsefullness(usefullness);
            }
        }
    }
    
    /*
        Returns a list of all words of a given length. It only goes down the 
        trie to a depth of the length desired.
    */
    public List<Word> getAllWordsOfLength(int depth){
        List<Word> words = new ArrayList<>();
        if(depth == 0){
            return words;
        }
        for(WordTrie node : children){
            if(node != null){
                if(node.isWordEnding && depth == 1){
                    words.add(new Word(node.getWord(), node.getUsefullness()));
                    return words;
                }
                words.addAll(node.getAllWordsOfLength(depth-1));
            }
        }
        return words;
    }
    /*
        Return a list of all the words in the trie
    */
    public List<Word> getAllWords(){
        List<Word> words = new ArrayList<>();
        for(WordTrie node : children){
            if(node != null){
                if(node.isWordEnding()){
                    words.add(new Word(node.getWord(), node.getUsefullness()));
                }
                words.addAll(node.getAllWords());
            }
        }
        return words;
    }
    
    /*
        Returns a list of all the words in the trie containing only letters
        within the range specified. It only accesses children in the 
        range using indices.
    */
    public List<Word> getAllWordsInRange(char low, char high){
        int lowIdx = low - 'a';
        int highIdx = high - 'a';
        List<Word> wordList = new ArrayList<>();
        for(int i = lowIdx; i < highIdx+1; i++){
            WordTrie node = children.get(i);
            if(node != null){
                if(node.isWordEnding()){
                    wordList.add(new Word(node.getWord(), node.getUsefullness()));
                }
                wordList.addAll(node.getAllWordsInRange(low, high));
            }
        }
        return wordList;
    }
    
    /*
        Returns a list of all the words in the trie containing only letters
        within the range specified. It only accesses children in the 
        range using indices.
    */
    public List<Word> getAllWordsWithLetters(char[] letters){
        
        List<Word> wordList = new ArrayList<>();
        for(Character c : letters){
            int idx = c - 'a';
            WordTrie node = children.get(idx);
            if(node != null){
                if(node.isWordEnding()){
                    wordList.add(new Word(node.getWord(), node.getUsefullness()));
                }
                wordList.addAll(node.getAllWordsWithLetters(letters));
            }
        }
        return wordList;
    }

    
    
}
