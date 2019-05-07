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
import animalType.*;

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
        /* Group gr = new Group();
        Button btn = new Button();
        btn.setText("Test");
        gr.getChildren().add(btn);
        ((Group) scene.getRoot()).getChildren().add(gr); */
        
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
        int totalNbOfAnimals = parser.getTotalNbOfAnimals();
        int nbOfTritons = parser.getMap().get(animalType.TRITON);
        int nbOfToads = parser.getMap().get(animalType.GRENOUILLE);
        int nbOfFrogs = parser.getMap().get(animalType.CRAPAUD);
        int nbOfOther = parser.getMap().get(animalType.AUTRE);
       
        // Creation d'une liste de donnees a mettre dans la pie chart
        ObservableList<PieChart.Data> pieChartData =
                FXCollections.observableArrayList(
                new PieChart.Data(animalType.TRITON.getName(), nbOfTritons),
                new PieChart.Data(animalType.GRENOUILLE.getName(), nbOfFrogs),
                new PieChart.Data(animalType.CRAPAUD.getName(), nbOfToads),
                new PieChart.Data(animalType.AUTRE.getName(), nbOfOther));
        
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
