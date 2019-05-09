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
import java.util.Arrays;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.chart.XYChart;

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
        parser = new JsonStatisticsParser(statHandler);
        parser.parseFile();
        statHandler.analyzeData();
        System.out.println(statHandler.test());

        Group root = new Group();
        Color backgroundColor = Color.rgb(110, 25, 222);
        Scene scene = new Scene(root, backgroundColor);
        scene.getStylesheets().add("charts/AppStyle.css");

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
        
        // Remplissage du graphe
        series.getData().add(new XYChart.Data("Jan", 23));
        series.getData().add(new XYChart.Data("Feb", 14));
        series.getData().add(new XYChart.Data("Mar", 15));
        series.getData().add(new XYChart.Data("Apr", 24));
        series.getData().add(new XYChart.Data("May", 34));
        series.getData().add(new XYChart.Data("Jun", 36));
        series.getData().add(new XYChart.Data("Jul", 22));
        series.getData().add(new XYChart.Data("Aug", 45));
        series.getData().add(new XYChart.Data("Sep", 43));
        series.getData().add(new XYChart.Data("Oct", 17));
        series.getData().add(new XYChart.Data("Nov", 29));
        series.getData().add(new XYChart.Data("Dec", 25));
        
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
        xAxis.setCategories(FXCollections.<String>observableArrayList(
                Arrays.asList("Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul",
                        "Aug", "Sep", "Oct", "Nov", "Dec")));
        sbc.setLayoutX(500);
        sbc.setLayoutY(500);
        
        // Definition des series (type d'animal)
        XYChart.Series<String, Number> seriesTriton = new XYChart.Series<>();
        XYChart.Series<String, Number> seriesFrog = new XYChart.Series<>();
        XYChart.Series<String, Number> seriesToad = new XYChart.Series<>();
        seriesTriton.setName("Tritons");
        seriesFrog.setName("Frogs");
        seriesToad.setName("Toads");
        
        // Donnees sur les tritons
        seriesTriton.getData().add(new XYChart.Data<>("Jan", 25));
        seriesTriton.getData().add(new XYChart.Data<>("Feb", 5));
        seriesTriton.getData().add(new XYChart.Data<>("Mar", 23));
        seriesTriton.getData().add(new XYChart.Data<>("Apr", 45));
        seriesTriton.getData().add(new XYChart.Data<>("May", 45));
        seriesTriton.getData().add(new XYChart.Data<>("Jun", 25));
        seriesTriton.getData().add(new XYChart.Data<>("Jul", 5));
        seriesTriton.getData().add(new XYChart.Data<>("Aug", 23));
        seriesTriton.getData().add(new XYChart.Data<>("Sep", 45));
        seriesTriton.getData().add(new XYChart.Data<>("Oct", 45));
        seriesTriton.getData().add(new XYChart.Data<>("Nov", 25));
        seriesTriton.getData().add(new XYChart.Data<>("Dec", 5));
        
        // Donnees sur les crapauds
        seriesToad.getData().add(new XYChart.Data<>("Jan", 40));
        seriesToad.getData().add(new XYChart.Data<>("Feb", 7));
        seriesToad.getData().add(new XYChart.Data<>("Mar", 38));
        seriesToad.getData().add(new XYChart.Data<>("Apr", 50));
        seriesToad.getData().add(new XYChart.Data<>("May", 50));
        seriesToad.getData().add(new XYChart.Data<>("Jun", 50));
        seriesToad.getData().add(new XYChart.Data<>("Jul", 38));
        seriesToad.getData().add(new XYChart.Data<>("Aug", 34));
        seriesToad.getData().add(new XYChart.Data<>("Sep", 32));
        seriesToad.getData().add(new XYChart.Data<>("Oct", 25));
        seriesToad.getData().add(new XYChart.Data<>("Nov", 6));
        seriesToad.getData().add(new XYChart.Data<>("Dec", 8));
        
        // Donnees sur les grenouilles
        seriesFrog.getData().add(new XYChart.Data<>("Jan", 50));
        seriesFrog.getData().add(new XYChart.Data<>("Feb", 12));
        seriesFrog.getData().add(new XYChart.Data<>("Mar", 22));
        seriesFrog.getData().add(new XYChart.Data<>("Apr", 34));
        seriesFrog.getData().add(new XYChart.Data<>("May", 55));
        seriesFrog.getData().add(new XYChart.Data<>("Jun", 66));
        seriesFrog.getData().add(new XYChart.Data<>("Jul", 34));
        seriesFrog.getData().add(new XYChart.Data<>("Aug", 36));
        seriesFrog.getData().add(new XYChart.Data<>("Sep", 39));
        seriesFrog.getData().add(new XYChart.Data<>("Oct", 25));
        seriesFrog.getData().add(new XYChart.Data<>("Nov", 10));
        seriesFrog.getData().add(new XYChart.Data<>("Dec", 8));
        
        sbc.getData().addAll(seriesTriton, seriesFrog, seriesToad);
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
