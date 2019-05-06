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
    
    // Tags saved in this image
    private ArrayList<Tag> tags;
    
    public Image(String path, ArrayList<Tag> tags) {
        this.path = path;
        this.tags = tags;
    }
    
    public ArrayList<Tag> getTags() {
        return this.tags;
    }
}