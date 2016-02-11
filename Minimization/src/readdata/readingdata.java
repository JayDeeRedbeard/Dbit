package readdata;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.zip.GZIPInputStream;

public class readingdata {
	/** Dies ist ein Program zum Einlesen der Testdaten vom Fachgebiet DATE um die Minimierung der D-Bits vorzunehmen
	@author Jan Dennis Reimer		
	@version1.0
	@param gesucht 	
	@param string	
	@return		
	*/
	public static ArrayList<Boolean> saveRowDoubleDBits = new ArrayList<Boolean>();
	//public static String testfile = "1_0095009ns.behavior ";
	//public static String testfile = "0_4758ns.behavior";
	//public static String testfile = "Peter";
	//public static String testfile = "Testmuster";
	//public static String testfile = "Testmusterppt";
	//public static String testfile = "domColumn";
			
	public static void main (String [] args) throws IOException{
		//ArrayList<ArrayList<Boolean>> booleanList = pattern(0);
		
		//printpattern2D(booleanList);System.out.println();
		//print.arrayList.print2DTEST(testpatternOneData());
		//ArrayList<ArrayList<ArrayList<Boolean>>> booleanList1 = testpatternOneData();
		//System.out.println(booleanList1.get(3).size());
	}
	/** Gibt die Anzahl der Fehler zurueck
	@author Jan Dennis Reimer		
	@version1.0
	@param  		
	*/
	public static int numberOfFailures(String testfile) throws IOException {
		return (nextPattern(0, testfile)-6);
	}
	/**
	 * Gibt die Anzahl der Outputs fuer das erste Pattern zurueck.
	@param  testfile welches aktuelle testfile wird benutzt		
	*/
	public static int numberOfOutputs(String testfile) throws IOException{
		// Es reicht aus nur ein Pattern zu betrachten
		int firstpattern= nextPattern(0,testfile);
		int secondpattern= nextPattern(firstpattern+1, testfile);
		int numberOfOutputs= secondpattern-firstpattern-1;
		return numberOfOutputs;
	}
	/** NICHT IN GEBRAUCH 
	 * Gibt die Anzahl der Testmuster fuer die Datei zurueck.
	@author Jan Dennis Reimer		
	@version1.0
	@param  		
	*/
	public static int howmuchtestpattern(String testfile)throws IOException{
		int a = 0;
		int b = 0;
		while (nextPattern(a, testfile)!= 42352){
			//System.out.println(a);   // zum Testen der Funktion
			a=nextPattern(a, testfile)+1;
			b++;
		}
		return b-1;	
	}
	/** Gibt jeweils die Zeilennummer aus nach der das naechste Testmuster losgeht	
	@param row 	Welche Reihe befinden wir uns gerade.	
	@return		die Zeilennummer aus dem naechsten Testmuster
	*/
	public static int nextPattern(int row, String testfile) throws IOException{
		
		try{
			int i = 1;
			boolean temp=true;
			String b="";
			GZIPInputStream gzip = new GZIPInputStream(new FileInputStream(longData.testpfad + "/" + testfile));
			BufferedReader s = new BufferedReader(new InputStreamReader(gzip));
			while ((b = s.readLine()) != null){
				if(b.contains("Pattern") && row<= i && temp==true){
					System.out.println(i);
					temp=false;
					return i+1;
				}
				i++;
			}
			s.close();
			gzip.close();
		}
			catch (NoSuchElementException e){	
				return 42352; //Wichtig fuer die Funktion howmuchtestpattern()
			}
		catch (Exception e){	
			return 42352; //Wichtig fuer die Funktion howmuchtestpattern()
		}
		return 0;
		}
	/** NICHT IN GEBRAUCH
	 * gets an D-Bit in the String dbit with {f1,f2,f4,f3}|1
	returns an Boolean ArrayList with [1 1 1 1 0 0 0]	
	@version1.0
	@param String dbit			Hat die Form : {f1,f2,f4,f3}|1	
	@return ArrayList 			Welches Bit abgedeckt ist
	*/
	public static ArrayList<Boolean> dbitcoveragerow(String dbit, String testfile)throws IOException{
		//int max...Maximale Anzahl an Fehler
		int max=numberOfFailures(testfile);
		ArrayList<Boolean> booleanList = new ArrayList<Boolean>();
		for(int i=0; i<= max; i++){
			String x= ""+ i;
			if (dbit.contains("f"+x+",")||dbit.contains("f"+x+"}")) //Es gibt nur diese 2 Moeglichkeiten
				booleanList.add(true);
			else
				booleanList.add(false);
		}
		return booleanList;
	}
	/**NICHT IN GEBRAUCH 
	 * Gibt immer ein Testmuster zurueck in einer ArrayList	
	@version1.1
	@param int whichpattern 	in welchen Testmuster in der Datei befinden wir uns?	//Wird nun immer auf 0 gesetzt um immer ein 2D-ArrayList zu bekommen.
	@return						Gibt eine 2D-ArrayList zurueck die man dann spaeter verarbeiten kann.
	*/
	public static ArrayList<ArrayList<Boolean>> pattern (int whichpattern,String testfile) throws IOException{
		
		//int counter=0;
		ArrayList<ArrayList<Boolean>> pattern= new ArrayList<ArrayList<Boolean>>();
		ArrayList<Boolean> tmp1= new ArrayList<Boolean>();
		String b = "";
		Scanner s = new Scanner(new File(testfile));
		int i= 1;						
		int limit=nextPattern(whichpattern, testfile); 														//Sonst kommt es zur erheblichen Verschlechterungen der Laufzeit!
		while (s.hasNextLine()){									
			Scanner tmp= new Scanner(s.nextLine());
			b=tmp.nextLine(); 																		//Zwischenspeicherung der aktuellen Zeile
			//System.out.println(b.contains("{f")+" "+ b);
			if (i>limit){
				//System.out.print(" Counter= "+counter+" ");
				if(b.contains("{f")){		
					tmp1= dbitcoveragerow(b, testfile);
					pattern.add(tmp1);
					saveRowDoubleDBits.add(false);
				}
				else{
					saveRowDoubleDBits.add(true);
				}
			//	counter++;
			}
			i++;
			tmp.close();
		}
		s.close();
		return pattern;
	}
	/** 
	 * NICHT IN GEBRAUCH 
	 * Gebe ein Testmuster zurueck in einer 2D-boolschen ArrayList	
	//Es wird nun immer nur noch ein pattern geben. die 3. Dimension wird weg gelassen.
	@param -
	@return			Um alle Testmuster abzudecken, wird fuer jedes Testmuster eine 2D-ArrayList erstellt 
					
	*/
	public static  ArrayList<ArrayList<Boolean>> testpatternOneData(String testfile)throws IOException{
		return pattern(0, testfile);
	}
	
	
}