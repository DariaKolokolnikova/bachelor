package geneticAlgorithm;

import java.util.ArrayList;

import java.util.Arrays;
import java.util.Random;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.awt.Point;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;



public class HFTStreckenAlgorithmus {
	
//	 ArrayList<String> gfg = new ArrayList<String>(
//	            Arrays.asList("Geeks",
//	                          "for",
//	                          "Geeks"));

	 static public ArrayList<Integer> route =   new ArrayList<Integer>(Arrays.asList(1,2,3,4,0,0,0,0,0,0,0,0,0,0,0,0)); //vllt dafür noch funktion um nach locationpoints liste zu erstellen
	 static  int locationCount;
     public static ArrayList<Punkt> locations= new ArrayList<Punkt>();
     public static ArrayList<Individuum> POP = new ArrayList<Individuum>();
     public static ArrayList<Individuum> PARneu = new ArrayList<Individuum>();
     public static ArrayList<Individuum> KID = new ArrayList<Individuum>();
     public static ArrayList<Individuum> PAR = new ArrayList<Individuum>();
     static int popSize=4;
     static String path="/IDP/src/geneticAlgorithm/locations.csv";
	 static String line="";
//     static List<Double> POPdistancesUnsorted= new ArrayList<Double>();
//     static List<Double> POPdistancesSorted=new ArrayList<Double>();
//     static List<Double> KIDdistancesUnsorted= new ArrayList<Double>();
//     static List<Double> bestDistances= new ArrayList<Double>();
//     static List<Integer> KIDmatchingIndices = new ArrayList<>();
//     static List<Integer> POPmatchingIndices = new ArrayList<>();
     static Punkt destination;
     static int runs;
     
     	     public static ArrayList<Individuum> test = new ArrayList<Individuum>();
     	     public static ArrayList<Individuum> test2 = new ArrayList<Individuum>();

	public static void main(String[] args) {
//		for (int i = 0; i < 6; i++) {
//			test.add(new Individuum());
//			test.get(i).setFitness(i+1.1);
//		}
//		System.out.println("amk"+test);
//		test2=selektionTournament(test, null);
//		System.out.println("ausgewählte:"+test2);
//		
//        for (Individuum string : test) {
//	     System.out.println("SOLLTE 0.61, 0,748 sein"+string.getFitness());
//        }
		
		locationCount=4;
		runs=3;
		//List<Punkt> locations=generateRandomLocations(locationCount);
//		for (int i = 0; i < 5; i++) {
//			locations.add(new Punkt(i+1,i+1));
//		}
		locations=getLocationsFromCSV();
		
		System.out.println("HFT-Streckenpunkte:"+ " ");
		for(Punkt element : locations){ 
			System.out.println(element.x + " "+element.y);
		}
		
		
		generaterRandomPopulation(locationCount);
		
		//Fitness berechnen
		for (Individuum individuum : POP) {
			 individuum.setFitness(calculateFitness(individuum.getRoute()));
		}
		
		
		//neue populationen, selektion,recombination,mutation
	
		for (int i = 0; i < runs; i++) {
			for (Individuum individuum : POP) {
				 individuum.setFitness(calculateFitness(individuum.getRoute()));
			}
			PARneu= rankSelektion(POP, 4);
			System.out.println("DURCHGANG"+i+"  size"+PARneu.size()+PARneu);
			for (Individuum indiv : PARneu) {
				System.out.println("FITNESS:"+indiv.fitness);
			}
			
			for (int j = 0; j < PARneu.size(); j++) {
				Individuum individuum=new Individuum();
				if(! (j % 2==0)) {individuum.setRoute(mutate(recombine(PARneu.get(j).getRoute(), PARneu.get(j-1).getRoute())));
                KID.add(individuum);				//add newpop
                individuum.setRoute(mutate(recombine(PARneu.get(j-1).getRoute(), PARneu.get(j).getRoute())));
                KID.add(individuum);	}
			}
			for (Individuum individuum : KID) {
				 individuum.setFitness(calculateFitness(individuum.getRoute()));
			}
			for (Individuum individuum : PARneu) {
				 individuum.setFitness(calculateFitness(individuum.getRoute()));
			}
			for (Individuum individuum : KID) {
				System.out.println("alle kinder"+individuum);
			}
			for (Individuum individuum : PARneu) {
				System.out.println("alle eltern"+individuum);

			}
			POP.clear();
			POP.addAll(KID);
			POP.addAll(PARneu);
			PARneu.clear();
			//PARneu=PAR;
			KID.clear();
		}
		
	}
		

	public static void generaterRandomPopulation(int students) {	 
        Individuum a;
	    for (int i = 0; i < popSize*2; i++) {
	    	a=new Individuum();
			a.generaterRandomRoute(students);
			POP.add(a);
		}
	}
	                
		 

	
	

	
	public static ArrayList<Integer> mutate(ArrayList<Integer> a) {
		int x= new Random().nextInt(locationCount*4);
		int j= new Random().nextInt(locationCount*4);
		if(x==j) {
		 j= new Random().nextInt(locationCount*4);
		}
		for (int i = 0; i < a.size(); i++) {
		if(i==x) {
			Collections.swap(a, i, j);
		}
		}
		return a;
	}
	
	public static ArrayList<Individuum> rouletteWheelSelection(ArrayList<Individuum> parentList, int toBeSelected){
		double fitness=0;
		ArrayList<Individuum> newChildrenList=new ArrayList<Individuum>();
		//ArrayList<Individuum> cloneChildren = (ArrayList<Individuum>) childrenList.clone();
		//vllt arraylisz clonen und dann muss man nich nochmal neu fitnes berechnen
		for (Individuum individuum : parentList) {
			individuum.setFitness(1/individuum.getFitness());
			System.out.println("1/fitnes"+individuum.getFitness());
			fitness+=individuum.getFitness();
		}
		System.out.println("test"+fitness);
		newChildrenList = selektionFitnessproportional(parentList, fitness, toBeSelected);
		return newChildrenList;}
	
	
	public static ArrayList<Individuum> selektionFitnessproportional(ArrayList<Individuum> parentList, double fitness, int toBeSelected){
		
		ArrayList<Individuum> newChildrenList=new ArrayList<Individuum>();
		for (int i = 0; i < parentList.size(); i++) {
			parentList.get(i).setFitness(parentList.get(i).getFitness()/fitness);
			if(i>0) {
				parentList.get(i).setFitness(parentList.get(i).getFitness()+parentList.get(i-1).getFitness());
			}
		}

		        
		for (int i = 0; i < toBeSelected; i++) {
			int ahhhh=0;
			double random= Math.random();
			System.out.println("random"+random);
			for (int j = 0; j < parentList.size(); j++) {
				if(random<=parentList.get(j).getFitness()) {
					if(newChildrenList.contains(parentList.get(j))){
						random=Math.random();						System.out.println("jap ist schon drin"+random);
                       ahhhh++;
                       if(ahhhh==3) {break;}
						j=0;
					}
					if(!newChildrenList.contains(parentList.get(j))){
						newChildrenList.add(parentList.get(j));
						System.out.println("added gerade:"+parentList.get(j).getFitness());
						break;
					}}//this one was selected
					//damit unterschiedliche Individuen ausgewählt werden
				 //muss entweder dieses aus der childlist entfernt werden
				//oder immer geprüft werden, ob es schonmal selektiert wurde	
			}
		}
		return newChildrenList;
	}
	public static ArrayList<Individuum> rankSelektion(ArrayList<Individuum> parentList, int toBeSelected){
		double fitness=0;
		ArrayList<Individuum> newChildrenList=new ArrayList<Individuum>();
		
		//ArrayList<Individuum> cloneChildren = (ArrayList<Individuum>) childrenList.clone();
		//vllt arraylisz clonen und dann muss man nich nochmal neu fitnes berechnen
		parentList=sortArray(parentList);
		System.out.println("sortiert"+parentList);
		Collections.reverse(parentList);
for (int j = 0; j < parentList.size(); j++) {
	parentList.get(j).setFitness(j+1);   
	fitness+=parentList.get(j).getFitness();
}
//		parentList.stream().forEach((individuum) -> {
//		   individuum.setFitness(1);
//		});
for (Individuum individuum : parentList) {
	System.out.println("rank:"+individuum.getFitness());
}
System.out.println("added fitness"+fitness);
		newChildrenList = selektionFitnessproportional(parentList, fitness, toBeSelected);
		return newChildrenList;
	}
	
	//eig muss parentslist gerade sein, 
	public static ArrayList<Individuum> selektionTournament(ArrayList<Individuum> parentsList, ArrayList<Individuum> childrenList){
		 ArrayList<Individuum> indiListe = new ArrayList<>();
		 ArrayList<Individuum> newParents = new ArrayList<>();

	        if(parentsList!=null) {
	        indiListe.addAll(parentsList);}
	        if(childrenList!=null) {
	        indiListe.addAll(childrenList);}
		for (int i = 0; i < indiListe.size(); i++) {
			 if(! (i % 2==0)) {
				 if(indiListe.get(i-1).getFitness()<=indiListe.get(i).getFitness()) {
				 newParents.add(indiListe.get(i-1));}
				 if(indiListe.get(i).getFitness()<indiListe.get(i-1).getFitness()) {
					 newParents.add(indiListe.get(i));}
			 }
		}
		System.out.println("neue eltern"+newParents);
		return newParents;	
	}
	
	public static ArrayList<Individuum> selektionElitaer(ArrayList<Individuum> parentsList, ArrayList<Individuum> childrenList){
        ArrayList<Individuum> indiListe = new ArrayList<>();        //Verwende eine Liste um Parents und Children zusammen zu werfen
        if(parentsList!=null) {
        indiListe.addAll(parentsList);}
        if(childrenList!=null) {
        indiListe.addAll(childrenList);}
        //WÃ¤hle Anzahl Eltern beste Individuen aus und gib sie zurÃ¼ck als Array Liste.

        indiListe=sortArray(indiListe);
        
       indiListe= new ArrayList<Individuum>(indiListe.subList(0, popSize));
for ( Individuum d: indiListe) {
	System.out.println("SORTIERT:"+d.fitness);
}
       return indiListe;
    }

	public static ArrayList<Individuum> sortArray(ArrayList<Individuum> listToSort){
		Individuum smaller;
        Individuum bigger;
        boolean run = true;
        for (int i = 0; i < listToSort.size() && run == true; i++) {
            run = false;
            for (int y = 0; y < listToSort.size()-1; y++) {
                if(listToSort.get(y).getFitness()> listToSort.get(y + 1).getFitness()) {
                    bigger = listToSort.get(y);
                    smaller = listToSort.get(y+1);
                    listToSort.set(y,smaller);
                    listToSort.set(y+1,bigger);
                    run = true;
                }
            }
        }
		return listToSort;
	}
	
	//rankselektion: teil aus elitär nehmen(sort als eigene methode refactorn)
	//und dann teil aus roulettewhel nehmen mit forgefertigten fitnesswerten 4,3,2,1
	
	
	public static ArrayList<Integer> recombine(List<Integer>a, List<Integer> b) {
		

		int cut=new Random().nextInt(locationCount*4-1); //Schnittstelle nicht zuweit rechts für varietät
		if(cut==0) {cut=1;} //damit schnittstelle nich zuweit links
		System.out.println("CUT:"+cut);
		List<Integer> usedLocationPoints= new ArrayList<Integer>();
		int zeroCount=0;
		ArrayList<Integer> newA= new ArrayList<Integer>();
		
		
	for (int i = 0; i < a.size(); i++) { 
		if(i<cut) {
			newA.add(a.get(i));
			if(a.get(i)==0) {
				zeroCount++;
			}
			if(a.get(i)!=0) {
			usedLocationPoints.add(a.get(i));
			}
		}
		else{
			break;
		}
	}
	
	
	for (int i = 0; i < b.size(); i++) { 
			
			if(b.get(i)==0) {
				if(zeroCount<locationCount*3) {
				zeroCount++;
				newA.add(b.get(i));
				}
			}
			if(b.get(i)!=0) {
			//prüfen ob er in used vorhanden is
				if(!usedLocationPoints.contains(b.get(i))) {
					usedLocationPoints.add(b.get(i));
					newA.add(b.get(i));
					}
			}
	}	
	
	
	return newA;
}
	
	public static double calculateFitness(List<Integer> a) { //fitness
		double distance= 0;
		List<Integer> passengers= new ArrayList<Integer>();
		for (int i = 0; i < a.size(); i+=4) {
			for (int j = 0; j < 4; j++) {
				if(a.get(i+j)!=0) {
					passengers.add(a.get(i+j));
				}
			}
			
			
			if(passengers.size()!=0) { //wenn 0fahrer, dann gibts nix zu rechnen
				if(passengers.size()==1) {
					 //hier berechnung für fall dass 1er nur im auto (nur abstand von location zu hft rechnen)	 
					 distance+=pythagoras(destination, locations.get(passengers.get(0)-1));	
					 distance+=pythagoras(locations.get(passengers.get(0)-1), destination);	
				}
				
				if(passengers.size()>1) {
			
			for (int g = 0; g < passengers.size(); g++) {
					
					if(g>0) {
						 distance+= pythagoras(destination,locations.get(passengers.get(g)-1));
						 distance+=   pythagoras(locations.get(passengers.get(g)-1),locations.get(passengers.get(g-1)-1));
					}
					if(g==passengers.size()-1) {
						
						 distance+= pythagoras(locations.get(passengers.get(g)-1), destination);
					}
				}			
				}
				

				}
		passengers.clear();	
		
		}
		
		return distance; 
		}

    
	private static double pythagoras(Punkt a, Punkt b) {
		double distanceX = a.x - b.x;
		double distanceY = a.y - b.y;
		return Math.sqrt( distanceX * distanceX + distanceY * distanceY);
	}
	
	
	public static ArrayList<Punkt> getLocationsFromCSV(){
		int counter=0;
		try (BufferedReader br = new BufferedReader(new FileReader("src\\geneticAlgorithm\\locations.csv"))) {
		    while ((line = br.readLine()) != null) {
		    	counter++; 
		    	String[] values = line.split(",");
		    	if(counter==1) {
		    		destination=new Punkt(Double.parseDouble(values[0]),Double.parseDouble(values[1]));
		    	}
		    	else {
		        locations.add(new Punkt(Double.parseDouble(values[0]),Double.parseDouble(values[1])));}
		    	 if(counter==locationCount+1) {
			        	break;
			        }
		    }
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		for (Punkt punkt : locations) {
//			System.out.println(punkt.x +" "+ punkt.y);
//			
//		}
		return locations;
	}
 
}
