/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsonstatisticsparser;

import animalType.animalType;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import com.google.gson.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import statistics.images.Image;
import statistics.tags.Tag;

/**
 *
 * @author Edd993Surface
 */
public class JsonStatisticsParser {

    private static final String HISTORIC = "historic.json";
    private static ArrayList<Image> images = new ArrayList<>();
    private static String type;
    private static int nbTotal;
    private int totalNbOfAnimals;
    private Map<animalType, Integer> map = new HashMap<>();
    
    public JsonStatisticsParser() {
        initiasizeHashMap();
        
    }

    private void initiasizeHashMap() {

        for (animalType a : animalType.values()) {
            map.put(a, 0);

        }
    }
     
     
    public JsonParser parseFile() {

        JsonParser jsonParser = new JsonParser();
        try (FileReader reader = new FileReader(HISTORIC)) {

            Object obj = jsonParser.parse(reader);

            JsonObject images = (JsonObject) obj;

            type = (String) images.get("type").toString();
            nbTotal = images.get("imageCounter").getAsInt();    
            JsonArray content = (JsonArray) images.get("content");
            parseImageContent(content);
            


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return jsonParser;
    }

    private void parseImageContent(JsonArray content) {

        for (Object image : content) {
            JsonObject jimage = (JsonObject) image;
            
            String path = (String) jimage.get("path").toString();
            JsonArray tags = (JsonArray) jimage.get("tags");
            
           ArrayList<Tag> tagsStructure = parseContentTags(tags);
           Image imageStructure = new Image(path, tagsStructure);
           
           images.add(imageStructure);
        }
        
    }

    private ArrayList<Tag> parseContentTags(JsonArray tags) {

        ArrayList<Tag> result = new ArrayList<Tag>();
        for (Object tag : tags) {
            JsonArray jtag = (JsonArray) tag;
            Tag tagStructure = new Tag();

            findAnimalType(jtag.get(0).getAsString());
            
            tagStructure.setTypeAnimal(jtag.get(0).getAsString());
            tagStructure.setIsMale(jtag.get(1).getAsBoolean());
            tagStructure.setSize(jtag.get(2).getAsDouble());
            tagStructure.setIsEnteringTunnel(jtag.get(3).getAsBoolean());

            result.add(tagStructure);
        }
        return result;
    }

    private void findAnimalType(String animal) {
        for (animalType a : animalType.values()) {
            if (animal.equals(a.getName())) {
                map.put(a, map.get(a) + 1);
            }
        }
    }
    
   public int getNbTotal() {
        return this.nbTotal;
    }
    
    public int getTotalNbOfAnimals() {
        return this.totalNbOfAnimals;
    }
    
    public Map<animalType, Integer> getMap() {
        return this.map;
    }
}
