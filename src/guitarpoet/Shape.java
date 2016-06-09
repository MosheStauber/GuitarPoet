/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package guitarpoet;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import javafx.util.Pair;

/**
 *
 * @author moshe
 */
public class Shape {
    private final String id;
    // The map is chars in shape, Pair<String num, offset from start> 
    private Map<Character, ShapePair<Integer,Integer>> fretPosition;
    private int rootOffset;
    
    public Shape(String id, int rootOffset){
        this.id = id;
        fretPosition = new LinkedHashMap<>();
        this.rootOffset = rootOffset;
    }
    
    public void addLetter(char c, int strNum, int offset){
        fretPosition.put(c, new ShapePair(strNum,offset));
    }
    
    public ShapePair getLetterPosition(char letter){
        return fretPosition.get(letter);
    }
    
    public int getRootOffset(){
        return this.rootOffset;
    }
    
    public String getId(){
        return this.id;
    }
    @Override
    public String toString(){
        StringBuilder bldr = new StringBuilder();
        bldr.append(this.id + "\n");
        
        Iterator it = fretPosition.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            bldr.append(pair.getKey() + " = " + pair.getValue() + "\n");
        }
        
        return bldr.toString();
    }
}
