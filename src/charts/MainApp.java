/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package charts;

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.chart.*;
import javafx.scene.Group;
import jsonstatisticsparser.*;

/**
 *
 * @author Marion
 */
public class MainApp extends Application {
    
    private JsonStatisticsParser parser;
    
    @Override 
    public void start(Stage stage) {
        
        // Recuperation des donnees du parser
        parser = new JsonStatisticsParser();
        parser.parseFile();
        
        // A integrer dans Swing avec JFXPanel
        Scene scene = new Scene(new Group());
        stage.setTitle("Crapauduc Viewer Statistics");
        stage.setWidth(500);
        stage.setHeight(500);
 
        // Pie chart principale
        PieChart chart = createMainPieChart();

        // A integrer dans Swing avec JFXPanel
        ((Group) scene.getRoot()).getChildren().add(chart);
        stage.setScene(scene);
        stage.show();
    }
    
    /**
     * Cree la pie chart principale
     * @return une nouvelle pie chart
     */
    public PieChart createMainPieChart() {
        
        // Recuperation du nombre d'animaux
        int totalNbOfAnimals = parser.getTotalNbOfAnimals();
        int nbOfTritons = parser.getNbOfTritons();
        int nbOfToads = parser.getNbOfToads();
        int nbOfFrogs = parser.getNbOfFrogs();
        int nbOfOther = parser.getNbOfOthers();
 
        // Creation d'une liste de donnees a mettre dans la pie chart
        ObservableList<PieChart.Data> pieChartData =
                FXCollections.observableArrayList(
                new PieChart.Data("Tritons", nbOfTritons),
                new PieChart.Data("Frogs", nbOfFrogs),
                new PieChart.Data("Toads", nbOfToads),
                new PieChart.Data("Other", nbOfOther));
        
        // Creation d'une pie chart
        final PieChart chart = new PieChart(pieChartData);
        chart.setTitle("Number of animals\n(" + totalNbOfAnimals + " tagged)");
        
        return chart;
    }
    
    /*
     * Main entry point
     */
    public static void main(String[] args) {

        launch(args);
    }
    
}
