/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import javafx.scene.chart.*;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;

/**
 *
 * @author Marion
 */
public class Application {
    
    public static void main(String[] args) {
        
        // Appel du parseur pour remplir les classes
        
        // Creation des statistiques
        
        // 1. Camembert principal
        // 1.1 Comptage nb d'animaux total
        // 1.2 Comptage nb d'animaux pour chaque type
        // 1.3 Creation du camembert
        ObservableList<PieChart.Data> mainPieData = FXCollections.observableArrayList(
                new PieChartData("Toads", 25), 
                new PieChartData("Frogs", 25),
                new PieChartData("Triton", 25),
                new PieChartData("Other", 25));
        PieChart mainPie = new PieChart();
    }
    
}
