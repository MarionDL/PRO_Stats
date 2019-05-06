/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsonstatisticsparser;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import com.google.gson.*;


/**
 *
 * @author Edd993Surface
 */
public class JsonStatisticsParser {

    private static final String HISTORIC = "historic.json";

    public static void main(String[] args) {

        JSONParser jsonParser = new JSONParser();

        try (FileReader reader = new FileReader(HISTORIC)) {

            Object obj = jsonParser.parse(reader);

            JSONObject images = (JSONOBject) obj;

            String type = (String) images.get("type");
            String imageCounter = (String) images.get("imageCounter");
            // parcours du tableau de personnes
            JSONArray content = (JSONArray) images.get("content");
            images.forEach(content -> parseImageContent((JSONObject) content));

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void parseImageContent(JSONObject content) {

        // obtenir les détails ...
        String path = (String) content.get("path");

        JSONArray tags = (JSONArray) content.get("tags");
        images.forEach(tags -> parseContentTags((JSONArray) tags));
    }

    private static void parseContentTags(JSONArray tags) {

        // obtenir les détails ...
        String animalType = (String) tags.get[0];
        Boolean isMale = (Boolean) tags.get[1];
        Double size = (Double) tags.get[2];
        Boolean isEnteringTunnel = (Boolean) tags.get[3];

    }
}
