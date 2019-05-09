/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package charts;

import animalType.animalType;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.StringJoiner;
import statistics.images.Image;

/**
 *
 * @author Marion
 */
public class StatisticsHandler {
    
    private static final String MIN = "min";
    private static final String MAX = "max";


    private Map<animalType, Integer> animalTypeCounter = new HashMap<>();
    public Map<String, Integer> cameraObservations = new HashMap<>();
    public Map<String, Integer> dateObservations = new HashMap<>();
    public Map<String, Integer> sequenceObservations = new HashMap<>();
    private static ArrayList<Image> images = new ArrayList<>();
    
    private int totalNbOfAnimals;
    
    List<String> cameraMaxKeys;
    List<String> cameraMinKeys;
    List<String> dateMaxKeys;
    List<String> dateMinKeys;
    List<String> sequenceMaxKeys;
    List<String> sequenceMinKeys;
    
    public StatisticsHandler() {
        initiasize();
    }

    private void initiasize() {
        for (animalType a : animalType.values()) {
            animalTypeCounter.put(a, 0);     
        }
        totalNbOfAnimals = 0;
    }

    private Integer findLimitValueInMap(Map map, String type) {

        Integer result = 0;

        if (type.equals(MIN)) {
            result = (Integer) Collections.min(map.values());
        } else if (type.equals(MAX)) {
            result = (Integer) Collections.max(map.values());
        }
        return result;
    }

    private List<String> findLimitKeysInMap(Map map, int value) {

        List<String> keys = new ArrayList<>();
        for (Object iter : map.entrySet()) {
            Entry<String, Integer> entry = (Entry<String, Integer>) iter;
            if (entry.getValue() == value) {
                keys.add(entry.getKey());
            }
        }
        return keys;

    }

    private String returnDataString(List<String> list) {

        StringJoiner joiner = new StringJoiner(" - ");
        for (String iter : list) {
            joiner.add(iter);
        }
        return joiner.toString();
    }

    private void countStringMap(Map map, String key, int value) {
        if (map.containsKey(key)) {
            map.put(key, (Integer) (map.get(key)) + value);
        } else {
            map.put(key, value);
        }
    }

    public void countCameraObservation(String cameraName, int numberOfTagsForAnImage) {
        this.countStringMap(cameraObservations, cameraName, numberOfTagsForAnImage);
    }

    public void countDaysObservation(String dayName, int numberOfTagsForAnImage) {
        this.countStringMap(dateObservations, dayName, numberOfTagsForAnImage);
    }

    public void countSequenceObservation(String sequenceName, int numberOfTagsForAnImage) {
        this.countStringMap(sequenceObservations, sequenceName, numberOfTagsForAnImage);
    }

    public void countAnimalType(String animal) {
        for (animalType a : animalType.values()) {
            if (animal.equals(a.getName())) {
                animalTypeCounter.put(a, animalTypeCounter.get(a) + 1);
            }
        }
    }

    public void analyzeData() {
        cameraMinKeys = findLimitKeysInMap(cameraObservations, findLimitValueInMap(cameraObservations, MIN));
        cameraMaxKeys = findLimitKeysInMap(cameraObservations, findLimitValueInMap(cameraObservations, MAX));
        dateMinKeys = findLimitKeysInMap(dateObservations, findLimitValueInMap(dateObservations, MIN));
        dateMaxKeys = findLimitKeysInMap(dateObservations, findLimitValueInMap(dateObservations, MAX));
        sequenceMinKeys = findLimitKeysInMap(sequenceObservations, findLimitValueInMap(sequenceObservations, MIN));
        sequenceMaxKeys = findLimitKeysInMap(sequenceObservations, findLimitValueInMap(sequenceObservations, MAX));
    }

    public void addImage(Image image) {
        images.add(image);
    }
    
    public void addNbAnimals(int n) {
       this.totalNbOfAnimals += n;
    }
    
    public Map<animalType, Integer> getAnimalTypeCounter() {
        return this.animalTypeCounter;
    }

    public void setTotalNbOfAnimals(int n) {
        this.totalNbOfAnimals = n;
    }

    public int getTaggedAnimals() {
        return this.totalNbOfAnimals;
    }
    
    public int getTaggedImages() {
        return images.size();
    }

    public String getMostUsedCamera() {
        return returnDataString(cameraMaxKeys);
    }

    public String getLeastUsedCamera() {
        return returnDataString(cameraMinKeys);
    }

    public String getMostFrequentDate() {
        return returnDataString(dateMaxKeys);
    }

    public String getLeastFrequentDate() {
        return returnDataString(dateMinKeys);
    }
    
    public String getMostTaggedSequence() {
        return returnDataString(sequenceMaxKeys);
    }

    public String getLeastTaggedSequence() {
        return returnDataString(sequenceMinKeys);
    }
    
    public int getTaggedSequenceNumber() {
        return sequenceObservations.size();
    }
    
}
