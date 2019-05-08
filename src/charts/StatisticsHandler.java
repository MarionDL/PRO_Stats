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

    private Map<animalType, Integer> animalTypeCounter = new HashMap<>();
    private Map<String, Integer> cameraObservations = new HashMap<>();
    private Map<String, Integer> daysObservations = new HashMap<>();
    private static ArrayList<Image> images = new ArrayList<>();
    private int totalNbOfAnimals;

    public StatisticsHandler() {
        initiasizeHashMap();
    }

    private void initiasizeHashMap() {
        for (animalType a : animalType.values()) {
            animalTypeCounter.put(a, 0);
        }
    }

    private void countStringMap(Map map, String key, int value) {
        if (map.containsKey(key)) {
            map.put(key, (Integer) (map.get(key)) + value);
        } else {
            map.put(key, value);
        }
    }

    public void addImage(Image image) {
        images.add(image);
    }

    public void countCameraObservation(String cameraName, int numberOfTagsForAnImage) {
        this.countStringMap(animalTypeCounter, cameraName, numberOfTagsForAnImage);
    }

    public void countDaysObservation(String dayName, int numberOfTagsForAnImage) {
        this.countStringMap(animalTypeCounter, dayName, numberOfTagsForAnImage);
    }

    public void countAnimalType(String animal) {
        for (animalType a : animalType.values()) {
            if (animal.equals(a.getName())) {
                animalTypeCounter.put(a, animalTypeCounter.get(a) + 1);
            }
        }
    }

    public Map<animalType, Integer> getAnimalTypeCounter() {
        return this.animalTypeCounter;
    }

    public void setTotalNbOfAnimals(int n) {
        this.totalNbOfAnimals = n;
    }

    public int getTotalNbOfAnimals() {
        return this.totalNbOfAnimals;
    }

}
