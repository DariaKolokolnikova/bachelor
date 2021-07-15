package geneticAlgorithm;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CSVreader {
    public static List<Punkt> locations= new ArrayList<Punkt>();

	public static void main(String[] args) {
		String path="/IDP/src/geneticAlgorithm/locations.csv";
		String line="";
		int counter=0;
		
		try (BufferedReader br = new BufferedReader(new FileReader("src\\geneticAlgorithm\\locations.csv"))) {
		    while ((line = br.readLine()) != null) {
		        String[] values = line.split(",");
		        counter++;
		        locations.add(new Punkt(Double.parseDouble(values[0]),Double.parseDouble(values[1])));
		        if(counter==20) {
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
		for (Punkt punkt : locations) {
			System.out.println(punkt.x +" "+ punkt.y);
		}
		System.out.println(counter);
	}

}
