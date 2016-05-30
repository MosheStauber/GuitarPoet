/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package guitarpoet;

import java.util.Comparator;

/**
 *
 * @author moshe
 */
public class Word implements Comparable<Word>{
    private String word;
    private int usefullness;
    
    public Word(){};
    
    public Word(String word, int usefullness){
        this.word = word;
        this.usefullness = usefullness;
    }
    
    public int getUsefulness(){
        return this.usefullness;
    }
    @Override
    public String toString(){
        return this.word;
    }
    
    @Override
    public int compareTo(Word w){
        return w.usefullness - this.usefullness;
    }
}
