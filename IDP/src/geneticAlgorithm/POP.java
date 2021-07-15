package geneticAlgorithm;

import java.util.ArrayList;
import java.util.List;

public class POP {
	public static ArrayList<Individuum> POP = new ArrayList<Individuum>();
	public  ArrayList<Punkt> locations = new ArrayList<Punkt>();
	 Punkt destination;
	public ArrayList<Individuum> getPOP() {
		return POP;
	}

	public void setRanks() {
		for (int i = 0; i < POP.size(); i++) {
			POP.get(i).setFitness(i+1);
		}
	}
	public void setPOP(ArrayList<Individuum> pOP) {
		POP.clear();
		POP.addAll(pOP);
	}

	public void generateRandomPopulation(int students,int popSize) {
		Individuum a;
		for (int i = 0; i < popSize * 2; i++) {
			a = new Individuum();
			a.generaterRandomRoute(students);
			POP.add(a);
		}
	}
	public  ArrayList<Punkt> getLocations() {
		return locations;
	}

	public  void setLocations(ArrayList<Punkt> locations) {
		this.locations = locations;
	}

	public  Punkt getDestination() {
		return destination;
	}

	public  void setDestination(Punkt destination) {
		this.destination = destination;
	}

	public void setFitness() { 
		for (Individuum individuum : POP) {
			individuum.setFitness(calculateFitness(individuum.getRoute()));
		}
	}
	
	public double calculateFitness(List<Integer> a) { // fitness
		double distance = 0;
		List<Integer> passengers = new ArrayList<Integer>();
		for (int i = 0; i < a.size(); i += 4) {
			for (int j = 0; j < 4; j++) {
				if (a.get(i + j) != 0) {
					passengers.add(a.get(i + j));
				}
			}

			if (passengers.size() != 0) { // wenn 0fahrer, dann gibts nix zu rechnen
				if (passengers.size() == 1) {
					// hier berechnung für fall dass 1er nur im auto (nur abstand von location zu
					// hft rechnen)
					distance += pythagoras(destination, locations.get(passengers.get(0) - 1));
					distance += pythagoras(locations.get(passengers.get(0) - 1), destination);
				}

				if (passengers.size() > 1) {

					for (int g = 0; g < passengers.size(); g++) {

						if (g > 0) {
							distance += pythagoras(destination, locations.get(passengers.get(g) - 1));
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
		return distance;}
	
	
	public double pythagoras(Punkt a, Punkt b) {
		double distanceX = a.x - b.x;
		double distanceY = a.y - b.y;
		return Math.sqrt(distanceX * distanceX + distanceY * distanceY);
	}

	
	
}
