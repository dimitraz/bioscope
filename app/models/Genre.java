package models;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

import edu.princeton.cs.introcs.In;

public class Genre {
    public List<String> genreList = new ArrayList<String>();
    
    public Genre() {
    }
    
    public List<String> parseData() { 
        File genreFile = new File("data/genre.dat");
        
        try (BufferedReader br = new BufferedReader(new FileReader(genreFile))) {
            String line;
            String delims = "[|]";

            while ((line = br.readLine()) != null) {
                String[] genreTokens = line.split(delims);
                genreList.add(genreTokens[0]);
            }
        }
        catch(Exception e) {
            System.out.println("Unable to read genres");
        }
        
        return genreList;
    }
}
