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
import javafx.scene.control.Button;

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
        Group mainGroup = new Group();
        Scene scene = new Scene(mainGroup);
        stage.setTitle("Crapauduc Viewer Statistics");
        stage.setWidth(1000);
        stage.setHeight(1000);
 
        // Pie chart principale
        PieChart chart = createMainPieChart();

        // A integrer dans Swing avec JFXPanel
        ((Group) scene.getRoot()).getChildren().add(chart);
        
        // Creation de la fenetre droite
        Group gr = new Group();
        Button btn = new Button();
        btn.setText("Test");
        gr.getChildren().add(btn);
        ((Group) scene.getRoot()).getChildren().add(gr);
        
        // A integrer dans Swing avec JFXPanel
        stage.setScene(scene);
        stage.show();
    }
    
    /**
     * Cree la pie chart principale
     * @return une nouvelle pie chart
     */
    public PieChart createMainPieChart() {
        
        // Recuperation du nombre d'animaux
        // int totalNbOfAnimals = parser.getTotalNbOfAnimals();
        /* int nbOfTritons = parser.getNbOfTritons();
        int nbOfToads = parser.getNbOfToads();
        int nbOfFrogs = parser.getNbOfFrogs();
        int nbOfOther = parser.getNbOfOthers(); */
        
        int totalNbOfAnimals = 100;
        int nbOfTritons = 25;
        int nbOfToads = 25;
        int nbOfFrogs = 25;
        int nbOfOther = 25;
 
        // Creation d'une liste de donnees a mettre dans la pie chart
        ObservableList<PieChart.Data> pieChartData =
                FXCollections.observableArrayList(
                new PieChart.Data("Tritons", nbOfTritons),
                new PieChart.Data("Frogs", nbOfFrogs),
                new PieChart.Data("Toads", nbOfToads),
                new PieChart.Data("Other", nbOfOther));
        
        // Change colors : faut le faire apres avoir affiché la scene
        /* for (PieChart.Data data : pieChartData) {
            data.getNode().setStyle("-fx-pie-color: #ffd700;");
        } */
        
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
