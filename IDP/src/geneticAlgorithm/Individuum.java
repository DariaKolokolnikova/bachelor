package geneticAlgorithm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
     

public class Individuum{  
	public  ArrayList<Integer> route;

	public double fitness = 0;
	
    public ArrayList<Integer> getRoute() {
		return route;
	}
	public void setRoute(ArrayList<Integer> route) {
		this.route = route;
	}
	public double getFitness() {
		return fitness;
	}
	public String toString() {
		return "Individuum [route=" + route + ", fitness=" + fitness + "]";
	}
	public void setFitness(double fitness) {
		this.fitness = fitness;
	}

	public void generaterRandomRoute(int students) {
		ArrayList<Integer> list = new ArrayList<Integer>(Collections.nCopies(students*4, 0));
		for (int i = 0; i < students; i++) {
			list.set(i, i+1);
		}
		Collections.shuffle(list);
		this.route=list;
	}
}
