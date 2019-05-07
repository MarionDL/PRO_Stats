/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statistics.images;

import statistics.tags.*;
import java.util.ArrayList;

/**
 *
 * @author Marion
 */
public class Image {
    
    // Path to this image
    private final String path;
    
    
    private static final String PATHSEPARATOR = "\\"; 
    private String camera;
    private String date;
    private String sequence;  // hh:mm form
    
    // Tags saved in this image
    private final ArrayList<Tag> tags;
    
    public Image(String path, ArrayList<Tag> tags) {
        this.path = path;
        
        pathDecomposition(path);
        
        this.tags = tags;
    }
    
    
    private void pathDecomposition(String path){
    
        String[] path_sep = path.split("\\");
        this.camera = path_sep[1];
        this.date = path_sep[2];
        this.sequence = path_sep[5] + ":" + path_sep[6];
    }
    
    public ArrayList<Tag> getTags() {
        return this.tags;
    }
    
    @Override
    public String toString(){
        return camera + " " + date + " " + sequence + " :    " + tags.toString() + "\n";
    }
    
    
}