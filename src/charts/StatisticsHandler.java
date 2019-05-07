/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package charts;

import animalType.animalType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import statistics.images.Image;

/**
 *
 * @author Marion
 */
public class StatisticsHandler {
    
    private Map<animalType, Integer> map = new HashMap<>();
    private static ArrayList<Image> images = new ArrayList<>();
    private int totalNbOfAnimals;
    
    public StatisticsHandler() {
        initiasizeHashMap(); 
    }
    
    private void initiasizeHashMap() {

        for (animalType a : animalType.values()) {
            map.put(a, 0);
        }
    }
    
    public void addImage(Image image) {
        images.add(image);
    }
    
    public void findAnimalType(String animal) {
        for (animalType a : animalType.values()) {
            if (animal.equals(a.getName())) {
                map.put(a, map.get(a) + 1);
            }
        }
    }
    
    public Map<animalType, Integer> getMap() {
        return this.map;
    }
    
    public void setTotalNbOfAnimals(int n) {
        this.totalNbOfAnimals = n;
    }
    
    public int getTotalNbOfAnimals() {
        return this.totalNbOfAnimals;
    }
    
}
