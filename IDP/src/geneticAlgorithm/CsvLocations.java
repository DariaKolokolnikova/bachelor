package geneticAlgorithm;

 import java.io.File;
 import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
 public class CsvLocations {

 public static void main(String[] args) {

 try {
   PrintWriter pw= new PrintWriter(new File("C:\\Users\\daria\\eclipse-workspace\\IDP\\src\\geneticAlgorithm\\locations.csv"));
   StringBuilder sb=new StringBuilder();
   for (int i = 0; i < 10000; i++) {
	double x= Math.random()*15;
	double y= Math.random()*15;
	sb.append(String.valueOf(x));
	sb.append(",");
	sb.append(String.valueOf(y));
	sb.append("\r\n");
}
   
  pw.write(sb.toString());
  pw.close();
   } catch (Exception e) {
	   System.out.println(e.getMessage().toString());
   }
}
}
