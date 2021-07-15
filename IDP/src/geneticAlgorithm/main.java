package geneticAlgorithm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.stream.IntStream;

public class main {
	
	
	
	
	
	
	
	
	
	
	
	
	 static List<Integer> a = Arrays.asList(3,4,1,2,6,3,3,0,0,0,0,0,7,3,4); //vllt dafür noch funktion um nach locationpoints liste zu erstellen
	 
	 public static ArrayList<Individuum> POP = new ArrayList<Individuum>();
	 
	 
	 
public static void main(String[] args) {
	
    Collections.shuffle(a);
for (Integer s : a) {
	System.out.println(s);
}
//	System.out.println("1 " + 1 % 2 );
//	System.out.println("2 " + 2% 2 );
//	System.out.println("2 " + 13% 2 );
//	 if(! (1332 % 2==0)) {
//		 System.out.println("sucess motherfucker");
//	 }
//	Individuum indi=new Individuum();
//	Individuum indi2=new Individuum();
//
//	indi.setFitness(3);
//	
//	POP.add(indi2);
//	POP.add(indi);
//	
//	
//	for (Individuum string : POP) {
//		string.setFitness(1/string.getFitness());
//	}
//	
//	
//
//	for (Individuum string : POP) {
//		System.out.println("hahahahha"+string);
//	}
//	a=a.subList(7, a.size()-1);
//	
//	System.out.println("man"+a);
//	
//	int students=2;
//	List<Integer> list = new ArrayList<Integer>(Collections.nCopies(students*4, 0));
//
//	for (int i = 0; i < students; i++) {
//		list.set(i, i+1);
//	}
//	Collections.shuffle(list);
//System.out.println(list);         //zufällige fahrroute generieren
//

	
	
	
//		ArrayList<Double> weightList = new ArrayList<>();
//		weightList.add(0.04);
//		weightList.add(0.99);
//		weightList.add(0.06);
//		weightList.add(0.99);
//		weightList.add(0.8);
//		weightList.add(0.4);
//		weightList.add(0.6);
//
//		for (int i = 0; i < 10000; i++) {
//			
//		
//		double maximalminus=Math.random()*0.1;
//		double maximalplus=Math.random()*0.1;
//		int mutationsStelle=   (int) (Math.random() * weightList.size());
//	    
//	    
//
//	    if(1-weightList.get(mutationsStelle)<0.1)
//	    {maximalplus=1-weightList.get(mutationsStelle);}
//	    if(weightList.get(mutationsStelle)<0.1)
//	    {maximalminus=weightList.get(mutationsStelle);}
//	   // double mutationsWert=  -0.1+Math.random() * 0.2;
//	    double mutationsWert=  -maximalminus+Math.random() * (maximalminus+maximalplus);
//	    weightList.set(mutationsStelle, weightList.get(mutationsStelle)+mutationsWert); ///////
//	    System.out.println("mutationswert "+weightList.get(mutationsStelle));
//		if(weightList.get(mutationsStelle)<0) {System.out.println("OH NOOOOOOOOOOOOOOOOOOOOOOOOOOOO");}
//		if(weightList.get(mutationsStelle)>1) {System.out.println("OH NOOOOOOOOOOOOOOOOOOOOOOOOOOOO");}
//
//		}
//		
//		
//		
//		List<Integer> list1 = new ArrayList<Integer>();
//		List<Integer> erqgebnis = new ArrayList<Integer>();
//list1.add(1);
//		 list1.add(2);
//        list1.add(3);
//        list1.add(4);
//		list1 = list1.subList(0, 2);
//		 System.out.println(list1);


		
//		 List<Integer> matchingIndices = new ArrayList<>();
//		 for (int i = 0; i < a.size(); i++) {
//		     int element = 4;
//
//		     if (a.get(i).equals(element)) {
//		         matchingIndices.add(i);
//		     }
//		 }
//
//		 // Print matches
//		// matchingIndices.forEach(System.out::println);
//		
//		
//		
//		
//		 List<List<Integer>> listOfLists = new ArrayList<List<Integer>>();
//	        List<Integer> list1 = new ArrayList<Integer>();
//	        List<Integer> ergebnis = new ArrayList<Integer>();
//
//	        list1.add(4);
//	        list1.add(3);
//	        
//	        
//	        //Collections.sort(list1);
//
//	        listOfLists.add(list1);
//	      
//	        List<Integer> anotherList = new ArrayList<Integer>();
//	 
//	        anotherList.add(4);
//	        anotherList.add(3);
//	        
//	  
//	        listOfLists.add(anotherList);
//	        System.out.println("sollte flase sein  "+anotherList.equals(list1) );
//	       
//	        
//	     
//	        
//	        //ZUERST PRÜFEN OB SCHON VORHANDEN! BOOLEAN TRUE SETZEN wenn vorhanden und dann nicht dazumachen
//	        System.out.println("list1"+list1+ "list2"+anotherList);
//	        for (int i = 0; i < listOfLists.size(); i++) {
//				
//	        	if(listOfLists.get(0).equals(listOfLists.get(i))) {
//					listOfLists.remove(0);
//				}
//	        /////
//	        	ergebnis=list1;
//	        			ergebnis.addAll(anotherList);
//
//	        	System.out.println("llalal"+ergebnis);
//			}
//	        
//	        
//	        
//	        System.out.println("ergebnis:");
//	        listOfLists.forEach(System.out::println);
		
	}
	
}
