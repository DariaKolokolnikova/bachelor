package geneticAlgorithm;

import java.util.ArrayList;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;
 
 
public class Test extends Application {
    public static ArrayList<Double> bestIndividuumOfPopulation = new ArrayList<Double>();

    @Override public void start(Stage stage) {
        stage.setTitle("Genetic Algorithm");
        //defining the axes
        final NumberAxis xAxis = new NumberAxis();
        final NumberAxis yAxis = new NumberAxis();
        
        xAxis.setLabel("Number of Month");
        //creating the chart
        final LineChart<Number,Number> lineChart = 
                new LineChart<Number,Number>(xAxis,yAxis);
                
        lineChart.setTitle("Genetic Algorithm");
        //defining a series
        XYChart.Series series = new XYChart.Series();
        series.setName("Best Fitness of Population");
        //populating the series with data
        //forschleife, das arraylist durchgeht und einfügt
        for (int i = 0; i < bestIndividuumOfPopulation.size(); i++) {
        	//an stelle von 205 array.get(i).fitness
			series.getData().add(new XYChart.Data(i, bestIndividuumOfPopulation.get(i)));
		}
        
        
        Scene scene  = new Scene(lineChart,800,600);
        lineChart.getData().add(series);
       
        stage.setScene(scene);
        stage.show();
    }
 
    public static void main(String[] args) {
    	
    	bestIndividuumOfPopulation.add(0.3);
    	bestIndividuumOfPopulation.add(22.3);
    	bestIndividuumOfPopulation.add(33.2);
         //addBestIndividuumOfPop() method jedes mal bei neuer population !!!!!1
        launch(args);
    }
}