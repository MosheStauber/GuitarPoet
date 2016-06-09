package guitarpoet;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author moshe
 */
public class Mapper {
    private List<Shape> shapes;
    private Map<Character, List<Shape>> lettersToShapes;
    
    public Mapper(){
        lettersToShapes = new HashMap<>(4);
        try {
            loadShapes();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Mapper.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    

    private void loadShapes() throws FileNotFoundException {
        
        // Load shapes array from JSON
        Gson gson = new Gson();
        JsonReader reader = new JsonReader(new FileReader("src/guitarpoet/Shapes.json"));        
        Type collectionType = new TypeToken<ArrayList<Shape>>(){}.getType();
        shapes = gson.fromJson(reader, collectionType);
        
        // Find which letters belong to which shapes and store in map
        for(int i = 'a'; i < 'z'; i++){
            // Add empty list for this letter
            lettersToShapes.put((char)i, new ArrayList<Shape>());
            for(Shape s : shapes){
                if(s.getLetterPosition((char)i) != null){
                    lettersToShapes.get((char)i).add(s);
                }
            }
        }        
//        System.out.println(shapes.get(0).toString());
    }
    
    private String getMapForWord(String word){
        StringBuilder bldr = new StringBuilder();
        int rootFret = 5; // Key of F
        
        for(char c : word.toCharArray()){
            Shape s = lettersToShapes.get(c).get(0);
            ShapePair<Integer, Integer> p = s.getLetterPosition(c);
            int start = rootFret - s.getRootOffset();
            
            bldr.append("String: " + p.getStringNumber() + "\tFret: " + (start + p.getOffset()) + "\t\t" + s.getId() +  "\n");
        }
        
        return bldr.toString();
    }
    
    public static void main(String[] args){
        Mapper map = new Mapper();
        
        while(true){
//                        System.out.println("Size of the program: " + (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));

            System.out.println("\n"
                    + "Press q to quit.\n"
                    + "1. Enter words without spaces\n");
            
            Scanner in = new Scanner(System.in);
            String input = in.nextLine();  
            
            if(input.equals("q")){
                break;
            }else{
                System.out.println(map.getMapForWord(input));
            }
            
        }
    }
}
