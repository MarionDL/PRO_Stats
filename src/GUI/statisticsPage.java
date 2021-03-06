/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package GUI;

import Statistics.components.Month;
import Statistics.parser.statParser;
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

import animalType.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.chart.XYChart;
import Statistics.handler.StatisticsHandler;

/**
 *
 * @author Marion
 */
public class statisticsPage extends JFrame {
    
    private static statParser parser;
    private static StatisticsHandler statHandler = new StatisticsHandler();
    
    public static void initAndShowGUI() {
            //JPanel background = new javax.swing.JPanel();
            JFrame mainFrame = new JFrame("Crapauduc Viewer Statistics");
            final JFXPanel fxPanel = new JFXPanel();
            mainFrame.add(fxPanel);
            mainFrame.setSize(1500, 1000);
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
        parser = new statParser(statHandler);
        parser.parseFile();
        statHandler.analyzeData();
        System.out.println(statHandler.test());

        Group root = new Group();
        Color backgroundColor = Color.rgb(110, 25, 222);
        Scene scene = new Scene(root, backgroundColor);
        scene.getStylesheets().add("Statistics/style/chartsStyle.css");

        /*
        * Creation de la Pie Chart principale
        */
        Group pieChartGroup = createMainPieChart();
        root.getChildren().add(pieChartGroup);

        /*
         * Creation de la zone d'infos droite
         */
        Group sideInfosGroup = createSideInfosZone();
        root.getChildren().add(sideInfosGroup);
        
        /*
         * Creation de la Line Chart mois/animaux 
         */
        Group lineChartGroup = createLineChart();
        root.getChildren().add(lineChartGroup);
        
        /*
         * Creation de la Bar Chart mois/animaux 
         */
        Group barChartGroup = createBarChart();
        root.getChildren().add(barChartGroup);

        return (scene);
    }
    
    /*
     * Cree un groupe avec la pie chart principale
     */
    public static Group createMainPieChart() {
        
        Group pieChartGroup = new Group();
        
        // Recuperation du nombre d'animaux
        int totalNbOfAnimals = statHandler.getTaggedAnimals();
        int nbOfTritons = statHandler.getAnimalTypeCounter().get(AnimalType.TRITON);
        int nbOfToads = statHandler.getAnimalTypeCounter().get(AnimalType.GRENOUILLE);
        int nbOfFrogs = statHandler.getAnimalTypeCounter().get(AnimalType.CRAPAUD);
        int nbOfOther = statHandler.getAnimalTypeCounter().get(AnimalType.AUTRE);
        
        // Creation d'une liste de donnees a mettre dans la pie chart
        ObservableList<PieChart.Data> pieChartData =
                FXCollections.observableArrayList(
                new PieChart.Data(AnimalType.TRITON.getName(), nbOfTritons),
                new PieChart.Data(AnimalType.GRENOUILLE.getName(), nbOfFrogs),
                new PieChart.Data(AnimalType.CRAPAUD.getName(), nbOfToads),
                new PieChart.Data(AnimalType.AUTRE.getName(), nbOfOther));
        
        PieChart chart = new PieChart(pieChartData);
        // Positionnement a gauche de la fenetre
        chart.setLayoutX(0);
        chart.setLayoutY(0);
        chart.setTitle("Nombre d'animaux\n(" + totalNbOfAnimals + " tagged)");
        chart.setLegendVisible(false);
        chart.setTitleSide(Side.BOTTOM);

        // Couleurs des tranches
        pieChartData.get(0).getNode().setStyle("-fx-pie-color: #8EFA9C;");
        pieChartData.get(1).getNode().setStyle("-fx-pie-color: #3F319B;");
        pieChartData.get(2).getNode().setStyle("-fx-pie-color: #180F50;");
        pieChartData.get(3).getNode().setStyle("-fx-pie-color: #807AA8;");

        // Ajout de la chart au root group
        pieChartGroup.getChildren().add(chart);
        
        return pieChartGroup;
    }
    
    /*
     * Cree un groupe avec la line chart mois/animaux
     */
    public static Group createLineChart() {
        Group lineChartGroup = new Group();
        
        // Definition des axes
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        
        final LineChart<String, Number> lineChart = new LineChart<>(xAxis, yAxis);
                
        lineChart.setTitle("Number of animals per month");
        // Positionnement a gauche de la fenetre
        lineChart.setLayoutX(0);
        lineChart.setLayoutY(500);
        lineChart.setLegendVisible(false);
        
        XYChart.Series series = new XYChart.Series();
        
        for (Month m : Month.values()){
           series.getData().add(new XYChart.Data(m.getAbbreviation(), statHandler.getAnimalNbByMonth(m)));
        }
        
        lineChart.getData().add(series);
        lineChartGroup.getChildren().add(lineChart);
        
        return lineChartGroup;
    }
    
    /*
     * Cree un groupe avec la bar chart mois/animaux
     */
    public static Group createBarChart() {
        Group barChartGroup = new Group();
        
        // Definition des axes
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        
        StackedBarChart<String, Number> sbc = new StackedBarChart<>(xAxis, yAxis);
        sbc.setTitle("Number of animals per month");
        
        List<String> xAxeNames = new ArrayList<String>();
        
        for (Month m : Month.values()){
          xAxeNames.add(m.getAbbreviation());
        }
        xAxis.setCategories(FXCollections.<String>observableArrayList(xAxeNames));

        sbc.setLayoutX(500);
        sbc.setLayoutY(500);
        

         for (AnimalType a : AnimalType.values()){               
            XYChart.Series<String, Number> serie = new XYChart.Series<>();
            serie.setName(a.getName());          
            sbc.getData().add(serie);
        }
          
        for (Month m : Month.values()) {
            List<Integer> list = statHandler.getAnimalNbByMonthByType(m);
            for (AnimalType a2 : AnimalType.values()) {
                XYChart.Series<String, Number> serie = sbc.getData().get(a2.ordinal());
                serie.getData().add(new XYChart.Data<>(m.getAbbreviation(), list.get(a2.ordinal())));
            }
        }
               
        barChartGroup.getChildren().add(sbc);        
        return barChartGroup;
    }
    
    /*
     * Cree un groupe avec la zone d'infos droite
     */
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
        imagesInfos.setText("tagged : "+ statHandler.getTaggedImages() +"\n" +
                            "untagged : XXX\n" +
                            "total count : XXX");
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
        sequencesInfos.setText("tagged : "+ statHandler.getTaggedSequenceNumber() +"\n" +
                "untagged : XXX\n" +
                "total count : XXX\n" +
                "most captures : "+ statHandler.getMostTaggedSequence() +"\n" +
                "least captures : "+ statHandler.getLeastTaggedSequence() +"\n");
        
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
        camerasInfos.setText("most used : "+ statHandler.getMostUsedCamera() +"\n" +
                             "least used : "+ statHandler.getLeastUsedCamera() +"\n");
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
        observationsInfos.setText("most : "+ statHandler.getMostFrequentDate() +"\n" +
                                 "least : "+ statHandler.getLeastFrequentDate() +"\n");
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
