package geneticAlgorithm;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

import java.util.Collections;
import java.util.List;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class HFTStreckenAlgorithmus extends Application {

	static int locationCount;
	public static ArrayList<Punkt> locations = new ArrayList<Punkt>();
	public static ArrayList<Individuum> POP = new ArrayList<Individuum>();
	public static ArrayList<Individuum> PAR = new ArrayList<Individuum>();
	public static ArrayList<Individuum> KID = new ArrayList<Individuum>();
	public static ArrayList<Double> bestFitnessOfPopulation = new ArrayList<Double>();
	static int popSize;
	static String line = "";
	static Punkt destination;
	static int runs;
	static int selectionChoice;

	@Override
	public void start(Stage stage) {
		String selection = "";
		if (selectionChoice == 1) {
			selection = "fitnessproportionale Selektion";
		}
		if (selectionChoice == 2) {
			selection = "Rangselektion";
		}
		if (selectionChoice == 3) {
			selection = "eitärer Selektion";
		}
		if (selectionChoice == 4) {
			selection = "Wettkampfselektion";
		}
		stage.setTitle("Genetischer Algorithmus mit " + selection);
		// defining the axes
		final NumberAxis xAxis = new NumberAxis();
		final NumberAxis yAxis = new NumberAxis();
		yAxis.setLabel("Fitness/Routenlänge");
		xAxis.setLabel("Iteration");
		// creating the chart
		final LineChart<Number, Number> lineChart = new LineChart<Number, Number>(xAxis, yAxis);
		lineChart.setTitle("Genetischer Algorithmus");
		// defining a series
		XYChart.Series series = new XYChart.Series();
		series.setName("Best Fitness of Population");
		// populating the series with data
		for (int i = 0; i < bestFitnessOfPopulation.size(); i++) {
			series.getData().add(new XYChart.Data(i, bestFitnessOfPopulation.get(i)));
		}
		Scene scene = new Scene(lineChart, 800, 600);
		lineChart.getData().add(series);
		stage.setScene(scene);
		stage.show();
	}

	public static void main(String[] args) {
		requestUserInput();
		long startTime = System.currentTimeMillis();

		
		locations = getLocationsFromCSV();
		generaterRandomPopulation(locationCount);

		for (int i = 0; i < runs; i++) {
			for (Individuum individuum : POP) {
				individuum.setFitness(calculateFitness(individuum.getRoute()));
			}
			bestFitnessOfPopulation.add(getBestFitnessOfPOP());

			if (selectionChoice == 1) {
				selektionFitnessproportional();
			}
			if (selectionChoice == 2) {
				rankSelektion();
			}
			if (selectionChoice == 3) {
				selektionElitaer();
			}
			if (selectionChoice == 4) {
				selektionTournament();
			}
			for (Individuum individuum : PAR) {
				individuum.setFitness(calculateFitness(individuum.getRoute()));
			}
			System.out.println("DURCHGANG:" +(1+i) + "  size" + PAR.size());
			for (Individuum indiv : PAR) {
				System.out.println("FITNESS:" + indiv.fitness);
			}
			generateKids();
			for (Individuum individuum : KID) {
				individuum.setFitness(calculateFitness(individuum.getRoute()));
			}
			for (Individuum individuum : PAR) {
				individuum.setFitness(calculateFitness(individuum.getRoute()));
			}
			POP.clear();
			POP.addAll(KID);
			POP.addAll(PAR);
			PAR.clear();
			KID.clear();
		}

		double comparedFitness = testFitness();
		System.out.println("\nWenn jeder Student einzeln fahren würde: Streckenlänge mit " + locationCount + " Autos: " + comparedFitness + "km");
		sortArray(POP);
		System.out.println("\nBestes Individuum:" + POP.get(0).getRoute() +"\nmit "+ countUsedCars(POP.get(0).getRoute())+" genutzten Autos und einer Gesamtstrecke von: "+POP.get(0).getFitness()+"km");
		System.out.println(comparedFitness - POP.get(0).getFitness() + "km gespart");

		long endTime = System.currentTimeMillis();
		long totalTime = endTime - startTime;
		
		System.out.println("RunTime: " + (totalTime/1000) + "sec");
		launch(args);
	}

	private static void requestUserInput() {
		Scanner myObj = new Scanner(System.in);
		System.out.println(
				"Wieviele Studenten sollen an die Hochschule gebracht werden?\nWERT ZWISCHEN 1 UND 9999 EINGEBEN");
		locationCount = myObj.nextInt();
		System.out.println("Wieviele Generationen soll der Algorithmus durchlaufen?");
		runs = myObj.nextInt();
		System.out.println(
				"Welche Selektionsmethode soll gewählt werden?\n1)Fitnessproportionale Selektion\n2)Rangselektion\n3)Elitäre Selektion\n4)Wettkampfselektion"
						+ "\nWERT ZWISCHEN 1 UND 4 eingeben");
		selectionChoice = myObj.nextInt();
		System.out.println("Wieviele Individuen soll jede Population beinhalten?\nGERADEN WERT EINGEBEN");
		popSize = myObj.nextInt();
	}

	
	public static void generateKids() {
		for (int j = 0; j < PAR.size(); j++) {
			Individuum individuum = new Individuum();
			Individuum individuum2 = new Individuum();
			if (!(j % 2 == 0)) {
				individuum.setRoute(mutate(recombine(PAR.get(j).getRoute(), PAR.get(j - 1).getRoute())));
				KID.add(individuum);
				individuum2.setRoute(mutate(recombine(PAR.get(j - 1).getRoute(), PAR.get(j).getRoute())));
				KID.add(individuum2);
			}
		}
	}

	public static void generaterRandomPopulation(int students) {
		Individuum a;
		for (int i = 0; i < popSize * 2; i++) {
			a = new Individuum();
			a.generaterRandomRoute(students);
			POP.add(a);
		}
	}

	public static ArrayList<Integer> mutate(ArrayList<Integer> a) {
		int x = new Random().nextInt(locationCount * 4);
		int j = new Random().nextInt(locationCount * 4);
		if (x == j) {
			j = new Random().nextInt(locationCount * 4);
		}
		for (int i = 0; i < a.size(); i++) {
			if (i == x) {
				Collections.swap(a, i, j);
			}
		}
		return a;
	}

	public static void selektionFitnessproportional() {
		double fitness = 0;
		for (Individuum individuum : POP) {
			individuum.setFitness(1 / individuum.getFitness());
			fitness += individuum.getFitness();
		}
		rouletteWheelSelection(fitness);
	}

	public static void rouletteWheelSelection(double fitness) {
		for (int i = 0; i < POP.size(); i++) {
			POP.get(i).setFitness(POP.get(i).getFitness() / fitness);
			if (i > 0) {
				POP.get(i).setFitness(POP.get(i).getFitness() + POP.get(i - 1).getFitness());
			}
		}
		for (int i = 0; i < popSize; i++) {
			double random = Math.random();
			for (int j = 0; j < POP.size(); j++) {
				if (random <= POP.get(j).getFitness()) {
					if (PAR.contains(POP.get(j))) {
						random = Math.random();
						j = 0;
					}
					if (!PAR.contains(POP.get(j))) {
						PAR.add(POP.get(j));
						break;
					}
				}
			}
		}
	}

	public static void rankSelektion() {
		double fitness = 0;
		POP = sortArray(POP);
		Collections.reverse(POP);
		for (int j = 0; j < POP.size(); j++) {
			POP.get(j).setFitness(j + 1);
			fitness += POP.get(j).getFitness();
		}
		rouletteWheelSelection(fitness);
	}


	public static void selektionTournament() {
		Collections.shuffle(POP);
		for (int i = 0; i < POP.size(); i++) {
			if (!(i % 2 == 0)) {
				if (POP.get(i - 1).getFitness() <= POP.get(i).getFitness()) {
					PAR.add(POP.get(i - 1));
				}
				if (POP.get(i).getFitness() < POP.get(i - 1).getFitness()) {
					PAR.add(POP.get(i));
				}
			}
		}
	}

	public static void selektionElitaer() {
		POP = sortArray(POP);
		PAR = new ArrayList<Individuum>(POP.subList(0, popSize));
	}

	public static Double getBestFitnessOfPOP() {
		ArrayList<Individuum> sortedArray = new ArrayList<Individuum>();
		sortedArray = sortArray(POP);
		return sortedArray.get(0).getFitness();
	}

	public static ArrayList<Individuum> sortArray(ArrayList<Individuum> listToSort) {
		Individuum smaller;
		Individuum bigger;
		boolean run = true;
		for (int i = 0; i < listToSort.size() && run == true; i++) {
			run = false;
			for (int y = 0; y < listToSort.size() - 1; y++) {
				if (listToSort.get(y).getFitness() > listToSort.get(y + 1).getFitness()) {
					bigger = listToSort.get(y);
					smaller = listToSort.get(y + 1);
					listToSort.set(y, smaller);
					listToSort.set(y + 1, bigger);
					run = true;
				}
			}
		}
		return listToSort;
	}

	public static ArrayList<Integer> recombine(List<Integer> route1, List<Integer> route2) {
		int cut = new Random().nextInt(locationCount * 4 - 1);                              
		if (cut == 0) {
			cut = 1;
		}                                                                                 
		List<Integer> usedLocationPoints = new ArrayList<Integer>();
		int zeroCount = 0;
		ArrayList<Integer> newRoute = new ArrayList<Integer>();
		for (int i = 0; i < route1.size(); i++) {
			if (i < cut) {
				newRoute.add(route1.get(i));
				if (route1.get(i) == 0) {
					zeroCount++;
				}
				if (route1.get(i) != 0) {
					usedLocationPoints.add(route1.get(i));
				}
			} else {
				break;
			}
		}

		for (int i = 0; i < route2.size(); i++) {
			if (route2.get(i) == 0) {
				if (zeroCount < locationCount * 3) {
					zeroCount++;
					newRoute.add(route2.get(i));
				}
			}
			if (route2.get(i) != 0) {
				if (!usedLocationPoints.contains(route2.get(i))) {
					usedLocationPoints.add(route2.get(i));
					newRoute.add(route2.get(i));
				}
			}
		}
		return newRoute;
	}

	public static int countUsedCars(List<Integer> a) { 
		int usedCarCount = locationCount;
		for (int i = 0; i < a.size(); i += 4) {
			List<Integer> passengers = new ArrayList<Integer>();
			for (int j = 0; j < 4; j++) {
				if (a.get(i + j) != 0) {
					passengers.add(a.get(i + j));
				}
			}

			if (passengers.size() == 0) {
				usedCarCount--;
			}
		}
		return usedCarCount;
	}

	public static double calculateFitness(List<Integer> a) { 
		double distance = 0;
		List<Integer> passengers = new ArrayList<Integer>();
		for (int i = 0; i < a.size(); i += 4) {
			for (int j = 0; j < 4; j++) {
				if (a.get(i + j) != 0) {
					passengers.add(a.get(i + j));
				}
			}
			if (passengers.size() != 0) { 
				if (passengers.size() == 1) {
					distance += pythagoras(locations.get(passengers.get(0) - 1), destination);
				}
				if (passengers.size() > 1) {
					for (int g = 0; g < passengers.size(); g++) {
						if (g > 0) {
							distance += pythagoras(locations.get(passengers.get(g) - 1),
									locations.get(passengers.get(g - 1) - 1));
						}
						if (g == passengers.size() - 1) {
							distance += pythagoras(locations.get(passengers.get(g) - 1), destination);
						}
					}
				}
			}
			passengers.clear();
		}
		return distance;
	}

	public static double testFitness() {
		double distance = 0;
		for (int i = 0; i < locationCount; i++) {
			distance += pythagoras(locations.get(i), destination);
		}
		return distance;
	}

	private static double pythagoras(Punkt a, Punkt b) {
		double distanceX = a.x - b.x;
		double distanceY = a.y - b.y;
		return Math.sqrt(distanceX * distanceX + distanceY * distanceY);
	}

	public static ArrayList<Punkt> getLocationsFromCSV() {
		int counter = 0;
		try (BufferedReader br = new BufferedReader(new FileReader("src\\geneticAlgorithm\\locations.csv"))) {
			while ((line = br.readLine()) != null) {
				counter++;
				String[] values = line.split(",");
				if (counter == 1) {
					destination = new Punkt(Double.parseDouble(values[0]), Double.parseDouble(values[1]));
				} else {
					locations.add(new Punkt(Double.parseDouble(values[0]), Double.parseDouble(values[1])));
				}
				if (counter == locationCount + 1) {
					break;
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return locations;
	}

}
