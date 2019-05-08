/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package charts;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.JFXPanel;
import javafx.geometry.Side;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javax.swing.*;

import jsonstatisticsparser.*;
import animalType.*;

/**
 *
 * @author Marion
 */
public class MainApp extends JFrame {
    
    private static JsonStatisticsParser parser;
    private static StatisticsHandler statHandler = new StatisticsHandler();
    
    public static void initAndShowGUI() {
            //JPanel background = new javax.swing.JPanel();
            JFrame mainFrame = new JFrame("Crapauduc Viewer Statistics");
            final JFXPanel fxPanel = new JFXPanel();
            mainFrame.add(fxPanel);
            mainFrame.setSize(1000, 500);
            mainFrame.setVisible(true);
            mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    initFX(fxPanel);
                }
            });
    }
    
    private static Scene createScene() {
        
        // Recuperation des donnees du parser
        parser = new JsonStatisticsParser(statHandler);
        parser.parseFile();

        Group root = new Group();
        Color backgroundColor = Color.rgb(110, 25, 222);
        Scene scene = new Scene(root, backgroundColor);
        scene.getStylesheets().add("charts/AppStyle.css");

        /*
        * Creation de la Pie Chart principale
        * */
        Group pieChartGroup = createMainPieChart();
        root.getChildren().add(pieChartGroup);

        /*
         * Creation de la zone d'infos droite
         */
        Group sideInfosGroup = createSideInfosZone();
        root.getChildren().add(sideInfosGroup);

        return (scene);
    }
    
    /*
     * Cree un groupe avec la pie chart principale
     */
    public static Group createMainPieChart() {
        
        Group pieChartGroup = new Group();
        
        // Recuperation du nombre d'animaux
        int totalNbOfAnimals = statHandler.getTotalNbOfAnimals();
        int nbOfTritons = statHandler.getAnimalTypeCounter().get(animalType.TRITON);
        int nbOfToads = statHandler.getAnimalTypeCounter().get(animalType.GRENOUILLE);
        int nbOfFrogs = statHandler.getAnimalTypeCounter().get(animalType.CRAPAUD);
        int nbOfOther = statHandler.getAnimalTypeCounter().get(animalType.AUTRE);
        
        // Creation d'une liste de donnees a mettre dans la pie chart
        ObservableList<PieChart.Data> pieChartData =
                FXCollections.observableArrayList(
                new PieChart.Data(animalType.TRITON.getName(), nbOfTritons),
                new PieChart.Data(animalType.GRENOUILLE.getName(), nbOfFrogs),
                new PieChart.Data(animalType.CRAPAUD.getName(), nbOfToads),
                new PieChart.Data(animalType.AUTRE.getName(), nbOfOther));
        
        PieChart chart = new PieChart(pieChartData);
        // Positionnement a gauche de la fenetre
        chart.setLayoutX(0);
        chart.setLayoutY(0);
        chart.setTitle("Nombre d'animaux\n(" + totalNbOfAnimals + " tagged)");
        chart.setLegendVisible(false);
        chart.setTitleSide(Side.BOTTOM);

        // Change colors : faut le faire apres avoir affiché la scene
        pieChartData.get(0).getNode().setStyle("-fx-pie-color: #8EFA9C;");
        pieChartData.get(1).getNode().setStyle("-fx-pie-color: #3F319B;");
        pieChartData.get(2).getNode().setStyle("-fx-pie-color: #180F50;");
        pieChartData.get(3).getNode().setStyle("-fx-pie-color: #807AA8;");

        // Ajout de la chart au root group
        pieChartGroup.getChildren().add(chart);
        
        return pieChartGroup;
    }
    
    public static Group createSideInfosZone() {
        
        Group sideInfosGroup = new Group();
        
        Text imagesLabel = new Text();
        imagesLabel.setFont(new Font(20));
        imagesLabel.setText("IMAGES");
        imagesLabel.setX(500);
        imagesLabel.setY(100);
        imagesLabel.setFill(Color.WHITE);
        sideInfosGroup.getChildren().add(imagesLabel);

        Text imagesInfos = new Text();
        imagesInfos.setFont(new Font(10));
        imagesInfos.setText("tagged : 423\n" +
                            "untagged : 327\n" +
                            "total count : 750");
        imagesInfos.setX(670);
        imagesInfos.setY(80);
        imagesInfos.setFill(Color.WHITE);
        sideInfosGroup.getChildren().add(imagesInfos);

        Text sequencesLabel = new Text();
        sequencesLabel.setFont(new Font(20));
        sequencesLabel.setText("SEQUENCES");
        sequencesLabel.setX(500);
        sequencesLabel.setY(200);
        sequencesLabel.setFill(Color.WHITE);
        sideInfosGroup.getChildren().add(sequencesLabel);

        Text sequencesInfos = new Text();
        sequencesInfos.setFont(new Font(10));
        sequencesInfos.setText("tagged : 40\n" +
                "untagged : 35\n" +
                "total count : 75");
        sequencesInfos.setX(670);
        sequencesInfos.setY(180);
        sequencesInfos.setFill(Color.WHITE);
        sideInfosGroup.getChildren().add(sequencesInfos);

        Text camerasLabel = new Text();
        camerasLabel.setFont(new Font(20));
        camerasLabel.setText("CAMERAS");
        camerasLabel.setX(500);
        camerasLabel.setY(300);
        camerasLabel.setFill(Color.WHITE);
        sideInfosGroup.getChildren().add(camerasLabel);

        Text camerasInfos = new Text();
        camerasInfos.setFont(new Font(10));
        camerasInfos.setText("most used : CR1\n" +
                "least used : CR5\n");
        camerasInfos.setX(670);
        camerasInfos.setY(285);
        camerasInfos.setFill(Color.WHITE);
        sideInfosGroup.getChildren().add(camerasInfos);

        Text observationsLabel = new Text();
        observationsLabel.setFont(new Font(20));
        observationsLabel.setText("OBSERVATIONS");
        observationsLabel.setX(500);
        observationsLabel.setY(400);
        observationsLabel.setFill(Color.WHITE);
        sideInfosGroup.getChildren().add(observationsLabel);

        Text observationsInfos = new Text();
        observationsInfos.setFont(new Font(10));
        observationsInfos.setText("most : 2017-02-23\n" +
                "least : 2017-3-11\n");
        observationsInfos.setX(670);
        observationsInfos.setY(385);
        observationsInfos.setFill(Color.WHITE);
        sideInfosGroup.getChildren().add(observationsInfos);
        
        return sideInfosGroup;
    }
    
    private static void initFX(JFXPanel fxPanel) {
        Scene scene = createScene();
        fxPanel.setScene(scene);
    }
    
    /*
     * Main entry point
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                initAndShowGUI();
            }
        });
    }
}
